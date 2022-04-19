package is.hi.hbv1.Services.Implementation;

import is.hi.hbv1.Persistence.Entities.Report;
// TODO uncomment this when enum works
//import is.hi.hbv1.Persistence.Entities.Report.ReportTitle;
import is.hi.hbv1.Persistence.Repositories.ReportRepository;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportServiceImplementation implements ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImplementation(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public void delete(Report report) {
        reportRepository.delete(report);
    }

    @Override
    public Report findByReportID(long reportID) {
        return reportRepository.findByReportID(reportID);
    }
}
