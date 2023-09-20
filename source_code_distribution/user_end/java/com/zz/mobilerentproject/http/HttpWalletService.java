package com.zz.mobilerentproject.http;

import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.WalletData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpWalletService {

    @POST("balance/showbalance")
    @FormUrlEncoded
    Call<BasicData<WalletData>> post(@Field("email") String email);

    @POST("balance/topup")
    @FormUrlEncoded
    Call<BasicData<WalletData>> post(@Field("email") String email, @Field("value") String value);

}
