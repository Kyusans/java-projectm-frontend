package project.management;

public class Student {
  // user_id para sa staff or admin (history)
  int stud_id;
  int user_id;
  String stud_fullName;
  String stud_schoolId;
  String stud_birthday;
  String stud_birthplace;
  String stud_gender;
  String stud_religion;
  String stud_address;
  String stud_email;
  String stud_contactNumber;
  String stud_prevSchool;
  String stud_course;
  String stud_gradeLevel;
  String stud_yearGraduated;
  String stud_fatherName;
  String stud_fatherOccupation;
  String stud_fatherContactNumber;
  String stud_motherName;
  String stud_motherOccupation;
  String stud_motherContactNumber;
  String stud_emergencyName;
  String stud_emergencyRelationship;
  String stud_emergencyPhone;
  String stud_emergencyAddress;
  // school id ni nga di ma update 
  String stud_school_id;
  String stud_dateEnrolled;

  public Student(String fullName, String schoolId, String dateBirth, String placeBirth, String gender, String religion,
  String address, String email, String contactNum, String prevSchool, String course, String yearLevel,
  String yearGrad, String fatherName, String fatherOccup, String fatherContact, String motherName,
  String motherOccup, String motherContact, String emergencyName, String emergencyRel,
  String emergencyContact, String emergencyAdd, int userId) {
    this.stud_fullName = fullName;
    this.stud_schoolId = schoolId;
    this.stud_birthday = dateBirth;
    this.stud_birthplace = placeBirth;
    this.stud_gender = gender;
    this.stud_religion = religion;
    this.stud_address = address;
    this.stud_email = email;
    this.stud_contactNumber = contactNum;
    this.stud_prevSchool = prevSchool;
    this.stud_course = course;
    this.stud_gradeLevel = yearLevel;
    this.stud_yearGraduated = yearGrad;
    this.stud_fatherName = fatherName;
    this.stud_fatherOccupation = fatherOccup;
    this.stud_fatherContactNumber = fatherContact;
    this.stud_motherName = motherName;
    this.stud_motherOccupation = motherOccup;
    this.stud_motherContactNumber = motherContact;
    this.stud_emergencyName = emergencyName;
    this.stud_emergencyRelationship = emergencyRel;
    this.stud_emergencyPhone = emergencyContact;
    this.stud_emergencyAddress = emergencyAdd;
    this.user_id = userId;
}

  // pang update student
  public Student(int studId ,int user_id, String stud_fullName, String stud_schoolId, String stud_birthday, String stud_birthplace,
  String stud_gender, String stud_religion, String stud_address, String stud_email, String stud_contactNumber,
  String stud_prevSchool, String stud_course, String stud_gradeLevel, String stud_yearGraduated, String stud_fatherName,
  String stud_fatherOccupation, String stud_fatherContactNumber, String stud_motherName, String stud_motherOccupation,
  String stud_motherContactNumber, String stud_emergencyName, String stud_emergencyRelationship, String stud_emergencyPhone,
  String stud_emergencyAddress, String stud_school_id) {
  this.stud_id = studId;
  this.user_id = user_id;
  this.stud_fullName = stud_fullName;
  this.stud_schoolId = stud_schoolId;
  this.stud_birthday = stud_birthday;
  this.stud_birthplace = stud_birthplace;
  this.stud_gender = stud_gender;
  this.stud_religion = stud_religion;
  this.stud_address = stud_address;
  this.stud_email = stud_email;
  this.stud_contactNumber = stud_contactNumber;
  this.stud_prevSchool = stud_prevSchool;
  this.stud_course = stud_course;
  this.stud_gradeLevel = stud_gradeLevel;
  this.stud_yearGraduated = stud_yearGraduated;
  this.stud_fatherName = stud_fatherName;
  this.stud_fatherOccupation = stud_fatherOccupation;
  this.stud_fatherContactNumber = stud_fatherContactNumber;
  this.stud_motherName = stud_motherName;
  this.stud_motherOccupation = stud_motherOccupation;
  this.stud_motherContactNumber = stud_motherContactNumber;
  this.stud_emergencyName = stud_emergencyName;
  this.stud_emergencyRelationship = stud_emergencyRelationship;
  this.stud_emergencyPhone = stud_emergencyPhone;
  this.stud_emergencyAddress = stud_emergencyAddress;
  this.stud_school_id = stud_school_id;
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

  public int getStudId() {
    return stud_id;
  }

  public void setStudId(int studId) {
      this.stud_id = studId;
  }

  public int getUserId() {
      return user_id;
  }

  public void setUserId(int userId) {
      this.user_id = userId;
  }

  public String getStudFullName() {
      return stud_fullName;
  }

  public void setStudFullName(String studFullName) {
      this.stud_fullName = studFullName;
  }

  public String getStudSchoolId() {
      return stud_schoolId;
  }

  public void setStudSchoolId(String studSchoolId) {
      this.stud_schoolId = studSchoolId;
  }

  public String getStudBirthday() {
      return stud_birthday;
  }

  public void setStudBirthday(String studBirthday) {
      this.stud_birthday = studBirthday;
  }

  public String getStudBirthplace() {
      return stud_birthplace;
  }

  public void setStudBirthplace(String studBirthplace) {
      this.stud_birthplace = studBirthplace;
  }

  public String getStudGender() {
      return stud_gender;
  }

  public void setStudGender(String studGender) {
      this.stud_gender = studGender;
  }

  public String getStudReligion() {
      return stud_religion;
  }

  public void setStudReligion(String studReligion) {
      this.stud_religion = studReligion;
  }

  public String getStudAddress() {
      return stud_address;
  }

  public void setStudAddress(String studAddress) {
      this.stud_address = studAddress;
  }

  public String getStudEmail() {
      return stud_email;
  }

  public void setStudEmail(String studEmail) {
      this.stud_email = studEmail;
  }

  public String getStudContactNumber() {
      return stud_contactNumber;
  }

  public void setStudContactNumber(String studContactNumber) {
      this.stud_contactNumber = studContactNumber;
  }

  public String getStudPrevSchool() {
      return stud_prevSchool;
  }

  public void setStudPrevSchool(String studPrevSchool) {
      this.stud_prevSchool = studPrevSchool;
  }

  public String getStudCourse() {
      return stud_course;
  }

  public void setStudCourse(String studCourse) {
      this.stud_course = studCourse;
  }

  public String getStudGradeLevel() {
      return stud_gradeLevel;
  }

  public void setStudGradeLevel(String studGradeLevel) {
      this.stud_gradeLevel = studGradeLevel;
  }

  public String getStudYearGraduated() {
      return stud_yearGraduated;
  }

  public void setStudYearGraduated(String studYearGraduated) {
      this.stud_yearGraduated = studYearGraduated;
  }

  public String getStudFatherName() {
      return stud_fatherName;
  }

  public void setStudFatherName(String studFatherName) {
      this.stud_fatherName = studFatherName;
  }

  public String getStudFatherOccupation() {
      return stud_fatherOccupation;
  }

  public void setStudFatherOccupation(String studFatherOccupation) {
      this.stud_fatherOccupation = studFatherOccupation;
  }

  public String getStudFatherContactNumber() {
      return stud_fatherContactNumber;
  }

  public void setStudFatherContactNumber(String studFatherContactNumber) {
      this.stud_fatherContactNumber = studFatherContactNumber;
  }

  public String getStudMotherName() {
      return stud_motherName;
  }

  public void setStudMotherName(String studMotherName) {
      this.stud_motherName = studMotherName;
  }

  public String getStudMotherOccupation() {
      return stud_motherOccupation;
  }

  public void setStudMotherOccupation(String studMotherOccupation) {
      this.stud_motherOccupation = studMotherOccupation;
  }

  public String getStudMotherContactNumber() {
      return stud_motherContactNumber;
  }

  public void setStudMotherContactNumber(String studMotherContactNumber) {
      this.stud_motherContactNumber = studMotherContactNumber;
  }

  public String getStudEmergencyName() {
      return stud_emergencyName;
  }

  public void setStudEmergencyName(String studEmergencyName) {
      this.stud_emergencyName = studEmergencyName;
  }

  public String getStudEmergencyRelationship() {
      return stud_emergencyRelationship;
  }

  public void setStudEmergencyRelationship(String studEmergencyRelationship) {
      this.stud_emergencyRelationship = studEmergencyRelationship;
  }

  public String getStudEmergencyPhone() {
      return stud_emergencyPhone;
  }

  public void setStudEmergencyPhone(String studEmergencyPhone) {
      this.stud_emergencyPhone = studEmergencyPhone;
  }

  public String getStudEmergencyAddress() {
      return stud_emergencyAddress;
  }

  public void setStudEmergencyAddress(String studEmergencyAddress) {
      this.stud_emergencyAddress = studEmergencyAddress;
  }

  public void setStudDateEnrolled(String dateEnrolled){
    this.stud_dateEnrolled = dateEnrolled;
  }

  public String getStudDateEnrolled(){
    return this.stud_dateEnrolled;
  }
}
