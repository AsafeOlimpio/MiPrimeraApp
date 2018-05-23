package com.teaching.android.miprimeraapp.loginactivity;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.database.AppDatabase;
import com.teaching.android.miprimeraapp.database.User;
import com.teaching.android.miprimeraapp.profileactivity.ProfileActivity;

import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Scanner sc = new Scanner(System.in);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.user);
        passwordEditText = findViewById(R.id.password);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Login");
        //No necesita parametros
        getSupportActionBar();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*SharedPreferences preferences = getSharedPreferences(getString(R.string.login_preference_file),Context.MODE_PRIVATE);
        String resText = preferences.getString("username",null);
        if (resText != null){
            String username = preferences.getString("username"," no_username");
            usernameEditText.setText(username.toString());
        }*/
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

        else {
            //Add database to our method
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,
            "film_library_database").allowMainThreadQueries().build();
            User retrivedUser = db.userDao().findByUsername(username);

            if (retrivedUser == null){
                Toast.makeText(this,"Usrname Does Not Exists",Toast.LENGTH_LONG).show();
            }
            else if (password.equals(retrivedUser.getPassword())){
                //LOGIN
                SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.login_preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("username", username);
                editor.apply();

                //Login
                Intent profileIntent = new Intent(this,ProfileActivity.class);
                startActivity(profileIntent);
            }
            else {
                passwordEditText.setError("Invalid Password");
            }
        }
    }

    public void Cancel(View view) {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    public void register(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.login_preference_file),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();
        Intent register = new Intent(this, ProfileActivity.class);
        startActivity(register);
    }


    public void prefs (){

    }
}
