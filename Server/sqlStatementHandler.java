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
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqNewAsset(AssetRequest<?> request){
        return "NULL";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting that a new user be created and 
     * added to the tables. Important note* -> Look into how ADUC could potentially have new users added (LOW 
     * CRITICALITY)
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqNewUser(AssetRequest<?> request){
        return "NULL";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of assets that
     * is stored in the tables, based on which search filter or term combination and a user has on at any given
     * time.
     * @return true or false depending on whether the message was sent successfully
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
                //TODO Find or make the asset user column
                sqlStatement = (sqlStatement + " WHERE _ IN (" + incomingData.getFirstTerm() + ")");
                break;
            case ASSET_STAT_TYPE:
                //TODO devolp inventory status term to ID method
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
     * Finds and returns the ID equivalent of the search term that was inputted by the user for the asset type TODO
     *      3 - Desktop
     *      4 - Laptop
     *      9 - Aircard
     *      1 - Hotspot
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
     * Finds and returns the ID equivalent of the search term that was inputted by the user for the inventory status TODO
     *      1 - In inventory
     *      2 - Assigned
     *      3 - Retired
     *      4 - Disposed
     *      5 - Loaned
     *      7 - Lost/Stolen
     *      8 - Damaged
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
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of useres that is
     * stored in the tables, based on what search conditions a user has on at any given time.
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqUserList(AssetRequest<?> request){
        String sqlStatement = "SELECT Asset_Name, AssetID, Asset_TypeID FROM tblAssets";
        sqlList incomingData = (sqlList)request.getData();
        if(incomingData.getFirstType().equals(sqlList.searchType.BASIC)){
            //No filter or search term is applied, so just send the most basic data of everything
            return sqlStatement;
        }
        return "NULL";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given user. That users' information is to be displayed in the main information display panel
     * on the right hand side of the application screen.
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqUserInfo(AssetRequest<?> request){
        return "NULL";
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given asset. That asset's information is to be displayed in the main information display 
     * panel on the right hand side of the application screen
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqAssetInfo(search request){
        String sqlStatement = "SELECT Asset_Name, AssetID, Asset_TypeID, Inventory_StatusID, VendorID, Model, Serial FROM tblAssets WHERE (Asset_Name IN " + request.assetName + ") AND (AssetID IN " + request.assetID + ") AND (Asset_TypeID IN " + request.typeID + ")";
        return sqlStatement;
    }
    
}