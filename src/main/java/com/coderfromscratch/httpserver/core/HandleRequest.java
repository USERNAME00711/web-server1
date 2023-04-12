package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.HTTP_dataType.HttpRequest;
import com.coderfromscratch.HTTP_dataType.HttpResponse;
import com.coderfromscratch.httpserver.config.Configuration;

import java.io.IOException;

public interface HandleRequest {
    public HttpResponse HandelRequest(HttpRequest httpRequest, Configuration cnf ) throws IOException, InterruptedException;

}
