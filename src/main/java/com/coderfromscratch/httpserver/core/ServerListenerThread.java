package com.coderfromscratch.httpserver.core;

//import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.Configuration;
//import com.coderfromscratch.httpserver.config.Host;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

  //  private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

   private Configuration cnf;
    private ServerSocket serverSocket;


    public ServerListenerThread(Configuration cnf) throws IOException {

        this.cnf = cnf;
        this.serverSocket = new ServerSocket(cnf.getPort());

    }





    @Override
    public void run() {

        try {


            while ( serverSocket.isBound() && !serverSocket.isClosed() ) {
                Socket socket = serverSocket.accept();


              //  LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket ,cnf);
                workerThread.start();

            }

        } catch (IOException e) {
            //LOGGER.error("Problem with setting socket", e);
        } finally {
            if (serverSocket!=null) {
                try {

                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

    }
}
