package com.zz.mobilerentproject.http;

import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.OrderListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpOrderService {

    @GET("order/get/all/email")
    Call<BasicData<OrderListData>> get(@Query("email") String email);

}
