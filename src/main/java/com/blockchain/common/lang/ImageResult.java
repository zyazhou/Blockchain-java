package com.blockchain.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageResult implements Serializable {
    private int errno;
    private String message;
    private Object data;

    public static ImageResult succ(Object data) {
        return succ(0, "操作成功", data);
    }
    public static ImageResult succ(int code, String msg, Object data) {
        ImageResult r = new ImageResult();
        r.setErrno(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }
    public static ImageResult fail(String msg) {
        return fail(1, msg, null);
    }
    public static ImageResult fail(String msg, Object data) {
        return fail(1, msg, data);
    }
    public static ImageResult fail(int code, String msg, Object data) {
        ImageResult r = new ImageResult();
        r.setErrno(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }
}