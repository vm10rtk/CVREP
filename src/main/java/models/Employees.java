package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "employees"
)
public class Employees
    implements Serializable
{
    private static final long serialVersionUID = 123456789L;
    @Id
@GeneratedValue(
        strategy = GenerationType.IDENTITY
)

@Column(
        name = "idemployees"
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
        name = "fio"
            //lenght = 150
)
private String FIO;
    @Column(
        name = "post"
        // lenght = 150
)
private String Post;
    @Column(
        name = "salary"
        // lenght = 150
)
private double Salary;
    @Column(
        name = "hiredate"
        // lenght = 150
)
private String Hiredate;
public Employees (){

}
public Employees(int id,Company company,String fio,String post,double salary,String hiredate){
    this.Id = id;
    this.Company= company;
    this.FIO=fio;
    this.Post=post;
    this.Salary=salary;
    this.Hiredate=hiredate;
}

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Company getCompany() {
        return this.Company;
    }

    public void setCompany(Company company) {
        this.Company= company;
    }

        public String getFIO(){return this.FIO;}
    public void setFIO(String fio){this.FIO=fio;}

    public String getPost(){return this.Post;}
    public void setPost(String post){this.Post=post;}

    public double getSalary(){return this.Salary;}
    public void setSalary(double salary){this.Salary=salary;}

    public String getHiredate(){return this.Hiredate;}
    public void setHiredate(String hiredate){this.Hiredate=hiredate;}
}
