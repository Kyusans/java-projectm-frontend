package project.management;
import java.util.Scanner;
import com.google.gson.Gson;
import java.io.Console;

public class App {
    static Index index = new Index();
    static Scanner scanner = new Scanner(System.in);
    public String begin(){
        boolean loginSuccessful = false;
        String jsonString = "";

        while (!loginSuccessful) {
            System.out.println("+---------------------------+");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.println("+---------------------------+");
            
            System.out.print("Password: ");
            String password = hidePassword();
            System.out.println("Logging in...");

            jsonString = index.login(username, password);

            index.clearScreen();
            if (jsonString.equalsIgnoreCase("0")) {
                System.out.println("Invalid username or password!\n");
            } else {
                System.out.println("Success!\n");
                loginSuccessful = true;
            }
        }
        return jsonString;
    }

    private static String hidePassword() {
        Console console = System.console();
        if (console == null) {
            return scanner.nextLine();
        } else {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        }
    }

    public static void main(String[] args) {
        String jsonString = "";
        index.clearScreen();
        boolean validChoice = false;
        while (!validChoice) {
            System.out.println("+---------------------------+");
            System.out.println("|         MARKETING         |");
            System.out.println("|     Sign in to Explore!   |");
            System.out.println("|                           |");
            System.out.println("| 1. Sign in                |");
            System.out.println("| 2. Exit                   |");
            System.out.println("+---------------------------+");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    validChoice = true;
                    index.clearScreen();
                    jsonString = new App().begin();
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    index.clearScreen();
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
        try {
            Index index = new Index();
            Gson gson = new Gson();
            User user = gson.fromJson(jsonString, User.class);
            SessionStorage.userId = user.getUserId();
            SessionStorage.username = user.getUsername();
            SessionStorage.password = user.getPassword();
            SessionStorage.userLevel = user.getLevel();
            if(user.getLevel() == 100){
                index.adminMenu();
            }else if(user.getLevel() == 90){
                index.staffMenu();
            }else{
                index.facultyMenu();
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
