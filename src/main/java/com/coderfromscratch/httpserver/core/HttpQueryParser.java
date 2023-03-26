package com.coderfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpQueryParser {

    public static HashMap<String, String> parseQueryParams(Socket socket) throws Exception {
        try{     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            String query = "";
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("GET")) {
                    int idx1 = inputLine.indexOf("?") + 1;
                    int idx2 = inputLine.indexOf(" HTTP/");
                    if (idx1 > 0 && idx2 > 0 && idx1 < idx2) {
                        query = inputLine.substring(idx1, idx2);
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
            return queryParams;
        }catch (IOException e1) {

            throw e1;
        }
        }
    }