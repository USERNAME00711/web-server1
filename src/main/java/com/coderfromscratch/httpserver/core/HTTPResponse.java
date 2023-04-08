package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.httpserver.config.Configuration;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private String content;
    private String contentType;
    private String webroot;


    public HTTPResponse(String webroot) {
        this.statusCode = 200;
        this.statusMessage = "OK";
        this.headers = new HashMap<>();
        this.content = "\"<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";
        this.contentType = "text/html";
        this.webroot = webroot;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void send(OutputStream out) throws Exception {

        String statusLine = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n";
        out.write(statusLine.getBytes(StandardCharsets.UTF_8));

        headers.put("Content-Type", contentType);
        headers.put("Content-Length", Integer.toString(content.getBytes(StandardCharsets.UTF_8).length));
        for (Map.Entry<String, String> header : headers.entrySet()) {
            String headerLine = header.getKey() + ": " + header.getValue() + "\r\n";
            out.write(headerLine.getBytes(StandardCharsets.UTF_8));
        }
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
            ProcessBuilder pb = new ProcessBuilder("php", webroot+"\\script.php");
            Process p = pb.start();
            String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            out.write(output.getBytes());









        //System.out.println(output);




}}
