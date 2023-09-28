package project.management;

public class User {
  public String user_username;
  public String user_password;
  public int user_id;
  public String user_fullName;
  public String user_email;
  public String user_contactNumber;
  public String user_address;
  public int user_level;

  public User(String username, String password, int userId, String fullname, int level){
    this.user_username = username;
    this.user_password = password;
    this.user_id = userId;
    this.user_fullName = fullname;
    this.user_level = level;
  }

  public User(String username, String password){
    this.user_username = username;
    this.user_password = password;
  }

  public User(String fullName, String username, String password, String contactNumber, String address,String email){
    this.user_fullName = fullName;
    this.user_username = username;
    this.user_password = password;
    this.user_contactNumber = contactNumber;
    this.user_address = address;
    this.user_email = email;
  }

  public User(){}

  public void setUserId(int userId){
    this.user_id = userId;
  }

  public String getUsername(){
    return this.user_username;
  }
  
  public String getFullname(){
    return this.user_fullName;
  }

  public String getPassword(){
    return this.user_password;
  }

  public int getUserId(){
    return this.user_id;
  }

  public String getEmail(){
    return this.user_email;
  }

  public int getLevel(){
    return this.user_level;
  }

  public void setUsername(String username){
    this.user_username = username;
  }

  public void setPassword(String password){
    this.user_password = password;
  }

}
