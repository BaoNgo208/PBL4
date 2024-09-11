package com.example.testandroid;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testandroid.Model.BoMon;
import com.example.testandroid.Model.LopHP;
import com.example.testandroid.QuanLyLopHP.GetAllLopHP;
import com.example.testandroid.QuanLyMonHoc.GetAllBM;
import com.example.testandroid.QuanLySinhVien.GetAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class LopHPArrayAdapter extends ArrayAdapter<LopHP> implements Filterable {
    Activity context;
    int IdLayout;
    ArrayList<LopHP> myList;
    ArrayList<LopHP> myListCopy ;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

    List<LopHP> filteredData ;
    LayoutInflater mInflater;

    public LopHPArrayAdapter(Activity context, int IdLayout, ArrayList<LopHP> myList) {
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

        LopHP lopHP = myList.get(position);


        TextView MaLopHP=convertView.findViewById(R.id.MaLopHP);
        MaLopHP.setText(lopHP.getMaBM().toString() );

        TextView TenLopHP= convertView.findViewById(R.id.TenLopHP);
        TenLopHP.setText(lopHP.getTenLopBM().toString());



        TextView SoLuong = convertView.findViewById(R.id.SoLuong);
        SoLuong.setText(lopHP.getSoLuong().toString());

        Button XemListSV=convertView.findViewById(R.id.XemListSV);
        XemListSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i = new Intent(view.getContext(), GetAll.class);
                 i.putExtra("MaBM",MaLopHP.getText().toString());
                 view.getContext().startActivity(i);
            }
        });

        return convertView;
    }

}
