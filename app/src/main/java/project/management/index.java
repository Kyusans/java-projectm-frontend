package project.management;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

class Index{
    String response = "";
    Scanner scanner = new Scanner(System.in);
    App app = new App();
    Gson gson = new Gson();
    Student student = new Student();
    Map<String, String> queryParams = new HashMap<>();

    public String login(String username, String password) {
        User user = new User(username, password);
        return HttpUtil.sendPostRequest("login", user, "users.php");
    }

    public void adminMenu() {
        int choice = 0;
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add student");
            System.out.println("2. Add staff");
            System.out.println("3. See history");
            System.out.println("4. Admin data");
            System.out.println("5. Sign out");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            clearScreen();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addStaff();
                    break;
                case 3:
                    seeHistory();
                    break;
                case 4:
                    System.out.println("Admin Data");
                    System.out.println("Username: " + SessionStorage.username);
                    System.out.println("Password: " + SessionStorage.password);
                    System.out.println("\n1. Update Username and Password");
                    System.out.println("2. Exit");
                    System.out.print("Select an option: ");
                    int adminChoice = scanner.nextInt();
                    scanner.nextLine();
                    clearScreen();
                    switch(adminChoice){
                        case 1:
                            updateAdminData();
                            break;
                        case 2:
                            adminMenu();
                        default:
                            System.out.println("\nInvalid input");
                    }
                    break;
                case 5:
                    // sign out
                    App.main(null);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void staffMenu(){
        while (true) {
            System.out.println("Staff Home Program");
            System.out.println("1. Add Student");
            System.out.println("2. View Student List");
            System.out.println("3. Sign out");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            clearScreen();
            switch (choice) {
                case 1:
                  addStudent();
                  break;
                case 2:
                  clearScreen();
                  viewStudentList();
                  break;
                case 3:
                    App.main(null);
                    break;
                default:
                    clearScreen();
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
        }
    }

    public void updateAdminData(){
        System.out.println("Leave fields blank to keep the current values.\n");
        System.out.print("Username [" + SessionStorage.username + "]: ");
        String username = scanner.nextLine();
        if(!username.isEmpty()){
            SessionStorage.username = username;
        }

        System.out.print("Password [" + SessionStorage.password + "]: ");
        String password = scanner.nextLine();
        if(!username.isEmpty()){
            SessionStorage.password = password;
        }
        String userId = String.valueOf(SessionStorage.userId);
        queryParams.put("user_username", SessionStorage.username);
        queryParams.put("user_password", SessionStorage.password);
        queryParams.put("user_id", userId);
        response = HttpUtil.sendPostRequest("updateAdmin", queryParams, "admin.php");
        if(response == "1"){
            System.out.println("Successfully updated");
        }else{
            System.out.println("Failed to update");
        }
    }

    public void seeHistory(){
        clearScreen();
        System.out.print("Select History\n1. Add student history\n2. Update student history\n3. Delete student history\nChoice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                getHistory("getAddStudentHistory", "addhist_dateAdded", " added ");
                break;
            case 2:
                getHistory("getUpdateHistory", "uphist_dateUpdated", " updated ");
                break;
            case 3:
                getHistory("getDeleteHistory", "delhist_dateDeleted", " deleted ");
                break;
        }
    }

    public void getHistory(String operation, String dateString, String statusMessage){
        clearScreen();
        response = HttpUtil.sendPostRequest(operation, null, "admin.php");
        if(!response.equalsIgnoreCase("0")){
            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
            int i = 1;
            for (JsonElement element : jsonArray) {
                JsonObject response = element.getAsJsonObject();
                String username = response.get("user_fullName").getAsString();
                String studname = response.get(operation.equals("getDeleteHistory") ? "delhist_fullName" : "stud_fullName" ).getAsString();
                String date = response.get(dateString).getAsString();
                System.out.println(i + ". " + username + statusMessage + studname + " in " + date);
                i++;
            }
        }else{
            System.out.println("No data found");
        }
        System.out.println("");
    }

    public void addStudent() {
        clearScreen();
        System.out.println("Adding Student");

        System.out.print("Enter Student Fullname: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter School ID: ");
        String schoolId = scanner.nextLine();

        System.out.print("Enter Date of Birth: ");
        String dateBirth = scanner.nextLine();

        System.out.print("Enter Place of Birth: ");
        String placeBirth = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Religion: ");
        String religion = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNum = scanner.nextLine();

        System.out.print("Enter Previous School: ");
        String prevSchool = scanner.nextLine();

        System.out.print("Enter Course (1)GAS, (2)HUMMS, (3)STEM, (4)ABM: ");
        int courseNum = scanner.nextInt();
        scanner.nextLine(); 
        String course = "";
        switch(courseNum){
            case 1:
                course = "GAS";
                break;
            case 2:
                course = "HUMMS";
                break;
            case 3:
                course = "STEM";
                break;
            case 4:
                course = "ABM";
                break;
            default:
                System.out.println("Invalid option. Please try again.\n");
                break;
        }

        System.out.print("Enter Year Level: ");
        String yearLevel = scanner.nextLine();

        System.out.print("Enter Year Graduated: ");
        String yearGrad = scanner.nextLine();

        System.out.print("Enter Father Fullname: ");
        String fatherName = scanner.nextLine();

        System.out.print("Enter Father Occupation: ");
        String fatherOccup = scanner.nextLine();

        System.out.print("Enter Father Contact Number: ");
        String fatherContact = scanner.nextLine(); 

        System.out.print("Enter Mother Fullname: ");
        String motherName = scanner.nextLine();

        System.out.print("Enter Mother Occupation: ");
        String motherOccup = scanner.nextLine();

        System.out.print("Enter Mother Contact Number: ");
        String motherContact = scanner.nextLine(); 

        System.out.println("Emergency Contact");
        System.out.print("Enter Emergency FullName: ");
        String emergencyName = scanner.nextLine();

        System.out.print("Enter Emergency Relationship: ");
        String emergencyRel = scanner.nextLine();

        System.out.print("Enter Emergency Contact Number: ");
        String emergencyContact = scanner.nextLine(); 

        System.out.print("Enter Emergency Address: ");
        String emergencyAdd = scanner.nextLine();

        int userId = SessionStorage.userId;

        Student student = new Student(fullName, schoolId, dateBirth, placeBirth, gender, religion, 
        address, email, contactNum, prevSchool, course, yearLevel, yearGrad, fatherName, fatherOccup, 
        fatherContact, motherName, motherOccup, motherContact, emergencyName, emergencyRel, emergencyContact, 
        emergencyAdd, userId);


        response = HttpUtil.sendPostRequest("addStudent", student, "users.php");
        clearScreen();
        // String studentJson = new Gson().toJson(student);
        // System.out.println("JSON Sent: " + studentJson);

        System.out.println("response: " + response);
        if (response.equalsIgnoreCase("1")) {
            System.out.println("\nStudent added successfully");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("\nStudent failed to add");
        } else {
            System.out.println("\nUnexpected response from the server: " + response);
        }
    }

    public void viewStudentList() {
        Student student = new Student();
        response = HttpUtil.sendPostRequest("getAllStudent", student, "users.php");
        if(response.equals("0")){
            System.out.println("Student list is currently empty.");
            staffMenu();
        }
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
        System.out.println("Student List: \n");
        System.out.println("0. Search Student");
        int i = 1;
        for (JsonElement element : jsonArray) {
            JsonObject response = element.getAsJsonObject();
            String fullName = response.get("stud_fullName").getAsString();
            System.out.println(i + ". " + fullName);
            i++;
        }
        System.out.print("\nEnter the number of the student you want to view: ");
        int index = scanner.nextInt() - 1;
        if(index == -1){
            System.out.println("index : " + index);
            System.out.println("search ni siya diri");
        }else if(index >= 0 && index < jsonArray.size()){
            JsonObject selectedStudent = jsonArray.get(index).getAsJsonObject();
            int studId = selectedStudent.get("stud_id").getAsInt();
            queryParams.put("stud_Id", String.valueOf(studId));
            response = HttpUtil.sendPostRequest("getSelectedStudent", queryParams, "users.php" );
            student = gson.fromJson(response, Student.class);
            clearScreen();
            System.out.println("Student information: \n");
            System.out.println("Full name: " + student.getStudFullName());
            System.out.println("School Id: " + student.getStudSchoolId());
            System.out.println("Date of Birth: " + student.getStudBirthday());
            System.out.println("Place of Birth: " + student.getStudBirthplace());
            System.out.println("Gender: " + student.getStudGender());
            System.out.println("Religion: " + student.getStudReligion());
            System.out.println("Address: " + student.getStudAddress());
            System.out.println("Email: " + student.getStudEmail());
            System.out.println("Contact Number: " + student.getStudContactNumber());
            System.out.println("Previous School: " + student.getStudPrevSchool());
            System.out.println("Course Code: " + student.getStudCourse());
            System.out.println("Grade Level: " + student.getStudGradeLevel());
            System.out.println("Year Graduated: " + student.getStudYearGraduated());
            System.out.println("Father Name: " + student.getStudFatherName());
            System.out.println("Father Occupation: " + student.getStudFatherOccupation());
            System.out.println("Father Contact Number: " + student.getStudFatherContactNumber());
            System.out.println("Mother Name: " + student.getStudMotherName());
            System.out.println("Mother Occupation: " + student.getStudMotherOccupation());
            System.out.println("Mother Contact Number: " + student.getStudMotherContactNumber());
            System.out.println("\nPerson to contact in case of emergency:");
            System.out.println("Name: " + student.getStudEmergencyName());
            System.out.println("Relationship: " + student.getStudEmergencyRelationship());
            System.out.println("Contact Number: " + student.getStudEmergencyPhone());
        } else {
            clearScreen();
            System.out.println("Invalid student number.");
            viewStudentList();
        }

        while (true) {
            System.out.print("\n1. Edit Information\n2. Delete / Remove file\n3. Back to Student list\n4. Home\nChoice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            clearScreen();
            switch (choice) {
                case 1:
                    clearScreen();
                    updateStudent(student);
                    break; 
                case 2:
                    deleteStudent(student, SessionStorage.userId);
                    break; 
                case 3:
                    clearScreen();
                    System.out.println("Returning to student list.\n");
                    viewStudentList();
                    break; 
                case 4:
                    staffMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void updateStudent(Student studentToUpdate){
        System.out.println("Edit Student Information:");
        System.out.println("Leave fields blank to keep the current values.\n");
        System.out.print("Full name [" + studentToUpdate.getStudFullName() + "]: ");
        String studFullName = scanner.nextLine();
        if(!studFullName.isEmpty()){
            studentToUpdate.setStudFullName(studFullName);
        }

        System.out.print("School Id [" + studentToUpdate.stud_schoolId + "]: ");
        String studSchoolId = scanner.nextLine();
        if(!studSchoolId.isEmpty()){
            studentToUpdate.setStudSchoolId(studSchoolId);
        }
        
        System.out.print("Date of Birth [" + studentToUpdate.stud_birthday + "]: ");
        String studBirthday = scanner.nextLine();
        if(!studBirthday.isEmpty()){
            studentToUpdate.setStudBirthday(studBirthday);
        }
        
        System.out.print("Place of Birth [" + studentToUpdate.stud_birthplace + "]: ");
        String studBirthplace = scanner.nextLine();
        if(!studBirthplace.isEmpty()){
            studentToUpdate.setStudBirthplace(studBirthplace);
        }
        
        System.out.print("Gender [" + studentToUpdate.stud_gender + "]: ");
        String studGender = scanner.nextLine();
        if(!studGender.isEmpty()){
            studentToUpdate.setStudGender(studGender);
        }
        
        System.out.print("Religion [" + studentToUpdate.stud_religion + "]: ");
        String studReligion = scanner.nextLine();
        if(!studReligion.isEmpty()){
            studentToUpdate.setStudReligion(studReligion);
        }
        
        System.out.print("Address [" + studentToUpdate.stud_address + "]: ");
        String studAddress = scanner.nextLine();
        if(!studAddress.isEmpty()){
            studentToUpdate.setStudAddress(studAddress);
        }

        System.out.print("Email [" + studentToUpdate.stud_email + "]: ");
        String studEmail = scanner.nextLine();
        if(!studEmail.isEmpty()){
            studentToUpdate.setStudEmail(studEmail);
        }
        
        System.out.print("Contact Number [" + studentToUpdate.stud_contactNumber + "]: ");
        String studContactNumber = scanner.nextLine();
        if(!studContactNumber.isEmpty()){
            studentToUpdate.setStudContactNumber(studContactNumber);
        }
        
        System.out.print("Previous School [" + studentToUpdate.stud_prevSchool + "]: ");
        String studPrevSchool = scanner.nextLine();
        if(!studPrevSchool.isEmpty()){
            studentToUpdate.setStudPrevSchool(studPrevSchool);
        }

        System.out.println("Course Code [" + studentToUpdate.stud_course + "] ");
        System.out.print("Enter Course (1)GAS, (2)HUMMS, (3)STEM, (4)ABM: ");

        if (scanner.hasNextInt()) {
            int studCourse = scanner.nextInt();
            scanner.nextLine();

            String course = "";
            switch (studCourse) {
                case 1:
                    course = "GAS";
                    break;
                case 2:
                    course = "HUMMS";
                    break;
                case 3:
                    course = "STEM";
                    break;
                case 4:
                    course = "ABM";
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }

            if (!course.isEmpty()) {
                studentToUpdate.setStudCourse(course);
            }
        } else {
            System.out.println("Invalid input.\n");
            scanner.nextLine();
        }
        
        System.out.print("Grade Level [" + studentToUpdate.stud_gradeLevel + "]: ");
        String studGradeLevel = scanner.nextLine();
        if(!studGradeLevel.isEmpty()){
            studentToUpdate.setStudGradeLevel(studGradeLevel);
        }

        System.out.print("Year Graduated [" + studentToUpdate.stud_yearGraduated + "]: ");
        String studYearGraduated = scanner.nextLine();
        if(!studYearGraduated.isEmpty()){
            studentToUpdate.setStudYearGraduated(studYearGraduated);
        }

        System.out.print("Father Name [" + studentToUpdate.stud_fatherName + "]: ");
        String studFatherName = scanner.nextLine();
        if(!studFatherName.isEmpty()){
            studentToUpdate.setStudFatherName(studFatherName);
        }

        System.out.print("Father Occupation [" + studentToUpdate.stud_fatherOccupation + "]: ");
        String studFatherOccupation = scanner.nextLine();
        if(!studFatherOccupation.isEmpty()){
            studentToUpdate.setStudFatherOccupation(studFatherOccupation);
        }

        System.out.print("Fahter Contact Number [" + studentToUpdate.getStudFatherContactNumber() + "]: ");
        String studFatherContactNumber = scanner.nextLine();
        if(!studFatherContactNumber.isEmpty()){
            studentToUpdate.setStudFatherContactNumber(studFatherContactNumber);
        }

        System.out.print("Mother Name [" + studentToUpdate.stud_motherName + "]: ");
        String studMotherName = scanner.nextLine();
        if(!studMotherName.isEmpty()){
            studentToUpdate.setStudMotherName(studMotherName);
        }
        
        System.out.print("Mother Occupation [" + studentToUpdate.stud_motherOccupation + "]: ");
        String studMotherOccupation = scanner.nextLine();
        if(!studMotherOccupation.isEmpty()){
            studentToUpdate.setStudMotherOccupation(studMotherOccupation);
        }

        System.out.println("\nPerson to contact in case of emergency:");
        System.out.print("Name [" + studentToUpdate.stud_emergencyName + "]: ");
        String studEmergencyName = scanner.nextLine();
        if(!studEmergencyName.isEmpty()){
            studentToUpdate.setStudEmergencyName(studEmergencyName);
        }

        System.out.print("Relationship [" + studentToUpdate.getStudEmergencyRelationship() + "]: ");
        String studEmergencyRelationship = scanner.nextLine();
        if(!studEmergencyRelationship.isEmpty()){
            studentToUpdate.setStudEmergencyRelationship(studEmergencyRelationship);
        }
        
        System.out.print("Contact Number [" + studentToUpdate.getStudEmergencyPhone() + "]: ");
        String studEmergencyPhone = scanner.nextLine();
        if(!studEmergencyPhone.isEmpty()){
            studentToUpdate.setStudEmergencyPhone(studEmergencyPhone);
        }

        System.out.print("Address ["+ studentToUpdate.stud_emergencyAddress+"]: ");
        String studEmergencyAddress = scanner.nextLine();
        if(!studEmergencyAddress.isEmpty()){
            studentToUpdate.setStudEmergencyAddress(studEmergencyAddress);
        }
        
        int userId = SessionStorage.userId;
        Student student = new Student(studentToUpdate.stud_id, userId, studentToUpdate.stud_fullName, studentToUpdate.stud_schoolId, studentToUpdate.stud_birthday,
        studentToUpdate.stud_birthplace, studentToUpdate.stud_gender, studentToUpdate.stud_religion, studentToUpdate.stud_address,
        studentToUpdate.stud_email, studentToUpdate.stud_contactNumber, studentToUpdate.stud_prevSchool, studentToUpdate.stud_course,
        studentToUpdate.stud_gradeLevel, studentToUpdate.stud_yearGraduated, studentToUpdate.stud_fatherName, studentToUpdate.stud_fatherOccupation,
        studentToUpdate.stud_fatherContactNumber, studentToUpdate.stud_motherName, studentToUpdate.stud_motherOccupation,
        studentToUpdate.stud_motherContactNumber, studentToUpdate.stud_emergencyName, studentToUpdate.stud_emergencyRelationship,
        studentToUpdate.stud_emergencyPhone, studentToUpdate.stud_emergencyAddress);
        
        response = HttpUtil.sendPostRequest("updateStudent", student, "users.php");
        clearScreen();
        // String studentJson = new Gson().toJson(student);
        // System.out.println("JSON Sent: " + studentJson);
        if (response.equalsIgnoreCase("1")) {
            System.out.println("\nStudent information has been successfully updated!\n");
            viewStudentList();
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Student information remains unchanged.\n");
        } else {
            System.out.println("Unexpected response from the server: " + response);
        }
        viewStudentList();
    }

    public void deleteStudent(Student studentToDelete, int userId){
        Student student = new Student(studentToDelete.stud_schoolId,studentToDelete.stud_id, userId, studentToDelete.stud_fullName, studentToDelete.stud_birthday, studentToDelete.stud_birthplace,
        studentToDelete.stud_gender, studentToDelete.stud_religion, studentToDelete.stud_address, studentToDelete.stud_email, studentToDelete.stud_contactNumber,
        studentToDelete.stud_prevSchool, studentToDelete.stud_course, studentToDelete.stud_gradeLevel, studentToDelete.stud_yearGraduated, studentToDelete.stud_fatherName,
        studentToDelete.stud_fatherOccupation, studentToDelete.stud_fatherContactNumber, studentToDelete.stud_motherName, studentToDelete.stud_motherOccupation, 
        studentToDelete.stud_motherContactNumber, studentToDelete.stud_emergencyName, studentToDelete.stud_emergencyRelationship, studentToDelete.stud_emergencyPhone,
        studentToDelete.stud_emergencyAddress, studentToDelete.stud_school_id);
        response = HttpUtil.sendPostRequest("deleteStudent", student, "users.php");
        clearScreen();
        // String studentJson = new Gson().toJson(student);
        // System.out.println("JSON Sent: " + studentJson);
        // System.out.println("response: " + response);
        if (response.equalsIgnoreCase("1")) {
            System.out.println("Student successfully deleted\n");
        }else if(response.equalsIgnoreCase("0")){
            System.out.println("Failed to delete student\n");
        }else{
            System.out.println("Unexpected response from the server: " + response);
        }
        viewStudentList();
    }

    public void addStaff() {
        clearScreen();
        String fullName, username, password, email;
        System.out.println("Adding Staff");
        System.out.print("Enter staff full name: ");
        fullName = scanner.nextLine();
        System.out.print("Enter staff username: ");
        username = scanner.nextLine();
        System.out.print("Enter staff password: ");
        password = scanner.nextLine();
        System.out.print("Enter staff email: ");
        email = scanner.nextLine();

        User user = new User(fullName, username, password, email);
        response = HttpUtil.sendPostRequest("addStaff", user, "admin.php");
        clearScreen();
        if (response.equalsIgnoreCase("1")) {
            System.out.println("Staff added successfully");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Failed to add staff");
        } else {
            System.out.println("Unexpected response from the server: " + response);
        }
    }

    public void clearScreen(){
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}