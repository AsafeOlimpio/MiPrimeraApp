package com.teaching.android.miprimeraapp.Interactors;

import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.R;

import java.util.ArrayList;

public class FilmsInteractor{
    private ArrayList<FilmModel> films;

    /*Constructor*/
    public FilmsInteractor(){
        //If i dont have any game, i create them
        if (films == null){

            //Adding the films
            //
            FilmModel sherlockFm = new FilmModel(0,"Sherlock Holmes","Lorem Ipsum...",
                    "https://www.youtube.com/watch?v=Egcx63-FfTE","","");

            FilmModel alienistFm = new FilmModel(1,"The Alienist","Lorem Ipsum...",
                    "https://www.youtube.com/watch?v=YtzgFRBvRy8","","");

            FilmModel tintinFm = new FilmModel(2,"Tintin","Lorem Ipsum...",
                    "https://www.youtube.com/watch?v=7NWtW699XME","","");

            films = new ArrayList<>();
            films.add(sherlockFm);
            films.add(alienistFm);
            films.add(tintinFm);
        }
    }

    public ArrayList<FilmModel> getFilms() {
        return films;
    }

    public FilmModel getFilmWithId(int id){
        //In a normal for film.lenght would not be used if not film.size because is an ArrayList
        //Get from films the movie with identifier id
        for (FilmModel film : films){
            if (film.getId()== id){
                return films.get(id);
            }
        }
        return null;
    }

    /*public int getFilmUrl(String  id){
        return films.get(id.toString()).getOfficialWebsiteUrl();
    }*/
}
