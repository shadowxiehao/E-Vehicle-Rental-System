package com.zz.mobilerentproject.bean;

public class OrderListData {

    private String startTime;
    private String money;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "OrderListData{" +
                "startTime='" + startTime +
                ", money='" + money +
                '}';
    }
}
