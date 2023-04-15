package PROJECT.WEBSERVER.Server;

import java.io.*;

public class HostsConfigurationLoader {
    private static final String ConfigurationPath ="/home/abderrahmane/web-server1/src/main/resources/cnf";
    public static void load() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(ConfigurationPath));
        System.out.println("loading Hosts Configurations...");
        String line;
        while ((line = reader.readLine())!=null ) {
            if(!(line.equals(""))) {
                HostsConfigurationManger hostsConfigurationManger=new HostsConfigurationManger(line);
                hostsConfigurationManger.loadHostConfiguration();
            }

        }
        System.out.println("Hosts Configurations loaded...");

    }
    public static void add (String path) throws IOException {
        FileWriter writer = new FileWriter(ConfigurationPath,true);
        writer.write("\n"+path);
        writer.close();


    }
}
