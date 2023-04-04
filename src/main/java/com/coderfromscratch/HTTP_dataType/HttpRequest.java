package com.coderfromscratch.HTTP_dataType;

import java.util.HashMap;

public class HttpRequest {
    public HashMap<String , String> Mapheaders = new HashMap<>();
    private HttpRequestligne requestligne;
    private HttpContentBody body;
    public void setRequestligne(HttpRequestligne requestligne) {
        this.requestligne = requestligne;
    }

    //private HttpHeder headers[];



    public HttpHeder  getHttpHeder ( String HttpHederName) {
        HttpHeder heder =new HttpHeder(HttpHederName ,Mapheaders.get(HttpHederName) );
        return heder;
    }
    /*public List<HttpHeder> getHttpHeaders() {
        List<HttpHeder>hederList=new ArrayList<HttpHeder>();
        Iterator it
        for(Mapheaders.Entry<String,String> entry: Mapheaders.entrySet())[
                ]

        return hederList;
    }*/

    public HttpRequestligne getRequestligne() {
        return requestligne;

    }

    public HttpContentBody getBody() {
        return body;
    }

    public void AddHttpHeder(HttpHeder httpHeder) {
        Mapheaders.put(httpHeder.getHttpHederName(),httpHeder.getHttpHederContent());
    }

    public void setBody(HttpContentBody body) {
        this.body = body;
    }
}
