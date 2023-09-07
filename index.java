import java.util.Scanner;

class Main{
  public static void main(String[] args) {
    Index index = new Index();
    index.login();
  }
}

class Index{
  public void login(){
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    String userData = DatabaseConnector.login(username, password);

    if(userData != null){
      System.out.println("Success!");
      System.out.println("userdata: " + userData);
    }else{
      System.out.println("Failed");
      System.out.println("userdata: " + userData);
    }
    scanner.close();
  }
}