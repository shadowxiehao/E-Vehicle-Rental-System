package com.zz.mobilerentproject.view.ordermodel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.FeedbackData;
import com.zz.mobilerentproject.http.HttpFeedbackService;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.paymentmodel.PaymentActivity;

import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurOrderActivity extends AppCompatActivity {

    private Bundle                  bundle;
    private Button                  feedback_button;
    private Button                  return_button;
    private TextView                priceText;
    private TextView                kmText;
    private OkHttpClient            client;
    private Retrofit                retrofit;
    private UserModel               userModel;
    private static UserModelManager manager;
    private HttpFeedbackService     httpFeedbackService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_order);
        bundle = this.getIntent().getExtras();//获取bundle对象
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpFeedbackService = retrofit.create(HttpFeedbackService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initView();
        initOnClickListener();
    }

    private void initOnClickListener() {
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurOrderActivity.this, PaymentActivity.class);
                //传递文字
                Bundle bundle = new Bundle();//定义Bundle对象存储要传递的值
                bundle.putString("price", "-" + priceText.getText());
                intent.putExtras(bundle);//将bundle对象给intent
                startActivity(intent);
            }
        });
        feedback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
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

    private void initView() {
        feedback_button = findViewById(R.id.feedback_button);
        return_button = findViewById(R.id.return_bike);
        priceText = findViewById(R.id.price_text);
        kmText = findViewById(R.id.km_text);

        Random r = new Random();
        Double km = r.nextDouble() * 5;
        kmText.setText(String.format("%.2f", km));
        priceText.setText(String.format("%.2f", km * 2));
    }

    private void showInputDialog(){
        final EditText editText = new EditText(CurOrderActivity.this);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(CurOrderActivity.this);
        builder.setTitle("Problem").setView(editText);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CurOrderActivity.this,
                                "Submit Success!",
                                Toast.LENGTH_SHORT).show();

                        PostFeedbackRequest(userModel.user_email, bundle.getString("sn"), String.valueOf(editText.getText()));
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void PostFeedbackRequest(String email, String sn, String content) {
        Call<BasicData<FeedbackData>> call = httpFeedbackService.post(email, sn, content);
        call.enqueue(new Callback<BasicData<FeedbackData>>() {
            @Override
            public void onResponse(Call<BasicData<FeedbackData>> call, Response<BasicData<FeedbackData>> response) {
                BasicData<FeedbackData> data = response.body();
                if(data.getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(), "submit success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "submit fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasicData<FeedbackData>> call, Throwable t) {

            }
        });
    }
}
