package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "admins")
public class Admin implements Serializable {
    private static final long serialVersionUID = 123456789L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    Users user;

    @Column
    private boolean rightsToRedactUsers;
    private boolean rightsToDeleteUsers;

    private boolean rightsToRedactAdmins;
    private boolean rightsToDeleteAdmins;

    public Admin() {    }
    public Admin(boolean rp, boolean dp, boolean ra, boolean da) {
        this.user = user;
        this.rightsToRedactUsers = rp;
        this.rightsToDeleteUsers = dp;
        this.rightsToRedactAdmins = ra;
        this.rightsToDeleteAdmins = da;
    }
    public Admin(Users user, boolean rp, boolean dp, boolean rd, boolean dd, boolean ra, boolean da) {
        this.user = user;
        this.rightsToRedactUsers = rp;
        this.rightsToDeleteUsers = dp;
        this.rightsToRedactAdmins = ra;
        this.rightsToDeleteAdmins = da;
    }

    public int getId() {
        return id;
    }
    public void setRightsToRedactUsers(boolean rp){
        rightsToRedactUsers = rp;
    }
    public boolean getRightsToRedactUsers(){return rightsToRedactUsers; }
    public void setRightsToDeleteUsers(boolean dp){
        rightsToDeleteUsers = dp;
    }
    public boolean getRightsToDeleteUsers(){return rightsToDeleteUsers; }

    public void setRightsToRedactAdmins(boolean ra){
        rightsToRedactAdmins = ra;
    }
    public boolean getRightsToRedactAdmins(){return rightsToRedactAdmins; }
    public void setRightsToDeleteAdmins(boolean da){
        rightsToDeleteAdmins = da;
    }
    public boolean getRightsToDeleteAdmins(){return rightsToDeleteAdmins; }
    public void setUser(Users user) {
        this.user = user;
    }
    public Users getUser(){return user;}

}