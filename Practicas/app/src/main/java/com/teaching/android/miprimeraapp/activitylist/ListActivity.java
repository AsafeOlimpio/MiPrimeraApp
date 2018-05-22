package com.teaching.android.miprimeraapp.activitylist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;
import com.teaching.android.miprimeraapp.loginactivity.LoginActivity;
import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.filmdetailactivity.FilmDetailActivity;

import java.io.File;

public class ListActivity extends AppCompatActivity {
    //ArrayList filmNames [];
    String [] filmNames = {"The Adventures of Sherlock Holmes", "The Alienist", "The Adventures of Tintin"};
    int [] filmIcons = {R.drawable.sherlock, R.drawable.alienist, R.drawable.tintin};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Presta atención en el orden del código y en la sintaxis
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_login:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListActivity.this,FilmDetailActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
        });


        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        //No necesita parametros
        getSupportActionBar();

        File internalDirectory = getFilesDir();
        File cacheDirectory = getCacheDir();

        Log.d("ListActivity","Interno" + internalDirectory.getAbsolutePath());
        Log.d("ListActivity","Cache2" + cacheDirectory.getAbsolutePath());

        getExternalFilesDir(null);
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return filmNames.length;
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
            View rowView = inflater.inflate(R.layout.list_view_style,parent,false);

            ImageView icon = rowView.findViewById(R.id.image_view);
            icon.setImageResource(filmIcons[position]);

            TextView textView = rowView.findViewById(R.id.text_view);
            textView.setText(filmNames[position]);
            return rowView;
        }

    }
}


