package org.buaa.scholar.utils;

import java.util.Date;

public class Response<T> {
    private int code;
    private String message;
    private T data;
    private Date time;

    public Response(T data) {
        this.code = 1001;
        this.message = "请求成功！";
        this.data = data;
        this.time = new Date();
    }

    public Response(int code, T data) {
        this.code = code;
        this.message = "";
        this.data = data;
        this.time = new Date();
    }

    public Response(String message, T data) {
        this.code = 1001;
        this.message = message;
        this.data = data;
        this.time = new Date();
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = new Date();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", time=" + time +
                '}';
    }
}
