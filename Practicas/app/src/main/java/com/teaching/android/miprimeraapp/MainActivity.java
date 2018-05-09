package com.teaching.android.miprimeraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.*;

public class MainActivity extends AppCompatActivity {

    /*LifeCycle*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }

    /*Methods*/

    /*onClicks*/
    public void onClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        intent.putExtra("url","https://www.google.com/gmail/");
        startActivity(intent);
    }

    public void opgl(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1234"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    public void oppf(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    /*Intents*/


}