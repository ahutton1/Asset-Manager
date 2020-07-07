package Server;

import enums.employmentStatus;

public class search {

    //Asset variables
    public String assetName;
    public int typeID, assetID;

    //User variables
    public String userFirst, userLast;
    public int empStat;

    //Used for asset searching in the content pane
    public search(String assetName, int typeID, int assetID){
        this.assetName = assetName;
        this.typeID = typeID;
        this.assetID = assetID;
    }

    //used for user searching in the content pane
    public search(String userFirst, String userLast, int empStat){
        this.userFirst = userFirst;
        this.userLast = userLast;
        this.empStat = empStat;
    }
    
}