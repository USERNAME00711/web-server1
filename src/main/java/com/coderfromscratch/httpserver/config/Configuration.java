package com.coderfromscratch.httpserver.config;

 public  class Configuration {

    private int port;
    private static String  webroot;

    public int getPort() {
        return port;
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
}
