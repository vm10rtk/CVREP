package GUILate.AdminFinIndCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.AdmnCRUD.AdminCRUD;
import GUILate.FinIndCRUDForAdmin.FinIndCRUDForAdmin;
import GUILate.LoginWindow;
import models.Admin;
import models.FinIndicators;

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





public class AdminFinIndCRUD extends JFrame {
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
    private List<FinIndicators> finIndicators;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminFinIndCRUD(String name, Admin admin, ObjectOutputStream coos,
                          ObjectInputStream cois, Socket socket, List<FinIndicators> finIndicators) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.finIndicators = finIndicators;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(finIndicators);
        addButton.addActionListener(new GUILate.AdminFinIndCRUD.AdminFinIndCRUD.addButtonActionListener());
        redButton.addActionListener(new GUILate.AdminFinIndCRUD.AdminFinIndCRUD.redButtonActionListener());
        delButton.addActionListener(new GUILate.AdminFinIndCRUD.AdminFinIndCRUD.delButtonActionListener());
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
    public void showData(List<FinIndicators> finIndicators) {
        Object[] columnTitle = new Object[]{"ID Компании",
                "Год учета показателей", "Расходы",
                "Доходы"};
        Object[][] objs = new Object[finIndicators.size()][4];
        for (int i = 0; i < finIndicators.size(); i++) {
            objs[i][0] = finIndicators.get(i).getCompany().getCompany_id();
            objs[i][1] = finIndicators.get(i).getYear();
            objs[i][2] = finIndicators.get(i).getInfoLiquidity();
            objs[i][3] = finIndicators.get(i).getInfoProfitability();

        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FinIndCRUDForAdmin window = new FinIndCRUDForAdmin("Добавление администратора",
                    admin, coos, cois, clientSocket, finIndicators, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                FinIndCRUDForAdmin window = new FinIndCRUDForAdmin("Редактирование ",
                        admin, coos, cois, clientSocket, finIndicators, table.getSelectedRow());
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
                        FinIndicators finIndicators1 = finIndicators.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(finIndicators1);
                        tableModel.removeRow(row);
                        finIndicators.remove(row);
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

