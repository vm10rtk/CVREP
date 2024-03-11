package GUILate.PnSCRUDForAdmin;

import GUILate.AdminInfoCRUD.AdminInfoCRUD;
import GUILate.AdminPnSCRUD.AdminPnSCRUD;
import GUILate.LoginWindow;
import models.Admin;
import models.Company;
import models.Info;
import models.ProductsAndService;

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

public class PnSCRUDForAdmin extends JFrame {

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
    private JLabel ActivityLabel;
    private JTextField textField3;

    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private int redactId;
    private JFrame frame;
    private List<ProductsAndService> productsAndServices;

    private List<Company> companies;

    public PnSCRUDForAdmin(String name, Admin admin, ObjectOutputStream coos,
                           ObjectInputStream cois, Socket socket,
                           List<ProductsAndService> productsAndServices, int redactId) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.redactId = redactId;
        this.productsAndServices = productsAndServices;
        this.clientSocket = socket;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        button.addActionListener(new buttonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AdminPnSCRUD window = new AdminPnSCRUD("Информация", admin, coos, cois, clientSocket, productsAndServices);
                dispose();
            }
        });
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ProductsAndService adminNew = new ProductsAndService();
                if(redactId != 0){
                    if(!admin.getRightsToRedactAdmins()){
                        JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
                        dispose();
                    }
                    adminNew = productsAndServices.get(redactId);
                    coos.writeObject("red");
                }
                else{
                    coos.writeObject("add");
                }
                checkIsExisting();
                Company company = new Company();
                company.setCompany_id(Integer.parseInt(IdTextField.getText()));
                adminNew.setCompany(company);

                adminNew.setName(yearField.getText());
                adminNew.setDescription(textField1.getText());
                adminNew.setPrice(Double.parseDouble(textField2.getText()));
                adminNew.setSoldcount(Integer.parseInt(textField3.getText()));
                coos.writeObject(adminNew);
                if(redactId != 0){
                    productsAndServices.set(redactId, adminNew);
                }
                else{
                    String mes = (String) cois.readObject();
                    if (mes.equals("success")) productsAndServices.add(adminNew);
                    else JOptionPane.showMessageDialog(null, "Такой логин уже занят.");
                }
                dispose();
                AdminPnSCRUD window = new AdminPnSCRUD("Информация", admin, coos, cois, clientSocket, productsAndServices);
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
