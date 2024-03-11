//import services.RecordService;
import models.*;
import services.*;

import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.*;

public class NewThread extends Thread {
    Socket clientAccepted;
    int connections;

    NewThread(Socket clientAccepted, int connections) {
        this.clientAccepted = clientAccepted;
        this.connections = ++connections;
    }

    public int getConnections() {
        return connections;
    }

    public void run() {
        ObjectInputStream sois = null;
        ObjectOutputStream soos = null;
        try {
            System.out.println("connection established....");
            System.out.println("Amount of connections = " + connections);
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());
            Initialization(sois, soos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sois.close();
                soos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Initialization(ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            int choice = 1;
            while (choice != 0) {
                choice = (int) sois.readObject();
                switch (choice) {
                    case 1:
                        //authorisation
                        Authorisation(sois, soos);
                        break;
                    case 2:
                        //registration
                        Registration(sois, soos);
                        break;
                    default: {
                        System.out.println("Exit confirmed.");
                        System.out.println("Amount of connections = " + --Main.connections);
                        clientAccepted.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Authorisation(ObjectInputStream sois, ObjectOutputStream soos) {
        String login, password;

        try {

            UsersService usersService = new UsersService();
            int role = -1; //1 - pat, 2 - doc, 3 - admin, 0 - аккаунта нет
            //do {
            Users userReceived = (Users) sois.readObject();
            System.out.println(userReceived);
            Users user = usersService.findIDByLoginNPassword(userReceived.getLogin(), userReceived.getPassword());
            if (user != null) {
            Admin admin = usersService.findAdminByUserId(user.getId());
            if (admin != null) {
                //админ
                role = 3;
                soos.writeObject(role);
                soos.writeObject(admin);
                adminMenu(admin,sois,soos);
                return;}
            else {

                        //USER!
                        role = 1;
                        soos.writeObject(role);
                        soos.writeObject(user);
                        UsersMenu(user, sois, soos);
                        return;
                    }
                }

            //Ошибка авторизации, или такого аккаунта нет
            role = 0;
            soos.writeObject(role);

        return;
        //}while (role == 0);
    } catch(IOException |
    ClassNotFoundException e)

    {
        throw new RuntimeException(e);
    }

}
    public void Registration (ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            UsersService usersService = new UsersService();
           //PatientService patientService = new PatientService();
            Users users = new Users();
            users = (Users) sois.readObject();
            String login = users.getLogin();
            String message;
            if (usersService.findIDByLogin(login) == null){
                usersService.saveEntity(users);
                message = "success";
                soos.writeObject(message);
            }
            else{
                message = "failed";
                soos.writeObject(message);
            }
        } catch (IOException | ClassNotFoundException e) {//| ClassNotFoundException ??????????
            throw new RuntimeException(e);
        }

    }
    public void adminMenu(Admin admin, ObjectInputStream sois, ObjectOutputStream soos){
        try {
            AdminService adminService = new AdminService();
            UsersService userService = new UsersService();
            CompanyService companyService = new CompanyService();
            EmployeesService employeesService = new EmployeesService();
            FinIndicatorsService finIndicatorsService = new FinIndicatorsService();
            FinReportsService finReportsService = new FinReportsService();
            InfoService infoService = new InfoService();
            ProductsAndServiceService productsAndServiceService = new ProductsAndServiceService();
            AdminMenu menu = new AdminMenu();
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
                    case "admins":{
                        List<Admin> admins = adminService.findAllAdmins();
                        soos.writeObject(admins);
                        menu.admins(admin, sois, soos, admins);
                        break;
                    }
                    case "finind":{
                        List<FinIndicators> finIndicators = finIndicatorsService.findAllEntities();
                        soos.writeObject(finIndicators);
                        menu.finind(admin, sois, soos, finIndicators);
                        break;
                    }
                    case "finrep":{
                        List<FinReports> finReports = finReportsService.findAllEntities();
                        soos.writeObject(finReports);
                        menu.finrep(admin, sois, soos, finReports);
                        break;
                    }
                    case "comp":{
                        List<Company> companies = companyService.findAllEntities();
                        soos.writeObject(companies);
                        menu.comp(admin, sois, soos, companies);
                        break;
                    }
                    case "pns":{
                        List<ProductsAndService> productsAndServices = productsAndServiceService.findAllEntities();
                        soos.writeObject(productsAndServices);
                        menu.pns(admin, sois, soos, productsAndServices);
                        break;
                    }
                    case "info":{
                        List<Info> infos = infoService.findAllEntities();
                        soos.writeObject(infos);
                        menu.Info(admin, sois, soos, infos);
                        break;
                    }
                    case "emp":{
                        List<Employees> employees = employeesService.findAllEntities();
                        soos.writeObject(employees);
                        menu.Employees(admin, sois, soos, employees);
                        break;
                    }
//                    case "comp":{
//                        List<Company> companies = companyService.findAllEntities();
//                        System.out.println(companies);
//                        soos.writeObject(companies);
//                        menu.companies(admin, sois, soos, companies);
//                        break;
//                    }
//                    case "patients":{
//                        List<Patient> patients = patientService.findAllPatients();
//                        soos.writeObject(patients);
//                        menu.patients(admin, sois, soos, patients);
//                        break;
//                    }
//                    case "tickets":{
//                        List<Ticket> tickets = ticketService.findAllTickets();
//                        soos.writeObject(tickets);
//                        menu.tickets(admin, sois, soos, tickets);
//                        break;
//                    }
                    case "users":{
                        List<Users> users = userService.findAllEntities();
                        soos.writeObject(users);
                        menu.users(admin, sois, soos, users);
                        break;
                    }
//                    case "diagrSexP":{
//                        soos.writeObject(patientService.findAllBySex("М"));
//                        soos.writeObject(patientService.findAllBySex("Ж"));
//                        break;
//                    }
//                    case "diagrSpecDocs":{
//                        List<Doctor> doctors = doctorService.findAllDoctors();
//                        soos.writeObject(doctors);
//                        break;
//                    }
//                    case "diagrDocTickets":{
//                        List<Doctor> doctors = doctorService.findAllDoctors();
//                        List<Ticket> tickets = ticketService.findAllTaken();
//                        soos.writeObject(doctors);
//                        soos.writeObject(tickets);
//                        break;
//                    }
                    case "diagrsalpay":{
                        List<Employees> employees = employeesService.findAllEntities();
                        soos.writeObject(employees);
                        break;
                    }
                    case "diagrcomprent":{
                        List<FinIndicators> finIndicators = finIndicatorsService.findAllEntities();
                        soos.writeObject(finIndicators);
                        break;
                    }
                    case "diagrliq":{
                        List<FinIndicators> finIndicators = finIndicatorsService.findAllEntities();
                        soos.writeObject(finIndicators);
                        break;
                    }
                    case "diagrpns":{
                        List<ProductsAndService> pns = productsAndServiceService.findAllEntities();
                        soos.writeObject(pns);
                        break;
                    }
                    case "diagrUsers":{
                        List<Admin> admins = adminService.findAllAdmins();
                        System.out.println(admins.size());
                        soos.writeObject(admins.size());
                        List<Users> users = userService.findAllEntities();
                        soos.writeObject(users.size());
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
    public void UsersMenu(Users users, ObjectInputStream sois, ObjectOutputStream soos){
        try {
            String menuButton;
            do{
                menuButton = (String) sois.readObject();
                switch(menuButton){
                    case "saveMe":{
                        Users newuser = (Users) sois.readObject();
                        UsersService usersService = new UsersService();
                        users = newuser;
                        usersService.updateEntity(users);
                        break;
                    }
                    case "ticketMenu":{//rename to FinIndicatorsMenu !!!!!!!!!!!!!!!!!!
                        //and add for all the entities in project the same ones
                        CompaniesMenu(sois, soos);
                        break;
                    }
                    case "info":{
                        List<Info> infoList = new ArrayList<>();
                        InfoService infoService = new InfoService();
                        infoList = infoService.findAllEntities();
                        System.out.println(infoList.size());
                        soos.writeObject(infoList);
                        break;
                    }
                    case "employees":{
                        List<Employees> employeesList = new ArrayList<>();
                        EmployeesService employeesService = new EmployeesService();
                        employeesList = employeesService.findAllEntities();
                        System.out.println(employeesList.size());
                        soos.writeObject(employeesList);
                        break;
                    }
                    case "finind":{
                        List<FinIndicators> finIndicatorsList = new ArrayList<>();
                        FinIndicatorsService finIndicatorsService = new FinIndicatorsService();
                        finIndicatorsList = finIndicatorsService.findAllEntities();
                        System.out.println(finIndicatorsList.size());
                        soos.writeObject(finIndicatorsList);
                        break;
                    }
                    case "finrep":{
                        List<FinReports> finReportsList = new ArrayList<>();
                        FinReportsService finReportsService = new FinReportsService();
                        finReportsList = finReportsService.findAllEntities();
                        System.out.println(finReportsList.size());
                        soos.writeObject(finReportsList);
                        break;
                    }
                    case "pns":{
                        List<ProductsAndService> productsAndServices = new ArrayList<>();
                        ProductsAndServiceService productsAndServiceService = new ProductsAndServiceService();
                        productsAndServices = productsAndServiceService.findAllEntities();
                        System.out.println(productsAndServices.size());
                        soos.writeObject(productsAndServices);
                        break;
                    }
                    case "scheduleDocs":{
                        CompanyService companyService = new CompanyService();
                        soos.writeObject(companyService.findAllEntities());
                        break;
                    }
                    case "getcompanydata":{
                        List<Company> companies = new ArrayList<>();
                        CompanyService companyService = new CompanyService();
                        companies = companyService.findAllEntities();
                        soos.writeObject(companies);
                        break;
                    }
                    case "readCompanies":{//del
                        List<Company> companies = new ArrayList<>();
                        CompanyService companyService = new CompanyService();
                        companies = companyService.findAllEntities();
                        readCompanies(sois, soos,companies);
                        break;
                   }


                    case "deleteMe":{

                        UsersService usersService = new UsersService();
                        usersService.deleteEntity(users);
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
    public void doctorMenu(){

    }


    public void CompaniesMenu(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException {
        CompanyService companyService = new CompanyService();
        List<Company> companies = companyService.findAllEntities();
        soos.writeObject(companies);
        String menuButton;
        do {
            menuButton = (String) sois.readObject();
            switch (menuButton){
                case "exit":{
                    return;
                }
            }
        }
        while(menuButton != "exit");
    }
    public void readCompanies(ObjectInputStream sois, ObjectOutputStream soos,List<Company> companies) throws IOException, ClassNotFoundException {
      CompanyService companyService = new CompanyService();
        //List<Ticket> tickets = ticketService.findAllTakenByPat(patient.getId());
        companies = companyService.findAllEntities();
        soos.writeObject(companies);
        String menuButton;
        do {
            menuButton = (String) sois.readObject();
            switch (menuButton){

                case "exit":{
                    return;
                }
            }
        }
        while(menuButton != "exit");
    }
}


