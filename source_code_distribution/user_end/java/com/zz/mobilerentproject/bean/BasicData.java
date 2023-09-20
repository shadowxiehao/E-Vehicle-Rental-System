package com.zz.mobilerentproject.bean;

public class BasicData<T> {

    private String code;
    private String msg;
    private T data;

    @Override
    public String toString() {
        return "BasicData{" +
                "code='" + code +
                ", msg='" + msg +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
