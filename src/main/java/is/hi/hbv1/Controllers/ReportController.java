package is.hi.hbv1.Controllers;

import is.hi.hbv1.FileHelper;
import is.hi.hbv1.Persistence.Entities.Email;
import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController implements Serializable {
    private ReportService reportService;


    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/getData")
    public String homePage(Model model) {
        // Call a method in a Service Class
        List<Report> allReports = reportService.findAll();
        // Add some data to the Model
        model.addAttribute("reports", allReports);
        return "reportData";
    }

    @RequestMapping(value = "/createReport", method = RequestMethod.GET)
    public String newReportGET(Report report, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser != null) {
            model.addAttribute("report", report);
            return "newReport";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/createReport", method = RequestMethod.POST)
    public String newReportPOST(Report report, BindingResult result, Model model, HttpSession session, @RequestParam("image") MultipartFile multipartFile) throws MessagingException {
        if (result.hasErrors()) {
            return "newReport";
        }

        model.addAttribute("report", report);
        report.setReportDate(LocalDate.now());
        User sessionUser = (User) session.getAttribute("loggedInUser");
        report.setUserID(sessionUser.getUserID());
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String tempImage = "";


        Report savedReport = reportService.save(report);

        if(filename.hashCode() != 0) {
            report.setReportImages(filename);
            String uploadDir = "uploads/reportImages/" + savedReport.getReportID();

            try {
                FileHelper.saveFile(uploadDir, filename, multipartFile);
            } catch (IOException exception) {
                // TODO pass some error to newReport that saving image failed
                return "/newReport";
            }
            tempImage = report.getReportImagesPath();
        }

        String tempEmail = sessionUser.getUserEmail();
        String tempTitle = report.getReportTitleAsString();
        String tempSubject = report.getReportSubject();
        String tempLocation = report.getReportLocation().toString();
        Email.sendEmail(tempEmail, tempTitle, tempSubject, tempLocation, tempImage, sessionUser.getUserName());
        return "confirmation";
    }

    @RequestMapping(value = "/getMap", method = RequestMethod.GET)
    public String mapGET() {
        return "map";
    }
}
