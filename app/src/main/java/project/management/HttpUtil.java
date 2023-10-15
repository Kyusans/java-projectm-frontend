package project.management;
import okhttp3.*;
import com.google.gson.Gson;

public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();
    // tig retrieve og send data sa web server
    private static final Gson gson = new Gson();
    
    public static String sendPostRequest(String operation, Object data, String url) {
        try {
          String BASE_URL = "http://localhost/management/" + url;
          // Convert object to JSON
          String jsonData = gson.toJson(data);

          // Define the request body
          RequestBody requestBody = new MultipartBody.Builder()
              .setType(MultipartBody.FORM)
              .addFormDataPart("operation", operation)
              .addFormDataPart("json", jsonData)
              .build();

          // Create the POST request
          Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();

          // Execute the request
          Response response = client.newCall(request).execute();

          // Handle the response
          if (response.isSuccessful()) {
              return response.body().string();
          } else {
              System.err.println("Request failed with code: " + response.code());
          }
          response.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        return "0";
    }
}
