package is.hi.hbv1.Persistence.Repositories;

import is.hi.hbv1.Persistence.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAll();
    Report save(Report report);
    void delete(Report report);

    //List<Report> findAllByUserID(Long userID); Ingimar commented out 23.okt
    Report findByReportID(Long reportID);

    /* Ingimar commented out 23.okt
    void sendToUser(Report report, String userEmail);
    void sendToRecipient(Report report);

     */

}
