package com.zz.mobilerentproject.view.loginmodel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mmkv.MMKV;
import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.UserData;
import com.zz.mobilerentproject.http.HttpLoginService;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.mainpage.ViewPageActivity;
import com.zz.mobilerentproject.view.registermodel.RegisterViewActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewActivity extends AppCompatActivity {

    MMKV kv = MMKV.defaultMMKV();

    private Button                  turn_register;
    private EditText                mUsername;
    private EditText                mPassword;
    private OkHttpClient            client;
    private Retrofit                retrofit;
    private HttpLoginService        httpLoginService;
    private UserModel               userModel;
    private static UserModelManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpLoginService = retrofit.create(HttpLoginService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initView();
        initOnClickListener();
    }

    private void inithttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private void initOnClickListener() {
        turn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginViewActivity.this, RegisterViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        turn_register = findViewById(R.id.turn_register);
    }

    public void login_click(View view) {
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Username is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpRequest(username, password);
    }

    private void HttpRequest(String username, String password) {
        Call<BasicData<UserData>> call = httpLoginService.post(username, password);
        call.enqueue(new Callback<BasicData<UserData>>() {
            @Override
            public void onResponse(Call<BasicData<UserData>> call, Response<BasicData<UserData>> response) {
                BasicData<UserData> data = response.body();
                if(data.getCode().equals("200")){
                    userModel.user_email = username;
                    userModel.user_password = password;
                    userModel.user_id = data.getData().getUser_id().toString();
                    userModel.user_name = data.getData().getUser_name().toString();;
                    manager.setUserModel(userModel);

                    kv.encode("login_judge", true);
                    Intent intent = new Intent(LoginViewActivity.this, ViewPageActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "account or password error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicData<UserData>> call, Throwable t) {

            }
        });
    }

}
