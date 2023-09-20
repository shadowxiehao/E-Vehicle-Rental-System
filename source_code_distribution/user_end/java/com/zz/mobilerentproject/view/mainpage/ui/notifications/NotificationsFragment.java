package com.zz.mobilerentproject.view.mainpage.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tencent.mmkv.MMKV;
import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.adapter.UserChoiceAdapter;
import com.zz.mobilerentproject.bean.UserChoiceData;
import com.zz.mobilerentproject.databinding.FragmentNotificationsBinding;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.loginmodel.LoginViewActivity;
import com.zz.mobilerentproject.view.usermodel.MessageViewActivity;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    MMKV kv = MMKV.defaultMMKV();
    private TextView                        mNameText;
    private View                            mView;
    private Button                          mExitButton;
    private FragmentNotificationsBinding    binding;
    private RecyclerView                    mRecyclerView;  //用户列表
    private UserChoiceAdapter               adapter;  //适配器
    private List<UserChoiceData>            mList;
    private LinearLayout                    mMessageLayout;
    private UserModel                       userModel;
    private static UserModelManager         manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mView = root;
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initData();
        initView();
        initOnClickListener();
        initRecyclerView();
        return root;
    }

    private void initData() {
        mList = new ArrayList<>();
        UserChoiceData userChoiceData = new UserChoiceData(R.drawable.wallet,"Wallet");
        UserChoiceData userChoiceData1 = new UserChoiceData(R.drawable.order,"Order");
        UserChoiceData userChoiceData2 = new UserChoiceData(R.drawable.help,"Help");
        UserChoiceData userChoiceData3 = new UserChoiceData(R.drawable.information,"Information");
        mList.add(userChoiceData);
        mList.add(userChoiceData1);
        mList.add(userChoiceData2);
        mList.add(userChoiceData3);

    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.user_choice_recyclerview);
        //设置瀑布流布局为2列，垂直方向滑动
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new UserChoiceAdapter(getContext(), mList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initOnClickListener() {
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kv.encode("login_judge", false);
                Intent intent = new Intent(getContext(), LoginViewActivity.class);
                startActivity(intent);
            }
        });
        mMessageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageViewActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        mExitButton = mView.findViewById(R.id.user_exit_button);
        mMessageLayout = mView.findViewById(R.id.message_linearlayout);
        mNameText = mView.findViewById(R.id.user_name);
        mNameText.setText(userModel.user_name);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}