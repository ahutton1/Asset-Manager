package DisplayObjects;
import java.io.Serializable;

/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import enums.*;

//Class that represents a user of the URMHC system
public class User implements Serializable{
    //TODO flush out user class variables
    private String lastName, firstName;
    private int empNo;
    private employmentStatus empStat;

    public User(){}

    public User(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public User(String lastName, String firstName, employmentStatus empStat){
        this.lastName = lastName;
        this.firstName = firstName;
        this.empStat = empStat;
    }

    public User(String lastName, String firstName, employmentStatus empStat, int empNo){
        this.lastName = lastName;
        this.firstName = firstName;
        this.empStat = empStat;
        this.empNo = empNo;
    }

    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setEmpNo(int empNo){this.empNo = empNo;}
    public void setEmpStat(employmentStatus empStat){this.empStat = empStat;}

    public String toListString(){ return (lastName + ", " + firstName + " : " + empStat); }

    public String toComboBoxString(){ return (lastName + ", " + firstName); }

    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public int getEmpNo(){return empNo;}
    public employmentStatus getEmpStat(){return empStat;}
}