package com.example.testandroid.QuanLySinhVien;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.ChonKhoi;
import com.example.testandroid.Model.Lop;
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

public class ChonLop extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_lop);
        Spinner spinner = (Spinner) findViewById(R.id.Khoa);
        List<String> lopSpinner = new ArrayList<>();

        String khoa = getIntent().getStringExtra("khoa");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<List<Lop>> call = retrofitInterface.getLop(khoa);

        call.enqueue(new Callback<List<Lop>>() {
            @Override
            public void onResponse(Call<List<Lop>> call, Response<List<Lop>> response) {
                if(response.code() == 200 ) {

                    List<Lop> list = response.body();
                    for(Lop l : list) {
                        lopSpinner.add(l.getName().toString());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChonLop.this,
                            android.R.layout.simple_spinner_item, lopSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                }
                else if (response.code() == 404) {

                }
            }

            @Override
            public void onFailure(Call<List<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(ChonLop.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button  = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sessionId = getIntent().getStringExtra("khoa");
                Intent i = new Intent(ChonLop.this, GetAll.class);
                if(sessionId != null) {
                    i.putExtra("lop",spinner.getSelectedItem().toString());
                    i.putExtra("ManagementType","Quản Lý Sinh Viên");
                    i.putExtra("khoa",khoa);

                    startActivity(i);
                }

            }
        });

        Button ThemLop = (Button) findViewById(R.id.AddLop);
        ThemLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ChonLop.this, AddLop.class);
                i.putExtra("khoa",khoa);
                startActivity(i);


            }
        });

        Button Back =(Button) findViewById(R.id.ChonLop_BackBtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(ChonLop.this, ChonKhoi.class);
                i.putExtra("ManagementType","Quản Lý Sinh Viên");
                System.out.println("Chon Lop  Type" + getIntent().getStringExtra("ManagementType"));
                startActivity(i);

            }
        });

    }


}