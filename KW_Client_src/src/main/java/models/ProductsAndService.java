package models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "productsandservice"
)
public class ProductsAndService implements Serializable {
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )@Column(
            name = "idproductsandservice"
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
            name = "name"
    )
    private String Name;
    @Column(
            name = "description"
    )
    private String Description;
    @Column(
            name = "price"
    )
    private double Price;
    @Column(
            name = "soldcount"
    )
    private int Soldcount;
    public ProductsAndService(){}
    public ProductsAndService(int id,Company company,String name,String description,double price,int soldcount){
        this.setId(id);
        this.setCompany(company);
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setSoldcount(soldcount);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public models.Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getSoldcount() {
        return Soldcount;
    }

    public void setSoldcount(int soldcount) {
        Soldcount = soldcount;
    }
}
