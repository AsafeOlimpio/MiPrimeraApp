package com.teaching.android.miprimeraapp.Presenter;

import android.view.View;

import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;
import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.view.FilmDetailView;

import java.util.ArrayList;

public class FilmDetailPresenter {
    private FilmsInteractor filmsInteractor;
    private FilmDetailView filmDetailView;

    public void startPresenting(FilmDetailView view){
        this.filmDetailView = view;
        filmsInteractor = new FilmsInteractor();
    }

    public void loadFilmWithId(int id){
        FilmModel filmModel = filmsInteractor.getFilmWithId(id);
        filmDetailView.onFilmLoaded(filmModel);
    }

    public ArrayList<FilmModel> getFilms(){
        return filmsInteractor.getFilms();
    }
}

