package DisplayObjects;
import java.io.Serializable;

/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/
import enums.*;

//Class that represents a user of the URMHC system
public class User implements Serializable{
    //TODO flush out user class variables
    private String lastName, firstName, deptCode;
    private employmentStatus empStat;

    public User(String lastName, String firstName, employmentStatus empStat){
        //TODO
        this.lastName = lastName;
        this.firstName = firstName;
        this.empStat = empStat;
    }

    public User(String lastName, String firstName, employmentStatus empStat, String deptCode){
        this.lastName = lastName;
        this.firstName = firstName;
        this.empStat = empStat;
        this.deptCode = deptCode;
    }

    public void setDeptCode(String deptCode){this.deptCode = deptCode;}

    public void setEmpStat(employmentStatus empStat){this.empStat = empStat;}

    public String toListString(){
        return (lastName + ", " + firstName + " : " + empStat);
    }

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public String getDeptCode(){return deptCode;}

    public employmentStatus getEmpStat(){return empStat;}
}