package project.management;
import okhttp3.*;
import java.io.Console;
import java.util.Scanner;
import com.google.gson.Gson;

public class App {
    public String begin(){
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccessful = false;
        String jsonString = "";

        while(!loginSuccessful){
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            jsonString = login(username, password);

            if(jsonString.equalsIgnoreCase("0")){
                System.out.println("Invalid username or password!");
            }else{
                System.out.println("Success!");
                loginSuccessful = true;
            }
            System.out.println(jsonString);
        }
        return jsonString;
    }

    public String login(String username, String password) {
        User user = new User(username, password);
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
    public static void main(String[] args) {
        String jsonString = new App().begin();
        try {
                Gson gson = new Gson();
                User user = gson.fromJson(jsonString, User.class);
                Index index = new Index();
                System.out.println("user level: " + user.getLevel());
                if(user.getLevel() == 100){
                    System.out.println("Administrator");
                    index.adminMenu();
                }else{
                    System.out.println("User");
                    index.staffMenu();
                }
                String fullname = user.getFullname();
                int userId = user.getUserId();

                System.out.println("Full Name : " + fullname);
                System.out.println("User ID : " + userId);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private static String hidePassword() {
        Console console = System.console();
        if (console == null) {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } else {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        }
        
    }
}
