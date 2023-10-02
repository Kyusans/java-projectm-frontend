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
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = hidePassword();

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
            System.out.println("MARKETING\nSign in to Explore!\n");
            System.out.print("1. Sign in\n2. Exit\n\nChoice: ");
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
            }else{
                index.staffMenu();
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    // private static String hidePassword() {
    //     Console console = System.console();
    //     if (console == null) {
    //         Scanner scanner = new Scanner(System.in);
    //         return scanner.nextLine();
    //     } else {
    //         char[] passwordChars = console.readPassword();
    //         return new String(passwordChars);
    //     }
        
    // }
}
