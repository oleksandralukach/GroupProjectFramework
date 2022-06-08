package utilities.database;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;
import utilities.ConfigReader;
import java.sql.*;


public class DBUtilV2 {
    private static Connection connection; //interface implemented by driver in our case mysql
    private static Statement statement; //functionality that executes queries
    private static final String JDBC_URL = ConfigReader.getProperty("jdbc_url");
    private static QueryRunner queryRunner = new QueryRunner();

    public static void openDBConnection() {

        openDBConnection("");
        // and by default (because database name is empty now) will open the one we provide in query with .tableName
    }
    public static void openDBConnection(String database) {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(JDBC_URL.replace("{DB}", database), "digitaluser", "Demo123!");
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Can't establish connection to DB");
        }
    }

    public static void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            statement = null;
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Can't close connection to DB");
        }
    }
//vargs for parametrization
    public static boolean executeStatement(String sqlStatement, Object...params){ //insert, delete, update
        if (connection == null)openDBConnection();
        try{
            if (params.length == 0) return statement.execute(sqlStatement);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); //for parameterization

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static  boolean insertBean(String query, Object bean, String[] properties){
        if (connection == null) DBUtilV2.openDBConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            queryRunner.fillStatementWithBean(preparedStatement, bean, properties);
            return preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean truncateTable(String tableName){
        String sqlStatement = String.format("TRUNCATE Table %s;", tableName);
        return executeStatement(sqlStatement);
    }

    public static boolean deleteRecord(String table,String column, String value){
        String statement = String.format("DELETE from %s where %s = '%s'", table, column, value);
        return executeStatement(statement);
    }

    //Parametrized query
    //VarArgs.This argument that can accept variable number of values is called varargs. [array]
    // You can provide here as many objects as you need or none.
    // The second String will be considered as an array of 1 element.
    // if I provide 3 Strings: first will be considered as query, second and third as array of 2 elements
    //["String"]
    //["String1", "String2"]]
    //SELECT * FROM digitalbank.user_profile WHERE id= ?;

    //Object... = Object[]
    //query("", "", "")
    public static ResultSet queryToRs(String query, Object... params){
        if (connection == null) openDBConnection();
        try {
            if (params.length == 0) return statement.executeQuery(query);
            else {
                PreparedStatement preparedStatement = connection.prepareStatement(query); //tracking ? and helping insert parameters instead
                //Replace ??? with params:
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]); //in preparedStatement first is index of ? (starts from 1), second - object(from params) which will replace it - (in array starts from 0) //so we put first param into prepared statement, then second and so on
                }
                return preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Not able to execute query");
        }
        return null;
    }

    public static ResultSetHandler query(String query, Object... params) {
        return new ResultSetHandler(queryToRs(query, params)); //new object of class based on constructor
    }

    public static void main(String[] args) {

//        String query = "SELECT * " +
//                "FROM digitalbank.user_profile " +
//                "WHERE country = ?" +
//                "OR country = ?" +
//                "OR country = ?" +
//                "ORDER BY country;";
//        ResultSet rs = DBUtilV2.query(query, "US", "USA", "United States");
//
//       DBUtil2.convertResultSetToListOfMaps(rs).forEach(System.out::println);
//
//        System.out.println("-------------------------------");
//        String query2 = "SELECT * " +
//                "FROM digitalbank.user_profile " +
//                "WHERE country = ?" +
//                "AND dob >= ?;";
//          rs = DBUtilV2.query(query2, "USA", "1994-05-12 00:00:00"); //positions matter respectively to query
//        resultSetHandler.convertResultSetToListOfMaps().forEach(System.out::println);
//
//        System.out.println("-----------------------------");
//        String query3 = "SELECT * " +
//                "FROM digitalbank.user_profile " +
//                "WHERE country = ?" +
//                "AND dob >= ?;";
//        rs = DBUtilV2.query(query3,"USA","1996-05-12 00:00:00" );
//        //it will display in a form of objects (and it will assign only fields that i declare in a class, regardless the query
//       DB2.convertResultSetToBeans(rs, UserProfile.class).forEach(System.out::println);
//
//        System.out.println("----------------------------");
//        UserProfile fromDB = UserProfile.getByID(5);
////        UserProfile fromAPI = new UserProfile();
////        Assert.assertEquals(fromDB,fromAPI);
//       System.out.println(UserProfile.getByID(5));
//
//        System.out.println("-------------------------------");
//        String query4 = "SELECT * FROM user_profile;";
//        rs = DBUtilV2.query(query4);
//       DB2.convertResultSetToBeans(rs, UserProfile.class).forEach(System.out::println);
   }

}
