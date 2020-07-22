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

    public void setAirCardInformation(vendors carrier, String phoneNumber, String simNumber, String panasonicIMEI){
        this.carrier = carrier;
        this.phoneNumber = phoneNumber;
        this.simNumber = simNumber;
        this.panasonicIMEI = panasonicIMEI;
    }

    /**
     * GETTERS FOR GUI VISUALIZATION
     */

    public String getAssetName(){ return assetName; }
    public assetTypes getAssetType(){ return assetType; }
    public int getAssetNumber(){ return assetNumber; }
    public vendors getAssetVendor(){ return vendor; }
    public String getAssetModel(){ return model; }
    public String getSerialNumber(){ return serialNumber; }
    public statusTypes getInvStat(){ return invStat; }
    public User getUser(){ return user; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getIMEINumber(){ return panasonicIMEI; }
    public String getSIMNumber(){ return simNumber; }
    public vendors getCarrier(){ return carrier; }
    
}