package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.HTTP_dataType.*;


public class httpRequestParser {

    private HttpHederParser httpHederParser=new HttpHederParser();
    private HttpRequestligneParser httpRequestligneParser=new HttpRequestligneParser();
    public HttpRequest Parse (String request) throws Exception {
        HttpRequest httpRequest = new HttpRequest();
        boolean parsingError = false;
        String[] httpLines=request.split("\r\n");
        if(httpLines!=null && httpLines.length>0)
        {
            httpRequest.setRequestligne( new HttpRequestligne(httpRequestligneParser.parse(httpLines[0],parsingError)));
            int i = 1;
            for( i = 1 ; i<httpLines.length;i++)
            {
                if (httpLines[i]!="") {
                    httpRequest.AddHttpHeder( new HttpHeder(httpHederParser.parse(httpLines[i],parsingError)));}
                else {break;}
            }


            if(i<httpLines.length && (httpRequest.getRequestligne().getMehod()).equals("POST"))
            {  // System.out.println( httpRequest.getRequestligne().getMehod());
                String body="";
                for(int j = i+1 ;j<httpLines.length;j++) {body=body+httpLines[j];}
                // System.out.println(body);
                httpRequest.setBody(new HttpContentBody(body));
            }




        }





        return(httpRequest);
    }


}
