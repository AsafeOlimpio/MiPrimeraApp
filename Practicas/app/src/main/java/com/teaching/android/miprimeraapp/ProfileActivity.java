package com.teaching.android.miprimeraapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private RadioButton rbmale;
    private RadioButton rbfemale;
    private MenuItem item = null;
    private String sd;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile_save,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveInternal();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usernameEditText = findViewById(R.id.user);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.email);
        ageEditText = findViewById(R.id.age);
        rbmale = findViewById(R.id.radio_button_male);
        rbfemale = findViewById(R.id.radio_button_female);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Profile");
        //No necesita parametros
        getSupportActionBar();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /*Save Method*/

    public void saveInternal(){

        Log.d("ProfileActivity","Username: " + usernameEditText.getText().toString());
        Log.d("ProfileActivity","Email: " + emailEditText.getText().toString());
        Log.d("ProfileActivity","Password: " + passwordEditText.getText().toString());
        Log.d("ProfileActivity","age: " + ageEditText.getText().toString());

        //Radio Buttons

        if (rbmale.isChecked()){
            Log.d("ProfileActivity","Male");
        }
        else if(rbfemale.isChecked()){
            Log.d("ProfileActivity","Female");
        }
    }

    public void  saveData(View view) {
        saveInternal();
    }

    public void deleteUser(View view) {
    }
}
