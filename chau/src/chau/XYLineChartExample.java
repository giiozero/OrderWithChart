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
public class XYLineChartExample extends JFrame {
 boolean showLegend = false;
boolean createURL = false;
boolean createTooltip = false;

public static ArrayList<String> Desc = new ArrayList();
public static ArrayList<String> Horas = new ArrayList();
public static ArrayList<String> Ordem = new ArrayList();
    
    public XYLineChartExample() {
        super("XY Line Chart Example with JFreechart");
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
 
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(rootPane);
        
        
    }
    public static void Execute() {
        new XYLineChartExample().setVisible(true);
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
       //Ordena Array que contém a Ordem de execução para obter valores
       OrdenaOrdem();
       double HorasSomadas=0.0;
       double UltimaOrdem=0;
       for (int i = 0; i < Ordem.size(); i++) { 
            XYSeries OrdemZero = new XYSeries(Desc.get(i));
            OrdemZero.add(HorasSomadas, UltimaOrdem);
            HorasSomadas= HorasSomadas + Double.parseDouble(Horas.get(i));
            System.out.println(HorasSomadas+","+Horas.get(i));
            OrdemZero.add(HorasSomadas, Double.parseDouble(Ordem.get(i)));
            dataset.addSeries(OrdemZero);
            UltimaOrdem=Double.parseDouble(Ordem.get(i));
       }
       return dataset;
   }
    
    private void OrdenaOrdem() {
         boolean flag;  
        do {  
            flag = false;  
            
            System.out.println(Ordem.size());
                for (int i = 1; i < Ordem.size(); i++) { 
                    double OrdemUm = Double.parseDouble(Ordem.get(i));
                    double HorasUm = Double.parseDouble(Horas.get(i));
                    String DescUm = Desc.get(i);
                    if (Ordem.size() > 1) {
                    OrdemUm = Double.parseDouble(Ordem.get(i -1));
                    HorasUm = Double.parseDouble(Horas.get(i -1));
                    DescUm = Desc.get(i -1);
                    } 
                    double OrdemDois = Double.parseDouble(Ordem.get(i));
                    if (OrdemUm > OrdemDois) {  
                        Ordem.set(i-1, String.valueOf(OrdemDois));  
                        Ordem.set(i, String.valueOf(OrdemUm));  

                        Horas.set(i-1, String.valueOf(Horas.get(i)));  
                        Horas.set(i, String.valueOf(HorasUm)); 

                        Desc.set(i-1, String.valueOf(Desc.get(i)));  
                        Desc.set(i, String.valueOf(DescUm));                            
                        flag = true;  
                    }  
                }  
           
        } while (flag);  
    }
   
 
 
}