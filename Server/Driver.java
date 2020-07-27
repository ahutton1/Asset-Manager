package Server;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.*;
import java.util.ArrayList;
/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import DisplayObjects.Asset;
import DisplayObjects.User;
import DisplayObjects.sqlList;
import Server.AssetRequest.RequestType;
import enums.assetTypes;
import enums.employmentStatus;
import enums.vendors;

class Driver{

    //Server variables
    private int port;
    protected ArrayList<String> clients;
    private static String servURL = "jdbc:sqlserver://10.221.0.220;databaseName=urmhc_equipment;user=read;password=vnsread";
    private static String userTblURL = "jdbc:sqlserver://10.221.0.220;databaseName=vnsIT;user=read;password=vnsread";

    //Debugging variable. Used to see if the server is actively accepting another server thread
    private boolean listeningDebugger;

    public static void main(String args[]){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery("SELECT Asset_Name, AssetID, Asset_TypeID FROM tblAssets");
            while(rs.next()){
                String Asset_Name = rs.getString("Asset_Name");
                int assetID = rs.getInt("AssetID");
                int asset_typeID = rs.getInt("Asset_TypeID");
                System.out.println(Asset_Name + "   " + assetID + "   " + asset_typeID);
            }
            conn.close();

        }catch(Exception e){
            System.out.println("Connection error occured. Logging and shutting down");
            System.out.println(e);
        }

        //Outside of testing suite: begin programming of actual server
        int portNumber = 54312;
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
            listeningDebugger = true;
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
    public void createNewUser(AssetRequest<?> incomingRequest){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   

            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqNewUser(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }
    }

    /**
     * Sends a SQL statement to the virtual server stating that a new Asset with specific sets
     * of data is being created. Utilizes the sqlStatementHandler class for assistance with
     * creating the statement.
     */
    public void createNewAsset(AssetRequest<?> incomingRequest){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   

            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqNewAsset(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }
    }

    /**
     * Sends a SQL statement to the virtual server stating that a client needs to view data on
     * a specific asset. The statement will retrieve the data on said asset and then provide it 
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the 
     * statement.
     */
    public AssetRequest<Asset> callAsset(AssetRequest<?> incomingRequest){
        Boolean assetIsLaptop = false;
        Boolean assetIsDamaged = false;
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   
            sqlStatementHandler ssh = new sqlStatementHandler();

            search toRetMaster = (search)incomingRequest.getData();
            String toRetName = toRetMaster.assetName;
            int toRetID = toRetMaster.assetID;
            int toRetTypeID = toRetMaster.typeID;
            assetTypes toRetType = ssh.assetTypeIDtoType(toRetTypeID);
            Asset toReturn = new Asset(toRetName, toRetID, toRetType);

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqAssetInfo(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                /**
                * List called in the following order . . .
                *      Asset Name               - NULL = ""
                *      Asset ID                 - NULL = "None"
                *      Asset Type               - NULL = "None"
                *      Inventory Status         - NULL = "None"
                *      User                     - NULL = "None"
                *      Vendor                   - NULL = "None"
                *      Model                    - NULL = ""
                *      Serial                   - NULL = ""
                *      Carrier                  - NULL = "None"
                *      Phone Number             - NULL = ""
                *      IMEI                     - NULL = ""
                *      SIM                      - NULL = ""
                *      Imaged Date              - NULL = ""
                *      Sent for repair          - CANNOT BE NULL AS IT IS A RADIO BUTTON / BOOLEAN
                *      Date sent for repair     - NULL = ""
                *      Damage Description       - NULL = ""
                */
                //Any returning statements go here
                String assetName = rs.getString("Asset_Name");
                    if(rs.wasNull()){assetName = "";}
                String assetNumber = rs.getString("Asset_Number");
                    if(rs.wasNull()){assetNumber = "";}
                int assetTypeAsInt = rs.getInt("Asset_TypeID");
                    if(rs.wasNull()){assetTypeAsInt = 0;}
                    if(assetTypeAsInt == 4){ assetIsLaptop = true; }else{ assetIsLaptop = false; }
                int inventoryStatusAsInt = rs.getInt("Inventory_StatusID");
                    if(rs.wasNull()){inventoryStatusAsInt = 0;}
                    if(inventoryStatusAsInt == 8){ assetIsDamaged = true; }else{ assetIsDamaged = false; }
                String userAsString = rs.getString("User");
                    if(rs.wasNull()){userAsString = "";}
                int vendorAsInt = rs.getInt("VendorID");
                    if(rs.wasNull()){vendorAsInt = 0;}
                String model = rs.getString("Model");
                    if(rs.wasNull()){model = "";}
                String serialNumber = rs.getString("Serial");
                    if(rs.wasNull()){serialNumber = "";}
                String imageDate = rs.getString("imageDate");
                    if(rs.wasNull()){imageDate = "";}

                //Laptops
                int carrierAsInt = 0;
                String phoneNumber = "";
                String panasonicIMEI = "";
                String simNumber = "";
                if(assetIsLaptop){
                    carrierAsInt = rs.getInt("Carrier");
                        if(rs.wasNull()){carrierAsInt = 0;}
                    phoneNumber = rs.getString("Phone_Number");
                        if(rs.wasNull()){phoneNumber = "";}
                    panasonicIMEI = rs.getString("IMEI ID");
                        if(rs.wasNull()){panasonicIMEI = "";}
                    simNumber = rs.getString("SIM Card");
                        if(rs.wasNull()){simNumber= "";}
                }
                
                //Damaged
                int sentForRepairAsInt = 0;
                String dateSentForRepair = "";
                String damageDescription = "";
                if(assetIsDamaged){
                    sentForRepairAsInt = rs.getInt("sentForRepair");
                        if(rs.wasNull()){sentForRepairAsInt = 0;}
                    dateSentForRepair = rs.getString("dateSentForRepair");
                        if(rs.wasNull()){dateSentForRepair = "";}
                    damageDescription = rs.getString("damageDescription");
                        if(rs.wasNull()){damageDescription = "";}
                }

                //Fill out the asset information to return
                toReturn.setInvStat(ssh.invIDtoStat(inventoryStatusAsInt));
                //Associate a user
                    String userFirst;
                    String userLast;
                    String userEmpStatString;
                    userLast = userAsString.substring(0,userAsString.indexOf(","));
                    userFirst = userAsString.substring(userAsString.indexOf(",")+2,userAsString.indexOf(":")-1);
                    userEmpStatString = userAsString.substring(userAsString.indexOf(":")+2,userAsString.length());
                    search userRet = new search(userFirst,userLast,userEmpStatString);
                    AssetRequest<User> user = callUser(new AssetRequest<search>(RequestType.CALL_USER,userRet));
                    toReturn.setUser(user.getData());
                toReturn.setAssetVendor(ssh.venIntToVen(vendorAsInt));
                toReturn.setAssetModel(model);
                toReturn.setSerialNumber(serialNumber);
                toReturn.setImageDate(imageDate);
                if(assetIsLaptop){
                    toReturn.setCarrier(ssh.venIntToVen(carrierAsInt));
                    toReturn.setPhoneNumber(phoneNumber);
                    toReturn.setIMEINumber(panasonicIMEI);
                    toReturn.setSIMNumber(simNumber);
                }
                if(assetIsDamaged){
                    if(sentForRepairAsInt == 1){
                        toReturn.setSentForRepair(true);
                    }else{
                        toReturn.setSentForRepair(false);
                    }
                    toReturn.setRepairDate(dateSentForRepair);
                    toReturn.setDamageDescription(damageDescription);
                }

            }

            //Close the connection for network security and bandwith reduction
            conn.close();

            AssetRequest<Asset> ar = new AssetRequest<Asset>(RequestType.CALL_ASSET,toReturn);
            return ar;
        }catch(Exception e){
            System.out.println("Error in calling the asset");
            System.out.println(e);
        }
        System.out.println("Error in calling the asset. Returning NULL from the server");
        return null;
    }

    /**
     * Sends a SQL statement to the virtual server stating that a client needs to view data on
     * a specific user. The statement will retrieve the data on said asset and then provide it 
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the 
     * statement.
     */
    public AssetRequest<User> callUser(AssetRequest<?> incomingRequest){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   
            sqlStatementHandler ssh = new sqlStatementHandler();
            search req = (search)incomingRequest.getData();
            User user = new User(req.userLast, req.userFirst, ssh.emplStatStringToStat(req.empStat));

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqUserInfo(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
                String lastName = rs.getString("drvNameLast");
                    if(rs.wasNull()){lastName = "VOID";}
                String firstName = rs.getString("drvNameFirst");
                    if(rs.wasNull()){firstName = "VOID";}
                int empNo = rs.getInt("drvEmpNo");
                    if(rs.wasNull()){empNo = 000000;}
                String emplStatAsString = rs.getString("drvEmplStatus");
                    if(rs.wasNull()){emplStatAsString = "NONE";}

                user.setEmpNo(empNo);
            }

            //Close the connection for network security and bandwith reduction
            conn.close();

            AssetRequest<User> ar = new AssetRequest<User>(AssetRequest.RequestType.CALL_USER, user);
            return ar;
        }catch(Exception e){
            System.out.println("Error in calling user");
            System.out.println(e);
        }
        System.out.println("Error in retrieving user. Server returning NULL");
        return null;
    }

    /**
     * Sends a SQL statemnet to the virtual server stating that a client needs to view a list
     * of all assests. The statement will retrieve the data on all assets and then provide it
     * to the client. Utilizes the sqlStatementHandler class for assistance with creating the
     * statement.
     */
    public AssetRequest<sqlList> callAssetList(AssetRequest<?> incomingRequest){
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;   

            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqAssetList(incomingRequest));
            sqlList sqllist = (sqlList)incomingRequest.getData();

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                String Asset_Name = rs.getString("Asset_Name");
                    if(rs.wasNull()){Asset_Name = "VOID";}
                int assetID = rs.getInt("AssetID");
                    if(rs.wasNull()){assetID = 0;}
                int asset_typeID = rs.getInt("Asset_TypeID");
                    if(rs.wasNull()){asset_typeID = 0;}
                sqllist.addAsset(new Asset(Asset_Name, assetID, toAssetType(asset_typeID)));
            }

            //Close the connection for network security and bandwith reduction
            conn.close();

            //Send back a request
            AssetRequest<sqlList> request = new AssetRequest<>(AssetRequest.RequestType.CALL_ASSET_LIST, sqllist);
            return request;

        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }
        System.out.println("callAssetList try failed");
        return null;
    }

    /**
     * Sends a SQL statment to the virtual server stating that a client needs to view a list
     * of all users. The statement will retriece the data on all users and then provide it 
     * to the clent. Utilizes the sqlStatementHandler class for assistance with creating the
     * statement.
     */
    public AssetRequest<sqlList> callUserList(AssetRequest<?> incomingRequest){
        sqlList sqllist = (sqlList)incomingRequest.getData();
        //Non-UP users
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;
            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqUserList(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
                String lastName = rs.getString("drvNameLast");
                    if(rs.wasNull()){lastName = "VOID";}
                String firstName = rs.getString("drvNameFirst");
                    if(rs.wasNull()){firstName = "VOID";}
                employmentStatus empStat = stringToEmp(rs.getString("drvEmplStatus"));
                    if(rs.wasNull()){ empStat = employmentStatus.NONE;}
                sqllist.addUser(new User(lastName,firstName,empStat));
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }

        //ADUC users
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(userTblURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;
            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqUserListAnnex(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
                String lastName = rs.getString("drvNameLast");
                    if(rs.wasNull()){lastName = "VOID";}
                String firstName = rs.getString("drvNameFirst");
                    if(rs.wasNull()){firstName = "VOID";}
                employmentStatus empStat = stringToEmp(rs.getString("drvEmplStatus"));
                    if(rs.wasNull()){ empStat = employmentStatus.NONE;}
                sqllist.addUser(new User(lastName,firstName,empStat));
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }

        AssetRequest<sqlList> request = new AssetRequest<>(AssetRequest.RequestType.CALL_USER_LIST, sqllist);
        return request;
    }

    /**
     * Method used to create a local list for an asset to be able to throw all of the potential users who could own
     * or be currently using it. Extremely similar to the "callUserList" method, except this one forgoes callnig the 
     * employment status of the users
     */
    public AssetRequest<sqlList> callLocalList(AssetRequest<?> incomingRequest){
        sqlList sqllist = (sqlList)incomingRequest.getData();
        //Non-UP users
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(servURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;
            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqLocalUserList(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
                String lastName = rs.getString("drvNameLast");
                    if(rs.wasNull()){lastName = "VOID";}
                String firstName = rs.getString("drvNameFirst");
                    if(rs.wasNull()){firstName = "VOID";}
                sqllist.addUser(new User(lastName,firstName));
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }

        //ADUC users
        try{
            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection(userTblURL);

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;
            sqlStatementHandler ssh = new sqlStatementHandler();

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery(ssh.reqUserListAnnex(incomingRequest));

            //Not necessary for methods that will not be returning anything
            while(rs.next()){
                //Any returning statements go here
                String lastName = rs.getString("drvNameLast");
                    if(rs.wasNull()){lastName = "VOID";}
                String firstName = rs.getString("drvNameFirst");
                    if(rs.wasNull()){firstName = "VOID";}
                sqllist.addUser(new User(lastName,firstName));
            }

            //Close the connection for network security and bandwith reduction
            conn.close();
        }catch(Exception e){
            System.out.println("Error in creating a new user");
            System.out.println(e);
        }

        AssetRequest<sqlList> request = new AssetRequest<>(AssetRequest.RequestType.CALL_LOCAL_USER_LIST, sqllist);
        return request;
    }

    private employmentStatus stringToEmp(String employmentString){
        switch(employmentString.toLowerCase()){
            case "terminated":
                return employmentStatus.TERMINATED;
            case "termed":
                return employmentStatus.TERMINATED;
            case "term":
                return employmentStatus.TERMINATED;
            case "loa":
                return employmentStatus.LOA;
            case "leave":
                return employmentStatus.LOA;
            case "leave of absence":
                return employmentStatus.LOA;
            case "active":
                return employmentStatus.ACTIVE;
            default:
                System.out.println("Error in reading the user employment status string.");
                break;
        }
        return null;
    }

    private assetTypes toAssetType(int typeID){
        switch(typeID){
            case 3:
                return assetTypes.DESKTOP;
            case 4:
                return assetTypes.LAPTOP;
            case 9:
                return assetTypes.AIRCARD;
            case 1:
                return assetTypes.HOTSPOT;
            default:
                System.out.println("Invalid asset type id");
                break;
        }
        return assetTypes.MONITOR;
    }
}