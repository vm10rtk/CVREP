package GUILate.AdminInfoCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.FinRepCRUDForAdmin.FinRepCRUDForAdmin;
import GUILate.InfoCRUDForAdmin.InfoCRUDForAdmin;
import GUILate.LoginWindow;
import models.Admin;
import models.FinReports;
import models.Info;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;





public class AdminInfoCRUD extends JFrame {
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
    private List<Info> infos;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminInfoCRUD(String name, Admin admin, ObjectOutputStream coos,
                         ObjectInputStream cois, Socket socket, List<Info> infos) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.infos = infos;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(infos);
        addButton.addActionListener(new addButtonActionListener());
        redButton.addActionListener(new redButtonActionListener());
        delButton.addActionListener(new delButtonActionListener());
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
    public void showData(List<Info> infos) {
        Object[] columnTitle = new Object[]{"ID Компании","Название",
                "Адрес", "Телефон",
                "Почта","Вид деятельности" };
        Object[][] objs = new Object[infos.size()][6];
        for (int i = 0; i < infos.size(); i++) {
            objs[i][0] = infos.get(i).getCompany().getCompany_id();
            objs[i][1] = infos.get(i).getCompany().getName();
            objs[i][2] = infos.get(i).getAddress();
            objs[i][3] = infos.get(i).getPhone();
            objs[i][4] = infos.get(i).getEmail();
            objs[i][5] = infos.get(i).getActivity();
        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            InfoCRUDForAdmin window = new InfoCRUDForAdmin("Добавление информации о компании",
                    admin, coos, cois, clientSocket, infos, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                InfoCRUDForAdmin window = new InfoCRUDForAdmin("Редактирование ",
                        admin, coos, cois, clientSocket, infos, table.getSelectedRow());
                dispose();
            }
        }
    }
    class delButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            if (table.getSelectedRows().length == 0) {
//                JOptionPane.showMessageDialog((Component)null, "Выберите строку.");
//            } else if(finIndicators.get(table1.getSelectedRow()).getId() == 1) {
//                JOptionPane.showMessageDialog((Component)null, "Невозможно удалить этого администратора.");
//            }
//            else {
//                if(!admin.getRightsToDeleteAdmins()){
//                    JOptionPane.showMessageDialog(null, "У вас нет прав на это.");
//                }
//                else {
                    try {
                        int row = table.getSelectedRow();
                        Info info = infos.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(info);
                        tableModel.removeRow(row);
                        infos.remove(row);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
     //   }
  //  }
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

