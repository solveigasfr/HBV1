package is.hi.hbv1.Services.Implementation;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.Report.ReportTitle;
import is.hi.hbv1.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImplementation implements ReportService {
    private List<Report> reportRepository = new ArrayList<>();
    private int id_counter = 0;

    @Autowired
    public ReportServiceImplementation() {
        // Create three random reports for our dummy repository. To be removed when JPA added.
        reportRepository.add(new Report(
                ReportTitle.GARBAGECAN,
                "Subject 1",
                new double[]{1.234},
                new String[]{"imgUrl1"},
                LocalDate.of(2021,5,13)));
        reportRepository.add(new Report(
                ReportTitle.STREETLIGHTS,
                "Subject 2",
                new double[]{12.34},
                new String[]{"imgUrl2"},
                LocalDate.of(2021,10,18)));
        reportRepository.add(new Report(
                ReportTitle.ROADWORK,
                "Subject 3",
                new double[]{123.4},
                new String[]{"imgUrl3"},
                LocalDate.of(2021,4,27)));
        // JPA gives each report and ID, but there we add them manually

        for(Report r: reportRepository) {
            r.setReportID(id_counter);
            id_counter++;
        }
    }

    @Override
    public List<Report> findAll() {
        return reportRepository;
    }

    @Override
    public Report save(Report report) {
        report.setReportID(id_counter);
        id_counter++;
        reportRepository.add(report);
        return report;
    }

    @Override
    public void sendToUser(Report report, String userEmail) {
    }

    @Override
    public void sendToRecipient(Report report) {
    }
}
