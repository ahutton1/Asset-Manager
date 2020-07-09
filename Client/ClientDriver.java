package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import GUI.GUIController;
import Server.*;

public class ClientDriver extends AssetConnection {

    private boolean goodToGo = false;
    private GUIController guic;

    public static void main(String args[]) throws Exception{
        ClientDriver reflect = new ClientDriver(54312);
        reflect.run();
    }

    public void run(){
        guic = new GUIController(this);
        guic.initialize();
    }

    //TODO Potentially hard-code the port in as it will not change once it is determined
    public ClientDriver(int port) throws Exception{
        super(new Socket("" + (InetAddress.getLocalHost()).getHostAddress(),54312));
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
                    case NEW_ASSET:
                        
                        break;
                    case NEW_USER:
                        
                        break;
                    case CALL_ASSET:
                        
                        break;
                    case CALL_USER:
                        
                        break;
                    case CALL_ASSET_LIST:
                        //TODO
                        break;
                    case CALL_USER_LIST:
                        
                        break;
                    default:
                        throw new Exception("Error reading request from server");
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    synchronized public void viewAssetList(){

    }


    synchronized public void viewUserList(){

    }


    synchronized public void save(){

    }


    synchronized public void searchForInput(){

    }


    synchronized public void viewUser(){

    }


    synchronized public void viewAsset(){

    }
}