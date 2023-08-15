
public class Report {
    int expenses;
    int profits;

    boolean compare( Report aReport ) {
        return compare( this, aReport );
    }
    static boolean compare( Report aReportLeft, Report aReportRight ) {
        boolean result = ( aReportLeft.profits == aReportRight.profits );
        result = result && ( aReportLeft.expenses == aReportRight.expenses );

        return result;
    }
}
