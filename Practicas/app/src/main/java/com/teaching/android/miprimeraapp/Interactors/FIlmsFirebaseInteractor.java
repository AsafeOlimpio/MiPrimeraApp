package com.teaching.android.miprimeraapp.Interactors;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teaching.android.miprimeraapp.Model.FilmModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FIlmsFirebaseInteractor{
    public FIlmsFirebaseInteractor() {
    }

    private ArrayList<FilmModel> films = new ArrayList<>();

    public void getFilms(final FilmsInteractorCallback callback){
        //1 - Call Firebase
        //2 - Get FilmModelList
        //3 - Notify Callback.onFilmsAvailable

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("films");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot filmNode : dataSnapshot.getChildren()){
                    FilmModel filmModel = filmNode.getValue(FilmModel.class);
                    Log.d("Firebase Interactor","Film: " + filmModel.getName());
                    films.add(filmModel);
                }

                callback.onFilmsAvailable();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<FilmModel> getFilms() {
        return films;
    }

    public FilmModel getFilmWithId(int id){
        //In a normal "for film.lenght" would not be used if not film.size because is an ArrayList
        //Get from films the movie with identifier id
        for (FilmModel film : films){
            if (film.getId()== id){
                return films.get(id);
            }
        }
        return null;
    }
}
