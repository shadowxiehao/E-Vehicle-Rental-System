package com.zz.mobilerentproject.http;

import com.zz.mobilerentproject.bean.BasicData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpRentService {

    @POST("order/init")
    @FormUrlEncoded
    Call<BasicData> post(@Field("email") String email, @Field("startTime") String startTime, @Field("sn") String sn);

    @POST("order/end")
    @FormUrlEncoded
    Call<BasicData> post_end(@Field("email") String email, @Field("endTime") String endTime);
}
