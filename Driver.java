import java.lang.System.Logger;
import java.sql.*;
/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import java.util.logging.FileHandler;

class Driver{
    public static void main(String args[]){
        try{
            //Determine where the program is connecting to
            //Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //Establish a connection with the specific database
            //Connection conn = DriverManager.getConnection("jdbc:ucanaccess:10.221.0.220/Databases/urmhc_equipment","read","vnsread");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.221.0.220;databaseName=urmhc_equipment;user=read;password=vnsread");

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;

            //Build out the testing suite and run the required tests
            rs = stmt.executeQuery("SELECT AssetID FROM tblAssets");
            while(rs.next()){
                int assetID = rs.getInt("AssetID");
                System.out.println(assetID);
            }
            conn.close();

        }catch(Exception e){
            System.out.println("Connection error occured. Logging and shutting down");
            System.out.println(e);
            /*try{
                FileHandler fh = new FileHandler("ErrorLog.log");
                Logger logger;
            }catch(Exception logError){
                System.out.println("Fatal Error Occured. Error Logging Failed. Shutting Down");
                System.out.println(logError);
            }*/
        }

    }
}