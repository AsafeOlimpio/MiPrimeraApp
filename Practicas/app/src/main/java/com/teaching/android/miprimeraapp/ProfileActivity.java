package com.teaching.android.miprimeraapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private RadioButton rbmale;
    private RadioButton rbfemale;
    private MenuItem item = null;
    private String sd;
    private static final int LONG_DELAY = 350000; // 3.5 seconds
    private static final int SHORT_DELAY = 200000;

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
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    //Show DatePickerDialog
                    new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        //Here we obtain the date chosen by the user
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //Here we set this age on "ageEditTextField"
                            int actualYear = Calendar.getInstance().get(Calendar.YEAR);
                            int edad = actualYear - year;
                            //ageEditText.setText(edad);
                            ageEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        }
                    },1997,7,23).show();
                }
            }
        });

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
        //We create "builder" variable wich will store our dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //We define the title and messages to be shown
        builder.setMessage(R.string.profile_dialog_delete).setTitle(R.string.profile_dialog_delete_confirmation);

        builder.setPositiveButton(R.string.profile_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //En este toast queremos referenciar a nuestra activity "ProfileActivity"
                Toast.makeText(ProfileActivity.this,R.string.profile_dialog_deletinguser,Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton(R.string.profile_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.profile_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //Now we create the dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
