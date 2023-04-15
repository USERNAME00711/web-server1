package PROJECT.WEBSERVER.Server;



import PROJECT.WEBSERVER.HTTP_dataType.HttpRequest;
import PROJECT.WEBSERVER.HTTP_dataType.HttpResponse;

import java.io.IOException;

public interface HandleRequest {
    public HttpResponse HandelRequest(HttpRequest httpRequest, Host host ) throws IOException, InterruptedException;

}
