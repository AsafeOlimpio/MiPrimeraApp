package com.teaching.android.miprimeraapp.filmdetailactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.fragments.FilmDetailFragment;
import com.teaching.android.miprimeraapp.view.FilmDetailView;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;

public class FilmDetailActivity extends AppCompatActivity implements FilmDetailView{

    private FilmDetailPresenter filmDetailPresenter;
    private int currentPosition;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        filmDetailPresenter = new FilmDetailPresenter();
        currentPosition = getIntent().getIntExtra("position", 0);

        //Always declare the toolbar on the activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        filmDetailPresenter.startPresenting(this);

        ViewPager myViewPager = findViewById(R.id.view_pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myPagerAdapter);
        myViewPager.setCurrentItem(currentPosition);
        getSupportActionBar().setTitle(myPagerAdapter.getPageTitle(currentPosition));
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Change the toolbar title with the one in the adapter
                getSupportActionBar().setTitle(myPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onFilmLoaded(FilmModel filmModel) {

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int filmId = filmDetailPresenter.getFilms().get(position).getId();
            return FilmDetailFragment.newInstance(filmId);
        }

        @Override
        public int getCount() {
            return filmDetailPresenter.getFilms().size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(filmDetailPresenter.getFilms().get(position).getName());
        }
    }
}
