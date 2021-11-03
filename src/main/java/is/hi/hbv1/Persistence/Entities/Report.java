package is.hi.hbv1.Persistence.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report implements Serializable {

    private long userID;

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportID;

    /*
    TODO
        -Get the Report to save more stuff than reportSubject
            -Find a way to get reportID and userID from a saved report
            -Find a way to save reportTitle to the report
        -Later: reportImages, reportLocation
     */

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

    /*
    public Report(ReportTitle reportTitle, String reportSubject, LocalDate reportDate) {
        this.reportTitle = reportTitle;
        this.reportSubject = reportSubject;
        this.reportDate = reportDate;
    }
     */

    public boolean isSelected(ReportTitle selectedReportTitle){
        if (selectedReportTitle != null) {
            return selectedReportTitle.equals(reportTitle);
        }
        return false;
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

    public String getReportTitleAsString(){
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
}
