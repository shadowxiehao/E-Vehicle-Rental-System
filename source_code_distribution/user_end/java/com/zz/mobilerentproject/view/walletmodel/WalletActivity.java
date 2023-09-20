package com.zz.mobilerentproject.view.walletmodel;

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
import com.zz.mobilerentproject.bean.UserData;
import com.zz.mobilerentproject.bean.WalletData;
import com.zz.mobilerentproject.http.HttpLoginService;
import com.zz.mobilerentproject.http.HttpWalletService;
import com.zz.mobilerentproject.util.UserModel;
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

public class WalletActivity extends AppCompatActivity {

    private Button                  recharge_button;
    private TextView                balance_text;
    private String                  balance;
    private Button                  back_button;
    private OkHttpClient            client;
    private Retrofit                retrofit;
    private UserModel               userModel;
    private static UserModelManager manager;
    private HttpWalletService       httpWalletService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpWalletService = retrofit.create(HttpWalletService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initView();
        initData();
        initOnClickListener();
    }

    private void initData() {
        GetBalanceRequest(userModel.user_email);
    }

    private void initOnClickListener() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recharge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBalanceRequest(userModel.user_email, "5");
            }
        });
    }

    private void initView() {
        back_button = findViewById(R.id.wallet_back);
        balance_text = findViewById(R.id.balance);
        recharge_button = findViewById(R.id.recharge_button);
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

    private void GetBalanceRequest(String email) {
        Call<BasicData<WalletData>> call = httpWalletService.post(email);
        call.enqueue(new Callback<BasicData<WalletData>>() {
            @Override
            public void onResponse(Call<BasicData<WalletData>> call, Response<BasicData<WalletData>> response) {
                BasicData<WalletData> data = response.body();
                balance = data.getData().getBalance().toString();
                balance_text.setText(balance);
            }

            @Override
            public void onFailure(Call<BasicData<WalletData>> call, Throwable t) {

            }
        });
    }

    private void UpdateBalanceRequest(String email, String value) {

        Call<BasicData<WalletData>> call = httpWalletService.post(email, value);
        call.enqueue(new Callback<BasicData<WalletData>>() {
            @Override
            public void onResponse(Call<BasicData<WalletData>> call, Response<BasicData<WalletData>> response) {
                BasicData<WalletData> data = response.body();
                balance = data.getData().getBalance().toString();
                balance_text.setText(balance);
            }

            @Override
            public void onFailure(Call<BasicData<WalletData>> call, Throwable t) {

            }
        });
    }

}
