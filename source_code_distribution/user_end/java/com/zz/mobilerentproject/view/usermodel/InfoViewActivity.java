package com.zz.mobilerentproject.view.usermodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;

public class InfoViewActivity extends AppCompatActivity {

    private        TextView         userEmail;
    private        TextView         userName;
    private        UserModel        userModel;
    private static UserModelManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initView();
    }

    private void initView() {
        userName = findViewById(R.id.user_info_name);
        userEmail = findViewById(R.id.user_info_email);
        System.out.println(userModel.user_name);
        userName.setText(userModel.user_name);
        userEmail.setText(userModel.user_email);
    }
}
