package bizobjects;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
public class Employee extends Person implements java.io.Serializable, User {
    private int code;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    //Implementing interface User
    @Transient
    public String getUserId() {
        return null;

        //should return String
    }

    @Transient
    public String getPassword() {
        return null;

        //should return String
    }

    @Transient
    public String getRole() {
        return null;

        //should return String
    }

    //*****Done Implementing interface User ****
}
