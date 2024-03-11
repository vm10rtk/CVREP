package models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "finreports"
)
public class FinReports implements Serializable {
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "idfinreports"
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
            name = "year"
    )
private String Year;
    @Column(
            name = "reporttype"
    )
private String Reporttype;
    @Column(
            name = "reportdate"
    )
private String Reportdate;
public FinReports(){}
   public FinReports(int id,Company company,String year,String reporttype,String reportdate){
    this.Id=id;
    this.Company=company;
    this.Year=year;
    this.Reporttype=reporttype;
    this.Reportdate=reportdate;
    }

    public int getId(){return this.Id;}
    public void setId(int id){this.Id=id;}

    public Company getCompany() {
        return this.Company;
    }

    public void setCompany(Company company) {
        this.Company= company;
    }

    public String getYear(){return this.Year;}
    public void setYear(String year){this.Year=year;}

    public String getReporttype(){return this.Reporttype;}
    public void setReporttype(String reporttype){this.Reporttype=reporttype;}

    public String getReportdate(){return this.Reportdate;}
    public void setReportdate(String reportdate){this.Reportdate=reportdate;}
}
