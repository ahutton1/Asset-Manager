import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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