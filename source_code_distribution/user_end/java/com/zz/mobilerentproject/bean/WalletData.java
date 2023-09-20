package com.zz.mobilerentproject.bean;

public class WalletData {

    private String          balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletData{" +
                "balance='" + balance +
                '}';
    }
}
