package src.source;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

//CLASE USADA PARA LOS EXPERIMENTOS
public class Grafica extends JFrame {

    private static final long serialVersionUID = 1L;

    public Grafica(ArrayList<Double> costes, ArrayList<Integer> k, ArrayList<Double> l) {
        // Create dataset
        DefaultCategoryDataset dataset = createDataset(costes, k);
        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Evaluación de experimentos con valores k y λ", // Chart title
                "k", // X-Axis Label
                "coste", // Y-Axis Label
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public Grafica(ArrayList<Double> costes) {
        // Create dataset
        DefaultCategoryDataset dataset = createDataset2(costes);
        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Experimentos para definir el número de iteraciones", // Chart title
                "steps", // X-Axis Label
                "coste", // Y-Axis Label
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(ArrayList<Double> costes, ArrayList<Integer> k) {
        String series1 = "λ = 1.0";
        String series2 = "λ = 0.01";
        String series3 = "λ = 0.0001";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int cnt = 0;
        for (int i = 0; i < costes.size(); ++i) {
            if (i % 3 == 0)
                dataset.addValue(costes.get(i), series1, k.get(cnt));
            else if (i % 3 == 1)
                dataset.addValue(costes.get(i), series2, k.get(cnt));
            else dataset.addValue(costes.get(i), series3, k.get(cnt));

            if (i == 2 || i == 5 || i == 8) ++cnt;
        }

        return dataset;
    }

    private DefaultCategoryDataset createDataset2(ArrayList<Double> costes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Integer> steps = new ArrayList<>(Arrays.asList(5000, 10000, 50000));
        String serie = "";

        for (int i = 0; i < costes.size(); ++i) {
            dataset.addValue(costes.get(i), serie, steps.get(i));
        }

        return dataset;
    }

}
