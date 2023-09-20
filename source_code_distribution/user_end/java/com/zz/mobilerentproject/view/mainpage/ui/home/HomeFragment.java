package com.zz.mobilerentproject.view.mainpage.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.databinding.FragmentHomeBinding;
import com.zz.mobilerentproject.view.ordermodel.MapScanActivity;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CardView            mBikeCardView;
    private CardView            mElectricCardView;
    private CardView            mCarCardView;
    private CardView            mChemistryCardView;
    private View                mView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mView = root;
        initView();
        initOnClickListener();
        return root;
    }

    private void initView() {
        mBikeCardView = mView.findViewById(R.id.bike_card);
        mElectricCardView = mView.findViewById(R.id.electric_card);
        mCarCardView = mView.findViewById(R.id.car_card);
        mChemistryCardView = mView.findViewById(R.id.chemistry_card);
    }

    private void initOnClickListener() {
        mBikeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapScanActivity.class);
                startActivity(intent);
            }
        });
        mElectricCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapScanActivity.class);
                startActivity(intent);
            }
        });
        mCarCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapScanActivity.class);
                startActivity(intent);
            }
        });
        mChemistryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapScanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}