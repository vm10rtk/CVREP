package GUILate;

import models.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Registration extends JFrame{

    private JTextField textFieldLogin;
    private JPasswordField textFieldPassword;
    private JButton registrationButton;
    private JPanel registrationPanel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;

    Registration(String name, ObjectOutputStream coos, ObjectInputStream cois, Socket socket){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.clientSocket = socket;
        setContentPane(registrationPanel);
        setSize(400, 400);

        setLocationRelativeTo(null);
        setVisible(true);
        registrationButton.addActionListener(new ButtonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try{
                    LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
                //проверка на корректный ввод всех полей
                try{
                    coos.writeObject(2);
                    Users user = new Users(textFieldLogin.getText(),
                            textFieldPassword.getText());
                    System.out.println(user.getLogin()+user.getPassword());
                    coos.writeObject(user);
                    String ans = (String) cois.readObject();
                    if (ans.equals("success")){
                        JOptionPane.showMessageDialog(null, "Вы были успешно зарегистрированы!");
                        LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Такой логин уже существует.");
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
        }
    }
}
