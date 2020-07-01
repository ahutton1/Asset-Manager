package Server;
//TODO: ALL METHODS ARE STUBS. Methods needs to be fully flushed out and created based on information provided and needed SQL statements

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
    public boolean reqNewAsset(){
        return true;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting that a new user be created and 
     * added to the tables. Important note* -> Look into how ADUC could potentially have new users added (LOW 
     * CRITICALITY)
     * @return true or false depending on whether the message was sent successfully
     */
    public boolean reqNewUser(){
        return true;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of assets that
     * is stored in the tables, based on which search filter or term combination and a user has on at any given
     * time.
     * @return true or false depending on whether the message was sent successfully
     */
    public boolean reqAssetList(){
        return true;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting the entire list of useres that is
     * stored in the tables, based on what search conditions a user has on at any given time.
     * @return true or false depending on whether the message was sent successfully
     */
    public boolean reqUserList(){
        return true;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given user. That users' information is to be displayed in the main information display panel
     * on the right hand side of the application screen.
     * @return true or false depending on whether the message was sent successfully
     */
    public boolean reqUserInfo(){
        return true;
    }

    /**
     * Method that sends a SQL statement to the SQL virtual server requesting all of the information regarding
     * a singular given asset. That asset's information is to be displayed in the main information display 
     * panel on the right hand side of the application screen
     * @return true or false depending on whether the message was sent successfully
     */
    public boolean reqAssetInfo(){
        return true;
    }
    
}