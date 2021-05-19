package com.example.imagenes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    Adapter adapter;
    RecyclerView recyclerView;
    String[] datoNombre, datoUrls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://simplifiedcoding.net/demos/view-flipper/heroes.php",
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("heroes");

                            datoNombre = new String[jsonArray.length()];
                            datoUrls = new String[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject hero = jsonArray.getJSONObject(i);

                                String nombre = hero.getString("name");
                                String imageUrl = hero.getString("imageurl");

                                Log.i("MOSTRAR", nombre + " - " + imageUrl);


                                recibir(nombre, imageUrl, jsonArray.length());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );


        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    int bandera = 0;


    public void recibir(String names, String imgUrls, int limite) {

        datoNombre[bandera] = names;
        datoUrls[bandera] = imgUrls;
        bandera++;
        if (bandera == limite) {
            recyclerView = findViewById(R.id.rcvListado);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ArrayList<imagenes> list = new ArrayList<>();


            for (int i = 0; i < datoNombre.length; i++) {
                list.add(new imagenes( datoNombre[i],datoUrls[i]));
            }

            adapter = new Adapter(list);
            recyclerView.setAdapter(adapter);
            bandera = 0;
        }
    }
}