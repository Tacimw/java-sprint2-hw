import java.util.ArrayList;
import java.util.Scanner;

public class YearlyReport {

    Report[] reports;
    short year;
    Scanner scanner;
    YearlyReport(Scanner aScanner, short aYear) {

        year = aYear;
        scanner = aScanner;
    }

    public static String GetFilename(short aYear) {
        //y.YYYY.csv
        return "y." + aYear + ".csv";
    }

    void AddYear() {
        String filename = GetFilename( year );
        if (FileReader.FileIsExist(filename)) {
            reports = new Report[Constants.months];
            ArrayList<String> lines = FileReader.readFileContents(filename);

            if (!lines.isEmpty()) {
                for (int i = 1; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length == 3) {
                        byte month;
                        int amount;
                        try {
                            month = Byte.parseByte( parts[0] );
                            month--;

                            amount = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            reports = null;
                            System.out.println("Неверный формат данных в файле годового отчета за " + year );
                            System.out.println("Строка: " + lines.get(i));
                            return;
                        }
                        boolean is_expense = Boolean.parseBoolean(parts[2]);
                        if (reports[month] == null) {
                            reports[month] = new Report();
                        }
                        if ( is_expense ) {
                            reports[month].expenses += amount;
                        } else {
                            reports[month].profits += amount;
                        }

                    } else {
                        reports = null;
                        System.out.println("Неверный формат данных в файле годового отчета за " + year );
                        System.out.println("Строка: " + lines.get(i));
                        return;
                    }
                }
            } else {
                reports = null;
                System.out.println("Годовой отчет за " + year + " не содержит данных.");
                return;
            }
            System.out.println("Данные за " + year + " загружены.");
        } else {
            reports = null;
            System.out.println("Отсутствует файл годового отчета за " + year );
        }
    }

    void CheckReport() {
        if (reports == null) {
            while (true) {
                System.out.println("Отсутствует данные годового отчета за " + year);
                System.out.println("1 - Загрузить данные из файла");
                System.out.println("2 - Пропустить");

                int key = scanner.nextInt();
                if (key == 1) {
                    AddYear();
                    return;
                } else if (key == 2) {
                    return;
                } else {
                    System.out.println("Неизвестная команда");
                }
            }
        }
    }

    void PrintYear() {
        /*
        рассматриваемый год;
        прибыль по каждому месяцу;
        средний расход за все имеющиеся операции в году;
        средний доход за все имеющиеся операции в году.
        */

        CheckReport();

        if ( reports != null ) {
            System.out.println(year);
            byte monthCount = 0;
            int averageProfit  = 0;
            int averageExpense = 0;

            System.out.println( "Прибыль в каждом месяце: " );
            for ( int i = 0; i < reports.length; i++ ) {
                if ( reports[i] != null ) {
                    monthCount++;
                    averageProfit += reports[i].profits;
                    averageExpense += reports[i].expenses;
                    System.out.println( "\t" + Constants.monthNames[i] + ":" + " ".repeat( Integer.max(2, 10 - Constants.monthNames[i].length() ) ) +  reports[i].profits);
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

    Report GetMonth( byte aMonth ) {
        Report result = null;
        CheckReport();
        if ((aMonth >= 0) && (aMonth < reports.length)) {
            result = reports[aMonth];
        } else {
            System.out.println("Неверное число месяца " + aMonth );
        }
        return result;
    }

}
