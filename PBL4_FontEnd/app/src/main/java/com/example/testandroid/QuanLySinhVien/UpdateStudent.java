package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testandroid.Model.Lop;
import com.example.testandroid.Model.User;
import com.example.testandroid.R;
import com.example.testandroid.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateStudent extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        String mssv = getIntent().getStringExtra("mssv");
        System.out.println(mssv);


        String khoa = getIntent().getStringExtra("khoa");
        String lop = getIntent().getStringExtra("lop");
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");

        EditText mssvET = findViewById(R.id.mssvET);
        mssvET.setFocusable(false);
        mssvET.setText(mssv);

        EditText nameET = findViewById(R.id.nameET);
        nameET.setText(name);

        EditText emailET = findViewById(R.id.emailET);
        emailET.setText(email);

        EditText passwordET = findViewById(R.id.passwordET);
        passwordET.setText(password);

        String[] arraySpinner = new String[] {
                "20", "21", "22", "23"
        };
        Spinner khoaSp = (Spinner) findViewById(R.id.KhoaSp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        khoaSp.setAdapter(adapter);

        for (int i = 0 ; i < arraySpinner.length ; i++ ) {
             if (Objects.equals(Arrays.asList(arraySpinner).get(i), khoa)) {
                 khoaSp.setSelection(i);
             }
        }
        Spinner lopSp = (Spinner) findViewById(R.id.LopSp);
        List<String> loplist = new ArrayList<>();
        Call<List<Lop>> call = retrofitInterface.getLop(khoa);
        call.enqueue(new Callback<List<Lop>>() {
            @Override
            public void onResponse(Call<List<Lop>> call, Response<List<Lop>> response) {
                if(response.code() == 200 ) {

                    List<Lop> list = response.body();
                    for(Lop l : list) {
                        loplist.add(l.getName().toString());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateStudent.this,
                            android.R.layout.simple_spinner_item, loplist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    lopSp.setAdapter(adapter);
                    for (int i = 0 ; i < loplist.size() ; i++ ) {
                        if (Objects.equals(loplist.get(i), lop)) {
                            lopSp.setSelection(i);
                        }
                    }


                }
                else if (response.code() == 404) {

                }
            }

            @Override
            public void onFailure(Call<List<Lop>> call, Throwable t) {

            }
        });
        khoaSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Call<List<Lop>> call = retrofitInterface.getLop(khoaSp.getSelectedItem().toString());
                List<String> newloplist = new ArrayList<>();
                call.enqueue(new Callback<List<Lop>>() {
                    @Override
                    public void onResponse(Call<List<Lop>> call, Response<List<Lop>> response) {
                        if(response.code() == 200 ) {

                            List<Lop> list = response.body();
                            for(Lop l : list) {
                                newloplist.add(l.getName().toString());

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateStudent.this,
                                    android.R.layout.simple_spinner_item, newloplist);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            lopSp.setAdapter(adapter);
                            for (int i = 0 ; i < newloplist.size() ; i++ ) {
                                if (Objects.equals(newloplist.get(i), lop)) {
                                    lopSp.setSelection(i);
                                }
                            }


                        }
                        else if (response.code() == 404) {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Lop>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    Button SuaBtn = (Button) findViewById(R.id.updateStudent_SuaBtn);

    SuaBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user = new User(
                    Integer.parseInt(mssv),
                    nameET.getText().toString(),
                    emailET.getText().toString(),
                    passwordET.getText().toString(),
                    lopSp.getSelectedItem().toString(),
                    khoaSp.getSelectedItem().toString()
            ) ;

            Call<Void> call = retrofitInterface.updateStudent(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200 ) {
                            Toast.makeText(UpdateStudent.this, "Update Thanh Cong" ,
                                    Toast.LENGTH_LONG).show();

                        }
                        else if(response.code() == 400) {
                            Toast.makeText(UpdateStudent.this, "Update That bai" ,
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
        }
    });

    Button back =(Button) findViewById(R.id.updateStudent_HuyBtn);

    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent i = new Intent(UpdateStudent.this, GetAll.class);
            i.putExtra("lop",lop);
            i.putExtra("khoa",khoa);
            startActivity(i);
        }
    });

    }
}