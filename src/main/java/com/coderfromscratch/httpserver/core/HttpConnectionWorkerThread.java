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

    private HttpQueryParser my_parser = new HttpQueryParser() ;

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
           // inputStream.read();
            outputStream = socket.getOutputStream();
           // HashMap<String, String> queryParams = my_parser.parseQueryParams(getSocket());
           // System.out.println(queryParams.toString());
           // LOGGER.info(queryParams.toString());
            //LOGGER.info("ypppppppppppppp");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

// Read the request line
            String requestLine = reader.readLine();
            String[] requestLineParts = requestLine.split("\\s+");
            String method = requestLineParts[0];
            String path = requestLineParts[1];
            String httpVersion = requestLineParts[2];

// Read the headers
            HashMap<String, String> headers = new HashMap<>();
            String headerLine;
            while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
                String[] headerParts = headerLine.split(":\\s+");
                headers.put(headerParts[0], headerParts[1]);
            }

// Read the body (if present)
            StringBuilder bodyBuilder = new StringBuilder();
            if (headers.containsKey("Content-Length")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));
                char[] buffer = new char[contentLength];
                int bytesRead = reader.read(buffer, 0, contentLength);
                bodyBuilder.append(buffer, 0, bytesRead);
            }

            String body = bodyBuilder.toString();
            File file = new File("output.txt"); // the file you want to write to

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(body);
            } catch (IOException e) {
                // handle exception
            }
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
