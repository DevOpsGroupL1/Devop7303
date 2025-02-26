package com.boltonuni.devop7303.models;

public class Response {
    private String responseMessage;
    private String responseCode;
    private Object data;

    public Response(String responseMessage, String responseCode, Object data) {
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
        this.data = data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseMessage='" + responseMessage + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", data=" + data +
                '}';
    }
}
