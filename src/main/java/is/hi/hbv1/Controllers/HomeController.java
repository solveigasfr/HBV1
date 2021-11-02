package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.ReportTitle;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    private ReportService reportService;

    @Autowired
    public HomeController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping("/home")
    public String homePage(Model model) {
        // Call a method in a Service Class
        List<Report> allReports = reportService.findAll();
        // Add some data to the Model
        model.addAttribute("reports", allReports);
        return "home";
    }

    @RequestMapping(value = "/newReport", method = RequestMethod.GET)
    public String newReportGET(Report report, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if(sessionUser  != null){
            model.addAttribute("report", report);
            return "/newReport";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/newReport", method = RequestMethod.POST)
    public String newReportPOST(Report report, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/newReport";
        }
        model.addAttribute("report", report);
        report.setReportDate(LocalDate.now());

        reportService.save(report);
        //model.addAttribute("reportTitle", report.getReportTitle());
        return "confirmation";
    }
}
