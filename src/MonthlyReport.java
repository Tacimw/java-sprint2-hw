import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport extends Report {

    HashMap<String, ArrayList<ValueRecord>> expenseRecords;
    HashMap<String, ArrayList<ValueRecord>> profitRecords;

    MonthlyReport() {
        expenseRecords = new HashMap<>();
        profitRecords = new HashMap<>();
    }

    ReportTotal getBiggestExpense() {
        return getBiggestFrom(expenseRecords);
    }

    ReportTotal getBiggestProfit() {
        return getBiggestFrom(profitRecords);
    }

    static ReportTotal getBiggestFrom(HashMap<String, ArrayList<ValueRecord>> records ) {
        String mostName = "";
        int mostSum = 0;

        for (String  productName: records.keySet()) {
            ArrayList<ValueRecord> values = records.get(productName);
            int totalSum = 0;
            for (ValueRecord value : values  ) {
                totalSum += value.quanity * value.price;
            }

            if (totalSum > mostSum) {
                mostSum = totalSum;
                mostName = productName;
            }
        }
        return new ReportTotal(mostName, mostSum);
    }

    void addRecord(String aName, int aPrice, int aQuanity, boolean aIsExpense) {
        if (aIsExpense) {
            expenses += aPrice * aQuanity;
            addRecord(expenseRecords, aName, aPrice, aQuanity);
        } else {
            profits += aPrice * aQuanity;
            addRecord(profitRecords, aName, aPrice, aQuanity);
        }
    }

    static void addRecord(HashMap<String, ArrayList<ValueRecord>> aRecords, String aName, int aPrice, int aQuanity) {
        if (!aRecords.containsKey(aName)) {
            aRecords.put(aName, new ArrayList<>());
        }
        aRecords.get(aName).add(new ValueRecord(aPrice, aQuanity));
    }

    static String getFilename(int aMonth, int aYear) {
        //m.YYYYMM.csv
        return "m." + aYear + String.format("%02d", aMonth + 1) + ".csv";
    }

    static MonthlyReport loadFromFile(String aFileName) {
        MonthlyReport monthlyReport = null;

        if (FileReader.FileIsExist(aFileName)) {
            ArrayList<String> lines = FileReader.readFileContents(aFileName);

            if (!lines.isEmpty()) {
                monthlyReport = new MonthlyReport();
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
                            System.out.println(aFileName + ": неверный формат данных.");
                            System.out.println("Строка: " + lines.get(i));
                            return null;
                        }
                        monthlyReport.addRecord(itemName, unitPrice, quantity, isExpense);
                    } else {
                        System.out.println(aFileName + ": неверный формат данных.");
                        System.out.println("Строка: " + lines.get(i));
                        return null;
                    }
                }
            }
        }

        return monthlyReport;
    }
}
