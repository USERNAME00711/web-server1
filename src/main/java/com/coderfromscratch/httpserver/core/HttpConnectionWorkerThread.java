package com.coderfromscratch.httpserver.core;
import java.io.*;

import com.coderfromscratch.HTTP_dataType.*;
import com.coderfromscratch.httpserver.config.Configuration;
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
       // FileInputStream Input = new FileInputStream(path);
       // BufferedInputStream bufferedInputStream = new BufferedInputStream(Input);
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


    /* @Override
     public void run() {
         String request;
         try {
             request = this.readRequest();
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
         System.out.println(request);
         HttpRequestligne httpRequestligne  = my_request.getRequestligne();
         HttpResponse httpResponse = new HttpResponse();
         HttpStatuseLigne httpStatuseLigne=new HttpStatuseLigne();
         httpStatuseLigne.setProtcoleVersion("HTTP/1.1");
         if(!httpRequestligne.getMehod().equals("Post") && !httpRequestligne.getMehod().equals("GET"))
         {

             System.out.println("methode not spotred");
             httpStatuseLigne.setStatusCode("405");
             httpStatuseLigne.setStatusMessage("Method Not Allowed");
             httpResponse.setHttpStatuseLigne(httpStatuseLigne);
         }
         else
         {
                 System.out.println("methode  spotred");
                 String root="/home/abderrahmane";
                 String defaulte="/home/abderrahmane/ParallelArch_Lecture_2_1.pdf";

                 // handl Get request
                 if(httpRequestligne.getMehod().equals("GET")) {
                 String resourse =httpRequestligne.getresourse();
                 String prams=httpRequestligne.getparams();
                 if(resourse.equals("/")){resourse=defaulte;}
                 else{resourse=resourse+root;}
                 Path pathe =Path.of(resourse);
                 File file=pathe.toFile();
                 if(!file.exists()||file.isDirectory()|| !file.canRead()) {
                     httpStatuseLigne.setStatusCode("404");
                     httpStatuseLigne.setStatusMessage("Not Found");
                     httpResponse.setHttpStatuseLigne(httpStatuseLigne);
                     }
                 else
                     {
                         System.out.println("resourse existe");
                         String contentType;
                         try {
                             contentType = Files.probeContentType(pathe);
                             System.out.println("Mime = " + contentType);
                             httpStatuseLigne.setStatusCode("200");
                             httpStatuseLigne.setStatusMessage("ok");
                             httpResponse.putHeder("Content-Type",contentType);
                             String content = new String(Files.readAllBytes(pathe), );
                             System.out.println(content);


                             httpResponse.setHttpStatuseLigne(httpStatuseLigne);
                             String content = new String(Files.readAllBytes(pathe), "UTF-8");
                             System.out.println(content);
                             httpResponse.putHeder("Content-Length", Integer.toString(content.length()));
                             HttpContentBody body = new HttpContentBody(content);
                             httpResponse.setHttpContentBody(body);
                             WriterResponce(httpResponse.toString());




                           //  System.out.println(httpResponse.getHttpContentBody().getBody());
                            // System.out.println(httpResponse.getHttpStatuseLigne().toString());
                            // System.out.println(httpResponse.toString());




                             } catch (IOException e) {
                             throw new RuntimeException(e);
                             }
                             if (contentType!=null){

                         }





                     int dotIndex=resourse.indexOf('.');
                     if(dotIndex==-1 || dotIndex==0 ||dotIndex== resourse.length()-1)
                     {

                     }
                     else
                     {
                        String exetantion=resourse.substring(dotIndex);
                        System.out.println(exetantion);
                        if(!exetantion.equals("php"))
                        {
                           
                        }

                     }


                 }


             }




         }


        // RequestHandler requestHandler = new RequestHandler(my_request);
        // HttpResponse httpResponse = requestHandler.handleRequest();



     }*/
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
          if( my_request.getRequestligne().getMehod().equals("GET") ){
            HandleGetRequest requestHandler = new  HandleGetRequest();
            HttpResponse httpResponse=new HttpResponse();
             try {
                 httpResponse=requestHandler.HandelRequest( my_request,cnf);

                } catch (IOException e) {
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



      /*  File file = new File("/home/abderrahmane/page.html");
        Path path = Path.of("/home/abderrahmane/page.html");
        String resp = "HTTP/1.1 " + "200 " + "ok" + "\r\n";
        int size = (int) file.length();
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resp = resp + "Content-Type: " + " " + contentType + "\r\n";
        resp = resp + "Content-Length: " + Integer.toString(size) + "\r\n" + "\r\n";
        try {


            WriterResponce(resp, "/home/abderrahmane/page.html");

      FileInputStream Input;
       try {
           Input = new FileInputStream("/home/abderrahmane/Capture d’écran de 2023-04-07 21-32-49.png");
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       }
       BufferedInputStream bufferedInputStream = new BufferedInputStream(Input);
       byte[] buffer=new byte[1024];
       int byteread;
       while(true){
           try {
               if (!((byteread=bufferedInputStream.read(buffer))!=-1)) break;
               } catch (IOException e) {
                throw new RuntimeException(e);
               }
           try {
               outputStream.write(buffer,0,byteread);
            } catch (IOException e) {
               throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }
          else{
              System.out.println("no yet sported methode");
          }
    }

}






  /*  @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String data = new String(buffer, 0, bytesRead);
            httpRequestParser my_parser = new httpRequestParser();
            HttpRequest my_request = my_parser.Parse(data)  ;
            HttpRequestligne line =my_request.getRequestligne();
            HTTPResponse httpResponse = new HTTPResponse(cnf.getWebroot()) ;
            httpResponse.send(outputStream);

            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } catch (Exception e) {
            LOGGER.error("An exception occurred while parsing query parameters", e);
        } finally {
            // close streams and socket
        }
    }*/


