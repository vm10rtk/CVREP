import dao.AdminDao;
import models.*;

import services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class AdminMenu {
    public void admins(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<Admin> admins){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        Admin adminNew = (Admin) sois.readObject();
                        AdminService adminService = new AdminService();
                        if (userService.findIDByLogin(adminNew.getUser().getLogin()) == null){
                            soos.writeObject("success");
                            userService.saveEntity(adminNew.getUser());
                            adminService.saveAdmin(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        Admin adminNew = (Admin) sois.readObject();
                        AdminService adminService = new AdminService();
                        adminService.updateAdmin(adminNew);
                        break;
                    }
                    case "del":{
                        Admin adminNew = (Admin) sois.readObject();
                        AdminService adminService = new AdminService();
                        adminService.deleteAdmin(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void finind(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<FinIndicators> finIndicators){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        FinIndicators adminNew = (FinIndicators) sois.readObject();
                        FinIndicatorsService finIndicatorsService = new FinIndicatorsService();
                        if (finIndicatorsService.findEntity(adminNew.getId()) == null){
                            soos.writeObject("success");
                            finIndicatorsService.saveEntity(adminNew);
                           // finIndicatorsService.saveAdmin(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        FinIndicators adminNew = (FinIndicators) sois.readObject();
                        FinIndicatorsService finIndicatorsService = new FinIndicatorsService();
                        finIndicatorsService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        FinIndicators adminNew = (FinIndicators) sois.readObject();
                        FinIndicatorsService finIndicatorsService = new FinIndicatorsService();
                        finIndicatorsService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void finrep(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<FinReports> finReports){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        FinReports adminNew = (FinReports) sois.readObject();
                        FinReportsService finReportsService = new FinReportsService();
                        if (finReportsService.findEntity(adminNew.getId() ) == null){
                            soos.writeObject("success");
                            finReportsService.saveEntity(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        FinReports adminNew = (FinReports) sois.readObject();
                        FinReportsService finReportsService = new FinReportsService();
                        finReportsService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        FinReports adminNew = (FinReports) sois.readObject();
                        FinReportsService finReportsService = new FinReportsService();
                        finReportsService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void comp(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<Company> companies){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        Company adminNew = (Company) sois.readObject();
                        CompanyService companyService = new CompanyService();
                        if (companyService.findEntity(adminNew.getCompany_id() ) == null){
                            soos.writeObject("success");
                            companyService.saveEntity(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        Company adminNew = (Company) sois.readObject();
                        CompanyService companyService = new CompanyService();
                        companyService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        Company adminNew = (Company) sois.readObject();
                        CompanyService companyService = new CompanyService();
                        companyService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void Employees(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<Employees> employees){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        Employees adminNew = (Employees) sois.readObject();
                        EmployeesService employeesService = new EmployeesService();
                        if (employeesService.findEntity(adminNew.getId() ) == null){
                            soos.writeObject("success");
                            employeesService.saveEntity(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        Employees adminNew = (Employees) sois.readObject();
                        EmployeesService employeesService = new EmployeesService();
                        employeesService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        Employees adminNew = (Employees) sois.readObject();
                        EmployeesService employeesService = new EmployeesService();
                        employeesService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void Info(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<Info> infos){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        Info adminNew = (Info) sois.readObject();
                        InfoService infoService = new InfoService();
                        if (infoService.findEntity(adminNew.getId() ) == null){
                            soos.writeObject("success");
                            infoService.saveEntity(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        Info adminNew = (Info) sois.readObject();
                        InfoService infoService = new InfoService();
                        infoService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        Info adminNew = (Info) sois.readObject();
                        InfoService infoService = new InfoService();
                        infoService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void pns(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<ProductsAndService> productsAndServices){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "add":{
                        ProductsAndService adminNew = (ProductsAndService) sois.readObject();
                        ProductsAndServiceService productsAndServiceService = new ProductsAndServiceService();
                        if (productsAndServiceService.findEntity(adminNew.getId() ) == null){
                            soos.writeObject("success");
                            productsAndServiceService.saveEntity(adminNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "red":{
                        ProductsAndService adminNew = (ProductsAndService) sois.readObject();
                        ProductsAndServiceService productsAndServiceService = new ProductsAndServiceService();
                        productsAndServiceService.updateEntity(adminNew);
                        break;
                    }
                    case "del":{
                        ProductsAndService adminNew = (ProductsAndService) sois.readObject();
                        ProductsAndServiceService productsAndServiceService = new ProductsAndServiceService();
                        productsAndServiceService.deleteEntity(adminNew);
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void users(Admin admin, ObjectInputStream sois, ObjectOutputStream soos, List<Users> users){
        UsersService userService = new UsersService();
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                if(userService.findIfUserExist(admin.getUser())){
                    soos.writeObject("good");
                }
                else {
                    soos.writeObject("bad");
                    return;
                }
                switch(menuButton){
                    case "red":{
                        Users userNew = (Users) sois.readObject();
                        if (userService.findIDByLogin(userNew.getLogin()) == null) {
                            soos.writeObject("success");
                            userService.updateEntity(userNew);
                        }
                        else{
                            soos.writeObject("failed");
                        }
                        break;
                    }
                    case "exit":{
                        return;
                    }
                }
            }while(menuButton != "exit");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
