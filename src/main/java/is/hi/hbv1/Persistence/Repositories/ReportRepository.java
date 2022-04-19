package is.hi.hbv1.Persistence.Repositories;

import is.hi.hbv1.Persistence.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAll();

    //List<Report> findAllByUserID(long userID);

    List<Report> findReportsByUserID(long userID);

    Report save(Report report);

    void delete(Report report);

    Report findByReportID(long reportID);
}
