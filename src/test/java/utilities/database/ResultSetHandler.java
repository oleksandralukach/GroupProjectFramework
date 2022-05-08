package utilities.database;

import org.apache.commons.dbutils.BeanProcessor;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetHandler {
    private ResultSet resultSet;
    private BeanProcessor beanProcessor;

    public ResultSetHandler(ResultSet resultSet){
        this.resultSet = resultSet;
        this.beanProcessor = new BeanProcessor();
    }

    private List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i < columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getLocalizedMessage());
        }
        return columnNames;
    }

    // T declares generic data type
    // In a way it postpones the Data type declaration
    public  <T> List<T> convertResultSetToBeans( Class<T> beanClass){
        try {
            return beanProcessor.toBeanList(resultSet, beanClass);
            //able to match column names with properties of the class and convert ResultSet to List of javabeans (Objects)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //key is column name, value is data from that column
    public List<Map<String, Object>> convertResultSetToListOfMaps() {
        List<Map<String, Object>> table = new ArrayList<>(); //table.size() return how many rows you have(each map is a row)
        List<String> columnNames = getColumnNames();
        while (true) {
            Map<String, Object> row = new HashMap<>();
            try {
                if (!resultSet.next()) break; //Moves the cursor forward one row from its current position, until rows exist
                for (String columnName : columnNames) {
                    row.put(columnName, resultSet.getObject(columnName));//value of that specific column.
                }
                table.add(row);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }
}
