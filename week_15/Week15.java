package week_15;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Week15 {


 public static void main(String[] args) throws Exception {
    String clientId = "b_OCxxzXfd4gtzWMOM7Z"; //���ø����̼� Ŭ���̾�Ʈ ���̵�"
     String clientSecret = "FZeDAtWC4x"; //���ø����̼� Ŭ���̾�Ʈ ��ũ����"

     String text;
     Scanner sc = new Scanner(System.in);
     System.out.print("�˻�� �Է��ϼ���:");
     text = sc.next();
     
     text = URLEncoder.encode(text, "UTF-8");
     


     String apiURL =  "https://openapi.naver.com/v1/search/movie?query=" + text;

     
     Map<String, String> requestHeaders = new HashMap<>();
     requestHeaders.put("X-Naver-Client-Id", clientId);
     requestHeaders.put("X-Naver-Client-Secret", clientSecret);
     String responseBody = get(apiURL,requestHeaders);


     System.out.println(responseBody);
    
   JSONParser jp = new JSONParser();
   JSONObject jo = (JSONObject) jp.parse(responseBody);
   JSONArray info_Array = (JSONArray) jo.get("items");
   
   for(int i = 0 ;i<info_Array.size();i++) {
      System.out.println("item_="+i+"=================================");
      JSONObject io = (JSONObject) info_Array.get(i);
      System.out.println("titie:\t"+io.get("title"));
      System.out.println("subtitle:\t"+io.get("subtitle"));
      System.out.println("director:\t"+io.get("director"));
      System.out.println("actor:\t"+io.get("actor"));
      System.out.println("userRating:\t"+io.get("userRating"));
   }
     
 }


 private static String get(String apiUrl, Map<String, String> requestHeaders){
     HttpURLConnection con = connect(apiUrl);
     try {
         con.setRequestMethod("GET");
         for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
             con.setRequestProperty(header.getKey(), header.getValue());
         }


         int responseCode = con.getResponseCode();
         if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
             return readBody(con.getInputStream());
         } else { // ���� �߻�
             return readBody(con.getErrorStream());
         }
     } catch (IOException e) {
         throw new RuntimeException("API ��û�� ���� ����", e);
     } finally {
         con.disconnect();
     }
 }


 private static HttpURLConnection connect(String apiUrl){
     try {
         URL url = new URL(apiUrl);
         return (HttpURLConnection)url.openConnection();
     } catch (MalformedURLException e) {
         throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
     } catch (IOException e) {
         throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
     }
 }


 private static String readBody(InputStream body){
     InputStreamReader streamReader = new InputStreamReader(body);


     try (BufferedReader lineReader = new BufferedReader(streamReader)) {
         StringBuilder responseBody = new StringBuilder();


         String line;
         while ((line = lineReader.readLine()) != null) {
             responseBody.append(line);
         }


         return responseBody.toString();
     } catch (IOException e) {
         throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
     }
 }
}