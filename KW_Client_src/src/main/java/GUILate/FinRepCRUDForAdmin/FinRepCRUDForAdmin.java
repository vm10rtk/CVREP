package GUILate.FinRepCRUDForAdmin;

import GUILate.AdminAdminCRUD.AdminAdminCRUD;
import GUILate.AdminFinIndCRUD.AdminFinIndCRUD;
import GUILate.AdminFinRepCRUD.AdminFinRepCRUD;
import GUILate.LoginWindow;
import models.*;
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

public class FinRepCRUDForAdmin extends JFrame {

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
    private JTextField textField3;
    private JLabel ActivityLabel;

    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private int redactId;
    private JFrame frame;
    private List<FinReports> finReports;

    private List<Company> companies;

    public FinRepCRUDForAdmin(String name, Admin admin, ObjectOutputStream coos,
                              ObjectInputStream cois, Socket socket,
                              List<FinReports> finReports, int redactId) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.redactId = redactId;
        this.finReports = finReports;
        this.clientSocket = socket;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        button.addActionListener(new buttonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AdminFinRepCRUD window = new AdminFinRepCRUD("Отчеты", admin, coos, cois, clientSocket, finReports);
                dispose();
            }
        });
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                FinReports adminNew = new FinReports();
                if(redactId != 0){
                    if(!admin.getRightsToRedactAdmins()){
                        JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
                        dispose();
                    }
                    adminNew = finReports.get(redactId);
                    coos.writeObject("red");
                }
                else{
                    coos.writeObject("add");
                }
                checkIsExisting();
                Company company = new Company();
                company.setCompany_id(Integer.parseInt(IdTextField.getText()));
                adminNew.setCompany(company);

                adminNew.setYear(yearField.getText());
                adminNew.setReporttype(textField1.getText());
                adminNew.setReportdate(textField2.getText());
                coos.writeObject(adminNew);
                if(redactId != 0){
                    finReports.set(redactId, adminNew);
                }
                else{
                    String mes = (String) cois.readObject();
                    if (mes.equals("success")) finReports.add(adminNew);
                    else JOptionPane.showMessageDialog(null, "Такой логин уже занят.");
                }
                dispose();
                AdminFinRepCRUD window = new AdminFinRepCRUD("Отчеты", admin, coos, cois, clientSocket, finReports);
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
