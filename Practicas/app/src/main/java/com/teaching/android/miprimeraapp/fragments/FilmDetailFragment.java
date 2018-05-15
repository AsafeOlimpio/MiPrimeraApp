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

import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.R;
import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmDetailFragment extends Fragment {


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

    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_film_detail, container, false);

        // Inflate the layout for this fragment & get ID
        int filmId = getArguments().getInt("filmId",0);
        FilmModel filmModel = new FilmsInteractor().getFilmWithId(filmId);

        //Change image
        ImageView photoBg = fragmentView.findViewById(R.id.photo);
        photoBg.setImageResource(filmModel.getBackgroundDrawable());

        TextView text = fragmentView.findViewById(R.id.lorem_text);
        text.setText(filmModel.getDescription());

        //Define action for button whatch trailer
        Button button = fragmentView.findViewById(R.id.whatch_trailer_button);

        this.url = filmModel.getOfficialWebsiteUrl();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        return fragmentView;
    }

}
