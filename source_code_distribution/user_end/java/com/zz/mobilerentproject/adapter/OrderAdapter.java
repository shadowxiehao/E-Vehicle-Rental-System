package com.zz.mobilerentproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.OrderData;
import com.zz.mobilerentproject.view.ordermodel.HistoryOrderActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context mContext;
    private List<OrderData> mList;

    public OrderAdapter(Context context, List<OrderData> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.user_order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderData user_order_data = mList.get(position);
        holder.order_address_text.setText(user_order_data.getUser_order_address());
        holder.order_type_image.setImageResource(user_order_data.getUser_order_image());
        holder.order_time_text.setText(user_order_data.getUser_order_time());
        holder.order_price_text.setText(user_order_data.getUser_order_price());
        holder.order_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistoryOrderActivity.class);
                //传递文字
                Bundle bundle = new Bundle();//定义Bundle对象存储要传递的值
                bundle.putString("address", user_order_data.getUser_order_address());
                bundle.putString("price", user_order_data.getUser_order_price());
                bundle.putString("time", user_order_data.getUser_order_time());
                //bundle.putInt("id",recommend.getImageId());
                intent.putExtras(bundle);//将bundle对象给intent

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView order_type_image;
        TextView order_address_text;
        TextView order_time_text;
        TextView order_price_text;
        CardView order_cardview;

        public ViewHolder(View view) {
            super(view);
            order_type_image = (ImageView) view.findViewById(R.id.order_type_image);
            order_address_text = (TextView) view.findViewById(R.id.order_address);
            order_time_text = (TextView) view.findViewById(R.id.order_time);
            order_price_text = (TextView) view.findViewById(R.id.order_price);
            order_cardview = (CardView) view.findViewById(R.id.order_cardview);

        }
    }

}
