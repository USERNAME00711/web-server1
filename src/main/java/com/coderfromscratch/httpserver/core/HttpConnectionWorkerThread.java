package com.coderfromscratch.httpserver.core;
import java.io.*;

import com.coderfromscratch.HTTP_dataType.*;
//import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.Configuration;
//import com.coderfromscratch.httpserver.config.Host;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    private Configuration cnf;

    public Socket getSocket() {
        return socket;
    }


    public HttpConnectionWorkerThread(Socket socket, Configuration cnf) {
        this.socket = socket;
        this.cnf = cnf;
    }


    public String readRequest() throws IOException {
        InputStream inputStream = null;
        inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String data = new String(buffer, 0, bytesRead);
        return data;
    }


    public void WriterResponce(String response) throws IOException {
        OutputStream outputStream = null;
        outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void WriterResponce(String response,  BufferedInputStream bufferedInputStream) throws IOException {
        OutputStream outputStream = null;
        outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[1024];
        int byteread;
        while (((byteread = bufferedInputStream.read(buffer)) != -1)) {
            outputStream.write(buffer, 0, byteread);

        }

    }
    public void WriterResponce(HttpResponse httpResponse) throws IOException {
        if(httpResponse!=null) {
            System.out.println("writing response");
            OutputStream outputStream = null;
            outputStream = socket.getOutputStream();
            outputStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
            // FileInputStream Input = new FileInputStream(path);
            // BufferedInputStream bufferedInputStream = new BufferedInputStream(Input);
            if (httpResponse.getBufferedInputStream() != null) {

                BufferedInputStream bufferedInputStream = httpResponse.getBufferedInputStream();
                byte[] buffer = new byte[1024];
                int byteread;
                while (((byteread = bufferedInputStream.read(buffer)) != -1)) {
                    outputStream.write(buffer, 0, byteread);

                }
            } else {
                System.out.println("buffernull");
            }

        }
    }



    public void run() {
        String request;

        try {
            request = this.readRequest();
            System.out.println(request);
           } catch (IOException e) {
            throw new RuntimeException(e);
           }
            httpRequestParser my_parser = new httpRequestParser();
            HttpRequest my_request;
            try {
            my_request = my_parser.Parse(request);
            } catch (Exception e) {
            throw new RuntimeException(e);
            }

            HandleGetRequest requestHandler = new  HandleGetRequest();
            HttpResponse httpResponse=new HttpResponse();
             try {
                 httpResponse=requestHandler.HandelRequest( my_request,cnf);

                } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
             }
        try {
            WriterResponce(httpResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }

}









