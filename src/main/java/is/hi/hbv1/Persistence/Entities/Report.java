package is.hi.hbv1.Persistence.Entities;

import java.time.LocalDate;

public class Report {
    public enum ReportTitle {
        TRAFFICLIGHTS,
        GARBAGECAN,
        STREETLIGHTS,
        ROADWORK,
        OTHER
    }
    private long userID;
    private long reportID;

    private ReportTitle reportTitle;
    private String reportSubject;
    private double[] reportLocation;
    private String[] reportImages;
    private LocalDate reportDate;

    public Report() {
    }

    public Report(ReportTitle reportTitle, String reportSubject, double[] reportLocation, String[] reportImages, LocalDate reportDate) {
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

    public void setReportTitle(ReportTitle reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportSubject() {
        return reportSubject;
    }

    public void setReportSubject(String reportSubject) {
        this.reportSubject = reportSubject;
    }

    public double[] getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(double[] reportLocation) {
        this.reportLocation = reportLocation;
    }

    public String[] getReportImages() {
        return reportImages;
    }

    public void setReportImages(String[] reportImages) {
        this.reportImages = reportImages;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
