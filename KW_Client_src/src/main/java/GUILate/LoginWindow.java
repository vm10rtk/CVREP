package GUILate;

import GUILate.AdminWindow.AdminWindow;
import models.Admin;
import models.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginWindow extends JFrame{
    private JTextField loginField;
    private JButton registrationButton;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPasswordField passwordField1;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;

    public LoginWindow(String name, ObjectOutputStream coos, ObjectInputStream cois, Socket socket){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.clientSocket = socket;
        setContentPane(loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        registrationButton.addActionListener(new ButtonActionListener());
        loginButton.addActionListener(new ButtonActionListener());
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try{
                    int result = JOptionPane.showConfirmDialog(null, "Вы хотите завершить работу программы?");
                    if (result == JOptionPane.YES_OPTION) {
                        coos.writeObject(0);
                        coos.close();
                        cois.close();
                        clientSocket.close();
                        dispose();
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    class ButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == loginButton){
                try{
                    //Авторизация
                    coos.writeObject(1);
                    //1 - пользователь,  3 - админ, 0 - аккаунта нет
                    int role = 0;
                    //do {
                        Users user = new Users(loginField.getText(),
                                passwordField1.getText());
                        coos.writeObject(user);
                        role = (int) cois.readObject();
                        if (role == 1) {
                            Users users = (Users) cois.readObject();
                            UsersWindow patientWindow = new UsersWindow("Окно пользователя",
                                    users, coos, cois, clientSocket);
                            dispose();
                        } else if (role == 3) {
                            Admin admin = (Admin) cois.readObject();
                            AdminWindow adminWindow = new AdminWindow("Окно администратора", admin, coos, cois, clientSocket);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Такого аккаунта не существует.");
                        }
                    //}while (role == 0);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else if (e.getSource() == registrationButton){
                try{
                    //Регистрация
                    setVisible(false);
                    Registration reg = new Registration("Регистрация", coos, cois, clientSocket);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
