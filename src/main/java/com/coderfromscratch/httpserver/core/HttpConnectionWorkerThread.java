package com.coderfromscratch.httpserver.core;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

   // private HttpQueryParser my_parser = new HttpQueryParser() ;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }



    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
           // HashMap<String, String> queryParams = my_parser.parseQueryParams(getSocket());

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String query = "";
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
               // System.out.println(inputLine);
                if (inputLine.startsWith("GET")) {
                    int idx1 = inputLine.indexOf("?") + 1;
                    int idx2 = inputLine.indexOf(" HTTP/");
                    if (idx1 > 0 && idx2 > 0 && idx1 < idx2) {
                        query = inputLine.substring(idx1, idx2);
                        System.out.println(query);
                    }
                }


                if (inputLine.equals("")) {
                    break;
                }

            }

            HashMap<String, String> queryParams = new HashMap<>();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    queryParams.put(key, value);
                }
            }
           /* queryParams.forEach((key, value) -> {
                System.out.println(key + " => " + value);
            });/*
           // inputStream.read();

          /* byte[] buffer = new byte[1024];

            int bytesRead = inputStream.read(buffer);
            String data = new String(buffer, 0, bytesRead);
            Httpparser.parseQueryParams(data);*/

            // do something with the data
            //System.out.println("Received data: " + data);
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";

            final String CRLF = "\r\n"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line  :   HTTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } catch (Exception e) {
            LOGGER.error("An exception occurred while parsing query parameters", e);
        } finally {
            // close streams and socket
        }
    }

}
