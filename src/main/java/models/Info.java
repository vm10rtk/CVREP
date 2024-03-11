package models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "info"
)
public class Info implements Serializable {
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "idinfo"
    )
    private int Id;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "idcompany"
    )
    private Company Company;
    @Column(
            name = "address"
    )
    private String Address;
    @Column(
            name = "phone"
    )
    private String Phone;
    @Column(
            name = "email"
    )
    private String Email;
    @Column(
            name = "activity"
    )
    private String Activity;
    public Info(){}
    public Info(int id,Company company,String address,String phone,String email,String activity){
        this.Id=id;
        this.Company=company;
        this.Address=address;
        this.Phone=phone;
        this.Email=email;
        this.Activity=activity;
    }

    public int getId(){return this.Id;}
    public void setId(int id){this.Id=id;}

    public Company getCompany() {
        return this.Company;
    }

    public void setCompany(Company company) {
        this.Company= company;
    }

    public String getAddress(){return this.Address;}
    public void setAddress(String address){this.Address=address;}

    public String getPhone(){return this.Phone;}
    public void setPhone(String phone){this.Phone=phone;}

    public String getEmail(){return this.Email;}
    public void setEmail(String email){this.Email=email;}

    public String getActivity(){return this.Activity;}
    public void setActivity(String activity){this.Activity=activity;
    }
}
