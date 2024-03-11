package GUILate.CompanyCRUDForAdmin;

import GUILate.AdminCompanyCRUD.AdminCompanyCRUD;
import GUILate.AdminFinRepCRUD.AdminFinRepCRUD;
import GUILate.FinRepCRUDForAdmin.FinRepCRUDForAdmin;
import GUILate.LoginWindow;
import GUILate.PatientCRUD;
import models.Admin;
import models.Company;
import models.FinReports;
import models.Users;

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

public class CompanyCRUDForAdmin extends JFrame{
    private JPanel panel;

    private JButton button;
    private JTextField phoneText;
    private JLabel phoneLabel;

    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private int redactId;
    private List<Company> companies;
    public CompanyCRUDForAdmin(String name, Admin admin, ObjectOutputStream coos,
                               ObjectInputStream cois, Socket socket,
                               List<Company> companies, int redactId){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.redactId = redactId;
        this.companies = companies;
//        if(redactId != 0) {
//            redPatCheckBox.setSelected(admins.get(redactId).getRightsToRedactUsers());
//            delPatCheckBox.setSelected(admins.get(redactId).getRightsToDeleteUsers());
//            redAdmCheckBox.setSelected(admins.get(redactId).getRightsToRedactAdmins());
//            delDocCheckBox.setSelected(admins.get(redactId).getRightsToDeleteAdmins());
//            loginLabel.setVisible(false);
//            passwLabel.setVisible(false);
//            loginTextField.setVisible(false);
//            passwordTextField.setVisible(false);
//        }

        this.clientSocket = socket;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        button.addActionListener(new buttonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AdminCompanyCRUD window = new AdminCompanyCRUD("Компании", admin, coos, cois, clientSocket, companies);
                dispose();
            }
        });
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Company adminNew = new Company();
                if(redactId != 0){
                    if(!admin.getRightsToRedactAdmins()){
                        JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
                        dispose();
                    }
                    adminNew = companies.get(redactId);
                    coos.writeObject("red");
                }
                else{
                    coos.writeObject("add");
                }
                checkIsExisting();
//                if(admin.getRightsToRedactUsers()) adminNew.setRightsToRedactUsers(redPatCheckBox.isSelected());
//                else adminNew.setRightsToRedactUsers(false);
//                if(admin.getRightsToDeleteUsers()) adminNew.setRightsToDeleteUsers(delPatCheckBox.isSelected());
//                else adminNew.setRightsToDeleteUsers(false);
////                if(admin.getRightsToRedactDoctors())  adminNew.setRightsToRedactDoctors(redDocCheckBox.isSelected());
////                else adminNew.setRightsToRedactDoctors(false);
////                if(admin.getRightsToDeleteDoctors()) adminNew.setRightsToDeleteDoctors(delDocCheckBox.isSelected());
////                else adminNew.setRightsToDeleteDoctors(false);
//                if(admin.getRightsToRedactAdmins()) adminNew.setRightsToRedactAdmins(redAdmCheckBox.isSelected());
//                else adminNew.setRightsToRedactAdmins(false);
//                if(admin.getRightsToDeleteAdmins()) adminNew.setRightsToDeleteAdmins(delAdmCheckBox.isSelected());
//                else adminNew.setRightsToDeleteAdmins(false);
//                if(redactId == 0){
//                    adminNew.setUser(new Users(loginTextField.getText(),
//                            passwordTextField.getText()));
//                }
//                coos.writeObject(IdTextField.getText());
//                Company newcomp = (Company) cois.readObject();


                adminNew.setName(phoneText.getText());

                coos.writeObject(adminNew);
                if(redactId != 0){
                    companies.set(redactId, adminNew);
                }
                else{
                    String mes = (String) cois.readObject();
                    if (mes.equals("success")) companies.add(adminNew);
                    else JOptionPane.showMessageDialog(null, "Такой логин уже занят.");
                }
                dispose();
                AdminCompanyCRUD window = new AdminCompanyCRUD("Компании", admin, coos, cois, clientSocket, companies);
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
            LoginWindow loginWindow = new LoginWindow("Авторизация", coos, cois, clientSocket);
        }
    }
}
