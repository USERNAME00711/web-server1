package com.coderfromscratch.httpserver.core;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;

import com.coderfromscratch.http.HttpRequest;
import com.coderfromscratch.http.HttpRequestligne;
import com.coderfromscratch.http.httpRequestParser;
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
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String data = new String(buffer, 0, bytesRead);
            httpRequestParser my_parser = new httpRequestParser();
            HttpRequest my_request = my_parser.Parse(data)  ;
            HttpRequestligne line =my_request.getRequestligne();
            System.out.println(my_request.Mapheaders );




            //String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my Simple Java HTTP Server</h1></body></html>";

           // final String CRLF = "\r\n"; // 13, 10

            /*String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line  :   HTTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());*/

            HTTPResponse httpResponse = new HTTPResponse() ;
            httpResponse.send(outputStream);

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
