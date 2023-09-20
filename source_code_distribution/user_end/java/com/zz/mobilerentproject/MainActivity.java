package com.zz.mobilerentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mmkv.MMKV;
import com.zz.mobilerentproject.view.loginmodel.LoginViewActivity;
import com.zz.mobilerentproject.view.mainpage.ViewPageActivity;

public class MainActivity extends AppCompatActivity {

    MMKV kv = MMKV.defaultMMKV();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(JudgeState()){
            Intent intent = new Intent(this, ViewPageActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
        }
    }

    private boolean JudgeState() {
        return kv.decodeBool("login_judge");
    }
}