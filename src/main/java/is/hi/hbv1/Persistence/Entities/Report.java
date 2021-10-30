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
    /* Ingimar commented out 23.okt
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY

     */
    private long userID;

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportID;





    @NotNull
    private ReportTitle reportTitle;
    private String reportSubject = null;
    @ElementCollection
    private List<Double> reportLocation = new ArrayList<>();
    @ElementCollection
    private List<String> reportImages = new ArrayList<>();
    //@NotNull
    private LocalDate reportDate;

    public Report() {
    }

    public Report(long userID, ReportTitle reportTitle, String reportSubject,
                  List<Double> reportLocation, List<String> reportImages,
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

    public List<String> getReportImages() {
        return reportImages;
    }

    public void setReportImages(List<String> reportImages) {
        this.reportImages = reportImages;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
