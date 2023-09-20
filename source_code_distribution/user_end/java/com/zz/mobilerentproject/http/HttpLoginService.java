package com.zz.mobilerentproject.http;



import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.UserData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpLoginService {

    @POST("user/login")
    @FormUrlEncoded
    Call<BasicData<UserData>> post(@Field("email") String username, @Field("password") String password);

    @GET("user/login")
    Call<BasicData<UserData>> get(@Field("email") String username, @Field("password") String password);

}
