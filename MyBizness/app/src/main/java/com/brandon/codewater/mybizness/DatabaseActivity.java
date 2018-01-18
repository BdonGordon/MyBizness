package com.brandon.codewater.mybizness;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.brandon.codewater.mybizness.Database.DbConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by brand on 2017-10-02.
 */

public class DatabaseActivity extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog box;
    JSONObject jsonObj;
    private String JSON_STRING;

    public DatabaseActivity(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0]; //type = "login"
        String login_url = DbConstants.serverLoginUrl;
        //Login type
        if(type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+ URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "="+ URLEncoder.encode(params[2], "UTF-8");
                writer.write(post_data);
                writer.flush();
                writer.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = null;
                String line = null;

                while((JSON_STRING = reader.readLine()) != null){
                    result += JSON_STRING;
                }

                reader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        box = new AlertDialog.Builder(context).create();
    }

    /**
     * CHECKPOINT 02:20 Oct 2, 2017... Learning JSON. How to make dynamic JSON from PHP
     * @param aVoid
     */
    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        box.setMessage("! " + aVoid + "! ");
        box.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
