package com.coderfromscratch.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ConxetionHndler {
    private int LISTEN_PORT = 8080;

    public ConxetionHndler(int listenport ) {
        this.LISTEN_PORT=listenport;

    }


    public void evevntloop() throws IOException
    {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",8080));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            int numberOfevent = selector.select();
            Set<SelectionKey> selectedionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter=selectedionKeys.iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if(key.isAcceptable()) {
                    System.out.println("damande");
                    ServerSocketChannel  serverChannel=(ServerSocketChannel) key.channel();
                    SocketChannel clientchannel = serverChannel.accept();

                    clientchannel.configureBlocking(false);

                    clientchannel.register(selector , SelectionKey.OP_READ);
                }
                if(key.isReadable()) {
                    SocketChannel clienChannel = ( SocketChannel) key.channel();
                    ByteBuffer buffer =  ByteBuffer.allocate(1024*8);
                    int bytesRead= clienChannel.read(buffer);

                    buffer.flip();
                    String request = new String(buffer.array(),StandardCharsets.UTF_8);
                    request = request.substring(0,bytesRead);
                    //System.out.println(request);
                    Thread requestHandler=new Thread(new RsponseHandler(request));
                    requestHandler.start();


                    String respenSe = "HTTP/1.1 200 ok\r\nContent-type: text/html; charset=utf-8\r\n"+

                            "\r\n"+
                            "<h1>hellow</h1> <h2>kouba</h2> <form method='POST' action='submit.php'>"+
                            "<lebel for='name'>NAME</lebal>"+
                            "<input type='text' id='name' name='name'><br></br>"+
                            "<lebel for='prename'>prname</lebal>"+

                            "<input type='text' id='prename' name='prename'><br></br>"+
                            "<button> send </button>"
                            + "</form>"
                            +"\r\n";

                    ByteBuffer outputBuffer =ByteBuffer.wrap(respenSe.getBytes(StandardCharsets.UTF_8));
                    clienChannel.write(outputBuffer);
                    clienChannel.close();
                }

            }








        }
    }
}

