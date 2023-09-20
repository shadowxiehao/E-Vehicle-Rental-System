package com.zz.mobilerentproject.view.mainpage;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.databinding.ActivityViewPageBinding;

public class ViewPageActivity extends AppCompatActivity {

    private ActivityViewPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_view_page);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}