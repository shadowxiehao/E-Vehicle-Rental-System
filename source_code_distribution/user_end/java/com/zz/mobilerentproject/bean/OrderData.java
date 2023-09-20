package com.zz.mobilerentproject.bean;

public class OrderData {

    private int user_order_image;
    private String user_order_address;
    private String user_order_time;
    private String user_order_price;

    public OrderData(int user_order_image, String user_order_address, String user_order_time, String user_order_price) {
        this.user_order_image = user_order_image;
        this.user_order_address = user_order_address;
        this.user_order_time = user_order_time;
        this.user_order_price = user_order_price;
    }

    public int getUser_order_image() {
        return user_order_image;
    }

    public void setUser_order_image(int user_order_image) {
        this.user_order_image = user_order_image;
    }

    public String getUser_order_address() {
        return user_order_address;
    }

    public void setUser_order_address(String user_order_address) {
        this.user_order_address = user_order_address;
    }

    public String getUser_order_time() {
        return user_order_time;
    }

    public void setUser_order_time(String user_order_time) {
        this.user_order_time = user_order_time;
    }

    public String getUser_order_price() {
        return user_order_price;
    }

    public void setUser_order_price(String user_order_price) {
        this.user_order_price = user_order_price;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "user_order_address='" + user_order_address +
                ", user_order_time='" + user_order_time +
                ", user_order_price='" + user_order_price +
                '}';
    }
}
