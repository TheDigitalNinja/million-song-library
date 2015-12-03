package io.swagger.api.impl;

import javax.xml.bind.annotation.XmlTransient;

public class MslApiResponseMessage{
    public static final int OK = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int TOO_BUSY = 4;

    int code;
    String type;
    String message;
    Object data;

    public MslApiResponseMessage() {
    }

    public MslApiResponseMessage(int code, String message) {
        initCode(code);
        this.message = message;
    }

    public MslApiResponseMessage(int code, String message, Object obj) {
        initCode(code);
        this.message = message;
        this.data = obj;
    }

    private void initCode(int code) {
        this.code = code;
        switch (code) {
            case ERROR:
                setType("error");
                break;
            case WARNING:
                setType("warning");
                break;
            case INFO:
                setType("info");
                break;
            case OK:
                setType("ok");
                break;
            case TOO_BUSY:
                setType("too busy");
                break;
            default:
                setType("unknown");
                break;
        }
    }

    @XmlTransient
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

