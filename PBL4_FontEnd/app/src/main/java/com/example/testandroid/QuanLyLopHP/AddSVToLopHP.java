 package com.example.testandroid.QuanLyLopHP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testandroid.QuanLySinhVien.GetAll;
import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class AddSVToLopHP extends AppCompatActivity {

     private Retrofit retrofit;
     private RetrofitInterface retrofitInterface;
     private String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_svto_lop_hp);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        String MaBm=getIntent().getStringExtra("MaBM");
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        System.out.println(MaBm);

        Button add = (Button) findViewById(R.id.AddSVToLopHP_btn);
        EditText mssv = findViewById(R.id.mssvEditText);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("mssv", mssv.getText().toString());
                map.put("MaBM", getIntent().getStringExtra("MaBM"));

                Call<Void> call = retrofitInterface.AddSVToLopHP(map) ;
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                          if(response.code() == 200) {
                              Toast.makeText(view.getContext(), "Thêm Sinh Viên vào Lớp Học Phần này Thành Công", Toast.LENGTH_SHORT).show();
                              finish();
                              Intent i = new Intent(AddSVToLopHP.this, GetAll.class);
                              i.putExtra("MaBM",MaBm);
                              view.getContext().startActivity(i);
                          }
                          else if(response.code() == 400) {
                              Toast.makeText(view.getContext(), "Sinh viên này đã có trong lớp này", Toast.LENGTH_SHORT).show();
                              finish();
                          }
                          else if(response.code() == 404) {
                              Toast.makeText(view.getContext(), "mssv sai", Toast.LENGTH_SHORT).show();
                              finish();
                          }
                          else if(response.code() == 405) {
                              Toast.makeText(view.getContext(), "Sinh viên này chưa đủ điều kiện đẻ học nhóm này", Toast.LENGTH_SHORT).show();
                              finish();
                          }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}