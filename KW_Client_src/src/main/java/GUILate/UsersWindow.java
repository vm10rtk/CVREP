package GUILate;


import GUILate.InfoTable.InfoTable;
import models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UsersWindow extends JFrame{
    private JButton crudMeButton;
    private JButton InfoButton;
    private JButton myMedCardButton;
    private JLabel textWelcome;
    private JPanel userPanel;
    private JButton viewEmployeesButton;
    private JButton ViewCompanyButton;
    private JButton FinRepButton;
    private JButton FinIndButton;
    private JButton PnSButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Users users;
    private Company company;
    private List<Company> companies;

    public UsersWindow(String name, Users users, ObjectOutputStream coos, ObjectInputStream cois, Socket socket){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.clientSocket = socket;
        this.users = users;
        setContentPane(userPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        crudMeButton.addActionListener(new crudMeButtonActionListener());
        InfoButton.addActionListener((new InfoButtonListener()));
        ViewCompanyButton.addActionListener(new ViewComp());
        FinRepButton.addActionListener(new FinRepButtonListener());
        FinIndButton.addActionListener(new FinIndButtonListener());
        //myMedCardButton.addActionListener(new myMedCardButtonActionListener());
        viewEmployeesButton.addActionListener(new EmployeesButtonListener());
        PnSButton.addActionListener(new PnSButtonListener());
        textWelcome.setText("\tДобро пожаловать, " + users.getLogin() + "!");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите выйти из аккаунта?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);
                    try {
                        coos.writeObject("exit");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
//        ViewCompanyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
    }
//    class ViewCompanyActionListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                coos.writeObject("viewCompanies");
//                dispose();
//                //companies = (List<Company>)cois.readObject();
//               CompanyTable companyTable = new CompanyTable("Просмотр списка компаний",getFrame(),companies,true,coos,cois,company);
//
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            } catch (ClassNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//
//    }
    class ViewComp implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                coos.writeObject("scheduleDocs");
                List<Company> companyList;
                companyList = (List<Company>) cois.readObject();
                CompanyTable doctorSchedule = new CompanyTable("Наименования компаний", getFrame(),
                        companyList, coos, cois);
                setEnabled(false);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class crudMeButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            dispose();
                PatientCRUD patientCRUD = new PatientCRUD("Изменение данных",
                        users, coos, cois, clientSocket);

        }
    }
    class InfoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                coos.writeObject("info");
                List<Info> infoList;
                infoList= (List<Info>) cois.readObject();
                System.out.println(infoList.size());
                InfoTable infotable = new InfoTable("Просмотр информации",
                        getFrame(),infoList,true, coos, cois);
                setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    class EmployeesButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                coos.writeObject("employees");
                List<Employees> employeesList;
                employeesList= (List<Employees>) cois.readObject();
                System.out.println(employeesList.size());
                EmployeesTable infotable = new EmployeesTable("Просмотр информации о сотрудниках",
                        getFrame(),employeesList,true, coos, cois);
                setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    class FinIndButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                coos.writeObject("finind");
                List<FinIndicators> finIndicatorsList;
                finIndicatorsList= (List<FinIndicators>) cois.readObject();
                System.out.println(finIndicatorsList.size());
                FinIndTable infotable = new FinIndTable("Просмотр информации о финансовых показателях",
                        getFrame(),finIndicatorsList,true, coos, cois);
                setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    class FinRepButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                coos.writeObject("finrep");
                List<FinReports> finReportsList;
                finReportsList= (List<FinReports>) cois.readObject();
                System.out.println(finReportsList.size());
                FinRepTable infotable = new FinRepTable("Просмотр информации о финансовых отчетах",
                        getFrame(),finReportsList,true, coos, cois);
                setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    class PnSButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                coos.writeObject("pns");
                List<ProductsAndService> productsAndServices;
                productsAndServices= (List<ProductsAndService>) cois.readObject();
                System.out.println(productsAndServices.size());
                PnSTable infotable = new PnSTable("Просмотр информации о продуктах/услугах компаний",
                        getFrame(),productsAndServices,true, coos, cois);
                setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    private JFrame getFrame(){
        return this;
    }




    class statsButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                coos.writeObject("stats");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class myMedCardButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                coos.writeObject("medCard");
                List<Record> records;
                records = (List<Record>) cois.readObject();
//                MedCardTable medCardTable = new MedCardTable("Медицинская карта", getFrame(),
//                        records, true, coos, cois, patient);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
