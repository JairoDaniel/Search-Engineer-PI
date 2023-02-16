package com.pureinsights.exercise.backend.repository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class httpHelper {

    private static final String USER_AGENT = "Mozilla/5.0";    
    private static final String ELASTICSEARCH_BASE_URL = "http://localhost:9200/";

    public String POST(String esQueryEndPoint, String postBody) throws IOException {
        URL obj = new URL(ELASTICSEARCH_BASE_URL+esQueryEndPoint);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        os.flush();
        byte[] body = postBody.getBytes("utf-8");
        os.write(body, 0, body.length);
        os.close();
        // For POST only - END

        int responseCode = httpURLConnection.getResponseCode();
        
        // Query successful
        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            
            return response.toString();
        } else {
            return "{\"count\":0000,\"message\": \"Error sending http request.\"}";
        }
    }
}