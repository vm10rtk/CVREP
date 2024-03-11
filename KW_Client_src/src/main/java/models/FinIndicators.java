package models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "financialind"
)
public class FinIndicators implements Serializable {
    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
        name = "idfinancialind"
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
            name = "infoExpenses"
    )
    private double InfoLiquidity;
    @Column(
            name = "infoIncomes"
    )

    private double InfoProfitability;
   public FinIndicators(){

    }
  public   FinIndicators(int id,Company company,String year,double infoLiquidity,double infoProfitability){
        this.Id=id;
        this.Company=company;
        this.Year=year;
        this.InfoLiquidity=infoLiquidity;
        this.InfoProfitability=infoProfitability;
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
    public void setYear(String year) {this.Year=year;}

    public double getInfoLiquidity(){return this.InfoLiquidity;}
    public void setInfoLiquidity(double infoLiquidity){this.InfoLiquidity=infoLiquidity;}
    public int getcompany_id(){
        return this.Company.getCompany_id();
    }
    public void setcompany_id(int company){
        this.Company.setCompany_id(company);
    }
    public double getInfoProfitability(){return this.InfoProfitability;}
    public void setInfoProfitability(double infoProfitability){this.InfoProfitability=infoProfitability;}


}
