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

public class UserCRUD extends JFrame{
    private JTextField loginTextField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JButton saveButton;
    private JLabel login;
    private JLabel oldPassword;
    private JLabel newPassword;
    private JPanel userCRUDPanel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Users user;
    private JFrame frame;

    public UserCRUD(String name, JFrame frame, Users user, ObjectOutputStream coos, ObjectInputStream cois) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.user = user;
        this.frame = frame;
        setContentPane(userCRUDPanel);
        loginTextField.setText(user.getLogin());
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        saveButton.addActionListener(new saveButtonActionListener());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setEnabled(true);
            }
        });
    }
    class saveButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите сменить данные?");
            if (result == JOptionPane.YES_OPTION) {
                if (user.getPassword() == oldPasswordField.getText().hashCode() &&
                        user.getPassword() != newPasswordField.getText().hashCode()) {
                    Users newUser = new Users(loginTextField.getText(), newPasswordField.getText());
                    JOptionPane.showMessageDialog(null, "Данные успешно изменены!");
                    try {
                        coos.writeObject("crudLoginPassword");
                        coos.writeObject(newUser);
                        frame.setEnabled(true);
                        dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Ошибка. Перепроверьте пароли");
                }
            }
        }
    }
}
