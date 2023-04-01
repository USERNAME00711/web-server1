package com.coderfromscratch.http;

public class HttpHederParser {
    public  HttpHeder parse(String httpligne,boolean parsingError )
    {
        HttpHeder httpHeder=null;
        String[] httptoken=httpligne.split(":");
        if(httptoken.length>=2)
        {
            String hhtpHederName=httptoken[0];
            String hhtpHederContenet=httptoken[1];
            httpHeder=new HttpHeder(hhtpHederName,hhtpHederContenet);
        }


        return httpHeder;

    }
}
