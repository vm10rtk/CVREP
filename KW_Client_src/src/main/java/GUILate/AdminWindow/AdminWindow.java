package GUILate.AdminWindow;

import GUILate.Diagaramcomprent.Diagramrent;
import GUILate.Diagramliq.Diagramliq;
import GUILate.Diagrampns.Diagrampns;
import GUILate.Diagramsalpay.Diagramsalpay;
import GUILate.RolesDiagram.RolesDiagram;

import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;

//import Diagrams.*;
import GUILate.AdminAdminCRUD.AdminAdminCRUD;
import GUILate.AdminCompanyCRUD.AdminCompanyCRUD;
import GUILate.AdminEmployeesCRUD.AdminEmployessCRUD;
import GUILate.AdminFinIndCRUD.AdminFinIndCRUD;
import GUILate.AdminFinRepCRUD.AdminFinRepCRUD;
import GUILate.AdminInfoCRUD.AdminInfoCRUD;
import GUILate.AdminPnSCRUD.AdminPnSCRUD;
import GUILate.AdminUserCRUD.AdminUserCRUD;
import GUILate.LoginWindow;

import models.*;
//import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AdminWindow extends JFrame{
    private JButton adminsButton;
    private JButton compButton;
    private JButton indicatorsButton;
    private JButton ticketsButton;
    private JButton recordsButton;
    private JPanel adminPanel;
    private JLabel text;
    private JButton usersButton;
    private JButton diagramSpecialButton;
    private JButton diagramLicvButton;
    private JButton diagramPribilButton;
    private JButton diagramUsersNRolesButton;
    private JLabel txt;
    private JButton EmployeesButton;
    private JButton PNSButton;
    private JButton reportsButton;
    private JButton salButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;

    public AdminWindow(String name, Admin admin, ObjectOutputStream coos,
                       ObjectInputStream cois, Socket socket){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        setContentPane(adminPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        adminsButton.addActionListener(new adminsButtonActionListener());
        indicatorsButton.addActionListener(new finindButtonActionListener());
        compButton.addActionListener(new CompanyButtonActionListener());
reportsButton.addActionListener(new finrepButtonActionListener());
        usersButton.addActionListener(new usersButtonActionListener());
    EmployeesButton.addActionListener(new EmployeesButtonActionListener());
recordsButton.addActionListener(new InfoButtonActionListener());
PNSButton.addActionListener(new PnSButtonActionListener());
diagramUsersNRolesButton.addActionListener((new diagramUsersNRolesButtonActionListener()));
diagramSpecialButton.addActionListener(new diagrampnsButtonActionListener());
diagramPribilButton.addActionListener(new diagramrentButtonActionListener());
salButton.addActionListener(new diagramsalpayButtonActionListener());
diagramLicvButton.addActionListener(new diagramliqButtonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите выйти из аккаунта?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    LoginWindow loginWindow = new LoginWindow("Авторизация", coos, cois, clientSocket);
                    try {
                        coos.writeObject("exit");
                        checkIsExisting();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
    class adminsButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("admins");
                checkIsExisting();
                List<Admin> admins = (List<Admin>) cois.readObject();
                AdminAdminCRUD window = new AdminAdminCRUD("Администраторы", admin, coos, cois, clientSocket, admins);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class finindButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("finind");
                checkIsExisting();
                List<FinIndicators> admins = (List<FinIndicators>) cois.readObject();
                AdminFinIndCRUD window = new AdminFinIndCRUD("Финансовые показатели", admin, coos, cois, clientSocket, admins);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class finrepButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("finrep");
                checkIsExisting();
                List<FinReports> admins = (List<FinReports>) cois.readObject();
                AdminFinRepCRUD window = new AdminFinRepCRUD("Финансовые отчеты", admin, coos, cois, clientSocket, admins);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class CompanyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("comp");
                checkIsExisting();

                List<Company> companies = (List<Company>) cois.readObject();
//                System.out.println(cois.readObject());
                AdminCompanyCRUD window = new AdminCompanyCRUD("Компании", admin, coos, cois, clientSocket, companies);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class EmployeesButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("emp");
                checkIsExisting();

                List<Employees> employees = (List<Employees>) cois.readObject();
//                System.out.println(cois.readObject());
                AdminEmployessCRUD window = new AdminEmployessCRUD("Сотрудники", admin, coos, cois, clientSocket, employees);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class PnSButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("pns");
                checkIsExisting();

                List<ProductsAndService> productsAndServices = (List<ProductsAndService>) cois.readObject();
//                System.out.println(cois.readObject());
                AdminPnSCRUD window = new AdminPnSCRUD("Продукты и услуги", admin, coos, cois, clientSocket, productsAndServices);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class InfoButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("info");
                checkIsExisting();

                List<Info> infos = (List<Info>) cois.readObject();
//                System.out.println(cois.readObject());
                AdminInfoCRUD window = new AdminInfoCRUD("Информация", admin, coos, cois, clientSocket, infos);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class usersButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("users");
                checkIsExisting();
                List<Users> users = (List<Users>) cois.readObject();
                AdminUserCRUD window = new AdminUserCRUD("Пользователи", admin, coos, cois, clientSocket, users);
                dispose();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
class diagramsalpayButtonActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            coos.writeObject("diagrsalpay");
            checkIsExisting();
            List<Employees> pns = (List<Employees>) cois.readObject();

            Diagramsalpay demo = new Diagramsalpay(
                    "Зарплаты сотрудников", pns, getFrame());
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
            setEnabled(false);

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
    class diagrampnsButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("diagrpns");
                checkIsExisting();
                List<ProductsAndService> pns = (List<ProductsAndService>) cois.readObject();

                Diagrampns demo = new Diagrampns(
                        "Доход от продажи товаров", pns, getFrame());
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                setEnabled(false);

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class diagramliqButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("diagrliq");
                checkIsExisting();
                List<FinIndicators> pns = (List<FinIndicators>) cois.readObject();

                Diagramliq demo = new Diagramliq(
                        "Показатель ликвидности в %", pns, getFrame());
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                setEnabled(false);

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class diagramrentButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("diagrcomprent");
                checkIsExisting();
                List<FinIndicators> pns = (List<FinIndicators>) cois.readObject();

                Diagramrent demo = new Diagramrent(
                        "Показатель ликвидности в %", pns, getFrame());
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                setEnabled(false);

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class diagramUsersNRolesButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("diagrUsers");
                checkIsExisting();

                int user = (int) cois.readObject();
                System.out.println(user);
                int adm = (int) cois.readObject();
                System.out.println(adm);
                RolesDiagram demo = new RolesDiagram("Диаграмма ролей", adm, user, getFrame());
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
                setEnabled(false);

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private void checkIsExisting(){
        String mes = null;
        try {
            mes = (String) cois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (mes.equals("bad")){
            JOptionPane.showMessageDialog(null, "Ваш аккаунт был удален третьим лицом.");
            dispose();
            LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);
        }
    }
    public JFrame getFrame(){ return this; }
}
