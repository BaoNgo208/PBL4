package com.example.testandroid;

import com.example.testandroid.Model.BoMon;
import com.example.testandroid.Model.Lop;
import com.example.testandroid.Model.LopHP;
import com.example.testandroid.Model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/login")
    Call<Void> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @GET("/get/lop/{khoa}")
    Call<List<User>> getLopByKhoa(@Path("khoa") int khoa);

    @GET("/get/khoa/{lop}")
    Call<List<User>> getSVByLopAndKhoa(@Path("lop") String lop);

    @GET("/get/lop/{khoa}")
    Call<List<Lop>> getLop(@Path("khoa") String khoa);

    @POST("/create/lop")
    Call<Void>AddLop(@Body HashMap<String,String> map);

    @POST("/create/student")
    Call<Void> createStudent(@Body User user);

    @GET("/getinfor/bao")
    Call<User> getInfor();

    @GET("/getall")
    Call<List<User>> getAll();
    @PUT("/update/student/")
    Call<Void> updateStudent(@Body User user);

    @DELETE("/delete/student/{mssv}")
    Call<Void>deleteStudent(@Path("mssv") int mssv);
    @PUT("/delete/student/lophp")
    Call<Void>deleteStudentFromLopHP(@Body HashMap<String, String> map);

    @GET("/get/bm/{khoa}")
    Call<List<BoMon>> getAllBMByKhoa(@Path("khoa") String khoa);

    @GET("/get/lophp/{IdBM}")
    Call<List<LopHP>> getAllLopHPByBoMon(@Path("IdBM") String IdBM);

    @GET("/get/lophp/sinhviens/{MaBM}")
    Call<List<User>> getAllSVByLopHP(@Path("MaBM") String MaBM);
    @PUT("/add/lophp/student")
    Call<Void> AddSVToLopHP(@Body HashMap<String,String> map);
}