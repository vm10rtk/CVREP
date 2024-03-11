package GUILate;

import models.Employees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesTable extends JFrame{
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
    private JTable EmployeesTable;
    private JButton FileButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private List<Employees> employees;
    //private List<Patient> patients;
    //private List<Doctor> doctors;
    private JFrame frame;
   // private Employees employees;
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
    private void showData(List<Employees> employees) throws IOException, ClassNotFoundException {
        //для пациента
        //вывод всех талонов для выбора
        //if (info != null) {
            //coos.writeObject("viewInfo");
            //infos = (List<Info>) cois.readObject();

            Object[] columnTitle = new Object[]{ "Компания", "ФИО","Дата найма","Должность","з/п"};
//            for(Company company:companies){
//
//                Object[]data = {company.getName(),
//                };
//
//                tableModel.addRow(data);
//            }

            Object[][] objs = new Object[employees.size()][5];
            for (int i = 0; i < employees.size(); i++) {
                objs[i][0] = employees.get(i).getCompany().getName();
                objs[i][1] = employees.get(i).getFIO();
                        objs[i][2] = employees.get(i).getHiredate();
                                objs[i][3] = employees.get(i).getPost();
                                        objs[i][4] = employees.get(i).getSalary();

            }
            DefaultTableModel newTableModel = new DefaultTableModel(objs    , columnTitle);
            tableModel = newTableModel;
            //tableModel.getDataVector().removeAllElements();
            EmployeesTable.setModel(newTableModel);
        //}
    }
    private void fillComboBoxes(){
        List<String> uniqueCompanyName = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            uniqueCompanyName.add(employees.get(i).getCompany().getName());
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
    class saveInfoToFile implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //System.out.println(companies.size());
            File file = new File("Employees.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter out = new FileWriter(file, true);
                try {
                    for(int i =0;i<employees.size();i++){
                        String info = employees.get(i).getCompany().getName()
                                + "\n"+employees.get(i).getFIO()+"\n"+employees.get(i).getHiredate()+ "\n"+employees.get(i).getPost()+ "\n"+employees.get(i).getSalary()+"\n"+"----------------------------"+"\n";
                        out.write(info);}
                    JOptionPane.showMessageDialog(null, "Файл записан" );

                } finally {
                    out.close();
                }
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
    }
    public EmployeesTable(String name, JFrame frame, List<Employees> employees, boolean onlyReading,
                          ObjectOutputStream coos, ObjectInputStream cois) throws IOException, ClassNotFoundException {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.employees = employees;

        this.frame = frame;
        EmployeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
           FileButton.addActionListener(new saveInfoToFile());
//        }
//        else{
//
//            findCompanyNameText.setVisible(true);
//            findCompanyButton.setVisible(true);
//
            CompanyViewAllButton.setVisible(true);
//            //patViewAllButton.addActionListener(new patViewAllButtonActionListener());
//        }
        showData(employees);
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
                showData(employees);
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
                showData(employees);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String doc = (String) findCompanyСomboBox.getSelectedItem();
            List<Employees> employees1 = new ArrayList<>();
            for (int i = 0; i < employees.size(); i++){
                if (doc.equals(EmployeesTable.getValueAt(i, 0))){
                   employees1.add(employees.get(i));
                }
            }
            try {
                showData(employees1);
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
