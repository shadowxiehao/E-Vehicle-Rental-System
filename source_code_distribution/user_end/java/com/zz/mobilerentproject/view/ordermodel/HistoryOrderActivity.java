package com.zz.mobilerentproject.view.ordermodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;


public class HistoryOrderActivity extends AppCompatActivity {

    private TextView            his_order_time;
    private TextView            his_order_price;
    private TextView            his_order_address;
    private Bundle              bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_order);
        bundle = this.getIntent().getExtras();//获取bundle对象
        initView();
        initOnClickListener();
    }

    private void initView() {
        his_order_time = findViewById(R.id.his_order_time);
        his_order_price = findViewById(R.id.his_order_price);
        his_order_address = findViewById(R.id.end_place);

        his_order_time.setText(bundle.getString("time"));
        his_order_price.setText(bundle.getString("price"));
        his_order_address.setText(bundle.getString("address"));
    }

    private void initOnClickListener() {

    }

}
