package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.Report;

import java.util.List;

public interface ReportService {
    List<Report> findAll();

    List<Report> findReportsByUserID(long userID);

    Report save(Report report);

    void delete(Report report);

    Report findByReportID(long reportID);
}
