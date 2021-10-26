package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.Report;

import java.util.List;

public interface ReportService {
    // bætti við findAll() til að sækja dummy reports til birtingar
    List<Report> findAll();
    Report save(Report report);
    void delete(Report report);
    //List<Report> findAllByUserID(Long userID);  Ingimar commented out 23.okt
    Report findByReportID(Long reportID);
    /* Ingimar commented out 23.okt
    void sendToUser(Report report, String userEmail);
    void sendToRecipient(Report report);

     */
}
