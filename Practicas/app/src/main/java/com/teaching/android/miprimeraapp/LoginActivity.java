package com.teaching.android.miprimeraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.user);
        passwordEditText = findViewById(R.id.password);
    }

    public void Login(View view) {
        //Get Values
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //Check empty values
        if (TextUtils.isEmpty(username)){
            //Empty username
            usernameEditText.setError(getString(R.string.empty_user));
        }
        else if (TextUtils.isEmpty(password)){
            passwordEditText.setError(getString(R.string.password));
        }
        else{
            Intent profileIntent = new Intent(this,ProfileActivity.class);
            startActivity(profileIntent);
        }
    }

    public void Cancel(View view) {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    public void register(View view) {
        Intent register = new Intent(this, LoginActivity.class);
        startActivity(register);
    }
}
