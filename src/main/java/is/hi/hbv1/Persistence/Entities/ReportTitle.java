package is.hi.hbv1.Persistence.Entities;

import javax.lang.model.element.Element;
import java.util.stream.Stream;

/* Report titles are implemented as a seperate enum class because
   each report can only take one of the predefined reportTitle values.
   Using enums will ease the implementation of further categories later on.
 */

public enum ReportTitle {
    TRAFFICLIGHTS("Faulty traffic lights"),
    GARBAGECAN("Full or faulty garbage can"),
    STREETLIGHTS("Faulty street lights"),
    ROADWORK("Potholes or other road damages"),
    OTHER("Other");

    private final String displayName;

    //private String theReportTitle;
    ReportTitle(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /*ReportTitle(String theReportTitle) {
        this.theReportTitle = theReportTitle;
    }*/
    public static Stream<ReportTitle> stream() {
        return Stream.of(ReportTitle.values());
    }



    public static ReportTitle fromString(String displayName) {
        for (ReportTitle b : ReportTitle.values()) {
            if (b.getDisplayName().equalsIgnoreCase(displayName)) {
                return b;
            }
        }
        return null;
    }

}
