package com.zz.mobilerentproject.bean;

public class RegisterData {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String              code;
    private String              firstName;
    private String              lastName;
    private String              email;
    private String              password;
    private String              role;
    private String              profile_picture;

    public RegisterData(String firstName, String lastName, String email, String password, String role, String profile_picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile_picture = profile_picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "firstName='" + firstName +
                ", lastName='" + lastName +
                ", email='" + email +
                ", password='" + password +
                ", role='" + role +
                ", profile_picture='" + profile_picture +
                '}';
    }
}
