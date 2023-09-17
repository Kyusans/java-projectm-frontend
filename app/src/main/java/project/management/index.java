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

    public String login(String username, String password) {
        User user = new User(username, password);
        return HttpUtil.sendPostRequest("login", user, "users.php");
    }
   
    public void adminMenu() {
        int choice = 0;
        while (choice != 4) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add student");
            System.out.println("2. Add staff");
            System.out.println("3. Sign out");
            System.out.println("4. Exit");
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
                    System.out.println("Signed out as admin.");
                    App.main(null);
                    break;
                case 4:
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
                  viewStudentList();
                  break;
                case 3:
                    // Exit
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
        }
       
    }

    public void addStudent() {
        System.out.println("\nAdding Student");
        System.out.print("Enter student Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter student school Id: ");
        String schoolId = scanner.nextLine();
        System.out.print("Enter student gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        System.out.print("Enter student course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter student year level: ");
        int yearLevel = scanner.nextInt(); 
        scanner.nextLine();
        System.out.print("Enter student address: ");
        String address = scanner.nextLine();

        int userId = SessionStorage.userId;
        Student student = new Student(schoolId, fullName, gender, email, courseCode, yearLevel, address, userId);
        response = HttpUtil.sendPostRequest("addStudent", student, "users.php");
        if (response.equalsIgnoreCase("1")) {
            System.out.println("\nStudent added successfully\n");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("\nStudent failed to add\n");
        } else {
            System.out.println("\nUnexpected response from the server: " + response);
        }
    }

    public void viewStudentList() {
        System.out.println("\nStudent List: ");
        Student student = new Student();
        response = HttpUtil.sendPostRequest("getAllStudent", student, "users.php");

        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
        
        int i = 1;
        for (JsonElement element : jsonArray) {
            JsonObject response = element.getAsJsonObject();
            String fullName = response.get("stud_fullName").getAsString();
            System.out.println(i + ". " + fullName);
            i++;
        }
        System.out.print("Enter the number of the student you want to view: ");
        int index = scanner.nextInt() - 1;
        if(index >= 0 && index < jsonArray.size()){
            JsonObject selectedStudent = jsonArray.get(index).getAsJsonObject();
            // String studentJsonString = selectedStudent.toString();
            int studId = selectedStudent.get("stud_id").getAsInt();
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("stud_Id", String.valueOf(studId));
            response = HttpUtil.sendPostRequest("getSelectedStudent", queryParams, "users.php" );
            student = gson.fromJson(response, Student.class);
            System.out.println("\nStudent information: \n");
            System.out.println("Full name: " + student.getStudentFullName());
            System.out.println("School Id: " + student.getStudentSchoolId());
            System.out.println("Gender: " + student.getStudentGender());
            System.out.println("Email: " + student.getStudentEmail());
            System.out.println("Course Code: " + student.getStudentCourseCode());
            System.out.println("Year Level: " + student.getStudentYearLevel());
            System.out.println("Address: " + student.getStudentAddress());
            System.out.println("Date Enrolled: " + student.getStudentDateEnrolled());
            
        } else {
            System.out.println("Invalid student number.");
        }

        while (true) {
            System.out.print("\n1. Edit Information\n2. Delete / Remove file\n3. Back to Student list\nChoice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    updateStudent(student);
                    break; 
                case 2:
                    System.out.println("Not implemented yet.");
                    break; 
                case 3:
                    System.out.println("Returning to student list.");
                    break; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void updateStudent(Student studentToUpdate){
        System.out.println("\n\nEdit Student Information:");
        System.out.println("Leave fields blank to keep the current values.\n");
        System.out.print("Full name [" + studentToUpdate.getStudentFullName() + "]: ");
        String studFullName = scanner.nextLine();
        if(!studFullName.isEmpty()){
            studentToUpdate.setStudentFullName(studFullName);
        }

        System.out.print("School Id [" + studentToUpdate.stud_schoolId + "]: ");
        String studSchoolId = scanner.nextLine();
        if(!studSchoolId.isEmpty()){
            studentToUpdate.setStudentSchoolId(studSchoolId);
        }

        System.out.print("Gender [" + studentToUpdate.stud_gender + "]: ");
        String studGender = scanner.nextLine();
        if(!studGender.isEmpty()){
            studentToUpdate.setStudentGender(studGender);
        }

        System.out.print("Email [" + studentToUpdate.stud_email + "]: ");
        String studEmail = scanner.nextLine();
        if(!studEmail.isEmpty()){
            studentToUpdate.setStudentEmail(studEmail);
        }

        System.out.print("Course Code [" + studentToUpdate.stud_courseCode + "]: ");
        String studCourseCode = scanner.nextLine();
        if(!studCourseCode.isEmpty()){
            studentToUpdate.setStudentCourseCode(studCourseCode);
        }
        System.out.print("Year Level [" + studentToUpdate.stud_yearLevel + "]: ");
        String studYearLevel = scanner.nextLine();
        if(!studYearLevel.isEmpty()){
            int yearLevel = Integer.parseInt(studYearLevel);
            studentToUpdate.setStudentYearLevel(yearLevel);
        }
        
        System.out.print("Address [" + studentToUpdate.stud_address + "]: ");
        String studAddress = scanner.nextLine();
        if(!studAddress.isEmpty()){
            studentToUpdate.setStudentAddress(studAddress);
        }
        
        int userId = user.getUserId();
        Student student = new Student(studentToUpdate.stud_id, studentToUpdate.stud_schoolId, studentToUpdate.stud_fullName, studentToUpdate.stud_gender, studentToUpdate.stud_email, studentToUpdate.stud_courseCode, studentToUpdate.stud_yearLevel, studentToUpdate.stud_dateEnrolled, studentToUpdate.stud_address, userId);
        response = HttpUtil.sendPostRequest("updateStudent", student, "users.php");
        
        if (response.equalsIgnoreCase("1")) {
            System.out.println("\nStudent information has been successfully updated!");
            viewStudentList();
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Failed to update student information. Please check your input and try again.");
        } else {
            System.out.println("Unexpected response from the server: " + response);
        }
    }

    public void addStaff() {
        String fullName, username, password, email;
        System.out.println("\nAdding Staff");
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

        if (response.equalsIgnoreCase("1")) {
            System.out.println("Staff added successfully");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Failed to add staff");
        } else {
            System.out.println("Unexpected response from the server: " + response);
        }
    }

}