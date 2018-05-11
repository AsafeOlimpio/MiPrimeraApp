package com.teaching.android.miprimeraapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        //No necesita parametros
        getSupportActionBar();
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


