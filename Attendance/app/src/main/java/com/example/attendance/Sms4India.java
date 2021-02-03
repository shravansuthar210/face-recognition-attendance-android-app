package com.example.attendance;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Sms4India extends AsyncTask<String, Void, String> {

    private String mobNumber;
    private String smsBody;
    private String apiKey;
    private String secretKey;
    private String useType;
    private String senderId;
    private String url;


    public Sms4India(String mobNo, String msg) {
        this.mobNumber = mobNo;
        this.smsBody = msg;
        this.apiKey = "";
        this.secretKey = "";
        this.senderId = "SMSIND";
        this.useType = "stage";
        this.url = "https://www.sms4india.com";
    }

    @Override
    public String doInBackground(String... strings) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            // construct data
            JSONObject urlParameters = new JSONObject();
            urlParameters.put("apikey", apiKey);
            urlParameters.put("secret", secretKey);
            urlParameters.put("usetype", useType);
            urlParameters.put("phone", mobNumber);
            urlParameters.put("message", URLEncoder.encode(smsBody, "UTF-8"));
            urlParameters.put("senderid", senderId);
            URL obj = new URL(url + "/api/v1/sendCampaign");
            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(urlParameters.toString().getBytes());
            // get the response
            BufferedReader bufferedReader = null;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
            Log.d("tag","sms respo- "+content.toString());
            return content.toString();
        } catch (Exception ex) {
            Log.e("tag","Exception in sms snd- "+ex);
            ex.printStackTrace();
            return "{'status':500,'message':'Internal Server Error'}";
        }
    }
}
