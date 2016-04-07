/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chau;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author giovanni
 */
public class XYLineChart extends JFrame {

public static ArrayList<String> Desc = new ArrayList();
public static ArrayList<String> Hours = new ArrayList();
public static ArrayList<String> Order = new ArrayList();
    
    public XYLineChart() {
        super("Gráfico XY - Trabalho P1");
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(rootPane);
    }
    public static void Execute() {
        new XYLineChart().setVisible(true);
    }
    private JPanel createChartPanel() {
    String chartTitle = "Fábrica de Cadeiras e Balanças";
    String xAxisLabel = "Horas";
    String yAxisLabel = "Ordem de Execução";
 
    XYDataset dataset = createDataset();
 
    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
            xAxisLabel, yAxisLabel, dataset);
    
    XYPlot plot = chart.getXYPlot();
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    
    // sets paint color for each series
    renderer.setSeriesPaint(0, Color.RED);
    renderer.setSeriesPaint(1, Color.GREEN);
    renderer.setSeriesPaint(2, Color.YELLOW);

    // sets thickness for series (using strokes)
    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
 
    plot.setBackgroundPaint(Color.DARK_GRAY);
    plot.setRangeGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.BLACK);
 
    plot.setDomainGridlinesVisible(true);
    plot.setDomainGridlinePaint(Color.BLACK);
    plot.setRenderer(renderer);
    File imageFile = new File("XYLineChart.png");
    int width = 800;
    int height = 900;

    try {
        ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
    } catch (IOException ex) {
        System.err.println(ex);
    }
   
 
    return new ChartPanel(chart);
    }
    private XYDataset createDataset() {
       XYSeriesCollection dataset = new XYSeriesCollection();
       //Function that Re-Order the arrays correctly
       OrderTheOrder();
       double SumOfHours=0.0;
       double LastOrder=0;
       
       //Make the Chart
       for (int i = 0; i < Order.size(); i++) { 
            XYSeries RowOfChart = new XYSeries(Desc.get(i));
            //This "SumOfHours" is the LAST used, NOT the new
            //Used to Connect the Lines
            RowOfChart.add(SumOfHours, LastOrder);
            //Here comes the NEW "SumOfHours"
            SumOfHours= SumOfHours + Double.parseDouble(Hours.get(i));
            //Add a Line
            RowOfChart.add(SumOfHours, Double.parseDouble(Order.get(i)));
            //add the values to make the chart
            dataset.addSeries(RowOfChart);
            //Get the Last Order
            LastOrder=Double.parseDouble(Order.get(i));
       }
       return dataset;
   }
    
    private void OrderTheOrder() {
        boolean flag;  
        do {  
            flag = false;  
            
            //Run the Array to Order everything Correctly
            for (int i = 1; i < Order.size(); i++) { 
                //Creating Variables to make all easy
                double OrderOne = Double.parseDouble(Order.get(i));
                double HoursOne = Double.parseDouble(Hours.get(i));
                String DescOne = Desc.get(i);
                
                //I can't get the Array value of Index -1... Sad
                if (Order.size() > 1) {
                OrderOne = Double.parseDouble(Order.get(i -1));
                HoursOne = Double.parseDouble(Hours.get(i -1));
                DescOne = Desc.get(i -1);
                }
                double OrderTwo = Double.parseDouble(Order.get(i));
                
                //Bubble Sort???
                if (OrderOne > OrderTwo) {
                    Order.set(i-1, String.valueOf(OrderTwo));  
                    Order.set(i, String.valueOf(OrderOne));  
                    Hours.set(i-1, String.valueOf(Hours.get(i)));  
                    Hours.set(i, String.valueOf(HoursOne)); 
                    Desc.set(i-1, String.valueOf(Desc.get(i)));  
                    Desc.set(i, String.valueOf(DescOne));                            
                    flag = true;  
                }  
            }  
        } while (flag);  
    }
   
 
 
}