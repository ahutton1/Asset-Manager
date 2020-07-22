package Server;
//TODO: ALL METHODS ARE STUBS. Methods needs to be fully flushed out and created based on information provided and needed SQL statements

import DisplayObjects.sqlList;

//For Reference -> View SQL Textbook

/**
 * Helper class that assists in the formatting of SQL statements to and from the server as clients connect and
 * interact with it. The handler class can take in given search conditions or general listings used in the GUI,
 * and develop dynamic SQL statments from those terms.
 */
public class sqlStatementHandler {

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting that a new asset be created and 
     * added to the tables.
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqNewAsset(AssetRequest<?> request){
        String sqlStatement = "INSERT INTO tblAssets VALUES (";

        //TODO - Determine the best way to insert things. Most likely all at once after a save icon is selected

        return sqlStatement;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting that a new user be created and 
     * added to the tables. Important note* -> Look into how ADUC could potentially have new users added (LOW 
     * CRITICALITY)
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqNewUser(AssetRequest<?> request){
        String sqlStatement = "INSERT INTO _ VALUES (";

        //TODO - Import the user table. Find out how ADUC interacts with SQL Server. Need to check both tblUsers and tblUsers_NonUP.
        //       Most likely will be importing to tblUsers_NonUP as anything else would be manually placed in ADUC

        return "NULL";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of assets that
     * is stored in the tables, based on which search filter or term combination and a user has on at any given
     * time.
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqAssetList(AssetRequest<?> request){
        String sqlStatement = "SELECT Asset_Name, AssetID, Asset_TypeID FROM tblAssets";
        sqlList incomingData = (sqlList)request.getData();
        if(incomingData.getFirstType().equals(sqlList.searchType.BASIC)){
            //No filter or search term is applied, so just send the most basic data of everything
            return sqlStatement;
        }
        switch(incomingData.getFirstType()){
            case BASIC:
                break;
            case ASSET_NAME:
                sqlStatement = (sqlStatement + " WHERE Asset_Name IN (" + incomingData.getFirstTerm() + ")");
                break;
            case ASSET_ID:
                sqlStatement = (sqlStatement + " WHERE AssetID IN (" + incomingData.getFirstTerm() + ")");
                break;
            case ASSET_TYPE:
                sqlStatement = (sqlStatement + " WHERE AssetTypeID IN (" + findEquivID(incomingData.getFirstTerm()) + ")");
                break;
            case ASSET_USER:
                sqlStatement = (sqlStatement + " WHERE _ IN (" + incomingData.getFirstTerm() + ")");
                break;
            case ASSET_STAT_TYPE:
                sqlStatement = (sqlStatement + " WHERE Inventory_StatusID IN (" + invStatToID(incomingData.getFirstTerm()) + ")");
                break;
            case ASSET_PHONE_NUMBER:
                sqlStatement = (sqlStatement + " WHERE Phone_Number IN (" + incomingData.getFirstTerm() + ")");
                break;
            default:
                System.err.println("Error in reading data type for asset list in translator");
                break;
        }
        return sqlStatement;
    }

    /**
     * Finds and returns the ID equivalent of the search term that was inputted by the user for the asset type
     *      3 - Desktop
     *      4 - Laptop
     *      9 - Aircard
     *      1 - Hotspot
     * Guaranteed 'equals' with toLowerCase() method. The user must type the search term in correctly if the results are going
     * to pop up. Any misspelling will cause the program to send a zero in as the asset type ID, showing no results. The 
     * program will not crash because of this though
     * @param searchTerm : String that is inputted by the user as a searching mechanism to find a certain type
     *                     of asset
     * @return int that represents an asset type
     */
    private int findEquivID(String searchTerm){
        int toReturn = 4;
        searchTerm = searchTerm.toLowerCase();
        switch(searchTerm){
            case "desktop":
                toReturn = 3;
                break;
            case "laptop":
                toReturn = 4;
                break;
            case "aircard":
                toReturn = 9;
                break;
            case "hotspot":
                toReturn = 1;
                break;
            default:
                toReturn = 0;
                System.out.println("No instance of declared asset found. Typo likely");
                break;
        }
        return toReturn;
    }

    /**
     * Finds and returns the ID equivalent of the search term that was inputted by the user for the inventory status
     *      1 - In inventory
     *      2 - Assigned
     *      3 - Retired
     *      4 - Disposed
     *      5 - Loaned
     *      7 - Lost/Stolen
     *      8 - Damaged
     * Guaranteed 'equals' with toLowerCase() method. The user must type the search term in correctly if the results are going
     * to pop up. Any misspelling will cause the program to send a zero in as the inventory status ID, showing no results. The 
     * program will not crash because of this though
     * @param searchTerm
     * @return
     */
    private int invStatToID(String searchTerm){
        int toReturn = 1;
        searchTerm = searchTerm.toLowerCase();
        switch(searchTerm){
            case "in inventory":
                toReturn = 1;
                break;
            case "assigned":
                toReturn = 2;
                break;
            case "retired":
                toReturn = 3;
                break;
            case "disposed":
                toReturn = 4;
                break;
            case "loaned":
                toReturn = 5;
                break;
            case "lost":
                toReturn = 7;
                break;
            case "stolen":
                toReturn = 7;
                break;
            case "lost/stolen":
                toReturn = 7;
                break;
            case "damaged":
                toReturn = 8;
                break;
            default:
                toReturn = 0;
                System.out.println("No instance of the declared search value found. Typo likely");
                break;
        }
        return toReturn;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of users that is
     * stored in the tables, based on what search conditions a user has on at any given time.
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqUserList(AssetRequest<?> request){
        String sqlStatement = "SELECT drvNameLast, drvNameFirst " /*, drvEmplStatus*/ + "FROM tblUsers_NonUP";
        sqlList incomingData = (sqlList)request.getData();
        if(incomingData.getFirstType().equals(sqlList.searchType.BASIC)){
            //No filter or search term is applied, so just send the most basic data of everything
            return sqlStatement;
        }
        switch(incomingData.getFirstType()){
            case USER_FIRST:
                sqlStatement = (sqlStatement + " WHERE drvNameFirst IN (" + handleName(incomingData.getFirstTerm()) + ")");
                break;
            case USER_LAST:
                sqlStatement = (sqlStatement + " WHERE drvNameLast IN (" + handleName(incomingData.getFirstTerm()) + ")");
                break;
            case USER_STAT:
                sqlStatement = (sqlStatement + " WHERE drvEmplStatus IN (" + incomingData.getFirstTerm() + ")");
                break;
            default:
                break;
        }
        return sqlStatement;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server to pull all of the users in the list of ADUC
     * and the list of non-UltiPro users stored on the SQL server.
     * @return : List of users based on the basic terms to pull everyone
     */
    public String reqLocalUserList(AssetRequest<?> request){
        return "SELECT drvNameLast, drvNameFirst FROM tblUsers_NonUP";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of users that is
     * stored in the tables, based on what search conditions a user has on at any given time.
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqUserListAnnex(AssetRequest<?> request){
        //TODO: Include joint searching of both the tblUsers and tblUsers_NonUP tables
        String sqlStatement = "SELECT drvNameLast, drvNameFirst " /*, drvEmplStatus*/ + "FROM EVNA_tbl";
        sqlList incomingData = (sqlList)request.getData();
        if(incomingData.getFirstType().equals(sqlList.searchType.BASIC)){
            //No filter or search term is applied, so just send the most basic data of everything
            return sqlStatement;
        }
        switch(incomingData.getFirstType()){
            case USER_FIRST:
                sqlStatement = (sqlStatement + " WHERE drvNameFirst IN (" + handleName(incomingData.getFirstTerm()) + ")");
                break;
            case USER_LAST:
                sqlStatement = (sqlStatement + " WHERE drvNameLast IN (" + handleName(incomingData.getFirstTerm()) + ")");
                break;
            case USER_STAT:
                sqlStatement = (sqlStatement + " WHERE drvEmplStatus IN (" + incomingData.getFirstTerm() + ")");
                break;
            default:
                break;
        }
        return sqlStatement;
    }

    /**
     * Small helper class that is used to correct any string's capitalization to how a name would be spelled
     * and capitalized. It takes the string, sends everything to lowercase, then sends the first letter to 
     * uppercase, then returns.
     * @param name : The name that is being handled as a safety precaution so that all searches can run without
     *               issue.
     * @return The string in name-style capitalization.
     */
    private String handleName(String name){
        name = name.toLowerCase();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given user. That users' information is to be displayed in the main information display panel
     * on the right hand side of the application screen.
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqUserInfo(AssetRequest<?> request){          //TODO
        search req = (search)request.getData();
        String sqlStatement = "SELECT drvNameLast, drvNameFirst, drvEmpNo, drvEmplStatus, ___ FROM tblUsers, tblUsers_NonUP WHERE (drvNameLast IN " + req.userLast + ") AND (drvNameFirst IN " + req.userFirst +") AND (drvEmplStatus IN " + req.empStat + ")";

        //TODO - Determine what information about the user should be shown in the content pane

        return sqlStatement;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given asset. That asset's information is to be displayed in the main information display 
     * panel on the right hand side of the application screen
     * @return A SQL statment in the form of a string that can be sent to the SQL Server requesting information
     */
    public String reqAssetInfo(AssetRequest<?> request){
        search req = (search)request.getData();
        String sqlStatement = "SELECT Asset_Name, AssetID, Asset_TypeID, Inventory_StatusID, VendorID, Model, Serial FROM tblAssets WHERE (Asset_Name IN " + req.assetName + ") AND (AssetID IN " + req.assetID + ") AND (Asset_TypeID IN " + req.typeID + ")";
        return sqlStatement;
    }
    
}