package io.swagger.api;

import javax.xml.bind.annotation.XmlTransient;

@javax.xml.bind.annotation.XmlRootElement
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class ApiResponseMessage {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int OK = 4;
    public static final int TOO_BUSY = 5;

    int code;
    String type;
    String message;
    Object data;

    public ApiResponseMessage() {
    }

    public ApiResponseMessage(int code, String message) {
        initCode(code);
        this.message = message;
    }

    public ApiResponseMessage(int code, String message, Object song) {
        initCode(code);
        this.message = message;
        this.data = song;
    }

    private void initCode (int code) {
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
