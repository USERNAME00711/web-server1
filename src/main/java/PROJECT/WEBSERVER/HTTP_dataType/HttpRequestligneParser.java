package PROJECT.WEBSERVER.HTTP_dataType;

public class HttpRequestligneParser {

    public HttpRequestligne parse(String httpligne, boolean parsingError) throws Exception {

        HttpRequestligne httpRequestligne = null ;
        String[] httpToken=httpligne.split(" ");
        httpRequestligne=new HttpRequestligne(httpToken[0],httpToken[1],httpToken[2]);
        return (httpRequestligne);
    }
}
