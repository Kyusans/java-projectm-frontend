package project.management;

public class Student {
  String stud_fullName;
  String stud_schoolId;
  String stud_birthday;
  String stud_course;
  String stud_lrn;

  public Student(String fullName, String schoolId, String birthday, String course, String lrn){
    this.stud_fullName = fullName;
    this.stud_schoolId = schoolId;
    this.stud_birthday = birthday;
    this.stud_course = course;
    this.stud_lrn = lrn;
  } 

  public String getStudentFullName(){
    return this.stud_fullName;
  }

  public String getStudentSchoolId(){
    return this.stud_schoolId;
  }

  public String getStudentBirthday(){
    return this.stud_birthday;
  }

  public String getStudentCourse(){
    return this.stud_course;
  }

  public String getStudentLrn(){
    return this.stud_lrn;
  }
}

