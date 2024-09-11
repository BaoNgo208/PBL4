package com.example.testandroid;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testandroid.Model.BoMon;
import com.example.testandroid.QuanLyLopHP.GetAllLopHP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class BoMonArrayAdapter extends  ArrayAdapter<BoMon> implements Filterable{
    Activity context;
    int IdLayout;
    ArrayList<BoMon> myList;
    ArrayList<BoMon> myListCopy ;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    List<BoMon> filteredData ;
    LayoutInflater mInflater;
//    ItemFilter mFilter = new ItemFilter();

    public BoMonArrayAdapter(Activity context, int IdLayout, ArrayList<BoMon> myList) {
            super(context,IdLayout,myList);
            this.context = context;
            this.IdLayout = IdLayout;
            this.myList = myList;
            myListCopy = new ArrayList<>(myList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(IdLayout,null);

        BoMon boMon = myList.get(position);


        TextView IdBM=convertView.findViewById(R.id.IdBM);
        IdBM.setText(boMon.getIdBM().toString() );

        TextView TenBM= convertView.findViewById(R.id.TenBM);
        TenBM.setText(boMon.getTenBM().toString());



        TextView Khoa = convertView.findViewById(R.id.BM_Khoa);
        Khoa.setText(boMon.getKhoa().toString());

        convertView.findViewById(R.id.XemListLop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(view.getContext(), GetAllLopHP.class);
                i.putExtra("IdBM",IdBM.getText().toString());
                view.getContext().startActivity(i);
            }
        });


        return convertView;
    }
}
