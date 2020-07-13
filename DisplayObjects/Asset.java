package DisplayObjects;

import java.io.Serializable;

/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import enums.*;

/*  The Asset class is an object that represents each individual asset  */
public class Asset implements Serializable{
    //Variable declarations
        private String assetName, model, serialNumber;
        private assetTypes assetType;
        private int assetNumber;
        private vendors vendor;
        private statusTypes invStat;
        private User user;

    //Laptop specific variable declarations
        private vendors carrier;
        private String phoneNumber, simNumber, panasonicIMEI;

    //Constructor for an asset. Called when a new asset is being created by a user
    public Asset(String assetName,assetTypes assetType,int assetNumber,vendors vendor,String model,String serialNumber,statusTypes invStat,User user){
        this.assetName = assetName;
        this.assetType = assetType;
        this.assetNumber = assetNumber;
        this.vendor = vendor;
        this.model = model;
        this.serialNumber = serialNumber;
        this.invStat = invStat;
        this.user = user;
    }

    public Asset(String assetname, int assetNumber, assetTypes assetType){
        this.assetName = assetname;
        this.assetType = assetType;
        this.assetNumber = assetNumber;
    }

    public String toListString(){
        return (assetName + "\t" + assetNumber + "\t" + assetType);
    }
    
}