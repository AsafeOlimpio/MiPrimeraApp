package com.teaching.android.miprimeraapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usernameEditText = findViewById(R.id.user);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.email);
        ageEditText = findViewById(R.id.age);
    }

    /*Save Method*/

    public void saveData(View view) {
        Log.d("ProfileActivity","Username: " + usernameEditText.getText().toString());
        Log.d("ProfileActivity","Email: " + emailEditText.getText().toString());
        Log.d("ProfileActivity","Password: " + passwordEditText.getText().toString());
        Log.d("ProfileActivity","age: " + ageEditText.getText().toString());
    }

    public void deleteUser(View view) {
    }
}
