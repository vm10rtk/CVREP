package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "users"
)
public class Users implements Serializable{
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    @Column(
            name = "id"
    )
    private int Id;

    @Column(
            name = "login"
    )
    private String Login;
    @Column(
            name="password"
    )
    private int Password;
    public Users(){

    }
    public Users(String login,String password){
        this.Login=login;
        this.Password=password.hashCode();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public int getPassword() {
        return Password;
    }

    public void setPassword(int password) {
        this.Password = password;
    }


}
