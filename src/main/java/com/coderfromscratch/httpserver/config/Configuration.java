package com.coderfromscratch.httpserver.config;

public  class Configuration {

    private int port;
    private static String  webroot;
    private static String  domain;

    public int getPort() {
        return port;
    }

    public static String getDomain() {
        return domain;
    }

    public  void setPort(int port) {
        this.port = port;
    }

    public  static String getWebroot() {
        return webroot;
    }


    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
    public void setDomain(String domain) {
        this.domain= domain;
    }
}
