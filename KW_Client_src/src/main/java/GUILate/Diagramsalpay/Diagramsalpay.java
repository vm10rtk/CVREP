package GUILate.Diagramsalpay;

import GUILate.LoginWindow;
import models.Admin;
import models.Employees;
import models.ProductsAndService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Diagramsalpay extends JFrame {

    private static final long serialVersionUID = 1L;
    public int admins ;
    public int users ;
    public List<Employees> employeesList = new ArrayList<>();
    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    private JPanel panelNewDiagram;
    private JPanel newpanel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private Socket clientSocket;
    private Admin admin;

    public Diagramsalpay(String title, List<Employees> employees, Frame frame) {
        super(title);
        this.employeesList = employees;
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(createDemoPanel());

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               // try {
//                    coos.writeObject("exit");
//                    checkIsExisting();
                   // AdminWindow adminWindow = new AdminWindow("Окно администратора",
                          // admin, coos, cois, clientSocket);
                    frame.setEnabled(true);

//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
           }
        });
    }
    public JPanel createDemoPanel() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        return chartPanel;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println(admins+"hahahhha");
        for(int i=0;i<employeesList.size();i++){
        dataset.addValue(employeesList.get(i).getSalary(),"Сотрудник",employeesList.get(i).getCompany().getName()+employeesList.get(i).getFIO());}
       // dataset.addValue(users, "Пользователи" , "Пользователи");
//        dataset.addValue(51345, "Жена", "Февраль");
//        dataset.addValue(66896, "Муж" , "Февраль");
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Доходы",
                null,                    // x-axis label
                "Сумма",                 // y-axis label
                dataset);
//        chart.addSubtitle(new TextTitle("В доходе включен только заработок "
//                + "по основной работе"));
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

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
