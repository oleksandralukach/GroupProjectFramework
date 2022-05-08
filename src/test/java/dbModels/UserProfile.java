package dbModels;

import junit.framework.TestResult;
import lombok.Data;
import utilities.database.DBUtilV2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//POJO(bean) class- only fields and their getter and setter
@Data
public class UserProfile {
    private int id;
    private String address;
    private String country;
    private String first_name;
    private String last_name;
    private String region;
    private String ssn;
    private String title;
    private String dob ;
    private String dom;
    private String email_address;
    private String gender;
    private String home_phone;
    private String locality;
    private String mobile_phone;
    private String postal_code;
    private String work_phone;

    private static final String INSERT_QUERY = "Insert into digitalbank.user_profile VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    private final String[] fields = {"id", "address", "country", "first_name", "last_name", "region", "ssn", "title",
            "dob", "dom", "email_address", "gender", "home_phone", "locality", "mobile_phone", "postal_code", "work_phone"
    };

    public UserProfile() {
    }

    //Custom ResultSetToBean/ instead of convertToBeans(), but constructor not that convenient
    //Same as beanProcessor.toBeanList(resultSet, beanClass);
    public UserProfile(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.address = resultSet.getString("address");
            this.country = resultSet.getString("country");
            this.first_name = resultSet.getString("first_name");
            this.last_name = resultSet.getString("last_name");
            this.region = resultSet.getString("region");
            this.ssn = resultSet.getString("ssn");
            this.title = resultSet.getString("title");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static UserProfile getByID(int id) {
        String query = "SELECT * " +
                "FROM digitalbank.user_profile " +
                "WHERE id = ?;";

        List<UserProfile> userProfiles = DBUtilV2.query(query, id).convertResultSetToBeans(UserProfile.class);
        if (userProfiles.size() == 0) {
            return null;
        }
        return userProfiles.get(0); //first element of array list
    }

    public boolean insert(){
        return DBUtilV2.insertBean(INSERT_QUERY,this, fields);
    }

}
