package is.hi.hbv1.Persistence.Entities;

public enum ReportTitle {
    TRAFFICLIGHTS("Faulty traffic lights"),
    GARBAGECAN("Full or faulty garbage can"),
    STREETLIGHTS("Faulty street lights"),
    ROADWORK("Potholes or other road damages"),
    OTHER("Other");

    private final String displayName;

    ReportTitle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
