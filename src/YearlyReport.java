import java.util.ArrayList;
public class YearlyReport {

    MonthlyTotal[] monthlyTotals;

    YearlyReport() {
        monthlyTotals = new MonthlyTotal[Constants.months];
    }

    MonthlyTotal getMonth( int aMonth ) {
        MonthlyTotal result = null;
        if ((aMonth >= 0) && (aMonth < monthlyTotals.length)) {
            result = monthlyTotals[aMonth];
        } else {
            System.out.println("Неверное число месяца " + aMonth );
        }
        return result;
    }

    void addRecord(int aMonth, int aTotalCost, boolean aIsExpense) {
        if ( monthlyTotals[aMonth] == null ) {
            monthlyTotals[aMonth] = new MonthlyTotal();
        }

        if (aIsExpense) {
            monthlyTotals[aMonth].expenses += aTotalCost;
        } else {
            monthlyTotals[aMonth].profits += aTotalCost;
        }
    }

    public static String getFilename(int aYear) {
        //y.YYYY.csv
        return "y." + aYear + ".csv";
    }

    public static YearlyReport loadFromFile(String aFileName) {
        YearlyReport yearlyReport = null;
        if (FileReader.fileIsExist(aFileName)) {
            ArrayList<String> lines = FileReader.readFileContents(aFileName);

            if (!lines.isEmpty()) {
                yearlyReport = new YearlyReport();
                for (int i = 1; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length == 3) {
                        byte month;
                        int amount;
                        try {
                            month = Byte.parseByte(parts[0]);
                            month--;

                            amount = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            System.out.println(aFileName + ": неверный формат данных.");
                            System.out.println("Строка: " + lines.get(i));
                            return null;
                        }
                        boolean is_expense = Boolean.parseBoolean(parts[2]);
                        yearlyReport.addRecord(month, amount, is_expense );

                    } else {
                        System.out.println(aFileName + ": неверный формат данных.");
                        System.out.println("Строка: " + lines.get(i));
                        return null;
                    }
                }
            }
        }

        return yearlyReport;
    }

}
