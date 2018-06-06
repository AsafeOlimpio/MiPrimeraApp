package com.teaching.android.miprimeraapp.profileactivity;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import com.teaching.android.miprimeraapp.database.AppDatabase;
import com.teaching.android.miprimeraapp.database.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;

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

    User user = new User();

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile_save, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        passwordEditText = findViewById(R.id.password);
        ageEditText = findViewById(R.id.age);
        rbmale = findViewById(R.id.radio_button_male);
        rbfemale = findViewById(R.id.radio_button_female);
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
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
                    }, 1997, 7, 23).show();
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

        Log.d("ProfileActivity", "isReadable: " + isExternalStorageReadable());
        Log.d("ProfileActivity", "isWritable: " + isExternalStorageWritable());
        Log.d("onCreate","This is onCreate");

        /*if (isExternalStorageReadable()) {
            File imgFile = new File(getExternalFilesDir(null), "businessman_login.png");
            if (imgFile.exists()) {
                ImageView myImage = findViewById(R.id.image_profile_view);
                myImage.setImageURI(Uri.fromFile(imgFile));
            }
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences loginSharedPrefs = getSharedPreferences(getString(R.string.login_preference_file),Context.MODE_PRIVATE);
        if (loginSharedPrefs != null) {
            String userSharedPrefs = loginSharedPrefs.getString("username", "");

            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                    "film_library_database").allowMainThreadQueries().build();
            user = db.userDao().findByUsername(userSharedPrefs);

            if (user != null) {
                usernameEditText.setText(user.getUsername());
                emailEditText.setText(user.getEmail());
                if (user.getGender() != null) {
                    if (user.getGender().equals("Male")) {
                        rbmale.setChecked(true);
                    }
                    if (user.getGender().equals("Female")) {
                        rbfemale.setChecked(true);
                    }
                }
                ageEditText.setText(user.getAge());
            }
        }
    }

    /*Save Method*/
    public void saveInternal() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "film_library_database").allowMainThreadQueries().build();

        try {
            user.setUsername(usernameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.setAge(ageEditText.getText().toString());

            if (rbmale.isChecked()) {
                user.setGender("Male");
            } else if (rbfemale.isChecked()) {
                user.setGender("Female");
            }
            db.userDao().insert(user);
        } catch (SQLiteConstraintException ex) {
            Toast.makeText(ProfileActivity.this, R.string.profile_sql_exception, Toast.LENGTH_LONG).show();
        }

        Log.d("ProfileActivity", "Username: " + usernameEditText.getText().toString());
        Log.d("ProfileActivity", "Email: " + emailEditText.getText().toString());
        Log.d("ProfileActivity", "Password: " + passwordEditText.getText().toString());
        Log.d("ProfileActivity", "age: " + ageEditText.getText().toString());

        //Radio Buttons

        if (rbmale.isChecked()) {
            Log.d("ProfileActivity", "Male");
        } else if (rbfemale.isChecked()) {
            Log.d("ProfileActivity", "Female");
        }
    }

    public void saveData(View view) {
        saveInternal();

        usernameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        rbmale.setChecked(false);
        rbfemale.setChecked(false);
        ageEditText.setText("");
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
                Toast.makeText(ProfileActivity.this, R.string.profile_dialog_deletinguser, Toast.LENGTH_LONG).show();

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
    String myCurrentPhotoPath;

    /**
     *This Method Creates an image file
     * @return Created imageFile
     * @throws IOException
     */
    private File createImageFile(){
        //Create an image file name
       File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       return new File(storageDir,"profile.jpg");
    }

    public void takeProfilePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                File photoFile = createImageFile();
                Uri photoUri = FileProvider.getUriForFile(this,"com.teaching.android.miprimeraapp",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent,100);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","This is onResume");
            //Defining the environment is needed
        File imgFile = createImageFile();
        if (imgFile.exists()) {
            ImageView myImage = findViewById(R.id.image_profile_view);
            myImage.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        }
    }
}
