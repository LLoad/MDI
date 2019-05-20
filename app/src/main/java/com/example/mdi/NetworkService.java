package com.example.mdi;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkService {
    @GET("/connect")
    Call<Result> connecting();

    @Multipart
    @POST("/upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part file, @Part("name")RequestBody description);
}
