package dbModels;

import lombok.Data;
import utilities.database.DBUtilV2;

import java.sql.ResultSet;
import java.util.List;

@Data //it automatically creates getter and setter class and constructors
public class Transaction {
    private String transactionDescription;
    private double transactionAmount;
    private double runningBalance;
    private String transactionDate;
    private String transactionCategory;
    private String transactionState;
    private static final String baseQuery = "SELECT T1.description AS transactionDescription, T1.amount AS transactionAmount, T1.running_balance AS runningBalance, T1.transaction_date AS transactionDate,  +\n" +
            "                T2.category AS transactionCategory, T2.name AS transactionState \n" +
            "                FROM digitalbank.account_transaction T1  \n" +
            "                JOIN transaction_type T2  \n" +
            "                ON T1.transaction_type_id=T2.id  \n" +
            "                JOIN transaction_state T3  \n" +
            "                ON T1.transaction_state_id=T3.id\n";

    public  static Transaction getByID(int id){
        String query = baseQuery + " WHERE T1.id = ?;";

        List<Transaction> transactionList = DBUtilV2.query(query, id).convertResultSetToBeans(Transaction.class);
        if (transactionList.isEmpty()){return null;}

        return transactionList.get(0); //first element in List
    }

//    public  static List<Transaction> getByCategory(String category){
//        String query = baseQuery + " WHERE T2.category = ?;";
//
//        ResultSet rs = DBUtilV2.query(query,category);
//        return DBUtilV2.convertResultSetToBeans(rs, Transaction.class);
//    }


    public static void main(String[] args) {
        System.out.println(Transaction.getByID(83));
        //System.out.println(Transaction.getByCategory("DEBIT").get(0).getTransactionDescription());
        //forEach(System.out::println);

        System.out.println(Transaction.getByID(83).getRunningBalance());
    }
}
