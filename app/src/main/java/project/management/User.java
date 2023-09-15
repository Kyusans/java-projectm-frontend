package project.management;

public class User {
  public String user_username;
  public String user_password;
  public int user_id;
  public String user_fullName;
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

  public int getLevel(){
    return this.user_level;
  }
}