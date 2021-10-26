package is.hi.hbv1.Services.Implementation;

import is.hi.hbv1.Persistence.Entities.Report;
// TODO uncomment this when enum works
//import is.hi.hbv1.Persistence.Entities.Report.ReportTitle;
import is.hi.hbv1.Persistence.Repositories.ReportRepository;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportServiceImplementation implements ReportService {
    /* Ingimar commented out 23.okt
    private List<Report> reportRepository = new ArrayList<>();
    private int id_counter = 0;
     */
    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImplementation(ReportRepository reportRepository) {

        // Create three random reports for our dummy repository. To be removed when JPA added.
        /* Ingimar commented out 23.okt
        reportRepository.add(new Report(
                ReportTitle.GARBAGECAN,
                "Subject 1",
                new ArrayList<>(Arrays.asList(2.39,3.95)),
                new ArrayList<>(Arrays.asList("image1.png", "image2.png")),
                LocalDate.of(2021,5,13)));
        reportRepository.add(new Report(
                ReportTitle.STREETLIGHTS,
                "Subject 2",
                new ArrayList<>(Arrays.asList(2.39,3.95)),
                new ArrayList<>(Arrays.asList("image50.png", "image51.png")),
                LocalDate.of(2021,10,18)));
        reportRepository.add(new Report(
                ReportTitle.ROADWORK,
                "Subject 3",
                new ArrayList<>(Arrays.asList(2.39,3.95)),
                new ArrayList<>(Arrays.asList("image99.png", "image100.png")),
                LocalDate.of(2021,4,27)));
        // JPA gives each report and ID, but there we add them manually

        for(Report r: reportRepository) {
            r.setReportID(id_counter);
            id_counter++;
        }
         */
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public Report save(Report report) {
        /* Ingimar commented out 23.okt
        report.setReportID(id_counter);
        id_counter++;
        reportRepository.add(report);
        return report;
         */
        return reportRepository.save(report);
    }

    @Override
    public void delete(Report report){
        reportRepository.delete(report);
    }

    /* Ingimar commented out 23.okt
    @Override
    public List<Report> findAllByUserID(Long userID){
        return reportRepository.findAllByUserID(userID);
    }
     */

    @Override
    public Report findByReportID(Long reportID){
        return reportRepository.findByReportID(reportID);
    }

    /* Ingimar commented out 23.okt
    @Override
    public void sendToUser(Report report, String userEmail) {
    }

    @Override
    public void sendToRecipient(Report report) {
    }
     */
}
