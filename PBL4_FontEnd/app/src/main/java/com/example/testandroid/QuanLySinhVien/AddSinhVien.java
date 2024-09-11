package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testandroid.Model.User;
import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddSinhVien extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinh_vien);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        String khoa = getIntent().getStringExtra("khoa");
        String lop = getIntent().getStringExtra("lop");
        System.out.println("add sv khoa " + khoa);




        Button addSv = (Button)findViewById(R.id.ThemSv);
        final EditText email = findViewById(R.id.email);
        final EditText name = findViewById(R.id.ten);
        final EditText password = findViewById(R.id.password);

        addSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("add sv khoa " + khoa);

                User user = new User(
                   name.getText().toString(),
                   email.getText().toString(),
                   password.getText().toString(),
                   lop,
                   khoa
                ) ;


                Call<Void> call = retrofitInterface.createStudent(user);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(AddSinhVien.this, "Tao Thanh Cong" ,
                                    Toast.LENGTH_LONG).show();




                        } else if (response.code() == 400) {

                            Toast.makeText(AddSinhVien.this, "Tao SV That Bai " ,
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddSinhVien.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    Button thoat = (Button) findViewById(R.id.thoat);

    thoat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent i = new Intent(AddSinhVien.this, GetAll.class);
            i.putExtra("lop",lop);
            i.putExtra("khoa",khoa);
            startActivity(i);
        }
    });



    }
}