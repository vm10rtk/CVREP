package GUILate.AdminUserCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.LoginWindow;
import GUILate.UserCRUDForAdmin.UserCRUDForAdmin;
import models.Admin;
import models.Users;
import models.Users;

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

public class AdminUserCRUD extends JFrame{
    private JTable table;
    private JButton button;
    private JPanel panel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private List<Users> users;
    private DefaultTableModel tableModel;

    public AdminUserCRUD(String name, Admin admin, ObjectOutputStream coos,
                           ObjectInputStream cois, Socket socket, List<Users> users){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.users = users;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        System.out.println(users);
        showData(users);

        button.addActionListener(new buttonActionListener());
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
    public void showData(List<Users> users) {
        System.out.println(users);
        Object[] columnTitle = new Object[]{"ID", "Логин", "Пароль (hash)"};
        Object[][] objs = new Object[users.size()][3];
        for (int i = 0; i < users.size(); i++) {
            objs[i][0] = users.get(i).getId();
            objs[i][1] = users.get(i).getLogin();
            objs[i][2] = users.get(i).getPassword();
        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog((Component) null, "Выберите строку.");
            }
            else {
                dispose();
                UserCRUDForAdmin window = new UserCRUDForAdmin("Редактирование пользователя",
                        admin, coos, cois, clientSocket, users, table.getSelectedRow());
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
    private JFrame getFrame(){
        return this;
    }
}
