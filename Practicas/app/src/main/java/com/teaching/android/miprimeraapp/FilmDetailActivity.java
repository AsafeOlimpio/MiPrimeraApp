package com.teaching.android.miprimeraapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.Presenter.FilmDetailPresenter;
import com.teaching.android.miprimeraapp.Presenter.FilmDetailPresenter;
import com.teaching.android.miprimeraapp.view.FilmDetailView;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;

public class FilmDetailActivity extends AppCompatActivity implements FilmDetailView{

    private String url;

    private FilmDetailPresenter filmDetailPresenter;
    private int currentFilmId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        filmDetailPresenter = new FilmDetailPresenter();
        currentFilmId = getIntent().getIntExtra("film_id", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        filmDetailPresenter.startPresenting(this);
        filmDetailPresenter.loadFilmWithId(currentFilmId);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onFilmLoaded(FilmModel filmModel) {
        //Update view with film model data
        ImageView photoBg = findViewById(R.id.photo);
        photoBg.setImageResource(filmModel.getBackgroundDrawable());

        TextView text = findViewById(R.id.lorem_text);
        text.setText(filmModel.getDescription());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(filmModel.getName());
        //No necesita parametros
        getSupportActionBar();

        this.url = filmModel.getOfficialWebsiteUrl();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void wt(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
