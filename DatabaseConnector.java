import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbmanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static String login(String username, String password){
      try {
        Connection conn = getConnection();
        String sql = "SELECT * FROM tblusers WHERE user_username=? AND user_password=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password); 
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          String userData = resultSet.getString("user_username");
          return userData;
        }else{
          
          return null;
        }

      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
    }

    public static int addStudent(String fullName, String schoolId, String age, String birthday, String course, String lrn){
      try {
        Connection conn = getConnection();
        String sql = "INSERT INTO tblstudents(stud_fullName, stud_schoolId, stud_birthday, stud_course, stud_lrn) ";
        sql += "VALUES(?,?,?,?,?,?) ";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, fullName);
        statement.setString(2, schoolId);
        statement.setString(4, birthday);
        statement.setString(5, course);
        statement.setString(6, lrn);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          return 1;
        }else{
          return 0;
        }
      } catch (Exception e) {
        return 0;
      }
    }
    public static String getStudent(){
      try {
        Connection conn = getConnection();
        String sql = "SELECT * FROM tblusers WHERE user_username=? AND user_password=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          String userData = resultSet.getString("user_username");
          return userData;
        }else{
          
          return null;
        }

      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
    }
    public static int updateStudent(int id, String fullName, String schoolId, String birthday, String course, String lrn){
      try {
        Connection conn = getConnection();
        String sql = "UPDATE tblstudents SET stude_id , stud_fullName=?, stud_schoolId=?, stud_birthday=?, stud_course=?, stud_lrn=?";
        sql += "WHERE stud_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, fullName);
        statement.setString(3, schoolId);
        statement.setString(4, birthday);
        statement.setString(5, course);
        statement.setString(6, lrn);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          return 1;
        }else{
          return 0;
        }
      } catch (Exception e) {
        return 0;
      }
    }
    public static String getStaff(){
      try {
        Connection conn = getConnection();
        String sql = "SELECT * FROM tblusers WHERE user_level = 1";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          return resultSet.getString(0);
        }else{
          return null;
        }
      } catch (Exception e) {
        return null;
      }
    }

    public static int addStaff(String username, String password,String fullname, String staffId, String birthday){
      try {
        Connection conn = getConnection();
        String sql = "INSERT INTO tblusers(user_username, user_password, user_fullName, user_staffId, user_birthday) ";
        sql += "VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, fullname);
        statement.setString(4, staffId);
        statement.setString(5, birthday);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
          return 1;
        }else{
          return 0;
        }
      } catch (Exception e) {
        return 0;
      }
    }

}
