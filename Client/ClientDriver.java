package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import Server.*;

public class ClientDriver extends AssetConnection {

    private boolean goodToGo = false;

    //TODO Potentially hard-code the port in as it will not change once it is determined
    public ClientDriver(int port) throws Exception{
        super(new Socket("" + (InetAddress.getLocalHost()).getHostAddress(),port));
    }

    @Override
    public void begin(){
        try{
            while(true){
                //Recognize and pull incoming request from the server
                AssetRequest<?> request = readRequest();

                //Determine what the request is asking for/wants/is telling the client
                switch(request.getType()){
                    case LOGIN_SUCCEEDED:
                        goodToGo = true;
                        break;
                    case PUSH_UPDATE:
                        break;
                    case ERROR:
                        break;
                    default:
                        throw new Exception("Error reading request from server");
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}