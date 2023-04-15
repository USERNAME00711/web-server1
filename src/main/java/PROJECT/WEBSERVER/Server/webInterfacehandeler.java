package PROJECT.WEBSERVER.Server;

import PROJECT.WEBSERVER.HTTP_dataType.*;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;

public class webInterfacehandeler extends HttpConnectionWorkerThread{

    public webInterfacehandeler(Socket socket, Host host) {
        super(socket, host);
    }

    public void generateResponse() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("php","-f",HostList.hosts.get(8080).getConf().getDefaulte(),HostList.toQuery());
        Process process = process = processBuilder.start();
        byte[] outputBytes=process.getInputStream().readAllBytes();
        String content = new String(outputBytes);
        HttpResponse httpResponse =new HttpResponse();
        HttpStatuseLigne httpStatuseLigne=new HttpStatuseLigne();
        httpStatuseLigne.setStatusCode("200");
        httpStatuseLigne.setStatusMessage("ok");
        httpStatuseLigne.setProtcoleVersion("HTTP/1.1");
        httpResponse.setHttpStatuseLigne(httpStatuseLigne);
        httpResponse.putHeder("Content-Length",Integer.toString(content.length()));
        httpResponse.putHeder("Content-Type","text/html");
        HttpContentBody httpContentBody=new HttpContentBody(content);
        httpResponse.setHttpContentBody(httpContentBody);
        WriterResponce(httpResponse);
    }

    public void run()
    {
        try {

        String request = this.readRequest();
        httpRequestParser my_parser = new httpRequestParser();
        HttpRequest my_request = my_parser.Parse(request);
        HttpRequestligne httpRequestligne=my_request.getRequestligne();
       if( httpRequestligne.getparams()!=null) {
           HashMap<String, String> map = httpRequestligne.parsQuery();
           if (map.containsKey("add")) {
               String Path=HostList.hosts.get(8080).getConf().getWebRote()+"/"+Integer.parseInt(map.get("port"))+"cnf";
               File file = new File(Path);
               file.createNewFile();
               HostConfigurattion hostConfigurattion=new HostConfigurattion(Integer.parseInt(map.get("port")),map.get("webroot"),map.get("domaine"),map.get("default"));
               Host host=new Host(hostConfigurattion);
               HostsConfigurationManger hostsConfigurationManger=new HostsConfigurationManger(Path);
               hostsConfigurationManger.saveHostConfiguration(host);
               host.StartLisning();
               HostsConfigurationLoader.add(Path);

           }
           if(map.containsKey("stop")){
            Host host= HostList.hosts.get(Integer.parseInt(map.get("port")));
            if(host.serverLitnerThread.isRunnig==true)
                {host.StopLisining();host.StopLisining();}
            else{host.StartLisning();}
           }


       }
        generateResponse();




        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

}
