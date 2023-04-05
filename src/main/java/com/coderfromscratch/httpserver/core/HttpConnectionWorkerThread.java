package com.coderfromscratch.httpserver.core;
import java.io.IOException;

import com.coderfromscratch.HTTP_dataType.HttpRequest;
import com.coderfromscratch.HTTP_dataType.HttpRequestligne;
import com.coderfromscratch.httpserver.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }
    private Configuration cnf ;

   // private HttpQueryParser my_parser = new HttpQueryParser() ;

    public HttpConnectionWorkerThread(Socket socket , Configuration cnf) {
        this.socket = socket;
        this.cnf = cnf ;
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
    }

}
