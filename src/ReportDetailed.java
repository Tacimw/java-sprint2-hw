import java.util.HashMap;

public class ReportDetailed extends Report {
    HashMap<String, HashMap<Integer, Integer>> detailsExpenses; // у одного товара в течении месяца в теории может быть разная цена.
    HashMap<String, HashMap<Integer, Integer>> detailsProfits;


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

    static void AddToHash(HashMap<String, HashMap<Integer, Integer>> details, String aItemName, int aQuantity, int aUnitPrice) {
        if (details.containsKey(aItemName)) {
            HashMap<Integer, Integer> detail = details.get(aItemName);
            if ( detail == null ) {
                detail = new HashMap<>();
            }
            if ( detail.containsKey(aUnitPrice) ) {
                Integer quanity = detail.get(aUnitPrice);
                quanity += aQuantity;
                detail.put(aUnitPrice, quanity);
            } else {
                detail.put(aUnitPrice, aQuantity);
            }

        } else {
            HashMap<Integer, Integer> detail = new HashMap<>();
            detail.put(aUnitPrice, aQuantity);
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

    static NameAndSum GetMostFrom( HashMap<String, HashMap<Integer,Integer>> details ) {
        String mostName = "";
        int mostSum = 0;

        for (String key : details.keySet()) {
            HashMap<Integer,Integer> detail = details.get(key);
            int sum = 0;
            for (Integer price : detail.keySet() ) {
                sum += detail.get(price) * price;
            }

            if (sum > mostSum) {
                mostSum = sum;
                mostName = key;
            }
        }
        return new NameAndSum(mostName, mostSum);
    }
}
