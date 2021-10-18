package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.Report;

import java.util.List;

public interface ReportService {
    // bætti við findAll() til að sækja dummy reports til birtingar
    List<Report> findAll();
    Report save(Report report);
    void sendToUser(Report report, String userEmail);
    void sendToRecipient(Report report);
}
