package com.example.testandroid;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testandroid.Model.User;
import com.example.testandroid.QuanLySinhVien.DeleteStudentInLopHP;
import com.example.testandroid.QuanLySinhVien.GetAll;
import com.example.testandroid.QuanLySinhVien.UpdateStudent;
import com.example.testandroid.QuanLySinhVien.deleteStudentDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyArrayAdapter extends ArrayAdapter<User> implements Filterable {

    Activity context;
    int IdLayout;
    ArrayList<User> myList;
    ArrayList<User> myListCopy ;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";
    boolean isGetAllSVByLop ;
    String MaBM;

    List<User> filteredData ;
    LayoutInflater mInflater;
    ItemFilter mFilter = new ItemFilter();




    public MyArrayAdapter(Activity context, int IdLayout, ArrayList<User> myList,boolean isGetAllSVByLop) {
        super(context, IdLayout,myList);
        this.context = context;
        this.IdLayout = IdLayout;
        this.myList = myList;
        this.isGetAllSVByLop= isGetAllSVByLop ;
        myListCopy = new ArrayList<>(myList);
    }

    public MyArrayAdapter(Activity context, int IdLayout, ArrayList<User> myList,boolean isGetAllSVByLop,String MaBM) {
        super(context, IdLayout,myList);
        this.context = context;
        this.IdLayout = IdLayout;
        this.myList = myList;
        this.isGetAllSVByLop= isGetAllSVByLop ;
        this.MaBM = MaBM;
        myListCopy = new ArrayList<>(myList);
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();



            int count = myListCopy.size();
            ArrayList<User> nlist = new ArrayList<User>(count);


            if(constraint == null || constraint.length() == 0) {
                nlist.addAll(myListCopy);
            }
            else  {
                for(User user : myListCopy) {
                    if(user.getName().toLowerCase().contains(filterString.trim())) {
                        nlist.add(user);
                    }
                }

            }


            results.values = nlist;
            results.count = nlist.size();
            System.out.println(results.count);
            return results;
        }



        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myList.clear();
            myList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(IdLayout,null);

        User user = myList.get(position);

        TextView email = convertView.findViewById(R.id.Mssv);
        email.setText(user.getMssv().toString());

        TextView name = convertView.findViewById(R.id.TenSV);
        name.setText(user.getName());

        TextView lop = convertView.findViewById(R.id.Lop);
        lop.setText(user.getLop());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        convertView.findViewById(R.id.SuaBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdateStudent.class);
                i.putExtra("mssv",user.getMssv().toString());
                i.putExtra("email",user.getEmail());
                i.putExtra("name" ,user.getName());
                i.putExtra("password",user.getPassword());
                i.putExtra("lop",user.getLop());
                i.putExtra("khoa",user.getKhoa());
                view.getContext().startActivity(i);

            }
        });

        convertView.findViewById(R.id.XoaBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isGetAllSVByLop) {
                    Intent i = new Intent(view.getContext(), deleteStudentDialog.class);
                    i.putExtra("mssv",user.getMssv().toString());
                    i.putExtra("lop",user.getLop());
                    i.putExtra("khoa",user.getKhoa());
                    view.getContext().startActivity(i);
                }
                else {

                    Intent i = new Intent(view.getContext(), DeleteStudentInLopHP.class);
                    i.putExtra("mssv",user.getMssv().toString());
                    i.putExtra("lop",user.getLop());
                    i.putExtra("khoa",user.getKhoa());
                    i.putExtra("MaBM",MaBM);
                    view.getContext().startActivity(i);
                }

            }
        });

        return convertView;


    }


}
