package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testandroid.Model.Lop;
import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddLop extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lop);
        String khoa = getIntent().getStringExtra("khoa");
        List<String> lopSpinner = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<List<Lop>> call = retrofitInterface.getLop(khoa);


        String[] arraySpinner = new String[] {
                khoa
        };
        Spinner s = (Spinner) findViewById(R.id.Khoa);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);






        Button them = (Button) findViewById(R.id.ThemSv);
        final EditText TenLop= (EditText) findViewById(R.id.email);





        them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> map = new HashMap<>();
                        String lop = s.getSelectedItem().toString();
                        map.put("name", TenLop.getText().toString());
                        map.put("khoa", lop);
                        Call<Void> call = retrofitInterface.AddLop(map);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if (response.code() == 200) {

                                    Toast.makeText( AddLop.this, "Them Thanh Cong" ,
                                            Toast.LENGTH_LONG).show();


                                } else if (response.code() == 400) {

                                    Toast.makeText( AddLop.this, "Them That Bai" ,
                                            Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AddLop.this, t.getMessage(),
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
                Intent i = new Intent(AddLop.this, ChonLop.class);
                i.putExtra("khoa",khoa);
                startActivity(i);

            }
        });


    }
}