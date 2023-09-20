package com.zz.mobilerentproject.bean;

public class UserData {

    private String          id;
    private String          firstName;
    private String          email;
    private String          password;

    public String getUser_id() {
        return id;
    }

    public void setUser_id(String user_id) {
        this.id = user_id;
    }

    public String getUser_name() {
        return firstName;
    }

    public void setUser_name(String user_name) {
        this.firstName = user_name;
    }

    public String getUser_email() {
        return email;
    }

    public void setUser_email(String user_email) {
        this.email = user_email;
    }

    public String getUser_password() {
        return password;
    }

    public void setUser_password(String user_password) {
        this.password = user_password;
    }

    //形参要跟后台传来的相同
    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id +
                ", firstName='" + firstName +
                ", email='" + email +
                ", password='" + password +
                '}';
    }
}
