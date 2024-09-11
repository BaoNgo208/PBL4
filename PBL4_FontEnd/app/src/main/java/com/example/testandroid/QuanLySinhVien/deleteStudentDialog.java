package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class deleteStudentDialog extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student_dialog);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Button deletebtn = (Button) findViewById(R.id.deleteDialog_deleteBtn);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = retrofitInterface.deleteStudent(Integer.parseInt(getIntent().getStringExtra("mssv"))) ;
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.code() == 200) {
                                            Toast.makeText(view.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
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