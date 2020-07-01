import java.io.IOException;
import java.lang.System.Logger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.*;
import java.util.ArrayList;
/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import java.util.logging.FileHandler;

class Driver{

    //Server variables
    private int port;
    protected ArrayList clients;

    //Debugging variable. Used to see if the server is actively accepting another server thread
    private boolean listeningDebugger;

    public static void main(String args[]){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.221.0.220;databaseName=urmhc_equipment;user=read;password=vnsread");

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery("SELECT AssetID FROM tblAssets");
            while(rs.next()){
                int assetID = rs.getInt("AssetID");
                System.out.println(assetID);
            }
            conn.close();

        }catch(Exception e){
            System.out.println("Connection error occured. Logging and shutting down");
            System.out.println(e);
            //Extra debugging code for a logging file for crashes
            /*try{
                FileHandler fh = new FileHandler("ErrorLog.log");
                Logger logger;
            }catch(Exception logError){
                System.out.println("Fatal Error Occured. Error Logging Failed. Shutting Down");
                System.out.println(logError);
            }*/
        }

        //Outside of testing suite: begin programming of actual server
        int portNumber = 0;
        Driver serverDriver = new Driver(portNumber);

    }

    protected Driver(int port){
        this.port = port;
        clients = new ArrayList<String>();
        runLoop();
    }

    //Constantly running loops that will allow the server to accept a client on via a thread and create another connection
    private void runLoop(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(listeningDebugger){
                new serverThread(serverSocket.accept(), this).start();
            }
        }catch(IOException ioe){
            System.out.println("Error listening on port: " + port);
            System.out.println("Exiting and printing report . . .");
            System.out.println(ioe);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Unknown fatal error occured. Exiting and printing report . . .");
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * Sends a SQL statement to the virtual server stating that a new User with specific sets
     * of data is being created. Utilizes the sqlStatementHandler class for assistance with
     * creating the statement.
     */
    public void createNewUser(){

    }

    /**
     * Sends a SQL statement to the virtual server stating that a new Asset with specific sets
     * of data is being created. Utilizes the sqlStatementHandler class for assistance with
     * creating the statement.
     */
    public void createNewAsset(){

    }

    /**
     * Sends a SQL statement to the virtual server stating that a client needs to view data on
     * a specific asset. The statement will retrieve the data on said asset and then provide it 
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the 
     * statement.
     */
    public void callAsset(){

    }

    /**
     * Sends a SQL statement to the virtual server stating that a client needs to view data on
     * a specific user. The statement will retrieve the data on said asset and then provide it 
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the 
     * statement.
     */
    public void callUser(){

    }

    /**
     * Sends a SQL statemnet to the virtual server stating that a client needs to view a list
     * of all assests. The statement will retrieve the data on all assets and then provide it
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the
     * statement.
     */
    public void callAssetList(){

    }

    /**
     * Sends a SQL statment to the virtual server stating that a client needs to view a list
     * of all users. The statement will retriece the data on all users and then provide it 
     * to the clent. Utilizes the sqlStatementHandler class for assistance with creating the
     * statement.
     */
    public void callUserList(){

    }
}