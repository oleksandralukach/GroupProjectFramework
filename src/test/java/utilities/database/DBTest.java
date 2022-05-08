package utilities.database;

import dbModels.UserProfile;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
    @AfterClass
    public static void tearDown() {
        DBUtilV2.close();
    }

    //instead of method convertToBeans()
    //get user profiles from db and map it to beans using custom constructor from that class
    @Test
    public void getUserProfiles() throws SQLException {
        DBUtilV2.openDBConnection("digitalbank");
        ResultSet rs = DBUtilV2.queryToRs("SELECT * FROM digitalbank.user_profile;");
        while (rs.next()) {
            System.out.println(new UserProfile(rs));
        }
    }

    @Test
    public void truncate() { //no access to database
        DBUtilV2.truncateTable("test.my_table");
    }

    @Test
    public void update() { //restricted access to database
        String email = "jane@email.com";
        int id = 1;
        String query = "Select email from test.my_table where id = ?;";
        String updateStatement = "Update test.my_table SET email = ? WHERE id -?;";
        //Update record
        DBUtilV2.executeStatement(updateStatement, email, id); //parametrised
        //validate record was updated
        // checking if query will return updated email
        Assert.assertEquals(
                DBUtilV2.query(query, id).convertResultSetToListOfMaps().get(0).get("email"),
                email);
    }

    @Test
    public void delete() {
        DBUtilV2.deleteRecord("test.my_table", "id", "1");
    }

    @Test
    public void insertWithQuery() {
        DBUtilV2.executeStatement("Insert Into test.my_table Value (null, Jack, Jane, null)");

    }

    @Test
    public void paramsInsert() {
        DBUtilV2.executeStatement("Insert Into test.my_table Value (???)", null, "Jack", "Jane", null);//parametrised
    }

    @Test
    public void insertProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(999);
        userProfile.setDob("1964-05-18 02:12:24");
        userProfile.setAddress("Hutchinson");
        userProfile.setEmail_address("email");
        userProfile.setCountry("USA");
        userProfile.insert();
    }
}
