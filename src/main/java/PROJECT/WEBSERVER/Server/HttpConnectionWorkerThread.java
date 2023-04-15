package PROJECT.WEBSERVER.Server;
import PROJECT.WEBSERVER.HTTP_dataType.HttpRequest;
import PROJECT.WEBSERVER.HTTP_dataType.HttpResponse;
import PROJECT.WEBSERVER.HTTP_dataType.httpRequestParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;
    private Host host;

    public HttpConnectionWorkerThread(Socket socket, Host host) {
        this.socket = socket;
        this.host = host;
    }
    public String readRequest() throws IOException {
        InputStream inputStream = null;
        inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String data = new String(buffer, 0, bytesRead);
        return data;
    }
    public void WriterResponce(HttpResponse httpResponse) throws IOException {
        if(httpResponse!=null) {
            OutputStream outputStream = null;
            outputStream = socket.getOutputStream();
            outputStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
            if (httpResponse.getBufferedInputStream() != null) {
                BufferedInputStream bufferedInputStream = httpResponse.getBufferedInputStream();
                byte[] buffer = new byte[1024];
                int byteread;
                while (((byteread = bufferedInputStream.read(buffer)) != -1)) {
                    outputStream.write(buffer, 0, byteread);
                }
            }

        }
    }

    public void run() {


        try {
            String request = this.readRequest();
            httpRequestParser my_parser = new httpRequestParser();
            HttpRequest my_request = my_parser.Parse(request);
            HandleGetRequest requestHandler = new  HandleGetRequest();
            HttpResponse httpResponse=requestHandler.HandelRequest( my_request,host);
            WriterResponce(httpResponse);
            socket.close();

           } catch ( Exception e ){
             throw new RuntimeException(e);
           }

    }

}









