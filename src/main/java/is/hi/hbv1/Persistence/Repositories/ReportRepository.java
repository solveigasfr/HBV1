package is.hi.hbv1.Persistence.Repositories;

import is.hi.hbv1.Persistence.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report save(Report report);
}
