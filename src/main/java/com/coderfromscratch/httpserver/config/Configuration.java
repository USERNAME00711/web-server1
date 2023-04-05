package com.coderfromscratch.httpserver.config;

public  class Configuration {

    private int port;
    private  String  webroot;
    private  String  domain;

    public int getPort() {
        return port;
    }

    public String getWebroot() {
        return webroot;
    }

    public String getDomain() {
        return domain;
    }
}


