package com.zz.mobilerentproject.http;



import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.FeedbackData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpFeedbackService {

    @POST("ticket/add")
    @FormUrlEncoded
    Call<BasicData<FeedbackData>> post(@Field("email") String username, @Field("sn") String sn, @Field("content") String content);

}
