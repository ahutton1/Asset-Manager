package Server;

import java.io.Serializable;

public class search implements Serializable{

    //Asset variables
    public String assetName, assetNumber;
    public int typeID;

    //User variables
    public String userFirst, userLast;
    public String empStat;

    //Used for asset searching in the content pane
    public search(String assetName, int typeID, String assetNumber){
        this.assetName = assetName;
        this.typeID = typeID;
        this.assetNumber = assetNumber;
    }

    //used for user searching in the content pane
    public search(String userFirst, String userLast, String empStat){
        this.userFirst = userFirst;
        this.userLast = userLast;
        this.empStat = empStat;
    }
    
}