package is.hi.hbv1.Persistence.Repositories;

import is.hi.hbv1.Persistence.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAll();

    Report save(Report report);

    void delete(Report report);

    Report findByReportID(long reportID);
}
