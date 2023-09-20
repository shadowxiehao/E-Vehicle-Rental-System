package com.zz.mobilerentproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.UserChoiceData;
import com.zz.mobilerentproject.view.usermodel.InfoViewActivity;
import com.zz.mobilerentproject.view.walletmodel.WalletActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserChoiceAdapter extends RecyclerView.Adapter<UserChoiceAdapter.ViewHolder>{

    private Context     mContext;
    private List<UserChoiceData>    mList;

    public UserChoiceAdapter(Context context, List<UserChoiceData> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.user_choice_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@Nullable @NotNull ViewHolder holder, int position) {
        UserChoiceData user_choice_data = mList.get(position);
        holder.user_choice_text.setText(user_choice_data.getUser_choice_text());
        holder.user_choice_image.setImageResource(user_choice_data.getUser_choice_image());
        holder.user_choice_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_choice_data.getUser_choice_text().equals("Information")){
                    Intent intent = new Intent(v.getContext(), InfoViewActivity.class);
                    mContext.startActivity(intent);
                }
                else if (user_choice_data.getUser_choice_text().equals("Wallet")){
                    Intent intent = new Intent(v.getContext(), WalletActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_choice_image;
        TextView user_choice_text;

        public ViewHolder(View view) {
            super(view);
            user_choice_image = (ImageView) view.findViewById(R.id.user_choice_image);
            user_choice_text = (TextView) view.findViewById(R.id.user_choice_text);
        }
    }

}
