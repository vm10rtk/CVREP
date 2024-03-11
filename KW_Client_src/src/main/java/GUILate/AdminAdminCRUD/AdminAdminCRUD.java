package GUILate.AdminAdminCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.AdmnCRUD.AdminCRUD;
import GUILate.LoginWindow;
import models.Admin;

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

public class AdminAdminCRUD extends JFrame{
    private JPanel adminCRUDPanel;
    private JTable table;
    private JButton addButton;
    private JButton delButton;
    private JButton redButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private List<Admin> admins;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminAdminCRUD(String name, Admin admin, ObjectOutputStream coos,
                          ObjectInputStream cois, Socket socket, List<Admin> admins) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.admins = admins;
        setContentPane(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(admins);
        button1.addActionListener(new addButtonActionListener());
        button3.addActionListener(new redButtonActionListener());
        button2.addActionListener(new delButtonActionListener());
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
    public void showData(List<Admin> admins) {
        Object[] columnTitle = new Object[]{"Логин",
                "Право на редактирование пользователя", "Право на удаление пользователя",
                "Право на редактирование администратора", "Право на удаление администратора"};
        Object[][] objs = new Object[admins.size()][5];
        for (int i = 0; i < admins.size(); i++) {
            objs[i][0] = admins.get(i).getUser().getLogin();
            objs[i][1] = admins.get(i).getRightsToRedactUsers();
            objs[i][2] = admins.get(i).getRightsToDeleteUsers();
            objs[i][3] = admins.get(i).getRightsToRedactAdmins();
            objs[i][4] = admins.get(i).getRightsToDeleteAdmins();
        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table1.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AdminCRUD window = new AdminCRUD("Добавление администратора",
                    admin, coos, cois, clientSocket, admins, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table1.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                AdminCRUD window = new AdminCRUD("Редактирование администратора",
                        admin, coos, cois, clientSocket, admins, table1.getSelectedRow());
                dispose();
            }
        }
    }
    class delButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table1.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog((Component)null, "Выберите строку.");
            } else if(admins.get(table1.getSelectedRow()).getId() == 1) {
                JOptionPane.showMessageDialog((Component)null, "Невозможно удалить этого администратора.");
            }
            else {
                if(!admin.getRightsToDeleteAdmins()){
                    JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
                }
                else {
                    try {
                        int row = table1.getSelectedRow();
                        Admin admin1 = admins.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(admin1);
                        tableModel.removeRow(row);
                        admins.remove(row);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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

