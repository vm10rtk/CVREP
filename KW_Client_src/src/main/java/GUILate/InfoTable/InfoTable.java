package GUILate.InfoTable;

import models.Company;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InfoTable extends JFrame{
    private JButton deleteTicketButton;
    private JButton takeTicketButton;
    private JTable CompanyTable;
    private DefaultTableModel tableModel;
    private JPanel companyTablePanel;
    private JButton findCompanyButton;
    private JLabel findCompanyNameText;
    private JButton Button;

    private JButton CompanyViewAllButton;
    private JPanel tablePanel;
    private JComboBox findCompanyСomboBox;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private List<Info> infos;
    //private List<Patient> patients;
    //private List<Doctor> doctors;
    private JFrame frame;
    private Info info;
//    public InfoTable(String name, JFrame frame, List<Company> companyList , ObjectOutputStream coos, ObjectInputStream cois) {
//        super(name);
//        this.coos = coos;
//        this.cois = cois;
//        this.frame = frame;
//        //this.doctors = doctors;
//        Object[] columnTitle = new Object[]{"Название"};
//        Object[][] objs = new Object[companyList.size()][1];
//        for (int i = 0; i < companyList.size(); i++) {
//            Company company1 = companyList.get(i);
//           objs[i][0] = company1.getName();
////            objs[i][1] = doctor.getSpeciality();
////            objs[i][2] = doctor.getShift();
////            if (doctor.getShift() == 1) objs[i][3] = "10:00 - 14:00";
////            else objs[i][3] = "15:00 - 19:00";
////            objs[i][4] = doctor.getEmail();
//        }
//        setLocationRelativeTo(null);
//        setSize(1200, 600);
//        setContentPane(companyTablePanel);
//        setVisible(true);
//        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
//        CompanyTable.setModel(newtableModel);
//        //findCompanyNameText.setVisible(false);
//        findCompanyСomboBox.setVisible(false);
//        CompanyViewAllButton.setVisible(false);
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                frame.setEnabled(true);
//            }
//        });
//    }
    private void showData(List<Info> infos) throws IOException, ClassNotFoundException {


            Object[] columnTitle = new Object[]{ "Компания", "Адрес","Телефон","Почта","Вид деятельности"};

            Object[][] objs = new Object[infos.size()][5];
            for (int i = 0; i < infos.size(); i++) {
                objs[i][0] = infos.get(i).getCompany().getName();
                objs[i][1] = infos.get(i).getAddress();
                        objs[i][2] = infos.get(i).getPhone();
                                objs[i][3] = infos.get(i).getEmail();
                                        objs[i][4] = infos.get(i).getActivity();

            }
            DefaultTableModel newTableModel = new DefaultTableModel(objs    , columnTitle);
            tableModel = newTableModel;
            //tableModel.getDataVector().removeAllElements();
            CompanyTable.setModel(newTableModel);
        //}
    }
    private void fillComboBoxes(){
        List<String> uniqueCompanyName = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            uniqueCompanyName.add(infos.get(i).getCompany().getName());
        }
        String[] comps = uniqueCompanyName.toArray(new String[uniqueCompanyName.size()]);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 0; i < comps.length; i++){
            model.addElement(comps[i]);
        }
        findCompanyСomboBox.setModel(model);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                try {
//                    System.out.println("go to menu");
//                    coos.writeObject("InfoMenu");
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
                frame.setEnabled(true);
            }
        });
    }
    public InfoTable(String name, JFrame frame, List<Info> infos, boolean onlyReading,
                        ObjectOutputStream coos, ObjectInputStream cois) throws IOException, ClassNotFoundException {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.infos = infos;

        this.frame = frame;
        CompanyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setContentPane(companyTablePanel);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
//        if(onlyReading){
//          //  takeTicketButton.setVisible(false);
//
//            //ticketToFileButton.addActionListener(new ticketToFileButtonActionListener());
//        }
//        if (company != null){
//            findCompanyButton.addActionListener(new findDocButtonActionListener());
        Button.addActionListener(new findCompanyButtonActionListener());
           CompanyViewAllButton.addActionListener(new InfoViewAllButtonActionListener());
//        }
//        else{
//
//            findCompanyNameText.setVisible(true);
//            findCompanyButton.setVisible(true);
//
            CompanyViewAllButton.setVisible(true);
//            //patViewAllButton.addActionListener(new patViewAllButtonActionListener());
//        }
        showData(infos);
        fillComboBoxes();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                try {
//                    System.out.println("go to menu");
//                    coos.writeObject("InfoMenu");
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
                frame.setEnabled(true);
            }
        });

    }
    class InfoViewAllButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //System.out.println(companies.size());
            try {
                showData(infos);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class findCompanyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                showData(infos);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String doc = (String) findCompanyСomboBox.getSelectedItem();
            List<Info> infos1 = new ArrayList<>();
            for (int i = 0; i < infos.size(); i++){
                if (doc.equals(CompanyTable.getValueAt(i, 0))){
                   infos1.add(infos.get(i));
                }
            }
            try {
                showData(infos1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class findPatButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //if(findDocTextField.getText())
        }
    }
}
