package project.management;

public class Student {
  int stud_id;
  String stud_schoolId;
  String stud_fullName;
  String stud_gender;
  String stud_email;
  String stud_courseCode;
  int stud_yearLevel;
  String stud_dateEnrolled;
  String stud_address;

  public Student(String schoolId, String fullName, String gender, String email, String courseCode, int yearLevel, String dateEnrolled, String address){
    this.stud_schoolId = schoolId;
    this.stud_fullName = fullName;
    this.stud_gender = gender;
    this.stud_email = email;
    this.stud_courseCode = courseCode;
    this.stud_yearLevel = yearLevel;
    this.stud_dateEnrolled = dateEnrolled;
    this.stud_address = address;
  } 

  public Student(int id){
    this.stud_id = id;
  }

  public Student(){}

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
