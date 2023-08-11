public class Report {

    int profits;
    int expenses;

    Report() {
        profits = 0;
        expenses = 0;
    }

    boolean Compare( Report aReport ) {
        return Compare( this, aReport );
    }
    static boolean Compare( Report aReportLeft, Report aReportRight ) {
        boolean result = ( aReportLeft.profits == aReportRight.profits );
        result = result && ( aReportLeft.expenses == aReportRight.expenses );

        return result;
    }
}
