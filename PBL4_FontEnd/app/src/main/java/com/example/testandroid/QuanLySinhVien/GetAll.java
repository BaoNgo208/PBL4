package com.example.testandroid.QuanLySinhVien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testandroid.Model.User;
import com.example.testandroid.MyArrayAdapter;
import com.example.testandroid.QuanLyLopHP.AddSVToLopHP;
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

public class GetAll extends AppCompatActivity {

    private Retrofit retrofit;
    private SearchView searchView;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

//    ArrayList<User> users;

    MyArrayAdapter myArrayAdapter;


    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        String khoa = getIntent().getStringExtra("khoa");
        String lop = getIntent().getStringExtra("lop");



        String MaBm=getIntent().getStringExtra("MaBM");
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        if(lop !=null && MaBm == null) {




            Call<List<User>> call = retrofitInterface.getSVByLopAndKhoa(lop);

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.code() == 200 ) {
                        ArrayList<User> users = (ArrayList<User>) response.body();
                        lv = findViewById(R.id.GetAllListView);

                        myArrayAdapter = new MyArrayAdapter(GetAll.this,R.layout.layout_item,users,true);
                        lv.setAdapter(myArrayAdapter);


                    }
                    else if (response.code() == 404) {
                        Toast.makeText(GetAll.this, "User Not Found" ,
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(GetAll.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });
        }
        else if (MaBm != null) {


            Call<List<User>> call = retrofitInterface.getAllSVByLopHP(MaBm);

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.code() == 200 ) {
                        ArrayList<User> users = (ArrayList<User>) response.body();
                        lv = findViewById(R.id.GetAllListView);

                        myArrayAdapter = new MyArrayAdapter(GetAll.this,R.layout.layout_item,users,false,MaBm );
                        lv.setAdapter(myArrayAdapter);


                    }
                    else if (response.code() == 404) {
                        Toast.makeText(GetAll.this, "User Not Found" ,
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(GetAll.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });
        }
        Button add = (Button) findViewById(R.id.Add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MaBm == null) {
                    Intent i = new Intent(GetAll.this, AddSinhVien.class);
                    i.putExtra("lop",lop);
                    i.putExtra("khoa",khoa);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(GetAll.this, AddSVToLopHP.class);
                    i.putExtra("MaBM",MaBm);
                    i.putExtra("khoa",khoa);
                    startActivity(i);

                }

            }
        });

        Button back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(GetAll.this,ChonLop.class);
                i.putExtra("khoa",khoa);
                i.putExtra("ManagementType","");
                System.out.println("get all type : " +  getIntent().getStringExtra("ManagementType"));
                startActivity(i);
            }
        });


        searchView = findViewById(R.id.searchView);
        EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myArrayAdapter.getFilter().filter(newText);
                return true;
            }
        });




    }




}