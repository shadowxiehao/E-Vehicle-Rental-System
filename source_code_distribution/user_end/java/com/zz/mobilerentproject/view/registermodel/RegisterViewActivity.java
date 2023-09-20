package com.zz.mobilerentproject.view.registermodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.FeedbackData;
import com.zz.mobilerentproject.bean.RegisterData;
import com.zz.mobilerentproject.bean.UserData;
import com.zz.mobilerentproject.http.HttpLoginService;
import com.zz.mobilerentproject.http.HttpRegisterService;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.loginmodel.LoginViewActivity;
import com.zz.mobilerentproject.view.mainpage.ViewPageActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterViewActivity extends AppCompatActivity {

    private Button                      btn_back;
    private Button                      btn_register;
    private TextView                    lastName;
    private TextView                    firstName;
    private TextView                    email;
    private TextView                    password;
    private OkHttpClient                client;
    private Retrofit                    retrofit;
    private HttpRegisterService         httpRegisterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpRegisterService = retrofit.create(HttpRegisterService.class);
        initView();
        initOnClickListener();
    }

    private void initOnClickListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void HttpRequest(String firstname, String lastname, String email, String password) {
        RegisterData registerData = new RegisterData(firstname, lastname, email, password, "USER", "");
        Call<RegisterData> call = httpRegisterService.post(registerData);
        call.enqueue(new Callback<RegisterData>() {

            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                RegisterData data = response.body();
                if(data.getCode().equals("200")){
                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Input Error, Please check", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {

            }

        });
    }

    private void initView() {
        lastName = findViewById(R.id.lastname);
        firstName = findViewById(R.id.firstname);
        email = findViewById(R.id.user_register_email);
        password = findViewById(R.id.register_password);
        btn_back = findViewById(R.id.btn_register_back);
        btn_register = findViewById(R.id.btn_register);
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

}
