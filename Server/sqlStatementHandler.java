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
                sqlStatement = (sqlStatement + " WHERE Asset_Name IS EQUAL " + incomingData.getFirstTerm());
                break;
            case ASSET_ID:
                sqlStatement = (sqlStatement + " WHERE AssetID IS EQUAL " + incomingData.getFirstTerm());
                break;
            case ASSET_TYPE:
                sqlStatement = (sqlStatement + " WHERE AssetTypeID IS EQUAL " + findEquivID(incomingData.getFirstTerm()));
                break;
            case ASSET_USER:
                //TODO Find or make the asset user column
                sqlStatement = (sqlStatement + " WHERE _ IS EQUAL " + incomingData.getFirstTerm());
                break;
            case ASSET_STAT_TYPE:
                //TODO devolp inventory status term to ID method
                sqlStatement = (sqlStatement + " WHERE Inventory_StatusID IS EQUAL " + incomingData.getFirstTerm());
                break;
            case ASSET_PHONE_NUMBER:
                sqlStatement = (sqlStatement + " WHERE Phone_Number IS EQUAL " + incomingData.getFirstTerm());
                break;
            default:
                System.err.println("Error in reading data type for asset list in translator");
                break;
        }

        return sqlStatement;
    }

    /**
     * Finds and returns the ID equialent of the search term that was inputted by the user for the asset type TODO
     * @param searchTerm
     * @return
     */
    private int findEquivID(String searchTerm){
        return 0;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of useres that is
     * stored in the tables, based on what search conditions a user has on at any given time.
     * @return true or false depending on whether the message was sent successfully
     */
    public String reqUserList(AssetRequest<?> request){
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
    public String reqAssetInfo(AssetRequest<?> request){
        return "NULL";
    }
    
}