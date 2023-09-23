package project.management;
import java.util.Scanner;
import com.google.gson.Gson;

public class App {
    static Index index = new Index();
    
    public String begin(){
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccessful = false;
        String jsonString = "";

        while(!loginSuccessful){
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            jsonString = index.login(username, password);

            index.clearScreen();
            if(jsonString.equalsIgnoreCase("0")){
                System.out.println("Invalid username or password!\n");
            }else{
                System.out.println("Success!\n");
                loginSuccessful = true;
            }
            // System.out.println(jsonString);
        }
    
        return jsonString;
    }
    public static void main(String[] args) {
        index.clearScreen();
        String jsonString = new App().begin();
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonString, User.class);
            Index index = new Index();
            SessionStorage.userId = user.getUserId();
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
