import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
    
}