import java.util.Scanner;

public class ReportEngine {
    Scanner scanner;

    int currentYear;
    MonthlyReport[] monthlyReports;
    YearlyReport yearlyReport;

    ReportEngine(Scanner aScanner) {
        currentYear = Constants.year;
        scanner = aScanner;
    }

    void loadMonthlyReports() {
        monthlyReports = new MonthlyReport[Constants.months];
        for (int i = 0; i < monthlyReports.length; i++) {
            String filename = MonthlyReport.getFilename(i, currentYear);
            monthlyReports[i] = MonthlyReport.loadFromFile(filename);
            if (monthlyReports[i] != null) {
                System.out.println("Загружены данные за " + Constants.getMonthName(i, currentYear).toLowerCase());
            } else {
                System.out.println("Данные за " + Constants.getMonthName(i, currentYear).toLowerCase() + " не найдены");
            }
        }
        System.out.println();
    }

    void loadYearlyReport() {
        String filename = YearlyReport.getFilename(currentYear);
        yearlyReport = YearlyReport.loadFromFile(filename);
        if (yearlyReport != null) {
            System.out.println("Загружены данные за " + currentYear + " год");
        } else {
            System.out.println("Данные за " + currentYear + " год не найдены");
        }
        System.out.println();
    }

    MonthlyReport getMonthlyReport(int aMonth) {
        MonthlyReport result = null;
        if ( monthlyReports != null ) {
            if ((aMonth >= 0) && (aMonth < monthlyReports.length)) {
                result = monthlyReports[aMonth];
            }
        }
        return result;
    }

    MonthlyTotal getYearlyMonthReport(int aMonth) {
        MonthlyTotal result = null;
        if ( yearlyReport != null ) {
            result = yearlyReport.getMonth(aMonth);
        }
        return result;
    }

    void compareReports() {
        checkMonthlyReport();
        checkYearlyReport();
        boolean noErrors = true;

        for ( int i = 0; i < Constants.months; i++ ) {

            Report monthly = getMonthlyReport(i);
            Report yearly = getYearlyMonthReport(i);

            if ( ( monthly != null ) && (yearly != null ) ) {
                boolean compare = monthly.Compare(yearly);
                if ( !compare ) {
                    System.out.println("Несовпадение данных за " + Constants.monthNames[i].toLowerCase() );
                    noErrors = false;
                }
            } else {
                if (monthly == null) {
                    System.out.println("Отсутствует данные месячного отчета за " + Constants.monthNames[i].toLowerCase() );
                    noErrors = false;
                }
                if (yearly == null) {
                    System.out.println("Отсутствует данные в годовом отчете за " + Constants.monthNames[i].toLowerCase() );
                    noErrors = false;
                }
            }
        }
        if ( noErrors ) {
            System.out.println("Сравнение завершено. Разногласий нет.");
        } else {
            System.out.println("Сравнение завершено.");
        }
        System.out.println();
    }

    void checkMonthlyReport() {
        if (monthlyReports == null) {
            while (true) {
                System.out.println("Отсутствует данные месячных отчетов");
                System.out.println("1 - Загрузить данные из файлов");
                System.out.println("2 - Пропустить");
                String input = scanner.nextLine();
                if ("1".equals(input)) {
                    loadMonthlyReports();
                    return;
                } else if ("2".equals(input)) {
                    return;
                } else {
                    System.out.println("Неизвестная команда");
                }
            }
        }
    }

    void checkYearlyReport() {
        if (yearlyReport == null) {
            while (true) {
                System.out.println("Отсутствует данные годового отчета");
                System.out.println("1 - Загрузить данные из файла");
                System.out.println("2 - Пропустить");
                String input = scanner.nextLine();
                if ("1".equals(input)) {
                    loadYearlyReport();
                    return;
                } else if ("2".equals(input)) {
                    return;
                } else {
                    System.out.println("Неизвестная команда");
                }
            }
        }
    }

    void printMonthlyReports() {
        checkMonthlyReport();
        if ( monthlyReports != null ) {
            for (int i = 0; i < monthlyReports.length; i++) {
                printMonthlyReport(i);
            }
        }

    }

    void printMonthlyReport(int aMonth) {
        if (monthlyReports[aMonth] != null) {
            System.out.println( Constants.getMonthName(aMonth, currentYear) );

            ReportTotal mostProfitable = monthlyReports[aMonth].getBiggestProfit();
            System.out.println("\tСамый прибыльный товар: " + mostProfitable.name + " - " + mostProfitable.cost);

            ReportTotal mostExpensive = monthlyReports[aMonth].getBiggestExpense();
            System.out.println("\tСамая большая трата:    " + mostExpensive.name + " - " + mostExpensive.cost);
/*
            for ( String product : monthlyReports[aMonth].expenseRecords.keySet() ) {
                for ( ValueRecord value : monthlyReports[aMonth].expenseRecords.get(product) ) {
                    System.out.println(product + " " + value.price + " " + value.quanity);
                }
            }
            System.out.println();
 */
            System.out.println();
        }
    }

    void printYearlyReport() {
        checkYearlyReport();
        if ( yearlyReport != null ) {
            System.out.println(currentYear);
            byte monthCount = 0;
            int averageProfit  = 0;
            int averageExpense = 0;

            System.out.println( "Прибыль в каждом месяце: " );
            for ( int i = 0; i < yearlyReport.monthlyTotals.length; i++ ) {
                if ( yearlyReport.monthlyTotals[i] != null ) {
                    monthCount++;
                    averageProfit += yearlyReport.monthlyTotals[i].profits;
                    averageExpense += yearlyReport.monthlyTotals[i].expenses;
                    System.out.println( "\t" + Constants.monthNames[i] + ":" + " ".repeat( Integer.max(2, 10 - Constants.monthNames[i].length() ) ) +  ( yearlyReport.monthlyTotals[i].profits - yearlyReport.monthlyTotals[i].expenses ) );
                }
            }
            if ( monthCount > 0 ) {
                averageProfit /= monthCount;
                averageExpense /= monthCount;

                System.out.println("\tCредний расход за все имеющиеся операции в году: " + averageExpense);
                System.out.println("\tCредний доход за все имеющиеся операции в году: " + averageProfit);
            } else {
                System.out.println("\tПрибыли нет");
                System.out.println("\tТрат нет");
            }
            System.out.println();
        }
    }
}
