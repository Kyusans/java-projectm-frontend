package project.management;

public class Student {
  // user_id para sa staff or admin (history)
  int user_id;
  int stud_id;
  String stud_schoolId;
  String stud_fullName;
  String stud_gender;
  String stud_email;
  String stud_courseCode;
  int stud_yearLevel;
  String stud_dateEnrolled;
  String stud_address;

  // pang add student
  public Student(String schoolId, String fullName, String gender, String email, String courseCode, int yearLevel, String address, int userId){
    this.stud_schoolId = schoolId;
    this.stud_fullName = fullName;
    this.stud_gender = gender;
    this.stud_email = email;
    this.stud_courseCode = courseCode;
    this.stud_yearLevel = yearLevel;
    this.stud_address = address;
    this.user_id = userId;
  } 

  // pang update student
  public Student(int studId, String schoolId, String fullName, String gender, String email, String courseCode, int yearLevel, String dateEnrolled, String address, int userId){
    this.stud_id = studId;
    this.stud_schoolId = schoolId;
    this.stud_fullName = fullName;
    this.stud_gender = gender;
    this.stud_email = email;
    this.stud_courseCode = courseCode;
    this.stud_yearLevel = yearLevel;
    this.stud_dateEnrolled = dateEnrolled;
    this.stud_address = address;
    this.user_id = userId;
  } 

  public Student(int studId, int userId, String fullName){
    this.stud_id = studId;
    this.user_id = userId;
    this.stud_fullName = fullName;
  }

  public Student(int id){
    this.stud_id = id;
  }

  public Student(){}

  public int getUserId(){
    return this.user_id;
  }

  public void setStudentSchoolId(String schoolId){
    this.stud_schoolId = schoolId;
  }
  
  public void setStudentFullName(String fullName){
    this.stud_fullName = fullName;
  }

  public void setStudentGender(String gender){
    this.stud_gender = gender;
  }

  public void setStudentEmail(String email){
    this.stud_email = email;
  }

  public void setStudentCourseCode(String courseCode){
    this.stud_courseCode = courseCode;
  }

  public void setStudentYearLevel(int yearLevel){
    this.stud_yearLevel = yearLevel;
  }

  public void setStudentDateEnrolled(String dateEnrolled){
    this.stud_dateEnrolled = dateEnrolled;
  }

  public void setStudentAddress(String address){
    this.stud_address = address;
  }

  public int getStudentId(){
    return this.stud_id;
  }

  public String getStudentFullName(){
    return this.stud_fullName;
  }
  
  public String getStudentSchoolId(){
    return this.stud_schoolId;
  }

  public String getStudentGender(){
    return this.stud_gender;
  }

  public String getStudentEmail(){
    return this.stud_email;
  }

  public String getStudentCourseCode(){
    return this.stud_courseCode;
  }

  public int getStudentYearLevel(){
    return this.stud_yearLevel;
  }

  public String getStudentDateEnrolled(){
    return this.stud_dateEnrolled;
  }

  public String getStudentAddress(){
    return this.stud_address;
  }

}
