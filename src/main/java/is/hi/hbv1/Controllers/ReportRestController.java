package is.hi.hbv1.Controllers;

import is.hi.hbv1.FileHelper;
import is.hi.hbv1.Persistence.Entities.Email;
import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.ReportTitle;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Persistence.Repositories.ReportRepository;
import is.hi.hbv1.Services.ReportService;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportRestController {
    private ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/getAllReports")
    public List<Report> getAllReports() throws InterruptedException {
        // Call a method in a Service Class
        List<Report> allReports = reportService.findAll();
        // Add some data to the Model
        //model.addAttribute("reports", allReports);
        return allReports;
    }

    @RequestMapping("/getAllReportsByUserID")
    public List<Report> getAllReportsByUserID(long userID) throws InterruptedException {
        // Call a method in a Service Class
        List<Report> allReportsByUserID = reportService.findReportsByUserID(userID);
        return allReportsByUserID;
    }

    @RequestMapping("/getAllReportTitles")
    public List<String> getAllReportTitles() throws InterruptedException {
        // Call a method in a Service Class
//        List<String> allReportTitles = reportService.findAll();
        List<String> allReportTitles = new ArrayList<String>();
        String temp_title = "";
        for (ReportTitle r_title: ReportTitle.values()) {
            temp_title = r_title.getDisplayName();
            allReportTitles.add(temp_title);
        }
        // Add some data to the Model
        //model.addAttribute("reports", allReports);
        return allReportTitles;
    }
//    @RequestMapping("/getAllReportTitles")
//    public ArrayList<ReportTitle> getAllReportTitles() throws InterruptedException {
//        // Call a method in a Service Class
////        List<String> allReportTitles = reportService.findAll();
////        List<String> allReportTitles = new ArrayList<String>();
//        ArrayList<ReportTitle> reportTitles= new ArrayList<>();
//        ReportTitle temp_title;
//        System.out.println("Ingimar testing here... adding reportTitles to a list");
//        for (ReportTitle r_title: ReportTitle.values()) {
//            temp_title = r_title;
//            reportTitles.add(temp_title);
//            System.out.println(temp_title.getDisplayName());
//        }
//        // Add some data to the Model
//        //model.addAttribute("reports", allReports);
//        return reportTitles;
//    }
    /*
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

        if(filename.hashCode() != 0) {
            report.setReportImages(filename);
            Report savedReport = reportService.save(report);
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

        //model.addAttribute("reportTitle", report.getReportTitle());
        return "confirmation";
    }

    @RequestMapping(value = "/getMap", method = RequestMethod.GET)
    public String mapGET() {
        return "map";
    }*/
}
