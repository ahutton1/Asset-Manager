package Server;
import java.io.Serializable;

public class AssetRequest<E extends Serializable> implements Serializable{

    /**
     * Handles the types of requests that can be sent back and forth between the clients and the server
     */
    public enum RequestType{
        /**
         * CLIENT
         * Used by the client application to request a connection to the server. The server will take the request
         * and store it in a special ArrayList as an IP address. This allows the server to treat the IP addresses 
         * as usernames, and keep track of any clients that are actively connected to the server.
         */
        LOGIN, 
        
        /**
         * SERVER
         * Used by the server to approve and provide access to a inbound client 'LOGIN' request. After the request
         * is run and checked, the server sends this as a knock-knock protocol to affirmate that the client has
         * successfully connected
         */
        LOGIN_SUCCEEDED, 
        
        /**
         * SERVER
         * Used by the server to notify all connected clients that a change was made to an active form and that they
         * need to receive updated versions of the forms.
         */
        PUSH_UPDATE, 
        
        /**
         * CLIENT
         * Used by the client to notify the server that they are disconnecting and that the thread needs to be closed.
         * The server must receive this message and then close and remove the thread from the list of IP addresses
         * being stored in order for the client to be able to connect again at a later time with that same IP.
         */
        LOGOUT, 
        
        /**
         * SERVER
         * Used by the server to notify all active users that a fatal error occurred and that the server is going
         * to be shut down. All connected users will be removed from the server and their applications will clsoe
         * upon receiving this message.
         */
        ERROR, 

        /**
         * CLIENT
         * Used by the client as a means of throwing an internal application error without causing a complete 
         * shutdown of the server in the mean time. Mainly used when the system fails to read a request that has been 
         * sent correctly, and the user needs to be notified that something internally had gone wrong.
         */
        USER_ERROR,
        
        /**
         * CLIENT
         * Used by the client to call the server and tell it that a new asset is being created. This will tell the 
         * server all of the information it needs to know in order to create a new asset in the SQL server asset
         * database.
         */
        NEW_ASSET, 
        
        /**
         * CLIENT
         * Used by the client to call the server and tell it that a new user is being created. This will tell the 
         * server all of the information it needs to know in order to create a new user in the SQL server user
         * database.
         */
        NEW_USER, 

        /**
         * CLIENT
         * Used by the client to call the server and request to view the in-depth information regarding a given asset.
         * Sends the server the given asset search term and info necessary for the system to develop a SQL statement.
         */
        CALL_ASSET,

        /**
         * CLIENT
         * Used by the client to call the server and request to view the indepth information regarding a given user.
         * Sends the server the given user search term and info necessary for the system to develop a SQL statment.
         */
        CALL_USER,

        /**
         * CLIENT
         * Used by the client to call the server and request a given list of assets. The request holds the user, the 
         * asset type that they have selected within the list, and then any other information in order for the server
         * to successfully create the necessary SQL statement.
         */
        CALL_ASSET_LIST,

        /**
         * CLIENT
         * Used by the client to call the server and request a given list of users. The request holds the user, and
         * the general request to get the basic information on all users. The server can then create a SQL statement
         * based on this information and report back with the necessary information. 
         */
        CALL_USER_LIST,

        /**
         * CLIENT
         * Used by the client to fill out the user list that is used to allow an association between an asset and a
         * user. Send the request to the server and then call a user list based on the basic search terms, pulling all
         * of the users from the server database.
         */
        CALL_LOCAL_USER_LIST,

        /**
         * CLIENT
         * Used to notify the server that the specific client thread is going to disconnect from the server. Allows for
         * a safe connection close and the server to close the thread and continue to run.
         */
        CLOSE_CONNECTION,

        /**
         * CLIENT
         * Used to notify the server that a connected client has made changes to their respective active asset and that those
         * changes need to be pushed to the server, database, and other connected clients.
         */
        SAVE_ASSET,

        /**
         * CLIENT
         * Used to notify the server that a connected client has made changes to their respective active user and that those
         * changes need to be pushed to the server, database, and other connected clients.
         */
        SAVE_USER
    }

    private RequestType type;
    private E data;

    public AssetRequest(RequestType type, E data){
        this.type = type;
        this.data = data;
    }

    public RequestType getType(){return type;}

    public E getData(){return data;}

    public String toString(){
        return "Server Request{ type = " + type + ", data = " + data + "}";
    }
    
}