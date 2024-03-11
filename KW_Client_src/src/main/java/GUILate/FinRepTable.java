package GUILate;

import models.FinReports;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FinRepTable extends JFrame{
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
    private JTable FinRepTable;
    private JButton FileButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private List<FinReports> finReports;
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
    private void showData(List<FinReports> finReports) throws IOException, ClassNotFoundException {
        //для пациента
        //вывод всех талонов для выбора
        //if (info != null) {
        //coos.writeObject("viewInfo");
        //infos = (List<Info>) cois.readObject();

        Object[] columnTitle = new Object[]{ "Компания", "Год","Тип отчета","Результаты/содержание отчета"};
//            for(Company company:companies){
//
//                Object[]data = {company.getName(),
//                };
//
//                tableModel.addRow(data);
//            }

        Object[][] objs = new Object[finReports.size()][4];
        for (int i = 0; i < finReports.size(); i++) {
            objs[i][0] = finReports.get(i).getCompany().getName();
            objs[i][1] = finReports.get(i).getYear();
            objs[i][2] = finReports.get(i).getReporttype();
            objs[i][3] = finReports.get(i).getReportdate();


        }
        DefaultTableModel newTableModel = new DefaultTableModel(objs    , columnTitle);
        tableModel = newTableModel;
        //tableModel.getDataVector().removeAllElements();
        FinRepTable.setModel(newTableModel);
        //}
    }
    class saveInfoToFile implements ActionListener {
        public void actionPerformed(ActionEvent e){
            //System.out.println(companies.size());
            File file = new File("Reports.txt");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter out = new FileWriter(file, true);
                try {
                    for(int i =0;i<finReports.size();i++){
                        String info = finReports.get(i).getCompany().getName()
                                + "\n"+finReports.get(i).getReporttype()+"\n"
                                +finReports.get(i).getReportdate()+
                                "\n"+finReports.get(i).getYear()+
                               // "\n"+finReports.get(i).getDescription()+
                                "\n"+"----------------------------"+"\n";
                        out.write(info);}
                } finally {
                    out.close();
                }
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
    }
    private void fillComboBoxes(){
        List<String> uniqueIndName = new ArrayList<>();
        for (int i = 0; i < finReports.size(); i++) {
            uniqueIndName.add(finReports.get(i).getCompany().getName());
        }
        String[] comps = uniqueIndName.toArray(new String[uniqueIndName.size()]);
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
    public FinRepTable(String name, JFrame frame, List<FinReports> finReports, boolean onlyReading,
                       ObjectOutputStream coos, ObjectInputStream cois) throws IOException, ClassNotFoundException {
        super(name);
        this.coos = coos;
        this.cois = cois;
        this.finReports = finReports;

        this.frame = frame;
        FinRepTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        showData(finReports);
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
                showData(finReports);
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
                showData(finReports);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String doc = (String) findCompanyСomboBox.getSelectedItem();
            List<FinReports> finIndicators1 = new ArrayList<>();
            for (int i = 0; i < finReports.size(); i++){
                if (doc.equals(FinRepTable.getValueAt(i, 0))){
                    finIndicators1.add(finReports.get(i));
                }
            }
            try {
                showData(finIndicators1);
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
