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
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Admin Menu:");
                System.out.println("1. List of Students");
                System.out.println("2. Add a student");
                System.out.println("3. Add a staff");
                System.out.println("4. History");
                System.out.println("5. Admin data");
                System.out.println("6. Staff data");
                System.out.println("7. Sign out");
                System.out.print("Select an Option: ");
                String choice = scanner.nextLine();

                clearScreen();

                switch (choice) {
                    case "1":
                        viewStudentList();
                        break;
                    case "2":
                        addStudent();
                        break;
                    case "3":
                        addStaff();
                        break;
                    case "4":
                        seeHistory();
                        break;
                    case "5":
                        System.out.println("Admin Data");
                        System.out.println("Username: " + SessionStorage.username);
                        System.out.println("Password: " + SessionStorage.password);
                        System.out.println("\n1. Update Username and Password");
                        System.out.println("2. Exit");
                        System.out.print("Select an option: ");
                        int adminChoice = scanner.nextInt();
                        scanner.nextLine();
                        clearScreen();
                        switch (adminChoice) {
                            case 1:
                                updateAdminData();
                                break;
                            case 2:
                                returnHome();
                                break;
                            default:
                                System.out.println("\nInvalid input");
                                break;
                        }
                        break;
                    case "6":
                        getStaff();
                        // Staff Data. Change data.
                        break;
                    case "7":
                        // Sign out
                        App.main(null);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                clearScreen();
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
  
    public void staffMenu(){
        while (true) {
            System.out.println("Staff Home Program");
            System.out.println("1. Insert Student");
            System.out.println("2. List of Student");
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
                  viewStudentList();
                  break;
                case 3:
                    App.main(null);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
        }
    }

    public void getStaff(){
        response = HttpUtil.sendPostRequest("getAllStaff", null, "admin.php");
        if(!response.equalsIgnoreCase("0")){
            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
            int i = 1;
            for (JsonElement element : jsonArray){
                JsonObject response = element.getAsJsonObject();
                String fullName = response.get("user_fullName").getAsString();
                System.out.println(i + ". " + fullName);
                i++;
            }
            System.out.print("\nEnter the student's code to view: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();
            if(index >= 0 && index < jsonArray.size()){
                JsonObject selectedStaff = jsonArray.get(index).getAsJsonObject();
                int userId = selectedStaff.get("user_id").getAsInt();
                queryParams.put("user_id", String.valueOf(userId));
                System.out.println("Request parameters: " + queryParams);
                response = HttpUtil.sendPostRequest("getSelectedStaff", queryParams, "admin.php");
                System.out.println("response: " + response);
                JsonObject selectedUser = JsonParser.parseString(response).getAsJsonObject();
                getSelectedStaff(selectedUser);
            }
        }
    }

    public void getSelectedStaff(JsonObject selectedStaff) {
        System.out.println("Selected Staff Data:\n");
        System.out.println("Username: " + selectedStaff.get("user_username").getAsString());
        System.out.println("Password: " + selectedStaff.get("user_password").getAsString());
        System.out.println("Full name: " + selectedStaff.get("user_fullName").getAsString());
        System.out.println("Email: " + selectedStaff.get("user_email").getAsString());
        System.out.println("Contact Number: " + selectedStaff.get("user_contactNumber").getAsString());
        System.out.println("Address: " + selectedStaff.get("user_address").getAsString());

        System.out.println("\n1. Update staff\n2. Delete staff\n3. Home");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                //updateStaff();
                break;
            case "2":
                // deleteStaff(); <are you sure>
                break;
            case "3":
                returnHome();
                break;
        }
    }

    public void updateAdminData(){
        System.out.println("To keep the current values, leave the fields blank..\n");
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

        // String jsonSent = new Gson().toJson(queryParams);
        // System.out.println("JSON Sent: " + jsonSent);
        // System.out.println("response: " + response);
        clearScreen();
        response = HttpUtil.sendPostRequest("updateAdmin", queryParams, "admin.php");
        if(response.equalsIgnoreCase("1")){
            System.out.println("Updated successfully!\n");
        }else if(response.equalsIgnoreCase("0")){
            System.out.println("User information remains unchanged.\n");
        }else{
            System.out.println("There was an error: " + response);
        }
        returnHome();
    }

    public void seeHistory(){
        clearScreen();
        System.out.print("History Data\n1. Added student history\n2. Updated student history\n3. Deleted student history\n4. Return to Home\nChoice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
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
            case 4:
                returnHome();
                break;
        }
    }

    public void getHistory(String operation, String dateString, String statusMessage) {
        clearScreen();
        response = HttpUtil.sendPostRequest(operation, null, "admin.php");
        if (!response.equalsIgnoreCase("0")) {
            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
            int i = 1;
            for (JsonElement element : jsonArray) {
                JsonObject response = element.getAsJsonObject();
                String username = response.get("user_fullName").getAsString();
                String studname = response.get(operation.equals("getDeleteHistory") ? "delhist_fullName" : "stud_fullName").getAsString();
                String date = response.get(dateString).getAsString();
                System.out.println(i + ". " + username + statusMessage + studname + " in " + date);
                i++;
            }

            if (operation.equalsIgnoreCase("getDeleteHistory")) {
                System.out.print("\nEnter the history entry number to view: ");
                int selectedIndex = scanner.nextInt();
                scanner.nextLine();
                if (selectedIndex >= 1 && selectedIndex <= jsonArray.size()) {
                    JsonObject selectedEntry = jsonArray.get(selectedIndex - 1).getAsJsonObject();
                    getSelectedDelStudent(selectedEntry);
                } else {
                    System.out.println("Invalid selection.");
                }
            }
        } else {
            System.out.println("No data found");
        }

        System.out.println("");
    }

    public void getSelectedDelStudent(JsonObject studentEntry){
        queryParams.put("delhist_id", studentEntry.get("delhist_id").toString());
        response = HttpUtil.sendPostRequest("getSelectedDeletedStudent", queryParams, "admin.php");
        JsonObject deletedStudentData = JsonParser.parseString(response).getAsJsonObject();
        clearScreen();
        printDeletedStudent(deletedStudentData);
        System.out.print("\n1. Retrieve student data\n2. Home\nChoice: ");
        String choice = scanner.nextLine();
        clearScreen();
        switch(choice) {
            case "1":
                retrieveStudent(deletedStudentData);
                break;
            case "2":
                returnHome();
                break;
            default:
                System.out.println("Invalid choice: ");
        }
    }

    public void retrieveStudent(JsonObject deletedStudent) {
        queryParams.put("delstud_id", String.valueOf(deletedStudent.get("delstud_id").getAsInt()));
        queryParams.put("delstud_fullName", deletedStudent.get("delstud_fullName").getAsString());
        queryParams.put("delstud_schoolId", deletedStudent.get("delstud_schoolId").getAsString());
        queryParams.put("delstud_birthday", deletedStudent.get("delstud_birthday").getAsString());
        queryParams.put("delstud_birthplace", deletedStudent.get("delstud_birthplace").getAsString());
        queryParams.put("delstud_gender", deletedStudent.get("delstud_gender").getAsString());
        queryParams.put("delstud_religion", deletedStudent.get("delstud_religion").getAsString());
        queryParams.put("delstud_address", deletedStudent.get("delstud_address").getAsString());
        queryParams.put("delstud_email", deletedStudent.get("delstud_email").getAsString());
        queryParams.put("delstud_contactNumber", deletedStudent.get("delstud_contactNumber").getAsString());
        queryParams.put("delstud_prevSchool", deletedStudent.get("delstud_prevSchool").getAsString());
        queryParams.put("delstud_course", deletedStudent.get("delstud_course").getAsString());
        queryParams.put("delstud_gradeLevel", deletedStudent.get("delstud_gradeLevel").getAsString());
        queryParams.put("delstud_yearGraduated", deletedStudent.get("delstud_yearGraduated").getAsString());
        queryParams.put("delstud_fatherName", deletedStudent.get("delstud_fatherName").getAsString());
        queryParams.put("delstud_fatherOccupation", deletedStudent.get("delstud_fatherOccupation").getAsString());
        queryParams.put("delstud_fatherContactNumber", deletedStudent.get("delstud_fatherContactNumber").getAsString());
        queryParams.put("delstud_motherName", deletedStudent.get("delstud_motherName").getAsString());
        queryParams.put("delstud_motherOccupation", deletedStudent.get("delstud_motherOccupation").getAsString());
        queryParams.put("delstud_motherContactNumber", deletedStudent.get("delstud_motherContactNumber").getAsString());
        queryParams.put("delstud_emergencyName", deletedStudent.get("delstud_emergencyName").getAsString());
        queryParams.put("delstud_emergencyRelationship", deletedStudent.get("delstud_emergencyRelationship").getAsString());
        queryParams.put("delstud_emergencyPhone", deletedStudent.get("delstud_emergencyPhone").getAsString());
        queryParams.put("delstud_emergencyAddress", deletedStudent.get("delstud_emergencyAddress").getAsString());
        response = HttpUtil.sendPostRequest("retrieveStudent", queryParams, "admin.php");
        // String jsonSent = new Gson().toJson(queryParams);
        // System.out.println("JSON Sent: " + jsonSent);
        // System.out.println("response: " + response);
        if(response.equalsIgnoreCase("1")){
            System.out.println("Student information has been successfully retrieved.\n");
        }else if(response.equalsIgnoreCase("0")){
            System.out.println("No student information found for the given criteria.\n");
        }else{
            System.out.println("An unexpected error occurred while retrieving student information.\n");
        }


    }

    public void printDeletedStudent(JsonObject deletedStudent) {
        System.out.println("Deleted Student Data: \n");
        System.out.println("Full name: " + deletedStudent.get("delstud_fullName").getAsString());
        System.out.println("School Id: " + deletedStudent.get("delstud_schoolId").getAsString());
        System.out.println("Date of Birth: " + deletedStudent.get("delstud_birthday").getAsString());
        System.out.println("Place of Birth: " + deletedStudent.get("delstud_birthplace").getAsString());
        System.out.println("Gender: " + deletedStudent.get("delstud_gender").getAsString());
        System.out.println("Religion: " + deletedStudent.get("delstud_religion").getAsString());
        System.out.println("Address: " + deletedStudent.get("delstud_address").getAsString());
        System.out.println("Email: " + deletedStudent.get("delstud_email").getAsString());
        System.out.println("Contact Number: " + deletedStudent.get("delstud_contactNumber").getAsString());
        System.out.println("Previous School: " + deletedStudent.get("delstud_prevSchool").getAsString());
        System.out.println("Course Code: " + deletedStudent.get("delstud_course").getAsString());
        System.out.println("Grade Level: " + deletedStudent.get("delstud_gradeLevel").getAsString());
        System.out.println("Year Graduated: " + deletedStudent.get("delstud_yearGraduated").getAsString());
        System.out.println("Father Name: " + deletedStudent.get("delstud_fatherName").getAsString());
        System.out.println("Father Occupation: " + deletedStudent.get("delstud_fatherOccupation").getAsString());
        System.out.println("Father Contact Number: " + deletedStudent.get("delstud_fatherContactNumber").getAsString());
        System.out.println("Mother Name: " + deletedStudent.get("delstud_motherName").getAsString());
        System.out.println("Mother Occupation: " + deletedStudent.get("delstud_motherOccupation").getAsString());
        System.out.println("Mother Contact Number: " + deletedStudent.get("delstud_motherContactNumber").getAsString());
        System.out.println("\nPerson to contact in case of emergency:");
        System.out.println("Name: " + deletedStudent.get("delstud_emergencyName").getAsString());
        System.out.println("Relationship: " + deletedStudent.get("delstud_emergencyRelationship").getAsString());
        System.out.println("Contact Number: " + deletedStudent.get("delstud_emergencyPhone").getAsString());
    }

    public void addStudent() {
        clearScreen();
        System.out.println("Adding Student");

        System.out.print("Student Fullname: ");
        String fullName = scanner.nextLine();

        System.out.print("School ID: ");
        String schoolId = scanner.nextLine();

        System.out.print("Date of Birth: ");
        String dateBirth = scanner.nextLine();

        System.out.print("Place of Birth: ");
        String placeBirth = scanner.nextLine();

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Religion: ");
        String religion = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Contact Number: ");
        String contactNum = scanner.nextLine();

        System.out.print("Previous School: ");
        String prevSchool = scanner.nextLine();

        System.out.print("Course (1)GAS, (2)HUMMS, (3)STEM, (4)ABM, (5)TVL: ");
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
            case 5:
                course = "TVL";
                break;
            default:
                System.out.println("Invalid option. Please try again.\n");
                break;
        }

        System.out.print("Year Level: ");
        String yearLevel = scanner.nextLine();

        System.out.print("Year Graduated: ");
        String yearGrad = scanner.nextLine();

        System.out.print("Father Fullname: ");
        String fatherName = scanner.nextLine();

        System.out.print("Father Occupation: ");
        String fatherOccup = scanner.nextLine();

        System.out.print("Father Contact Number: ");
        String fatherContact = scanner.nextLine(); 

        System.out.print("Mother Fullname: ");
        String motherName = scanner.nextLine();

        System.out.print("Mother Occupation: ");
        String motherOccup = scanner.nextLine();

        System.out.print("Mother Contact Number: ");
        String motherContact = scanner.nextLine(); 

        System.out.println("Emergency Contact");
        System.out.print("Emergency FullName: ");
        String emergencyName = scanner.nextLine();

        System.out.print("Emergency Relationship: ");
        String emergencyRel = scanner.nextLine();

        System.out.print("Emergency Contact Number: ");
        String emergencyContact = scanner.nextLine(); 

        System.out.print("Emergency Address: ");
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

        // System.out.println("response: " + response);
        if (response.equalsIgnoreCase("1")) {
            System.out.println("\nStudent added successfully");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("\nStudent failed to add");
        } else {
            System.out.println("\nUnexpected response from the server: " + response);
        }
    }

    public void viewStudentList() {
        System.out.print("1. View all students\n2. View all students organized by strand\n3. Search student\n4. Return to Home\nChoice: ");
        int choiceView = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch(choiceView){
            case 1:
                getAllStudent();
                break;
            case 2:
                getAllStudentByStrand();
                break;
            case 3:
                searchStudent();
                break;
            case 4:
                returnHome();
                break;    
                
        }
        student = gson.fromJson(response, Student.class);
        System.out.println("Student Data: \n");
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
        while (true) {
            System.out.print("\n1. Edit Data\n2. Delete / Remove file\n3. Back to Student list\n4. Home\n Choice: ");
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
                    returnHome();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void getAllStudent(){
        Student student = new Student();
        response = HttpUtil.sendPostRequest("getAllStudent", student, "users.php");
        if(response.equals("0")){
            System.out.println("Student list is currently empty.");
            staffMenu();
        }
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
        System.out.println("Student List: \n");
        int i = 1;
        for (JsonElement element : jsonArray) {
            JsonObject response = element.getAsJsonObject();
            String fullName = response.get("stud_fullName").getAsString();
            System.out.println(i + ". " + fullName);
            i++;
        }
        System.out.print("\nEnter the student's code to view: ");
        int index = scanner.nextInt() - 1;
        if(index >= 0 && index < jsonArray.size()){
            JsonObject selectedStudent = jsonArray.get(index).getAsJsonObject();
            int studId = selectedStudent.get("stud_id").getAsInt();
            queryParams.put("stud_Id", String.valueOf(studId));
            response = HttpUtil.sendPostRequest("getSelectedStudent", queryParams, "users.php" );
            clearScreen();
        } else {
            clearScreen();
            System.out.println("Invalid student code.");
            viewStudentList();
        }
    }

    public void getAllStudentByStrand(){
        System.out.print("1. GAS\n2. HUMMS\n3. STEM\n4. ABM\n5. TVL\n6. Exit\nChoice: ");
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
            case 5:
                course = "TVL";
                break;
            case 6:
                viewStudentList();
                break;
            default:
                System.out.println("Invalid option. Please try again.\n");
                break;
        }
        queryParams.put("stud_course", course);
        response = HttpUtil.sendPostRequest("getAllStudentByStrand", queryParams, "users.php");
        // String jsonSent = new Gson().toJson(queryParams);
        // System.out.println("JSON Sent: " + jsonSent);
        // System.out.println("response: " + response);
        clearScreen();
        if(response.equalsIgnoreCase("0")){
            System.out.println("Student list is currently empty.\n");
            returnHome();
        }else{
            try {
                JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
                System.out.println(course + " Student List: \n");
                int i = 1;
                for (JsonElement element : jsonArray) {
                    JsonObject response = element.getAsJsonObject();
                    String fullName = response.get("stud_fullName").getAsString();
                    System.out.println(i + ". " + fullName);
                    i++;
                }
                System.out.print("\nEnter the number of the student you want to view: ");
                int index = scanner.nextInt() - 1;
                if(index >= 0 && index < jsonArray.size()){
                    JsonObject selectedStudent = jsonArray.get(index).getAsJsonObject();
                    int studId = selectedStudent.get("stud_id").getAsInt();
                    queryParams.put("stud_Id", String.valueOf(studId));
                    response = HttpUtil.sendPostRequest("getSelectedStudent", queryParams, "users.php" );
                    clearScreen();
                } else {
                    clearScreen();
                    System.out.println("Invalid student number.");
                    viewStudentList();
                }
            } catch (Exception e) {
                System.out.println("Nag error siya " + e.getMessage());
                JsonObject studentObject = JsonParser.parseString(response).getAsJsonObject();
            }

        }
    }

    public void searchStudent(){
        try {
            System.out.print("Enter student school Id: ");
            String schoolId = scanner.nextLine();
            queryParams.put("stud_schoolId", schoolId);
            response = HttpUtil.sendPostRequest("searchStudent", queryParams, "users.php");
            // System.out.println("response: " + response);
            if(response.equalsIgnoreCase("0")){
                clearScreen();
                System.out.println("Invalid school id");
                searchStudent();
            }
            clearScreen();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
        System.out.print("Enter Course (1)GAS, (2)HUMMS, (3)STEM, (4)ABM, (5)TVL: ");
        String course = "";
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                try {
                    int studCourse = Integer.parseInt(input);

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
                        case 5:
                            course = "TVL";
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.\n");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.\n");
                }
            }

            if (!course.isEmpty()) {
                studentToUpdate.setStudCourse(course);
            }
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
        String fullName, username, password, contact, address, email;
        System.out.println("Adding Staff");
        System.out.print("Enter staff full name: ");
        fullName = scanner.nextLine();
        System.out.print("Enter staff username: ");
        username = scanner.nextLine();
        System.out.print("Enter staff password: ");
        password = scanner.nextLine();
        System.out.print("Enter staff contact number: ");
        contact = scanner.nextLine();
        System.out.print("Enter address: ");
        address = scanner.nextLine();
        System.out.print("Enter staff email: ");
        email = scanner.nextLine();

        User user = new User(fullName, username, password, contact, address, email);
        response = HttpUtil.sendPostRequest("addStaff", user, "admin.php");
        clearScreen();
        if (response.equalsIgnoreCase("1")) {
            System.out.println("Staff added successfully\n");
        } else if (response.equalsIgnoreCase("0")) {
            System.out.println("Failed to add staff\n");
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

    public void returnHome(){
        if(SessionStorage.userLevel == 100){
            adminMenu();
        }else{
            staffMenu();
        }
    }
}