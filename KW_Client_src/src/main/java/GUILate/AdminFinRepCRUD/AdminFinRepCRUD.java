package GUILate.AdminFinRepCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.FinIndCRUDForAdmin.FinIndCRUDForAdmin;
import GUILate.FinRepCRUDForAdmin.FinRepCRUDForAdmin;
import GUILate.LoginWindow;
import models.Admin;
import models.FinIndicators;
import models.FinReports;

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





public class AdminFinRepCRUD extends JFrame {
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
    private List<FinReports> finReports;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminFinRepCRUD(String name, Admin admin, ObjectOutputStream coos,
                           ObjectInputStream cois, Socket socket, List<FinReports> finReports) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.finReports = finReports;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(finReports);
        addButton.addActionListener(new AdminFinRepCRUD.addButtonActionListener());
        redButton.addActionListener(new AdminFinRepCRUD.redButtonActionListener());
        delButton.addActionListener(new AdminFinRepCRUD.delButtonActionListener());
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
    public void showData(List<FinReports> finReports) {
        Object[] columnTitle = new Object[]{"ID Компании",
                "Год отчета", "Тип отчета",
                "Результаты" };
        Object[][] objs = new Object[finReports.size()][4];
        for (int i = 0; i < finReports.size(); i++) {
            objs[i][0] = finReports.get(i).getCompany().getCompany_id();
            objs[i][1] = finReports.get(i).getYear();
            objs[i][2] = finReports.get(i).getReporttype();
            objs[i][3] = finReports.get(i).getReportdate();

        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FinRepCRUDForAdmin window = new FinRepCRUDForAdmin("Добавление администратора",
                    admin, coos, cois, clientSocket, finReports, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                FinRepCRUDForAdmin window = new FinRepCRUDForAdmin("Редактирование ",
                        admin, coos, cois, clientSocket, finReports, table.getSelectedRow());
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
                        FinReports finIndicators1 = finReports.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(finIndicators1);
                        tableModel.removeRow(row);
                        finReports.remove(row);
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

