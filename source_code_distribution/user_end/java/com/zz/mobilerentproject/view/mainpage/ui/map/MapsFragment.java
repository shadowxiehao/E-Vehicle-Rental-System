package com.zz.mobilerentproject.view.mainpage.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.zz.mobilerentproject.databinding.FragmentMapsBinding;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.adapter.OrderAdapter;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.OrderData;
import com.zz.mobilerentproject.bean.OrderListData;
import com.zz.mobilerentproject.http.HttpOrderService;
import com.zz.mobilerentproject.util.PermissionUtils;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
     ActivityCompat.OnRequestPermissionsResultCallback,OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;

    private FragmentMapsBinding     binding;
    private View                    mView;
    private GoogleMap               map;
    private ImageView               mScan;
    private int                     requestCode;
    private Toast                   toast;
    private List<OrderData>         mList;
    private RecyclerView            mRecyclerView;  //Order列表
    private OrderAdapter            adapter;  //适配器
    private HttpOrderService        httpOrderService;
    private UserModel               userModel;
    private static UserModelManager manager;
    private List<OrderListData>     orderList;

    private OkHttpClient            client;
    private Retrofit                retrofit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mView = root;
        inithttpClient();
        retrofit = new Retrofit.Builder().baseUrl("http://20.68.139.52/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpOrderService = retrofit.create(HttpOrderService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initData();
        initView();
        initRecyclerView();
        return root;
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng glasgow = new LatLng(55.83, -4.25);
        map.addMarker(new MarkerOptions()
                .position(glasgow));
        map.moveCamera(CameraUpdateFactory.newLatLng(glasgow));
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.83, -4.25), 15f));
        enableMyLocation();
    }

//    private void HttpRequest(String email) {
//        Call<BasicData<OrderListData>> call = httpOrderService.get(email);
//        call.enqueue(new Callback<BasicData<OrderListData>>() {
//            @Override
//            public void onResponse(Call<BasicData<OrderListData>> call, Response<BasicData<OrderListData>> response) {
//                BasicData<OrderListData> data = response.body();
//                if(data.getCode().equals("200")){
////                    orderList = data.getData();
//                    System.out.println(orderList);
//                }
//                else{
//                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BasicData<OrderListData>> call, Throwable t) {
//
//            }
//
//        });
//    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.user_order_recyclerview);
        //设置瀑布流布局为2列，垂直方向滑动
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderAdapter(getContext(), mList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
//        HttpRequest(userModel.user_email);
        mList = new ArrayList<>();
//        for (int i=0;i<orderList.size();i++) {
//
//        }
        OrderData orderData = new OrderData(R.drawable.car,"28/32 Cathedral Sq",
                "08/10/2022 3:26pm","£1.65");
        OrderData orderData1 = new OrderData(R.drawable.bike,"122-128 Dumbarton Rd",
                "07/10/2022 5:58pm","£1.58");
        OrderData orderData2 = new OrderData(R.drawable.car,"28/32 Cathedral Sq",
                "08/10/2022 3:26pm","£1.65");
        mList.add(orderData);
        mList.add(orderData1);
        mList.add(orderData2);
    }

    private void initView() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }

        // 2. Otherwise, request location permissions from the user.
        PermissionUtils.requestLocationPermissions((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE, true);
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
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }




    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }



}