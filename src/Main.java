import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport(scanner, Constants.year);
        YearlyReport yearlyReport = new YearlyReport(scanner, Constants.year);

        while (true) {
            PrintMenu();
            int key = scanner.nextInt();
            switch (key) {
                case (1):
                    monthlyReport.AddMonts();
                    break;
                case (2):
                    yearlyReport.AddYear();
                    break;
                case (3):
                    boolean noErrors = true;
                    for (byte i = 0; i < Constants.months; i++ ) {
                        Report montly = monthlyReport.GetMonth(i);
                        Report yearly = yearlyReport.GetMonth(i);

                        if ( ( montly != null ) && (yearly != null ) ) {
                            boolean compare = montly.Compare(yearly);
                            if ( !compare ) {
                                System.out.println("Несовпадение данных за " + Constants.monthNames[i].toLowerCase() );
                                noErrors = false;
                            }
                        } else {
                            if (montly == null) {
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
                    break;
                case (4):
                    monthlyReport.PrintMonths();
                    break;
                case (5):
                    yearlyReport.PrintYear();
                    break;
                case (6):
                    return;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }

    static void PrintMenu() {
        System.out.println( " 1 - Считать все месячные отчёты" );
        System.out.println( " 2 - Считать годовой отчёт" );
        System.out.println( " 3 - Сверить отчёты" );
        System.out.println( " 4 - Вывести информацию обо всех месячных отчётах" );
        System.out.println( " 5 - Вывести информацию о годовом отчёте" );
        System.out.println( " 6 - Выйти" );
    }
    /*
    Считать все месячные отчёты — прочитать данные из файлов месячных отчётов, сохранить их в память программы.
💡 При выборе действия «считать все месячные отчёты» должно происходить считывание трёх файлов:
  m.202101.csv
  m.202102.csv
  m.202103.csv

Считать годовой отчёт — прочитать данные из файла годового отчёта, сохранить их в память программы.
💡 При выборе действия «считать годовой отчёт» должно происходить считывание из одного файла:
  y.2021.csv

Сверить отчёты — по сохранённым данным проверить, сходятся ли отчёты за месяцы и за год.
Вывести информацию обо всех месячных отчётах — по сохранённым данным вывести в консоль имеющуюся информацию.
Вывести информацию о годовом отчёте — по сохранённым данным вывести в консоль имеющуюся информацию.
    */
}

