// package project.management;

// import java.util.ArrayList;
// import java.util.Scanner;

// public class Test {
//   public class StaffHomeProgram {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//           System.out.println("Student List:");
//           // List<Student> students = manager.getStudents();
//           // for (int i = 0; i < students.size(); i++) {
//           //     System.out.println((i + 1) + ". " + students.get(i).fullName);
//           // }

//           System.out.println("Enter the number of the student you want to view:");
//           int index = scanner.nextInt() - 1;
//           // System.out.println(manager.getStudent(index));

//           System.out.println("1. Edit Information\n2. Delete / Remove file\n3. Back to Student list");
//           int choice = scanner.nextInt();
//           switch (choice) {
//               case 1:
//                   System.out.println("Enter new full name:");
//                   scanner.nextLine(); // consume newline
//                   String fullName = scanner.nextLine();
//                   System.out.println("Enter new school ID:");
//                   String schoolID = scanner.nextLine();
//                   System.out.println("Enter new LRN:");
//                   String LRN = scanner.nextLine();
//                   System.out.println("Enter new birthday:");
//                   String birthday = scanner.nextLine();
//                   System.out.println("Enter new gender:");
//                   String gender = scanner.nextLine();
//                   // manager.getStudent(index).fullName = fullName;
//                   // manager.getStudent(index).schoolID = schoolID;
//                   // manager.getStudent(index).LRN = LRN;
//                   // manager.getStudent(index).birthday = birthday;
//                   // manager.getStudent(index).gender = gender;
//                   break;
//               case 2:
//                   // manager.removeStudent(index);
//                   break;
//               case 3:
//                   break;
//           }
//     }
//   }
