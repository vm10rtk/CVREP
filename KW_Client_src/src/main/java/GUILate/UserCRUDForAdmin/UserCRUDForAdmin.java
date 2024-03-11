package GUILate.UserCRUDForAdmin;

import GUILate.AdminUserCRUD.AdminUserCRUD;
import GUILate.LoginWindow;
import GUILate.LoginWindow.*;
import models.Admin;
import models.Users;
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

public class UserCRUDForAdmin extends JFrame{
    private JTextField logTextField;
    private JTextField passwTextField;
    private JButton button;
    private JLabel text2;
    private JLabel text1;
    private JPanel panel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;
    private int redactId;
    private List<Users> users;

    public UserCRUDForAdmin(String name, Admin admin, ObjectOutputStream coos,
                            ObjectInputStream cois, Socket socket,
                            List<Users> users, int redactId){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.users = users;
        this.redactId = redactId;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        button.addActionListener(new buttonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AdminUserCRUD window = new AdminUserCRUD("Пользователи", admin, coos, cois, clientSocket, users);
                dispose();
            }
        });
    }
    class buttonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                //User oldUser = users.get(redactId);
                Users userNew = new Users();
                userNew.setId(users.get(redactId).getId());
                coos.writeObject("red");
                checkIsExisting();
                userNew.setLogin(logTextField.getText());
                if (!passwTextField.getText().isEmpty())
                    userNew.setPassword(passwTextField.getText().hashCode());
                coos.writeObject(userNew);
                String mes = (String) cois.readObject();
                if (mes.equals("success")) users.set(redactId, userNew);
                else{
                    JOptionPane.showMessageDialog(null, "Такой логин уже занят.");
                    //userNew = oldUser;
                    //users.set(redactId, oldUser);
                }
                dispose();
                AdminUserCRUD window = new AdminUserCRUD("Пользователи", admin, coos, cois, clientSocket, users);
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
