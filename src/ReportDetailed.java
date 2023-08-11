import java.util.HashMap;

public class ReportDetailed extends Report {
    HashMap<String, ReportDetail> detailsExpenses;
    HashMap<String, ReportDetail> detailsProfits;

    ReportDetailed( ) {
        detailsExpenses = new HashMap<>();
        detailsProfits = new HashMap<>();
    }

    void AddDetails(String aItemName, boolean aIsExpense, int aQuantity, int aUnitPrice) {
        if (aIsExpense) {
            AddToHash(detailsExpenses, aItemName, aQuantity, aUnitPrice);
            expenses += aQuantity * aUnitPrice;
        } else {
            AddToHash(detailsProfits, aItemName, aQuantity, aUnitPrice);
            profits += aQuantity * aUnitPrice;
        }
    }

    static void AddToHash(HashMap<String, ReportDetail> details, String aItemName, int aQuantity, int aUnitPrice) {
        if (details.containsKey(aItemName)) {
            ReportDetail detail = details.get(aItemName);
            detail.quantity += aQuantity;
            detail.unit_price += aUnitPrice;
        } else {
            ReportDetail detail = new ReportDetail(aQuantity, aUnitPrice);
            details.put(aItemName, detail);
        }
    }

    void Clear() {
        expenses = 0;
        profits = 0;
        detailsProfits.clear();
        detailsExpenses.clear();
    }

    public NameAndSum GetMostExpensive() {
        return GetMostFrom( detailsExpenses );
    }

    public NameAndSum GetMostProfitable() {
        return GetMostFrom( detailsProfits );
    }

    static NameAndSum GetMostFrom( HashMap<String, ReportDetail> details ) {
        String mostName = "";
        int mostSum = 0;

        for (String key : details.keySet()) {
            ReportDetail detail = details.get(key);

            int sum = detail.quantity * detail.unit_price;

            if (sum > mostSum) {
                mostSum = sum;
                mostName = key;
            }
        }
        return new NameAndSum(mostName, mostSum);
    }
}
