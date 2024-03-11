package GUILate.RolesDiagram;
import org.hibernate.loader.collection.OneToManyJoinWalker;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;
//import Diagrams.*;
import GUILate.AdminAdminCRUD.AdminAdminCRUD;
import GUILate.AdminCompanyCRUD.AdminCompanyCRUD;
import GUILate.AdminEmployeesCRUD.AdminEmployessCRUD;
import GUILate.AdminFinIndCRUD.AdminFinIndCRUD;
import GUILate.AdminFinRepCRUD.AdminFinRepCRUD;
import GUILate.AdminInfoCRUD.AdminInfoCRUD;
import GUILate.AdminPnSCRUD.AdminPnSCRUD;
import GUILate.AdminUserCRUD.AdminUserCRUD;
import GUILate.LoginWindow;

import models.*;
//import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import org.jfree.data.general.PieDataset;

public class RolesDiagram extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
public int admins ;
public int users ;
    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    private JPanel panelNewDiagram;

    public RolesDiagram(String title,int admin,int users,Frame frame) {
        super(title); this.users = users;
        this.admins = admin;
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
        chartPanel.setPreferredSize(new Dimension(500, 270));
        return chartPanel;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println(admins+"hahahhha");
        dataset.addValue(users,  "Админ", "Админ");
        dataset.addValue(admins,"Пользователи" , "Пользователи");
//        dataset.addValue(51345, "Жена", "Февраль");
//        dataset.addValue(66896, "Муж" , "Февраль");
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "кол-во админов/пользователей",
                null,                    // x-axis label
                "Количетсво",                 // y-axis label
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


}
