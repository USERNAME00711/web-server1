package PROJECT.WEBSERVER.Server;
import java.io.IOException;

public class Host {
    HostConfigurattion conf;
    ServerLitnerThread serverLitnerThread;
    public Host (HostConfigurattion conf) throws IOException {this.conf=conf;HostList.hosts.put(conf.getPort(),this);}
    public HostConfigurattion getConf() {
        return conf;
    }
    public void setConf(HostConfigurattion conf) {
        this.conf = conf;
    }
    public void StartLisning() throws IOException {serverLitnerThread=new ServerLitnerThread(this,true);serverLitnerThread.start();}
    public void StopLisining() {serverLitnerThread.interrupt();serverLitnerThread.Stop();}
}
