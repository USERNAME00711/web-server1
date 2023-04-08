package com.coderfromscratch.HTTP_dataType;
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



}
