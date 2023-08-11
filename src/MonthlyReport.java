import java.util.ArrayList;
import java.util.Scanner;

public class MonthlyReport {

    ReportDetailed[] reports;
    short year;
    Scanner scanner;

    MonthlyReport(Scanner aScanner, short aYear) {
        reports = new ReportDetailed[Constants.months];
        year = aYear;
        scanner = aScanner;
    }

    static String GetFilename(byte aMonth, short aYear) {
        //m.YYYYMM.csv
        return "m." + aYear + String.format("%02d", aMonth + 1) + ".csv";
    }

    void AddMonts() {
        for (byte i = 0; i < reports.length; i++) {
            AddMonth(i);
        }
    }

    void AddMonth(byte aMonth) {
        if ( reports[aMonth] != null ) {
            reports[aMonth].Clear();
            reports[aMonth] = null;
        }

        String filename = GetFilename(aMonth, year);
        if (FileReader.FileIsExist(filename)) {
            ArrayList<String> lines = FileReader.readFileContents(filename);

            if (!lines.isEmpty()) {
                for (int i = 1; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length == 4) {

                        String itemName = parts[0];
                        boolean isExpense = Boolean.parseBoolean(parts[1]);
                        int quantity;
                        int unitPrice;
                        try {
                            quantity = Integer.parseInt(parts[2]);
                            unitPrice = Integer.parseInt(parts[3]);
                        } catch (NumberFormatException e) {
                            reports[aMonth] = null;
                            System.out.println("Неверный формат данных в файле месячного отчета за " + Constants.monthNames[aMonth].toLowerCase());
                            System.out.println("Строка: " + lines.get(i));
                            return;
                        }

                        if (reports[aMonth] == null) {
                            reports[aMonth] = new ReportDetailed();
                        }
                        reports[aMonth].AddDetails(itemName, isExpense, quantity, unitPrice);
                    } else {
                        reports[aMonth] = null;
                        System.out.println("Неверный формат данных в файле месячного отчета за " + Constants.monthNames[aMonth].toLowerCase());
                        System.out.println("Строка: " + lines.get(i));
                        return;
                    }
                }
            } else {
                reports[aMonth] = null;
                System.out.println("Месячный отчет за " + Constants.monthNames[aMonth].toLowerCase() + " не содержит данных.");
                return;
            }
            System.out.println("Данные за " + Constants.monthNames[aMonth].toLowerCase() + " загружены.");
        } else {
            reports[aMonth] = null;
            System.out.println("Отсутствует файл месячного отчета за " + Constants.monthNames[aMonth].toLowerCase());
        }
    }

    ReportDetailed GetMonth( byte aMonth ) {
        ReportDetailed result = null;
        CheckReport(aMonth);
        if ((aMonth >= 0) && (aMonth < reports.length)) {
            result = reports[aMonth];
        } else {
            System.out.println("Неверное число месяца " + aMonth );
        }
        return result;
    }

    void CheckReport(byte aMonth) {
        if (reports[aMonth] == null) {
            while (true) {
                System.out.println("Отсутствует данные месячного отчета за " + Constants.monthNames[aMonth].toLowerCase());
                System.out.println("1 - Загрузить данные из файла");
                System.out.println("2 - Пропустить");
                String key = scanner.next();
                int input;
                try {
                    input = Integer.parseInt(key);
                } catch (NumberFormatException e ) {
                    input = 0;
                }

                if (input == 1) {
                    AddMonth(aMonth);
                    return;
                } else if (input == 2) {
                    return;
                } else {
                    System.out.println("Неизвестная команда");
                }
            }
        }
    }

    void PrintMonths() {
        for (byte i = 0; i < reports.length; i++) {
            PrintMonth(i);
        }
    }


    void PrintMonth(byte aMonth) {
        /*
        название месяца;
        самый прибыльный товар, название товара и сумму;
        самую большую трату, название товара и сумму.
        */
        CheckReport(aMonth);
        if (reports[aMonth] != null) {
            System.out.println(Constants.monthNames[aMonth] + " " + year);

/*
            for ( String key : reports[aMonth].detailsProfits.keySet() ) {
                for ( Integer price : reports[aMonth].detailsProfits.get(key).keySet() ) {
                    System.out.println(key + " " + price + " " + reports[aMonth].detailsProfits.get(key).get(price));
                }
            }
*/

            NameAndSum mostProfitable = reports[aMonth].GetMostProfitable();
            System.out.println("\tСамый прибыльный товар: " + mostProfitable.name + " - " + mostProfitable.sum);

            NameAndSum mostExpensive = reports[aMonth].GetMostExpensive();
            System.out.println("\tСамая большая трата:    " + mostExpensive.name + " - " + mostExpensive.sum);
            System.out.println();
        }
    }
}
