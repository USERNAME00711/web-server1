package PROJECT.WEBSERVER.Server;

import java.util.HashMap;

public class HostList {
   public static HashMap<Integer, Host> hosts = new HashMap<>();

    public HashMap<Integer, Host> getHosts() {
        return hosts;
    }

    public static String toQuery(){

        String query="";
        for(int key : hosts.keySet()) {
            Host host = HostList.hosts.get(key);
            String Started;
            if(host.serverLitnerThread==null||host.serverLitnerThread.isRunnig==false){
                Started="Stop" ;
            }
            else {
                Started="RUNING" ;
            }
            query=query+"defulte="+host.conf.defaulte+'&'+"port="+ Integer.toString(host.conf.port) +'&'+"domaine="+host.conf.domainName+'&'+"webRote="+host.conf.webRote+'&'+"status="+Started+" ";
        }
        query=query.substring(0,query.length()-1);
        return query;
    }

}
