package Server;

import java.io.Serializable;

import enums.employmentStatus;

public class search implements Serializable{

    //Asset variables
    public String assetName;
    public int typeID, assetID;

    //User variables
    public String userFirst, userLast;
    public String empStat;

    //Used for asset searching in the content pane
    public search(String assetName, int typeID, int assetID){
        this.assetName = assetName;
        this.typeID = typeID;
        this.assetID = assetID;
    }

    //used for user searching in the content pane
    public search(String userFirst, String userLast, String empStat){
        this.userFirst = userFirst;
        this.userLast = userLast;
        this.empStat = empStat;
    }
    
}