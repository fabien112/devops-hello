package com.devops.hello.dto;

public class HelloResponse {

    private String message;
    private String version;

    public HelloResponse() {
    }

    public HelloResponse(String message, String version) {
        this.message = message;
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
