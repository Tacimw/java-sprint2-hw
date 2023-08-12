import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReportEngine reportEngine = new ReportEngine(scanner);

        while (true) {
            printMenu();
            String input = scanner.nextLine();
            if ("1".equals(input)) {
                reportEngine.loadMonthlyReports();
            } else if ("2".equals(input)) {
                reportEngine.loadYearlyReport();
            } else if ("3".equals(input)) {
                reportEngine.compareReports();
            } else if ("4".equals(input)) {
                reportEngine.printMonthlyReports();
            } else if ("5".equals(input)) {
                reportEngine.printYearlyReport();
            } else if ("6".equals(input)) {
                return;
            } else {
                System.out.println("Неверная команда");
            }
        }
    }

    static void printMenu() {
        System.out.println(" 1 - Считать все месячные отчёты");
        System.out.println(" 2 - Считать годовой отчёт");
        System.out.println(" 3 - Сверить отчёты");
        System.out.println(" 4 - Вывести информацию обо всех месячных отчётах");
        System.out.println(" 5 - Вывести информацию о годовом отчёте");
        System.out.println(" 6 - Выйти");
    }

}

