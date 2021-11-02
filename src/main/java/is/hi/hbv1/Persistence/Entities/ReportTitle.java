package is.hi.hbv1.Persistence.Entities;

import javax.lang.model.element.Element;

public enum ReportTitle {
    TRAFFICLIGHTS("Faulty traffic lights"),
    GARBAGECAN("Full or faulty garbage can"),
    STREETLIGHTS("Faulty street lights"),
    ROADWORK("Potholes or other road damages"),
    OTHER("Other");

    private final String displayName;


    ReportTitle(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
