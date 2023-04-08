package com.coderfromscratch.httpserver.config;

public  class Configuration {

    private int port;
    private  String  webroot;
    private  String  domain;
    private String defaulte;

    public int getPort() {
        return port;
    }

    public  String getDomain() {
        return domain;
    }

    public  void setPort(int port) {
        this.port = port;
    }

    public   String getWebroot() {
        return webroot;
    }


    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
    public void setDomain(String domain) {
        this.domain= domain;
    }

    public String getDefaulte() {
        return defaulte;
    }

    public void setDefaulte(String defaulte) {
        this.defaulte = defaulte;
    }
}
