import java.io.Serializable;

public class AssetRequest<E extends Serializable> implements Serializable{

    public enum RequestType{
        /**
         * Used by the client application to request a connection to the server. The server will take the request
         * and store it in a special ArrayList as an IP address. This allows the server to treat the IP addresses 
         * as usernames, and keep track of any clients that are actively connected to the server.
         */
        LOGIN, 
        
        /**
         * Used by the server to approve and provide access to a inbound client 'LOGIN' request. After the request
         * is run and checked, the server sends this as a knock-knock protocol to affirmate that the client has
         * successfully connected
         */
        LOGIN_SUCCEEDED, 
        
        /**
         * Used by the server to notify all connected clients that a change was made to an active form and that they
         * need to receive updated versions of the forms.
         */
        PUSH_UPDATE, 
        
        /**
         * Used by the client to notify the server that they are disconnecting and that the thread needs to be closed.
         * The server must receive this message and then close and remove the thread from the list of IP addresses
         * being stored in order for the client to be able to connect again at a later time with that same IP.
         */
        LOGOUT, 
        
        /**
         * Used by the server to notify all active users that a fatal error occurred and that the server is going
         * to be shut down. All connected users will be removed from the server and their applications will clsoe
         * upon receiving this message.
         */
        ERROR, 
        
        /**
         * Used by the client to call the server and tell it that a new asset is being created. This will tell the 
         * server all of the information it needs to know in order to create a new asset in the SQL server asset
         * database.
         */
        NEW_ASSET, 
        
        /**
         * Used by the clietn to call the server and tel it that a new user is being created. This will tell the 
         * server all of the information it needs to know in order to create a new user in the SQL server user
         * database.
         */
        NEW_USER, 
    }

    public AssetRequest(){

    }
    
}