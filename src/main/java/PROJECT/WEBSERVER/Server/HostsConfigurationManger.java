package PROJECT.WEBSERVER.Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class HostsConfigurationManger {
    String ConfigurationPath;



    public  HostsConfigurationManger(String  ConfigurationPath){
        this.ConfigurationPath=ConfigurationPath;

    }
    public void loadHostConfiguration() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HostConfigurattion hostConfigurattion;
        hostConfigurattion= objectMapper.readValue(new File(ConfigurationPath), HostConfigurattion.class);
        Host host=new Host(hostConfigurattion);
    }
    public void saveHostConfiguration(Host host) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(ConfigurationPath), host.conf);
    }

}
