package com.brandon.codewater.mybizness;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by brand on 2017-09-13.
 */

public class ProfileActivity extends Activity {

    private TextView mName;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_profile_layout);

        mName = (TextView) findViewById(R.id.test_text);

        String firstname = "Brandon";
        String type = "profile";

        new ProfileDb(getApplicationContext(),  mName, 1).execute(type, firstname);
    }

    class ProfileDb extends AsyncTask<String, Void, String>{
        private Context context;
        private TextView mNameText;
        private AlertDialog alert;
        private int byGetorPost = 0;

        public ProfileDb(Context context, TextView mNameText, int flag){
            this.context = context;
            this.mNameText = mNameText;
            this.byGetorPost = flag;
        }

        /**
         * Here we want to do POST operation and validate variable
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            Log.d("BACKGROUND", "attempting");
            String type = params[0];
            //10.0.2.2 for standard to communicate with a host server
            String profile_url = "http://35.182.39.231/mybizness/getusers.php";

            /**
             * Here is to POST some data
             */
            if(type.equals("profile")){
                //First we must create URLs and what not
                try {
                    URL url = new URL(profile_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream output = httpURLConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
                    //"firstname" would be the key that is encoded as UTF-8 which is assigned to value of params[1] which is first name
                    String postData = URLEncoder.encode("first_name", "UTF-8") +"="+URLEncoder.encode(params[1], "UTF-8");

                    writer.write(postData);
                    writer.flush();
                    writer.close();
                    output.close();

                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"));
                    String result = null;
                    String line ;

                    while((line = reader.readLine()) != null){
                        result += line;
                    }

                    reader.close();
                    input.close();
                    httpURLConnection.disconnect();

                    //will return an Integer for SUCCESS(1) or FAILURE(0)
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
            //super.onPreExecute();
            Log.d("PREEXECUTE", "attempting");
        }

        @Override
        protected void onPostExecute(String aVoid) {
            //super.onPostExecute(aVoid);
            if (aVoid != null) {
                Log.d("POSTEXECUTE", "!" + aVoid + "!");
            }
            else{
                Log.d("POSTEXECUTE", "attempting");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
