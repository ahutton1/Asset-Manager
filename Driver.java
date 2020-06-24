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
            Class.forName("jdbc:sqlserver://10.221.0.220;urmhc_equipment");

            //Establish a connection with the specific database
            Connection conn = DriverManager.getConnection("jdbc:odbc:urmhc_equipment","read","vnsread");

            //Create a placeholder for a concrete statement that will allow us to make and view a SELECT statement
            Statement stmt = conn.createStatement();

            //Create a placeholder for the results from a given select query
            ResultSet rs;

            //TODO Implement the select statement and printing method to test if the connection worked as it needs to

        }catch(Exception e){
            System.out.println("Connection error occured. Logging and shutting down");
            System.out.println(e);
            //try{
                //FileHandler fh = new FileHandler("ErrorLog.log");
                //Logger logger;
            //}catch(Exception logError){

            //}
        }

    }
}

