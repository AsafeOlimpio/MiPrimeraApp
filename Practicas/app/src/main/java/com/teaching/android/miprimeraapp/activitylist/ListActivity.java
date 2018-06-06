package com.teaching.android.miprimeraapp.activitylist;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.teaching.android.miprimeraapp.Interactors.FIlmsFirebaseInteractor;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractorCallback;
import com.teaching.android.miprimeraapp.Manifest;
import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.broadcast_receiver.MyConnectivityReceiver;
import com.teaching.android.miprimeraapp.filmdetailactivity.FilmDetailActivity;
import com.teaching.android.miprimeraapp.loginactivity.LoginActivity;

import java.io.File;

public class ListActivity extends AppCompatActivity {
    //ArrayList filmNames [];
    private FIlmsFirebaseInteractor fIlmsFirebaseInteractor;
    private MyAdapter myAdapter;
    private ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Presta atención en el orden del código y en la sintaxis
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Check Connectivity
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true){

            fIlmsFirebaseInteractor = new FIlmsFirebaseInteractor();
            fIlmsFirebaseInteractor.getFilms(new FilmsInteractorCallback() {
                @Override
                public void onFilmsAvailable() {
                    //Here, FilmsFirebaseInteractor already got the list of games
                    findViewById(R.id.progressBar3).setVisibility(View.GONE);
                    myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
                }
            });

            listView = findViewById(R.id.list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListActivity.this, FilmDetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });

            File internalDirectory = getFilesDir();
            File cacheDirectory = getCacheDir();

            Log.d("ListActivity", "Interno" + internalDirectory.getAbsolutePath());
            Log.d("ListActivity", "Cache2" + cacheDirectory.getAbsolutePath());

            getExternalFilesDir(null);

            //Get Token
            String token = FirebaseInstanceId.getInstance().getToken();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("device_push_token");
            reference.setValue(token);
        }

        else {
            Toast.makeText(ListActivity.this, R.string.no_connectivity, Toast.LENGTH_LONG).show();
            findViewById(R.id.progressBar3).setVisibility(View.GONE);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        //No necesita parametros
        getSupportActionBar();

        //Register Broadcast Receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new MyConnectivityReceiver(), intentFilter);

       int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(ListActivity.this,"GeoPermission Granted",Toast.LENGTH_LONG).show();
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Log.d("GPS","Location" + locationManager);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fIlmsFirebaseInteractor.getFilms().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_view_style, parent, false);

            ImageView icon = rowView.findViewById(R.id.image_view);
            Glide.with(ListActivity.this).load(fIlmsFirebaseInteractor.getFilms().get(position).
                    getIcon()).into(icon);

            TextView textView = rowView.findViewById(R.id.text_view);
            textView.setText(fIlmsFirebaseInteractor.getFilms().get(position).getName());
            return rowView;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Thank You",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"...",Toast.LENGTH_LONG).show();
            }
        }
    }
}


