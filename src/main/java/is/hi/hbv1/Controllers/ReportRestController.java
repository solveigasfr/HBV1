package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.ReportTitle;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ReportRestController {
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private ReportService reportService;
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_LOC_LAT = "location_latitude";
    private static final String KEY_LOC_LONG = "location_longitude";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DATE = "date";
    private static final String KEY_REPORT_ID = "report_id";

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
    @Transactional
    @RequestMapping("getReport/{reportIdString}")
    public HashMap<String, String> getReport(@PathVariable(value = "reportIdString") String reportIdString) throws InterruptedException {
        System.out.println("I am here");
        long tempReportId = Long.parseLong(reportIdString);
        Report report = reportService.findByReportID(tempReportId);
        HashMap<String, String> reportMap = new HashMap<String, String>();
        long tempUserIdLong = report.getUserID();
        String tempTitle = report.getReportTitleAsString();
        String tempReportSubject = report.getReportSubject();
        List<Double> tempLocation = report.getReportLocation();
        String tempImg = report.getReportImages();
        LocalDate tempDate = report.getReportDate();
        String tempUserId = Long.toString(tempUserIdLong);
        Double tempLatD = tempLocation.get(0);
        Double tempLongD = tempLocation.get(1);
        String tempLat = Double.toString(tempLatD);
        String tempLong = Double.toString(tempLongD);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        String tempDateStr = tempDate.format(dtf);

        reportMap.put(KEY_USER_ID, tempUserId);
        reportMap.put(KEY_TITLE, tempTitle);
        reportMap.put(KEY_SUBJECT, tempReportSubject);
        reportMap.put(KEY_LOC_LAT, tempLat);
        reportMap.put(KEY_LOC_LONG, tempLong);
        reportMap.put(KEY_IMAGE, tempImg);
        reportMap.put(KEY_DATE, tempDateStr);

        if(report != null) {
            return reportMap;
        }
        return null;
    }

    /*
    @RequestMapping("/getAllReportsByUserID/{id}")
    public List<Report> getAllReportsByUserID(@PathVariable("id") String userIDString) throws InterruptedException {
        System.out.println();
        System.out.println("getAllReportsByUserID started");
        long userID = Long.parseLong(userIDString);
        System.out.println("Calling reportService.findReportsByUserID with userID: " + userID);
        // Call a method in a Service Class
        List<Report> allReportsByUserID = reportService.findReportsByUserID(userID);

        if (allReportsByUserID != null) {
            System.out.println("printing subjects og reports that we will send");
            for (int i = 0; i < allReportsByUserID.size(); i++) {
                System.out.println("Subject of this one is subject is " + allReportsByUserID.get(i).getReportSubject());
            }
        }
        return allReportsByUserID;
    }
     */

    @RequestMapping("/getAllReportsByUserID/{id}")
    public ArrayList<HashMap<String,String>> getAllReportsByUserID(@PathVariable("id") String userIDString) throws InterruptedException {
        System.out.println();
        System.out.println("getAllReportsByUserID started");
        long userID = Long.parseLong(userIDString);
        System.out.println("Calling reportService.findReportsByUserID with userID: " + userID);
        // Call a method in a Service Class
        List<Report> allReportsByUserID = reportService.findReportsByUserID(userID);

        ArrayList<HashMap<String,String>> listOfReportsAsHashMaps = new ArrayList<>();

        if (allReportsByUserID != null) {
            Report r;
            String tempUserID = "";
            String tempTitle = "";
            String tempSubj = "";
            String tempLocationLat = "";
            String tempLocationLong = "";
            String tempImg = "";
            String tempDate = "";
            String tempReportID = "";

            LocalDate localdate;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

            System.out.println("printing subjects and reports that we will send");
            for (int i = 0; i < allReportsByUserID.size(); i++) {
                HashMap<String, String> reportMap = new HashMap<String, String>();
                r = allReportsByUserID.get(i);
                tempUserID = String.valueOf(r.getUserID());
                tempTitle = r.getReportTitleAsString();
                try {
                    tempSubj = r.getReportSubject();
                    System.out.println("Subject of this one is " + tempSubj);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    tempLocationLat = String.valueOf(r.getReportLocationLatitude());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    tempLocationLong = String.valueOf(r.getReportLocationLongitude());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    tempImg = r.getReportImages();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    localdate = r.getReportDate();
                    tempDate = localdate.format(dtf);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tempReportID = String.valueOf(r.getReportID());

                reportMap.put(KEY_USER_ID, tempUserID);
                reportMap.put(KEY_TITLE, tempTitle);
                reportMap.put(KEY_SUBJECT, tempSubj);
                reportMap.put(KEY_LOC_LAT, tempLocationLat);
                reportMap.put(KEY_LOC_LONG, tempLocationLong);
                reportMap.put(KEY_IMAGE, tempImg);
                reportMap.put(KEY_DATE, tempDate);
                reportMap.put(KEY_REPORT_ID, tempReportID);

                listOfReportsAsHashMaps.add(reportMap);
            }
        }
        return listOfReportsAsHashMaps;
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

    @PostMapping(value = "/saveReport")
    public Boolean saveReport(@RequestParam Map<String,String> reportMap) {
        String userID = reportMap.get("ID");
        String newReportTitle = reportMap.get("reportTitle");
        String reportSubject = reportMap.get("reportSubject");
        String reportLat = reportMap.get("reportLat");
        String reportLong = reportMap.get("reportLong");
        String reportImg = reportMap.get("reportImg");
        try {
            Long saveThisUserID = Long.parseLong(userID);
            ReportTitle reportTitleNew = ReportTitle.fromString(newReportTitle);
            System.out.println("printing reporto titelos");
            System.out.println(reportTitleNew);
            List<Double> listD = new ArrayList<>();
            Double lat = Double.parseDouble(reportLat);
            Double newReportLong = Double.parseDouble(reportLong);
            listD.add(lat);
            listD.add(newReportLong);
            LocalDate date = LocalDate.now();
            Report newReport = new Report(saveThisUserID, reportTitleNew, reportSubject, listD, reportImg, date);
            reportService.save(newReport);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    private byte[] imageDecoder(String image) {
        byte[] decodedImage = base64Decoder.decode(image);
        System.out.println("Decoded password is: " + decodedImage);
        return decodedImage;
    }*/
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
