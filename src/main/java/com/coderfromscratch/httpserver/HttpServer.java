package com.coderfromscratch.httpserver;

import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.ConfigurationManager;
import com.coderfromscratch.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {

        List<Configuration> conf;
        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("/home/abderrahmane/web-server1/src/main/resources/http.json");
        conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        Configuration configuration;
        for (int i = 0; i < conf.size(); i++) {
            configuration = conf.get(i);
            LOGGER.info("Using Port: " + configuration.getPort());
            LOGGER.info("Using WebRoot: " + configuration.getWebroot());
            LOGGER.info("Using Domain: " + configuration.getDomain());


            try {
                ServerListenerThread serverListenerThread = new ServerListenerThread(conf.get(i));
                serverListenerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO handle later.
            }
        }


    }
}


