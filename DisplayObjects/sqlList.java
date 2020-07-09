package DisplayObjects;

import java.io.Serializable;
import java.util.ArrayList;

import Server.search;

public class sqlList implements Serializable{

    public enum searchType{
        //The first name of any given user
        USER_FIRST,

        //The last name of any given user
        USER_LAST,

        //The computer name given to any given asset
        ASSET_NAME,

        //The ID number associated to any given asset. Found on the asset tag
        ASSET_ID,

        //The type of machine that any given asset is. Ex: Laptop, Desktop, etc . . .
        ASSET_TYPE,

        //The default search parameter
        BASIC,

        //The user associated with any given asset
        ASSET_USER,

        //The phone number associated with a panasonic Air Card asset type
        ASSET_PHONE_NUMBER,

        //The inventory status of any given asset
        ASSET_STAT_TYPE,

        //The employment status of any given user
        USER_STAT,

        //The model of any given asset
        ASSET_MODEL
    }

    //Lists to hold the users and assets that are being returned in the search functionality
    private ArrayList<User> userList;
    private ArrayList<Asset> assetList;

    //First search term and type. Acts as filter #1
    public searchType searchTypeOne;
    public String searchTermOne;

    //Second search term and type. Acts as filter #2
    public searchType searchTypeTwo;
    public String searchTermTwo;

    /**
     * Constructor for initializing and creating the list functionality
     */
    public sqlList(){
        userList = new ArrayList<>();
        assetList = new ArrayList<>();
        searchTypeOne = searchType.BASIC;
        searchTypeTwo = searchType.BASIC;       //TODO: Subject to change. Determine default search term and set typeTwo to equal it. As of active it is TYPE: NULL
    }

    //Returns the list of users. Mainly used by GUI
    public ArrayList<User> getUserList(){return userList;}

    //Returns the list of assets. Mainly used by GUI
    public ArrayList<Asset> getAssetList(){return assetList;}

    //Adds a user to the user list
    public void addUser(User user){userList.add(user);}

    //Adds an asset to the asset list
    public void addAsset(Asset asset){assetList.add(asset);}

    //Sets the first search term and type
    public void setFirst(searchType styo, String steo){
        this.searchTypeOne = styo;
        this.searchTermOne = steo;
    }

        //Getters
        public searchType getFirstType(){return searchTypeOne;}

        public String getFirstTerm(){return searchTermOne;}

    //Sets the second search term and type
    public void setSecond(searchType styt, String stet){
        this.searchTypeTwo = styt;
        this.searchTermTwo = stet;
    }

        //Getters
        public searchType getSecondType(){return searchTypeTwo;}

        public String getSecondTerm(){return searchTermTwo;}
    
}