package com.coderfromscratch.http;

public class HttpHeder {
    private  String HttpHederName;
    private  String HttpHederContent;

    public HttpHeder(String httpHederName  , String httpHederContent) {
        HttpHederContent = httpHederContent;
        HttpHederName=httpHederName;
    }
    public HttpHeder(HttpHeder httpHeder) {
        this.HttpHederContent = httpHeder.HttpHederContent;
        this.HttpHederName=httpHeder.HttpHederName;
    }

    public String getHttpHederContent() {
        return HttpHederContent;
    }



    public String getHttpHederName() {
        return HttpHederName;
    }

    public void setHttpHederName(String httpHederName) {
        HttpHederName = httpHederName;
    }

    public void setHttpHederContent(String httpHederContent) {
        HttpHederContent = httpHederContent;
    }

}
