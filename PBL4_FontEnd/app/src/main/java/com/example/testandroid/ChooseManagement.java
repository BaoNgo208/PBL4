package com.example.testandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_management);

        Button QLSV =(Button) findViewById(R.id.QLSV) ;
        Button QLBM =(Button) findViewById(R.id.QLBM) ;

        QLSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(ChooseManagement.this, ChonKhoi.class);
                i.putExtra("ManagementType",QLSV.getText().toString());
                startActivity(i);
            }
        });

        QLBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(ChooseManagement.this, ChonKhoi.class);
                i.putExtra("ManagementType",QLBM.getText().toString());
                startActivity(i);
            }
        });

    }


}