package com.teaching.android.miprimeraapp.profileactivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.teaching.android.miprimeraapp.R;

import java.io.File;
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
        Log.d("ProfileActivity","isReadable: " + isExternalStorageReadable());
        Log.d("ProfileActivity","isWritable: " + isExternalStorageWritable());

        if (isExternalStorageReadable()){
            File imgFile = new File(getExternalFilesDir(null),"businessman_login.png");
            if (imgFile.exists()){
                ImageView myImage = findViewById(R.id.image_profile_view);
                myImage.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.profile_preferences_file),Context.MODE_PRIVATE);

        String username = sharedPrefs.getString("username","no_username");
        usernameEditText.setText(username.toString());

        String email = sharedPrefs.getString("email","no_email");
        emailEditText.setText(email.toString());

        String maleOrFemale = sharedPrefs.getString("gender","no_gender");
        if (maleOrFemale.equalsIgnoreCase("Male")){
            rbmale.setChecked(true);
        }
        else  if (maleOrFemale.equalsIgnoreCase("Female")){
            rbfemale.setChecked(true);
        }
        String age = sharedPrefs.getString("age","no_age");
        ageEditText.setText(age.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.profile_preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username",usernameEditText.getText().toString());
        editor.putString("email",emailEditText.getText().toString());
        //Gender
        if (rbmale.isChecked()){
            editor.putString("gender","Male");
        }
        else if(rbfemale.isChecked()){
            editor.putString("gender","Female");
        }

        else {
            editor.remove("gender");
        }

        //Age
        if (ageEditText != null){
            editor.putString("age",ageEditText.getText().toString());
        }
        //Do not Forget to Aply the Changes!!!!!!
        editor.apply();
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

                usernameEditText.setText("");

                emailEditText.setText("");

                rbmale.setChecked(false);
                rbfemale.setChecked(false);

                ageEditText.setText("");
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

    public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
