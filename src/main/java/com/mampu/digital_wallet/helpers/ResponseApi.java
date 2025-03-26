package com.mampu.digital_wallet.helpers;

public class ResponseApi {
    private boolean success;
    private String message;
    private Object data;

    public ResponseApi(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
