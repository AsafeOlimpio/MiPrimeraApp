package com.teaching.android.miprimeraapp.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBar;

import com.bumptech.glide.Glide;
import com.teaching.android.miprimeraapp.Interactors.FIlmsFirebaseInteractor;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractorCallback;
import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;
import com.teaching.android.miprimeraapp.activitylist.ListActivity;
import com.teaching.android.miprimeraapp.webview.WebViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmDetailFragment extends Fragment {

    private FIlmsFirebaseInteractor fIlmsFirebaseInteractor;

    public FilmDetailFragment() {
        // Required empty public constructor
    }

    public static FilmDetailFragment newInstance(int filmId){
        FilmDetailFragment fragment = new FilmDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("filmId", filmId);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_film_detail, container, false);

        // Inflate the layout for this fragment & get ID
        final int filmId = getArguments().getInt("filmId",0);
        fIlmsFirebaseInteractor = new FIlmsFirebaseInteractor();
        fIlmsFirebaseInteractor.getFilms(new FilmsInteractorCallback() {
            public String url;

            @Override
            public void onFilmsAvailable() {
                final FilmModel filmModel = fIlmsFirebaseInteractor.getFilmWithId(filmId);

                //Change image
                ImageView photoBg = getView().findViewById(R.id.photo);
                Glide.with(getView()).load(filmModel.getIcon()).into(photoBg);
                //photoBg.setImageResource(filmModel.getBackground());

                TextView text = getView().findViewById(R.id.lorem_text);
                text.setText(filmModel.getDescription());

                //Define action for button whatch trailer
                Button button = getView().findViewById(R.id.whatch_trailer_button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //"getContext" is used to get the context of the activity. Is necessary in some cases
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra("url",filmModel.getOfficialWebsiteUrl());
                        startActivity(intent);
                    }
                });
                this.url = filmModel.getOfficialWebsiteUrl();
            }
        });

        return fragmentView;
    }

}
