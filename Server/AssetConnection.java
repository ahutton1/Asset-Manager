package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import Server.AssetRequest.RequestType;

public abstract class AssetConnection extends Thread {

    protected Socket socket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    private boolean open;
    protected final int SLEEP_MS = 100;

    public AssetConnection(Socket socket) throws IOException{
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        open = true;
    }

    public void close(){
        try{
            open = false;
            AssetRequest<?> closeRequest = new AssetRequest(RequestType.CLOSE_CONNECTION, "close");
            sendRequest(closeRequest);
            socket.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public abstract void begin();

    public void run(){
        while(open){
            begin();
        }
    }

    /**
     * Method that allows the program to read a request that is being sent between two communicating parties within the system.
     * 
     * @return A formatted request that represents a piece of data being communicated between the given client and server
     * @throws Exception e
     */
    public AssetRequest<?> readRequest() throws Exception{
        try{
            Object request = in.readUnshared();
            if(request instanceof AssetRequest){
                return (AssetRequest<?>)request;
            }else{
                throw new ClassNotFoundException();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return new AssetRequest<Serializable>(AssetRequest.RequestType.USER_ERROR, "Error Reading Request.");
    }

    /**
     * Sends a request with given information 
     * @param request : A request for a change with a given type and data
     */
    public void sendRequest(AssetRequest<?> request){
        try{
            out.writeUnshared(request);
            out.flush();
            out.reset();
            this.sleep(SLEEP_MS);
        }catch(Exception e){
            System.out.println("Send Request Error");
            System.out.println(e);
        }
    }
    
}