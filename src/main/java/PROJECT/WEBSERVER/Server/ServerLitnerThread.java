package PROJECT.WEBSERVER.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLitnerThread extends Thread{
     boolean isRunnig;
     Host host ;
     ServerSocket serverSocket ;

  public ServerLitnerThread(Host host,boolean isRunnig ) throws IOException {
      this.host=host ;
      this.isRunnig=isRunnig;
      serverSocket=new ServerSocket();

  }

  @Override
    public void run(){
      try {
          serverSocket.bind(new InetSocketAddress(this.host.conf.port));
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
      while(isRunnig == true){
        Socket socket;
      try {

          socket = serverSocket.accept();
          } catch (IOException e) {
          throw new RuntimeException(e);
          }
      if((host.getConf().getPort()!=8080)) {

            HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket,host);
            workerThread.start();
        }
        else {
             webInterfacehandeler webInterfacehandeler=new webInterfacehandeler(socket,host);
              webInterfacehandeler.start();
             }
    }
      try {
          serverSocket.close();
          } catch (IOException e) {
          throw new RuntimeException(e);
      }

  }
  public void Stop(){
      this.isRunnig=false;
  }
  public void setRunnig(boolean isRunnig){this.isRunnig=isRunnig;}
  public boolean setRunning(){return  isRunnig ; }



}
