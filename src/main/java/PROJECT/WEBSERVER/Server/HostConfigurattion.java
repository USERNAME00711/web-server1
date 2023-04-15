package PROJECT.WEBSERVER.Server;

public class HostConfigurattion {
    
    int port;
    String webRote;
    String domainName;
    String defaulte;
    public HostConfigurattion(){}
    public HostConfigurattion(int port, String webRote, String domainName, String defaulte)
    {this.defaulte=defaulte;this.port=port;this.domainName=domainName;this.webRote=webRote;}

    public String getDefaulte() {
        return defaulte;
    }

    public void setDefaulte(String defaulte) {
        this.defaulte = defaulte;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getWebRote() {
        return webRote;
    }

    public void setWebRote(String webRote) {
        this.webRote = webRote;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
