package com.zz.mobilerentproject.http;

import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.FeedbackData;
import com.zz.mobilerentproject.bean.RegisterData;
import com.zz.mobilerentproject.bean.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpRegisterService {

    @POST("user/add")
    Call<RegisterData> post(@Body RegisterData registerData);

}
