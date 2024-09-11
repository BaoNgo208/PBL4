package com.example.testandroid.QuanLyLopHP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testandroid.BoMonArrayAdapter;
import com.example.testandroid.LopHPArrayAdapter;
import com.example.testandroid.Model.BoMon;
import com.example.testandroid.Model.LopHP;
import com.example.testandroid.QuanLyMonHoc.GetAllBM;
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

public class GetAllLopHP extends AppCompatActivity {

    private Retrofit retrofit;
    private SearchView searchView;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    LopHPArrayAdapter lopHPArrayAdapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_lop_hp);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);

        String IdBM = getIntent().getStringExtra("IdBM");
        System.out.println(IdBM);
        Call<List<LopHP>> call = retrofitInterface.getAllLopHPByBoMon(IdBM);

        call.enqueue(new Callback<List<LopHP>>() {
            @Override
            public void onResponse(Call<List<LopHP>> call, Response<List<LopHP>> response) {
                if(response.code() == 200 ) {

                    ArrayList<LopHP> lopHPS = (ArrayList<LopHP>) response.body();
                    System.out.println(lopHPS.get(0).getTenLopBM());

                    lv = findViewById(R.id.GetAllLopHPListView);

                    lopHPArrayAdapter = new LopHPArrayAdapter(GetAllLopHP.this,R.layout.lophp_layout_item,lopHPS);
                    lv.setAdapter(lopHPArrayAdapter);


                }
                else if (response.code() == 404) {
                    Toast.makeText(GetAllLopHP.this, "LopHP Not Found" ,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LopHP>> call, Throwable t) {

            }
        });
    }
}