package com.example.testandroid;

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

import com.example.testandroid.QuanLyMonHoc.GetAllBM;
import com.example.testandroid.QuanLySinhVien.ChonLop;

public class ChonKhoi extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_khoi);




        String[] arraySpinner = new String[] {
                "20", "21", "22", "23"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                 System.out.println(s.getSelectedItem().toString());
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String ManagementType=getIntent().getStringExtra("ManagementType");
                System.out.println( " Chon Khoi: ManagementType"+ ManagementType);
                if(ManagementType.equals("Quản Lý Sinh Viên") ) {
                    Intent i = new Intent(ChonKhoi.this, ChonLop.class);
                    i.putExtra("khoa",s.getSelectedItem().toString());
                    i.putExtra("ManagementType",ManagementType);
                    startActivity(i);
                }
                else {

                    Intent i = new Intent(ChonKhoi.this, GetAllBM.class);
                    i.putExtra("khoa",s.getSelectedItem().toString());
                    startActivity(i);
                }


            }
        });

        Button back =(Button) findViewById(R.id.ChonKhoi_Backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChonKhoi.this, ChooseManagement.class);
                startActivity(i);
            }
        });
    }

}