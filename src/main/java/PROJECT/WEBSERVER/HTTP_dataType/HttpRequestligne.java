package PROJECT.WEBSERVER.HTTP_dataType;

import java.net.URLDecoder;
import java.util.HashMap;

public class HttpRequestligne {
    String mehod;
    String Protocole;
    String Uri;



    public HttpRequestligne(String Mehod,String uri ,String protocole) {
        Uri = uri;
        mehod=Mehod;
        Protocole=protocole;

    }
    public HttpRequestligne(HttpRequestligne httpRequestligne) {
        this.Uri = httpRequestligne.Uri;
        this.mehod=httpRequestligne.mehod;
        this.Protocole=httpRequestligne.Protocole;

    }

    public void setUri(String uri) {
        Uri = uri;
    }



    public String getUri() {
        return Uri;
    }

    public String getMehod() {
        return mehod;
    }

    public void setMehod(String mehod) {
        this.mehod = mehod;
    }
    public String getresourse() {
       int i =this.Uri.indexOf("?");
       if(i>0){return Uri.substring(0,i);}
       else{return Uri;}
    }
    public String getparams() {
        int i =this.Uri.indexOf("?");
        if(i>0){return Uri.substring(i+1);}
        else{return null;}
    }
    public HashMap<String , String> parsQuery()
    {
        String str=this.getparams();
        HashMap<String, String> map = new HashMap<>();

        try {
            String[] pairs = str.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(map);
        return (map);
    }



}
