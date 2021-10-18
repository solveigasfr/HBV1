package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class HomeController {
    private ReportService reportService;

    @Autowired
    public HomeController(ReportService reportService) {
        this.reportService = reportService;
    }


    @RequestMapping("/")
    public String homePage(Model model) {
        // Call a method in a Service Class
        List<Report> allReports = reportService.findAll();
        // Add some data to the Model
        model.addAttribute("reports", allReports);
        return "home";
    }
/*
    @RequestMapping(value = "/addreport", method = RequestMethod.GET)
    public String addReportForm(Report report) {
        return "newReport";
    }
    */
}
