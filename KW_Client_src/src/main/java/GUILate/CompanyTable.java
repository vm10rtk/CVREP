package GUILate;

import models.Company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class CompanyTable extends JFrame{
    private JButton deleteTicketButton;
    private JButton takeTicketButton;
    private JTable CompanyTable;
    private DefaultTableModel tableModel;
    private JPanel companyTablePanel;
    private JButton findCompanyButton;
    private JLabel findCompanyNameText;


    private JButton CompanyViewAllButton;
    private JPanel tablePanel;

    private JButton найтиButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private List<Company> companies;
    //private List<Patient> patients;
    //private List<Doctor> doctors;
    private JFrame frame;
    private Company company;
    public CompanyTable (String name, JFrame frame, List<Company> companyList , ObjectOutputStream coos, ObjectInputStream cois) {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.frame = frame;
        //this.doctors = doctors;
        Object[] columnTitle = new Object[]{"Название"};
        Object[][] objs = new Object[companyList.size()][1];
        for (int i = 0; i < companyList.size(); i++) {
            Company company1 = companyList.get(i);
           objs[i][0] = company1.getName();
//            objs[i][1] = doctor.getSpeciality();
//            objs[i][2] = doctor.getShift();
//            if (doctor.getShift() == 1) objs[i][3] = "10:00 - 14:00";
//            else objs[i][3] = "15:00 - 19:00";
//            objs[i][4] = doctor.getEmail();
        }
        setLocationRelativeTo(null);
        setSize(1200, 600);
        setContentPane(companyTablePanel);
        setVisible(true);
        DefaultTableModel newtableModel = new DefaultTableModel(objs, columnTitle);
        CompanyTable.setModel(newtableModel);
        //findCompanyNameText.setVisible(false);


        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setEnabled(true);
            }
        });

        ;
    }
//    private void showData(List<Company> companies) throws IOException, ClassNotFoundException {
//        //для пациента
//        //вывод всех талонов для выбора
//        if (company != null) {
//            coos.writeObject("viewCompanies");
//            companies = (List<Company>) cois.readObject();
//
//            Object[] columnTitle = new Object[]{ "Наименование "};
//            for(Company company:companies){
//
//                Object[]data = {company.getName()};
//                tableModel.addRow(data);
//            }
//
////            Object[][] objs = new Object[companies.size()][1];
////            for (int i = 0; i < companies.size(); i++) {
////                objs[i][0] = company.getName();
////            }
//            DefaultTableModel newTableModel = new DefaultTableModel(null, columnTitle);
//            tableModel = newTableModel;
//            tableModel.getDataVector().removeAllElements();
//            CompanyTable.setModel(newTableModel);
//        }
//    }
//    private void fillComboBoxes(){
//        HashSet<String> uniqueDoctorName = new HashSet<>();
//        for (int i = 0; i < companies.size(); i++) {
//            uniqueDoctorName.add(companies.get(i).getName());
//        }
//        String[] docs = uniqueDoctorName.toArray(new String[uniqueDoctorName.size()]);
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//        for (int i = 0; i < docs.length; i++){
//            model.addElement(docs[i]);
//        }
//        findCompanyСomboBox.setModel(model);
//    }
//    public CompanyTable(String name, JFrame frame, List<Company> companies, boolean onlyReading,
//                        ObjectOutputStream coos, ObjectInputStream cois, Company company) throws IOException, ClassNotFoundException {
//        super(name);
//        this.coos = coos;
//        this.cois = cois;
//        this.companies = companies;
//        this.company = company;
//        this.frame = frame;
//        CompanyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        setContentPane(companyTablePanel);
//        setSize(1200, 600);
//        setLocationRelativeTo(null);
//        setVisible(true);
////        if(onlyReading){
////          //  takeTicketButton.setVisible(false);
////
////            //ticketToFileButton.addActionListener(new ticketToFileButtonActionListener());
////        }
////        if (company != null){
////            findCompanyButton.addActionListener(new findDocButtonActionListener());
//           CompanyViewAllButton.addActionListener(new docViewAllButtonActionListener());
////        }
////        else{
////
////            findCompanyNameText.setVisible(true);
////            findCompanyButton.setVisible(true);
////
//            CompanyViewAllButton.setVisible(true);
////            //patViewAllButton.addActionListener(new patViewAllButtonActionListener());
////        }
//        showData(companies);
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                try {
//                    coos.writeObject("ticketMenu");
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//                frame.setEnabled(true);
//            }
//        });
//
//    }
//    class docViewAllButtonActionListener implements ActionListener {
//        public void actionPerformed(ActionEvent e){
//            //System.out.println(companies.size());
//            try {
//                showData(companies);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            } catch (ClassNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    class findDocButtonActionListener implements ActionListener {
//        public void actionPerformed(ActionEvent e){
//            try {
//                showData(companies);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            } catch (ClassNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//            String doc = (String) findCompanyСomboBox.getSelectedItem();
//            List<Company> companies1 = new ArrayList<>();
//            for (int i = 0; i < companies.size(); i++){
//                if (doc.equals(CompanyTable.getValueAt(i, 0))){
//                   companies1.add(companies.get(i));
//                }
//            }
//            try {
//                showData(companies1);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            } catch (ClassNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    class findPatButtonActionListener implements ActionListener {
//        public void actionPerformed(ActionEvent e){
//            //if(findDocTextField.getText())
//        }
//    }
}
