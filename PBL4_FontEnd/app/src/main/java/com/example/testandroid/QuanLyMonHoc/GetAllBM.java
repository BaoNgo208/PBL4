package com.example.testandroid.QuanLyMonHoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testandroid.BoMonArrayAdapter;
import com.example.testandroid.Model.BoMon;
import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetAllBM extends AppCompatActivity {
    private Retrofit retrofit;
    private SearchView searchView;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    BoMonArrayAdapter boMonArrayAdapter ;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_bm);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);

        String khoa = getIntent().getStringExtra("khoa");

        Call<List<BoMon>> call = retrofitInterface.getAllBMByKhoa(khoa);

        call.enqueue(new Callback<List<BoMon>>() {
            @Override
            public void onResponse(Call<List<BoMon>> call, Response<List<BoMon>> response) {
                if(response.code() == 200 ) {
                    Toast.makeText(GetAllBM.this, "User Found" ,
                            Toast.LENGTH_LONG).show();
                    ArrayList<BoMon> bomons = (ArrayList<BoMon>) response.body();
//                    System.out.println(bomons.get(0).getTenBM());

                    lv = findViewById(R.id.GetAllBoMonListView);

                    boMonArrayAdapter = new  BoMonArrayAdapter(GetAllBM.this,R.layout.bm_layout_item,bomons);
                    lv.setAdapter(boMonArrayAdapter);


                }
                else if (response.code() == 404) {
                    Toast.makeText(GetAllBM.this, "User Not Found" ,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BoMon>> call, Throwable t) {

            }
        });


    }
}