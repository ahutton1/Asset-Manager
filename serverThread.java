import java.io.IOException;
import java.net.Socket;

public class serverThread extends AssetConnection{

    private Driver server;
    
    public serverThread(Socket socket, Driver server) throws IOException{
        super(socket);
        this.server = server;
    }
}