package is.hi.hbv1.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import is.hi.hbv1.Controllers.ReportController;
import is.hi.hbv1.Persistence.Repositories.ReportRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
// kommenta hér hvað serializable gerir
@JsonIgnoreProperties({"reportLocation"})

public class Report implements Serializable {

    private long userID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportID;

    @NotNull
    private ReportTitle reportTitle;
    private String reportSubject = null;

    @ElementCollection
    private List<Double> reportLocation = new ArrayList<>();

    @Column(nullable = true, length = 64)
    private String reportImages;

    //@NotNull

    private LocalDate reportDate;

    public Report() {
    }

    public Report(long userID, ReportTitle reportTitle, String reportSubject,
                  List<Double> reportLocation, String reportImages,
                  LocalDate reportDate) {
        this.userID = userID;
        this.reportTitle = reportTitle;
        this.reportSubject = reportSubject;
        this.reportLocation = reportLocation;
        this.reportImages = reportImages;
        this.reportDate = reportDate;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getReportID() {
        return reportID;
    }

    public void setReportID(long reportID) {
        this.reportID = reportID;
    }

    public ReportTitle getReportTitle() {
        return reportTitle;
    }

    public String getReportTitleAsString() {
        return reportTitle.getDisplayName();
    }

    public void setReportTitle(ReportTitle reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportSubject() {
        return reportSubject;
    }

    public void setReportSubject(String reportSubject) {
        this.reportSubject = reportSubject;
    }

    public List<Double> getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(List<Double> reportLocation) {
        this.reportLocation = reportLocation;
    }

    public String getReportImages() {
        return reportImages;
    }

    public void setReportImages(String reportImages) {
        this.reportImages = reportImages;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    @Transient
    public String getReportImagesPath() {
        return "uploads/reportImages/" + this.reportID + "/" + this.reportImages;
    }
}
