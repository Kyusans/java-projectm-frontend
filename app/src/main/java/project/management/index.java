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
    User user = new User();
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
            System.out.println("4. Sign out");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

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
                    // sign out
                    App.main(null);
                    break;
                case 5:
                    clearScreen();
                    System.out.println("Exiting...");
                    System.exit(0);
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
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                  addStudent();
                  break;
                case 2:
                  clearScreen();
                  viewStudentList();
                  break;
                case 3:
                    // Exit
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    clearScreen();
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
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
        String studentJson = new Gson().toJson(student);
        System.out.println("JSON Sent: " + studentJson);

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
        if(index == 0){
            //search
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
            System.out.println("Gender: " + student.getStudGender());
            System.out.println("Email: " + student.getStudEmail());
            System.out.println("Course Code: " + student.getStudCourse());
            System.out.println("Year Level: " + student.getStudGradeLevel());
            System.out.println("Address: " + student.getStudAddress());
            System.out.println("Date Enrolled: " + student.getStudDateEnrolled());
            
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
                    updateStudent(student);
                    break; 
                case 2:
                    deleteStudent(student.getStudId(), SessionStorage.userId, student.getStudFullName());
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
        clearScreen();
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

        System.out.print("Gender [" + studentToUpdate.stud_gender + "]: ");
        String studGender = scanner.nextLine();
        if(!studGender.isEmpty()){
            studentToUpdate.setStudGender(studGender);
        }

        System.out.print("Email [" + studentToUpdate.stud_email + "]: ");
        String studEmail = scanner.nextLine();
        if(!studEmail.isEmpty()){
            studentToUpdate.setStudEmail(studEmail);
        }
        
        int userId = SessionStorage.userId;
        // Student student = new Student(studentToUpdate.stud_id, studentToUpdate.stud_schoolId, studentToUpdate.stud_fullName, studentToUpdate.stud_gender, studentToUpdate.stud_email, studentToUpdate.stud_courseCode, studentToUpdate.stud_yearLevel, studentToUpdate.stud_dateEnrolled, studentToUpdate.stud_address, userId);
        response = HttpUtil.sendPostRequest("updateStudent", student, "users.php");
        clearScreen();
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

    public void deleteStudent(int studId, int userId, String fullName){
        Student student = new Student(studId, userId, fullName);
        response = HttpUtil.sendPostRequest("deleteStudent", student, "users.php");
        clearScreen();
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