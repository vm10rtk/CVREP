package GUILate.EmployeesCRUDForAdmin;

import GUILate.AdminEmployeesCRUD.AdminEmployessCRUD;
import GUILate.AdminFinIndCRUD.AdminFinIndCRUD;
import GUILate.LoginWindow;
import models.Admin;
import models.Company;
import models.Employees;
import models.FinIndicators;

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

public class EmployeesCRUDForAdmin extends JFrame {

    private JPanel panel;

    private JPanel panel2;
    private JLabel loginLabel;
    private JButton button;
    private JLabel IdLabel;
    private JTextField IdTextField;
    private JLabel YearLabel;
    private JTextField yearField;
    private JLabel expensesField;
    private JTextField textField1;
    private JLabel incomeField;
    private JTextField textField2;
    private JLabel dateLabel;
    private JTextField textField3;

    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private int redactId;
    private JFrame frame;
    private List<Employees> employees;

    private List<Company> companies;

    public EmployeesCRUDForAdmin(String name, Admin admin, ObjectOutputStream coos,
                                 ObjectInputStream cois, Socket socket,
                                 List<Employees> employees, int redactId) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.redactId = redactId;
        this.employees = employees;
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
                AdminEmployessCRUD window = new AdminEmployessCRUD("Сотрудники", admin, coos, cois, clientSocket, employees);
                dispose();
            }
        });
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Employees adminNew = new Employees();
                if(redactId != 0){
                    if(!admin.getRightsToRedactAdmins()){
                        JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
                        dispose();
                    }
                    adminNew = employees.get(redactId);
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
                Company company = new Company();
                company.setCompany_id(Integer.parseInt(IdTextField.getText()));
                adminNew.setCompany(company);

                adminNew.setFIO(yearField.getText());
                adminNew.setPost(textField1.getText());
                adminNew.setSalary(Double.parseDouble((textField2.getText())));
                adminNew.setHiredate(textField3.getText());

                coos.writeObject(adminNew);
                if(redactId != 0){
                    employees.set(redactId, adminNew);
                }
                else{
                    String mes = (String) cois.readObject();
                    if (mes.equals("success")) employees.add(adminNew);
                    else JOptionPane.showMessageDialog(null, "Такой логин уже занят.");
                }
                dispose();
                AdminEmployessCRUD window = new AdminEmployessCRUD("Сотрудники", admin, coos, cois, clientSocket, employees);
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
