package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import DisplayObjects.sqlList;

public class serverThread extends AssetConnection{

    private Driver server;
    private String localHostString;
    private boolean goodToGo = false;
    
    public serverThread(Socket socket, Driver server) throws IOException{
        super(socket);
        this.server = server;
        login();
    }

    @Override
    public void begin(){
        try{
            while(true){
                //Recognize and pull incoming request from the server
                AssetRequest<?> request = readRequest();

                /** TODO: Develop returns on each case that sends the user or asset data back to the client GUI */
                //Determine what the request is asking for/wants/is telling the client
                switch(request.getType()){
                    case LOGIN:
                        break;
                    case LOGIN_SUCCEEDED:
                        //Sent by server. Should not be received here
                        goodToGo = true;
                        break;
                    case PUSH_UPDATE:
                        //Sent by server. Should not be received here
                        break;
                    case LOGOUT:
                        logoff();
                        break;
                    case ERROR:
                        //Sent by server. Should not be received here
                        //Return to the client if an error occurs while the server is performing tasks
                        goodToGo = false;
                        break;
                    case USER_ERROR:
                        break;
                    case NEW_ASSET:
                        server.createNewAsset(request);
                        break;
                    case NEW_USER:
                        server.createNewUser(request);
                        break;
                    case CALL_ASSET:
                            System.out.println("<CALL ASSET> REQUEST RECEIVED . . . INITILIZING REQUEST RESPONSE");
                        sendRequest(server.callAsset(request));
                        break;
                    case CALL_USER:
                            System.out.println("<CALL USER> REQUEST RECEIVED . . . INITIALIZING REQUEST RESPONSE");
                        sendRequest(server.callUser(request));
                        break;
                    case CALL_ASSET_LIST:
                        System.out.println(((sqlList)request.getData()).getFirstTerm());
                        AssetRequest<sqlList> homebound = server.callAssetList(request);
                        sendRequest(homebound);
                        break;
                    case CALL_USER_LIST:
                            System.out.println("Developing user list dossier with callUserList");
                        AssetRequest<sqlList> dossier = server.callUserList(request);
                        //Add error check in here
                            System.out.println("Sending the developing dossier back to the client");
                        sendRequest(dossier);
                        break;
                    case CALL_LOCAL_USER_LIST:
                            System.out.println("CALL_LOCAL_USER_LIST called");
                        AssetRequest<sqlList> localListings = server.callLocalList(request);
                        //Add error check in here
                            System.out.println("Sending the local listings back . . .");
                        sendRequest(localListings);
                            System.out.println("Sent");
                        break;
                    default:
                        throw new Exception("Error reading request from client");
                }
            }
        }catch(Exception e){
            System.out.println(e);
            server.disconnectServerThread(localHostString);
            return;
        }
    }

    /**
     * Method used to log a user into the server so that they may access the database. Checks to see if the user has already been added
     * to the list of active users due to having the application open on their machine already. If the application is already open on 
     * their machine already, this thread closes and alerts the user that they should use the application that is already open.
     */
    private void login(){
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            //Stores the ipv4 address of the connecting  client as the username in the server listings
            String localhostString = "" + localhost.getHostAddress();
            //Sets a boolean to determine if the ip address had already been registered as active on the server
            boolean alreadyExisting = false;
            for(int i = 0; i < server.clients.size(); i++){
                if(server.clients.get(i).equals(localhostString)){
                    alreadyExisting = true;
                    break;
                }
            }

            /**
             * Checks to see if the ip address of the user who is trying to connect has already been registered and is active on the
             * server. This prevents having one person log on with many applications on one machine, and helps to limit the network
             * bandwith and keep the server running smoothly.
             */
            if(alreadyExisting){
                System.out.println("IP Address already registered to server. Please use the application that is already open on this machine");
                close();
            }else{
                server.clients.add(localhostString);
                this.localHostString = localhostString;
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void logoff(){
        server.clients.remove(localHostString);
        close();
    }
}