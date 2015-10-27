package com.otravela.otravela;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michaelpierre on 10/26/15.
 */
public class HttpManager {
    public static String getData(String uri)
    {
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch(IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static String getDataAutenticated(String uri, String userName, String Password)
    {
        BufferedReader reader = null;
        byte[]  loginBytes = (userName + ":" + Password).getBytes();
        StringBuilder loginBuilder = new StringBuilder().append("Basic ").append(Base64.encodeToString(loginBytes, Base64.DEFAULT));
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // pass authincation for header

            con.addRequestProperty("Authorization", loginBuilder.toString());

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch(IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
