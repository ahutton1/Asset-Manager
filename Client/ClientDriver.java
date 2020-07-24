package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JList;

import DisplayObjects.sqlList;
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
        begin();
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
                System.out.println("Pull read request");
                AssetRequest<?> request = readRequest();
                System.out.println(request.getType());
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
                        System.out.println("Update asset list is called by the client driver");
                        guic.updateList((sqlList)request.getData());
                        break;
                    case CALL_USER_LIST:
                        System.out.println("Update user list is called by the client driver");
                        guic.updateList((sqlList)request.getData());
                        break;
                    case CALL_LOCAL_USER_LIST:
                        System.out.println("Local user list called and created for asset assignment");
                        guic.fillOutAssetUserList((sqlList)request.getData());
                        break;
                    default:
                        throw new Exception("Error reading request from server");
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public sqlList localListRead(){
        try{
            AssetRequest<?> request = readRequest();
            return (sqlList)request.getData();
        }catch(Exception e){
            System.out.println("Error in reading in the local list data");
            System.out.println(e);
            return null;
        }
        
    }

}