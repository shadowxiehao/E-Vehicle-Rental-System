package com.zz.mobilerentproject.bean;

import android.widget.ImageView;

public class UserChoiceData {

    private int user_choice_image;
    private String user_choice_text;

    public UserChoiceData(int user_choice_image, String user_choice_text) {
        this.user_choice_image = user_choice_image;
        this.user_choice_text = user_choice_text;
    }



    public int getUser_choice_image() {
        return user_choice_image;
    }

    public void setUser_choice_image(int user_choice_image) {
        this.user_choice_image = user_choice_image;
    }

    public String getUser_choice_text() {
        return user_choice_text;
    }

    public void setUser_choice_text(String user_choice_text) {
        this.user_choice_text = user_choice_text;
    }

    @Override
    public String toString() {
        return "UserChoiceData{" +
                "user_choice_image=" + user_choice_image +
                ", user_choice_text='" + user_choice_text +
                '}';
    }


}
