
public class Report {
    int expenses;
    int profits;

    boolean Compare( Report aReport ) {
        return Compare( this, aReport );
    }
    static boolean Compare( Report aReportLeft, Report aReportRight ) {
        boolean result = ( aReportLeft.profits == aReportRight.profits );
        result = result && ( aReportLeft.expenses == aReportRight.expenses );

        return result;
    }
}
