package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.HTTP_dataType.HttpContentBody;
import com.coderfromscratch.HTTP_dataType.HttpRequest;
import com.coderfromscratch.HTTP_dataType.HttpResponse;
import com.coderfromscratch.HTTP_dataType.HttpStatuseLigne;
import com.coderfromscratch.httpserver.config.Configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HandlePostRequest implements HandleRequest{

    public boolean isAccessible(File file)
    {
        return (file.exists()&&file.canRead()&&file.isFile());
    }
    public boolean isScrypte(String resourse) {
        boolean isscrypte = false;
        int dotIndex = resourse.indexOf('.');
        if (dotIndex != -1 && dotIndex != 0 && dotIndex != resourse.length() - 1) {
            String exetantion = resourse.substring(dotIndex);
            System.out.println(exetantion);
            if (exetantion.equals(".php")) {
                isscrypte = true;
            }

        }
        return isscrypte;
    }
    @Override
    public HttpResponse HandelRequest(HttpRequest httpRequest, Configuration cnf) throws IOException, InterruptedException {
        HttpResponse httpResponse =new HttpResponse();
        HttpStatuseLigne httpStatuseLigne = new HttpStatuseLigne();
        String webroot=cnf.getWebroot();
        String defaulte=cnf.getDefaulte();
        //String query=httpRequest.getRequestligne().getparams();
       // System.out.println("query= "+query);


        String resours=httpRequest.getRequestligne().getresourse();
        if(resours.equals("/"))
        {resours=defaulte;}
        else {resours=webroot+resours;}
        System.out.println(resours);
        Path path= Path.of(resours);
        File file=new File(resours);
        if(isAccessible(file))
        {
            if(!isScrypte(resours)){
                System.out.println("notscript");
                String contentlength=Long.toString(file.length());
                String contentType= Files.probeContentType(path).toString();
                if(contentType==null){}
                httpStatuseLigne.setProtcoleVersion("HTTP/1.1");
                httpStatuseLigne.setStatusCode("200");
                httpStatuseLigne.setStatusMessage("ok");
                httpResponse.setHttpStatuseLigne( httpStatuseLigne);
                httpResponse.putHeder("Content-Type",contentType);
                httpResponse.putHeder("Content-Length",contentlength);
                FileInputStream Input = new FileInputStream(resours);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(Input);
                httpResponse.setBufferedInputStream(bufferedInputStream);

            }

            else {
                System.out.println("YES IS SCRIPT");
                ProcessBuilder processBuilder = new ProcessBuilder("php","-f",resours,"-d","cgi.force_redirect=0");
                processBuilder.environment().put("REQUEST_METHOD", "GET");
                if(httpRequest.getHttpHeder("Content-Type").getHttpHederContent().equals("application/x-www-form-urlencoded")) {
                    HttpContentBody body = httpRequest.getBody();
                    if (body != null) {
                        processBuilder.environment().put("QUERY_STRING", body.getBody());
                        processBuilder.environment().put("REDIRECT_STATUS", "200");
                        processBuilder.environment().put("GATEWAY_INTERFACE", "CGI/1.1");
                        //processBuilder.environment().put("SCRIPT_NAME", "script.php");
                        processBuilder.environment().put("PATH_INFO", "");
                        processBuilder.environment().put("SERVER_PORT", Integer.toString(cnf.getPort()));
                        processBuilder.environment().put("SERVER_NAME", "127.0.0.1");
                        processBuilder.environment().put("SERVER_PROTOCOL", "HTTP/1.1");
                        processBuilder.environment().put("CONTENT_LENGTH", "0");
                    }
                }
                else {

                }

                String content;
                int size;
                Process process = processBuilder.start();
                if (process.waitFor() == -1){
                    httpStatuseLigne.setStatusCode("500");
                    content= "<html><h1> 500 page not fond </h1></html>";
                    size = content.length();

                }
                else {
                    httpStatuseLigne.setStatusCode("200");
                    byte[] outputBytes = process.getInputStream().readAllBytes();
                    size=outputBytes.length;
                    content = new String(outputBytes);






                }

                HttpContentBody httpContentBody=new HttpContentBody(content);
                httpResponse.setHttpContentBody(httpContentBody);
                httpResponse.putHeder("Content-Length",Integer.toString(size));



                httpStatuseLigne.setProtcoleVersion("HTTP/1.1");
                httpStatuseLigne.setStatusCode("200");
                httpStatuseLigne.setStatusMessage("ok");
                httpResponse.setHttpStatuseLigne( httpStatuseLigne);
                httpResponse.putHeder("Content-Type","text/html");



            }

        }
        else
        {
            httpStatuseLigne.setProtcoleVersion("HTTP/1.1");
            httpStatuseLigne.setStatusCode("404");
            httpStatuseLigne.setStatusMessage("Not Found");
            httpResponse.setHttpStatuseLigne(httpStatuseLigne);
            httpResponse.putHeder("Content-Type","text/html");
            String content= "<html><h1> 404 page not fond </h1></html>";
            HttpContentBody httpcontent=new HttpContentBody(content);
            httpResponse.setHttpContentBody(httpcontent);

        }
        return httpResponse;
    }
}


