package GUILate.AdminCompanyCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.AdmnCRUD.AdminCRUD;
import GUILate.CompanyCRUDForAdmin.CompanyCRUDForAdmin;
import GUILate.LoginWindow;
import models.Admin;
import models.Company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AdminCompanyCRUD extends JFrame{
    private JPanel adminCRUDPanel;
    private JTable table;
    private JButton addButton;
    private JButton delButton;
    private JButton redButton;
    private JPanel panel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private List<Company> companies;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminCompanyCRUD(String name, Admin admin, ObjectOutputStream coos,
                          ObjectInputStream cois, Socket socket, List<Company> companies) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.companies = companies;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(companies);
        addButton.addActionListener(new GUILate.AdminCompanyCRUD.AdminCompanyCRUD.addButtonActionListener());
        redButton.addActionListener(new GUILate.AdminCompanyCRUD.AdminCompanyCRUD.redButtonActionListener());
        delButton.addActionListener(new GUILate.AdminCompanyCRUD.AdminCompanyCRUD.delButtonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    coos.writeObject("exit");
                    checkIsExisting();
                    AdminWindow adminWindow = new AdminWindow("Окно администратора",
                            admin, coos, cois, clientSocket);
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void showData(List<Company> companies) {
        Object[] columnTitle = new Object[]{"Название"};
        Object[][] objs = new Object[companies.size()][1];
        for (int i = 0; i < companies.size(); i++) {
            objs[i][0] = companies.get(i).getName();
        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CompanyCRUDForAdmin window = new CompanyCRUDForAdmin("Добавление компании",
                    admin, coos, cois, clientSocket, companies, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                CompanyCRUDForAdmin window = new CompanyCRUDForAdmin("Редактирование акомпании",
                        admin, coos, cois, clientSocket, companies, table.getSelectedRow());
                dispose();
            }
        }
    }
    class delButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog((Component)null, "Выберите строку.");
            }
            else {
                    try {
                        int row = table.getSelectedRow();
                        Company company = companies.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(company);
                        tableModel.removeRow(row);
                        companies.remove(row);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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


