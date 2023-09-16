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
    Student student = new Student();
    Gson gson = new Gson();

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
            switch (choice) {
                case 1:
                  addStudent();
                  break;
                case 2:
                  viewStudentList();
                  break;
                case 3:
                  // go back login
                  break;
                case 4:
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
        System.out.print("Enter student date enrolled: ");
        String dateEnrolled = scanner.nextLine();
        System.out.print("Enter student address: ");
        String address = scanner.nextLine();

        Student student = new Student(schoolId, fullName, gender, email, courseCode, yearLevel, dateEnrolled, address);
        response = HttpUtil.sendPostRequest("addStudent", student, "users.php");
        if (response.equalsIgnoreCase("1")) {
            System.out.println("Student added successfully");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Student failed to add");
        } else {
            System.out.println("Unexpected response from the server: " + response);
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
            System.out.println("Date Enrolled: " + student.getStudentDateEnrolled());
            System.out.println("Address: " + student.getStudentAddress());
            
        } else {
            System.out.println("Invalid student number.");
        }

        while (true) {
            System.out.print("\n\n1. Edit Information\n2. Delete / Remove file\n3. Back to Student list\nChoice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // update student
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