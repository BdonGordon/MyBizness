package com.brandon.codewater.mybizness;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by brand on 2017-10-01.
 */

public class SignInActivity extends Activity implements View.OnClickListener {
    private EditText mUsername, mPassword;
    private ImageButton mSignIn;
    private String username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        mUsername = (EditText) findViewById(R.id.username_signin);
        mPassword = (EditText) findViewById(R.id.password_signin);
        mSignIn = (ImageButton) findViewById(R.id.sign_in_button);

    }

    public void OnLogin(View view){
        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        DatabaseActivity db = new DatabaseActivity(this);
        //type of operation would be login
        String type = "login";
        db.execute(type, username, password);
    }

    @Override
    public void onClick(View v) {
    }
}
