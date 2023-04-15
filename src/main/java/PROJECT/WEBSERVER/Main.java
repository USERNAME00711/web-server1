package PROJECT.WEBSERVER;

import PROJECT.WEBSERVER.Server.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HostsConfigurationLoader.load();
        httpServer.StartServer();

    }
}