package PROJECT.WEBSERVER.HTTP_dataType;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private HttpStatuseLigne httpStatuseLigne;
    private HashMap<String , String> Mapheaders = new HashMap<>();
    private HttpContentBody httpContentBody=null;
    private BufferedInputStream bufferedInputStream;

    public BufferedInputStream getBufferedInputStream() {
        return bufferedInputStream;
    }

    public void setBufferedInputStream(BufferedInputStream bufferedInputStream) {
        this.bufferedInputStream = bufferedInputStream;
    }

    public HttpContentBody getHttpContentBody() {
        return httpContentBody;
    }

    public void setHttpContentBody(HttpContentBody httpContentBody) {
        this.httpContentBody = httpContentBody;
    }

    public HttpStatuseLigne getHttpStatuseLigne() {
        return httpStatuseLigne;
    }

    public void setHttpStatuseLigne(HttpStatuseLigne httpStatuseLigne) {
        this.httpStatuseLigne = httpStatuseLigne;
    }

    public void putHeder(HttpHeder httpHeder)
    {
        Mapheaders.put(httpHeder.getHttpHederName(),httpHeder.getHttpHederContent());
    }
    public void putHeder(String hederName , String headerValue)
    {
        Mapheaders.put(hederName,headerValue);
    }
    public String toString() {
        String Requset = null;
        if (this != null) {

            Requset = this.httpStatuseLigne.toString();
            for (Map.Entry<String, String> header : Mapheaders.entrySet()) {
                Requset = Requset + header.getKey() + ": " + header.getValue() + "\r\n";
            }
            Requset = Requset + "\r\n";
            if (httpContentBody != null) {
                Requset = Requset + "\r\n" + httpContentBody.getBody();


            }
        }
        return Requset;

    }

}
