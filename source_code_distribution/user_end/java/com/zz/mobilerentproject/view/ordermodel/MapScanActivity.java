package com.zz.mobilerentproject.view.ordermodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.http.HttpRentService;
import com.zz.mobilerentproject.util.PermissionUtils;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapScanActivity extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;

    private GoogleMap               map;
    private ImageView               mScan;
    private int                     requestCode;
    private Toast                   toast;
    private OkHttpClient            client;
    private Retrofit                retrofit;
    private UserModel               userModel;
    private static UserModelManager manager;
    private HttpRentService         httpRentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_scan);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpRentService = retrofit.create(HttpRentService.class);
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

    private void initView() {
        mScan = findViewById(R.id.scan);
    }

    private void initOnClickListener() {
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MapScanActivity.this, CaptureActivity.class), requestCode);
            }
        });
    }

    private void PostRentRequest(String email, String startTime, String sn) {
        Call<BasicData> call = httpRentService.post(email, startTime, sn);
        call.enqueue(new Callback<BasicData>() {
            @Override
            public void onResponse(Call<BasicData> call, Response<BasicData> response) {
                BasicData data = response.body();
                if(data.getCode().equals("200")) {
                    showToast("Scan Successful!");
                    Intent intent = new Intent(MapScanActivity.this, CurOrderActivity.class);
                    Bundle bundle = new Bundle();//定义Bundle对象存储要传递的值
                    bundle.putString("sn", sn);
                    intent.putExtras(bundle);//将bundle对象给intent
                    startActivity(intent);
                }
                else{
                    showToast("Scan Fail!");
                }
            }

            @Override
            public void onFailure(Call<BasicData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            String result = CameraScan.parseScanResult(data);
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            System.out.println(ts);
            PostRentRequest(userModel.user_email, ts, result);
        }
        else{
            showToast("Scan Fail!");
        }
    }

    private void showToast(String text){
        if(toast == null){
            toast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        }else{
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText("Scan Successful!");
        }
        toast.show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng glasgow = new LatLng(55.83, -4.25);
        map.addMarker(new MarkerOptions()
                .position(glasgow));
        LatLng glasgow1 = new LatLng(55.832, -4.253);
        map.addMarker(new MarkerOptions()
                .position(glasgow1));
        LatLng glasgow2 = new LatLng(55.829, -4.253);
        map.addMarker(new MarkerOptions()
                .position(glasgow2));
        LatLng glasgow3 = new LatLng(55.828, -4.251);
        map.addMarker(new MarkerOptions()
                .position(glasgow3));
        map.moveCamera(CameraUpdateFactory.newLatLng(glasgow));
        enableMyLocation();
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.83, -4.25), 15f));

    }


    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            System.out.println("111111111111");
            map.setMyLocationEnabled(true);
            return;
        }

        // 2. Otherwise, request location permissions from the user.
        PermissionUtils.requestLocationPermissions((AppCompatActivity) this, LOCATION_PERMISSION_REQUEST_CODE, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(this.getSupportFragmentManager(), "dialog");
    }




    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }
}
