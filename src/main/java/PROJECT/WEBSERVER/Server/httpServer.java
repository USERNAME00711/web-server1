package PROJECT.WEBSERVER.Server;

import java.io.IOException;

public class httpServer {



    public static void StartServer() throws IOException {
        for(int key : HostList.hosts.keySet()) {
            System.out.println(key);
            Host host = HostList.hosts.get(key);
            host.StartLisning();
        }
    }
    public static void  StopServer()
     {
         for (int key : HostList.hosts.keySet()) {
             Host host = HostList.hosts.get(key);
             host.StopLisining();

         }

     }

}
