package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testandroid.QuanLyLopHP.GetAllLopHP;
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

public class DeleteStudentInLopHP extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student_in_lop_hp);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Button delete = (Button) findViewById(R.id.deleteSVInLopHP_deleteBtn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();

                map.put("mssv", getIntent().getStringExtra("mssv"));
                map.put("MaBM", getIntent().getStringExtra("MaBM"));

                Call<Void> call = retrofitInterface.deleteStudentFromLopHP(map) ;
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            Toast.makeText(view.getContext(), "Xóa Khỏi Lớp Học Phần này Thành Công", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(view.getContext(), GetAll.class);
                            i.putExtra("lop",getIntent().getStringExtra("lop"));
                            i.putExtra("khoa",getIntent().getStringExtra("khoa"));
                            i.putExtra("MaBM",getIntent().getStringExtra("MaBM"));
                            view.getContext().startActivity(i);
                        }
                        else if (response.code() == 400){
                            Toast.makeText(view.getContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(view.getContext(),GetAll.class);
                            i.putExtra("lop",getIntent().getStringExtra("lop"));
                            i.putExtra("khoa",getIntent().getStringExtra("khoa"));
                            view.getContext().startActivity(i);
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