package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(
        name = "company"
)
public class Company implements Serializable {
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "idcompany"
    )
private int Company_id;
    @Column(
            name = "name"
    )
private String Name;
//    @Column(
//            name = "incomes"
//    )
//    private double incomes;
//    @Column(
//            name = "profit"
//    )
//private double profit;
//
    @OneToMany(
            mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<Employees> employeeslist;
    @OneToMany(
            mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<FinIndicators> finIndicatorslist;
    @OneToMany(
            mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<FinReports> FinReportslist;
    @OneToMany(
            mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<Info> Infolist;
    @OneToMany(
            mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<ProductsAndService> ProductsAndServicelist;

    public Company(){

    }
    public Company(int company_id,String name){
    this.Company_id=company_id;
    this.Name=name;
    }



    public int getCompany_id(){return this.Company_id;}

    public void setCompany_id(int company_id){this.Company_id=company_id;}

    public String getName(){return this.Name;}
    public void setName(String name){this.Name=name;}

    public List<Employees> getEmployeeslist() {
        return employeeslist;
    }

    public void setEmployeeslist(List<Employees> employeeslist) {
        this.employeeslist = employeeslist;
    }
    public List<FinIndicators> getFinIndicatorslist(){
    return finIndicatorslist;
    }

    public void setFinIndicatorslist(List<FinIndicators> finIndicatorslist) {
        this.finIndicatorslist = finIndicatorslist;
    }

    public List<FinReports> getFinReportslist() {
        return FinReportslist;
    }

    public void setFinReportslist(List<FinReports> finReportslist) {
        FinReportslist = finReportslist;
    }

    public List<Info> getInfolist() {
        return Infolist;
    }

    public void setInfolist(List<Info> infolist) {
        Infolist = infolist;
    }

    public List<ProductsAndService> getProductsAndServicelist() {
        return ProductsAndServicelist;
    }

    public void setProductsAndServicelist(List<ProductsAndService> productsAndServicelist) {
        ProductsAndServicelist = productsAndServicelist;
    }

}
