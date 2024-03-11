package GUILate;

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

public class PatientCRUD extends JFrame{

    private JButton userCRUDButton;
    private JButton deleteMeButton;

    private JPanel patientCRUDPanel;
    private JButton saveMeButton;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField textField1;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Users users;

    public PatientCRUD(String name, Users users, ObjectOutputStream coos, ObjectInputStream cois, Socket socket){
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.clientSocket = socket;
        this.users = users;
        setContentPane(patientCRUDPanel);


        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
       // userCRUDButton.addActionListener(new userCRUDButtonActionListener());
       saveMeButton.addActionListener(new saveMeButtonActionListener());
        deleteMeButton.addActionListener(new deleteMeButtonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                UsersWindow patientWindow = new UsersWindow("Окно пользователя", users, coos, cois, clientSocket);
            }
        });
    }
    class userCRUDButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            setEnabled(false);
            UserCRUD userCRUD = new UserCRUD("Изменение аккаунта", getFrame(),
                    users, coos, cois);
        }
    }
    class saveMeButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                coos.writeObject("saveMe");
                users.setLogin(textField1.getText());
                users.setPassword(passwordField2.getText().hashCode());
                coos.writeObject(users);
                dispose();
                UsersWindow patientWindow = new UsersWindow("Окно пользователя",
                        users, coos, cois, clientSocket);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class deleteMeButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить аккаунт? Данные будут утеряны навсегда.");
                if (result == JOptionPane.YES_OPTION) {
                    coos.writeObject("deleteMe");
                    LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);
                    dispose();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private JFrame getFrame(){
        return this;
    }
}
