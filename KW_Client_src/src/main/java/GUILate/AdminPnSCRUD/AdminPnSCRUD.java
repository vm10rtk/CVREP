package GUILate.AdminPnSCRUD;

import GUILate.AdminWindow.AdminWindow;
import GUILate.InfoCRUDForAdmin.InfoCRUDForAdmin;
import GUILate.LoginWindow;
import GUILate.PnSCRUDForAdmin.PnSCRUDForAdmin;
import models.Admin;
import models.Info;
import models.ProductsAndService;

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





public class AdminPnSCRUD extends JFrame {
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
    private List<ProductsAndService> productsAndServices;
    private DefaultTableModel tableModel;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    private JPanel title;
    private JLabel title1;
    private JTable table1;

    public AdminPnSCRUD(String name, Admin admin, ObjectOutputStream coos,
                        ObjectInputStream cois, Socket socket, List<ProductsAndService> productsAndServices) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.admin = admin;
        this.clientSocket = socket;
        this.productsAndServices = productsAndServices;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        showData(productsAndServices);
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
    public void showData(List<ProductsAndService> productsAndServices) {
        Object[] columnTitle = new Object[]{"ID Компании","Название компании",
                "Название товра/услуги", "Описание",
                "Цена","Количество продаж" };
        Object[][] objs = new Object[productsAndServices.size()][6];
        for (int i = 0; i < productsAndServices.size(); i++) {
            objs[i][0] = productsAndServices.get(i).getCompany().getCompany_id();
            objs[i][1] = productsAndServices.get(i).getCompany().getName();
            objs[i][2] = productsAndServices.get(i).getName();
            objs[i][3] = productsAndServices.get(i).getDescription();
            objs[i][4] = productsAndServices.get(i).getPrice();
            objs[i][5] = productsAndServices.get(i).getSoldcount();
        }
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        tableModel = newtableModel;
        table.setModel(newtableModel);
    }
    class addButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PnSCRUDForAdmin window = new PnSCRUDForAdmin("Добавление информации о продуктах и услугах компаний",
                    admin, coos, cois, clientSocket, productsAndServices, 0);
            dispose();
        }
    }
    class redButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(null, "Выберите строку.");
            }
            else {
                PnSCRUDForAdmin window = new PnSCRUDForAdmin("Редактирование ",
                        admin, coos, cois, clientSocket, productsAndServices, table.getSelectedRow());
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
                        ProductsAndService info = productsAndServices.get(row);
                        coos.writeObject("del");
                        checkIsExisting();
                        coos.writeObject(info);
                        tableModel.removeRow(row);
                        productsAndServices.remove(row);
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

