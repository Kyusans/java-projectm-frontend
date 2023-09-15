package project.management;
import java.util.Scanner;
import okhttp3.*;
import java.io.Console;
import com.google.gson.Gson;

class Index{
    Scanner scanner = new Scanner(System.in);
    App app = new App();
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

    public void addStudent() {
        System.out.println("\nAdding Student");
        System.out.print("Enter student Full Name: ");
        String fullName = scanner.nextLine();
        scanner.nextLine(); 
        System.out.print("Enter student Birthday: ");
        String birthday = scanner.nextLine();
        System.out.print("Enter student SchoolId: ");
        String schoolId = scanner.nextLine();
        System.out.print("Enter student Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter student Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter student Social Media Accounts: ");
        String socialMediaAccounts = scanner.nextLine();

        // Student student = new Student(name, age, birthday, lrn, address, contactNumber, socialMediaAccounts);
        // studentList.add(student);
        System.out.println("Student added successfully!");
        Student student = new Student(fullName, password);
        //convert object to json using GSON class
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        // Define the request body
        RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("operation", "login")
        .addFormDataPart("json", gson.toJson(user))
        .build();

        //Create the POST request
        Request request = new Request.Builder()
            .url("http://localhost/management/users.php")
            .post(requestBody)
            .build();

        String responseBody="0";
        try {
            // Execute the request
            Response response = client.newCall(request).execute();
            // Handle the response
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            } else {
                System.err.println("Request failed with code: " + response.code());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public void addStaff() {
        System.out.println("\nAdding Staff");
        System.out.print("Enter staff Name: ");
        String name = scanner.nextLine();

        // Staff staff = new Staff(name);
        // staffList.add(staff);
        System.out.println("Staff added successfully!");
        scanner.close();
    }

    public void staffMenu(){
        while (true) {
            System.out.println("Student List:");
            // List<Student> students = manager.getStudents();
            // for (int i = 0; i < students.size(); i++) {
            //     System.out.println((i + 1) + ". " + students.get(i).fullName);
            // }

            System.out.println("Enter the number of the student you want to view:");
            int index = scanner.nextInt() - 1;
            // System.out.println(manager.getStudent(index));

            System.out.println("1. Edit Information\n2. Delete / Remove file\n3. Back to Student list");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter new full name:");
                    scanner.nextLine(); // consume newline
                    String fullName = scanner.nextLine();
                    System.out.println("Enter new school ID:");
                    String schoolID = scanner.nextLine();
                    System.out.println("Enter new LRN:");
                    String LRN = scanner.nextLine();
                    System.out.println("Enter new birthday:");
                    String birthday = scanner.nextLine();
                    System.out.println("Enter new gender:");
                    String gender = scanner.nextLine();
                    // manager.getStudent(index).fullName = fullName;
                    // manager.getStudent(index).schoolID = schoolID;
                    // manager.getStudent(index).LRN = LRN;
                    // manager.getStudent(index).birthday = birthday;
                    // manager.getStudent(index).gender = gender;
                    break;
                case 2:
                    // manager.removeStudent(index);
                    break;
                case 3:
                    break;
            }
        }
    }
}