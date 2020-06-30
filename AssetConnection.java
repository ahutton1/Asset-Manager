import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public abstract class AssetConnection extends Thread {

    protected Socket socket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    private boolean open;
    protected final int SLEPP_MS = 100;

    public AssetConnection(Socket socket) throws IOException{
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        open = true;
    }

    public void close(){
        try{
            open = false;
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
    
}