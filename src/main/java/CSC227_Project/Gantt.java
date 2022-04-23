package CSC227_Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Gantt extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    public Gantt(final String title) {

        super(title);

        final GanttCategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 270));
        setContentPane(chartPanel);

    }

    public static GanttCategoryDataset createDataset() {
        final TaskSeries s1 = new TaskSeries("P0");
        Task t4 = new Task("P0", new SimpleTimePeriod(0, 10));
        s1.add(t4);
        t4 = new Task("P1", new SimpleTimePeriod(10, 20));
        s1.add(t4);
        t4 = new Task("P2", new SimpleTimePeriod(20, 30));
        s1.add(t4);
        final TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        return collection;
    }


    /*  private static Date date(final int day, final int month, final int year) {

     final Calendar calendar = Calendar.getInstance();
     calendar.set(year, month, day);
     final Date result = calendar.getTime();
     return result;

     */
    private JFreeChart createChart(final GanttCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
                "Gantt ", // chart title
                "PRO", // domain axis label
                "TIME (ms)", // range axis label
                dataset, // data
                true, // include legend
                true, // tooltips
                false // urls
        );

        CategoryPlot plot = chart.getCategoryPlot();
        DateAxis axis = (DateAxis) plot.getRangeAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("SSS"));
        axis.setMaximumDate(new Date(60));
        return chart;
    }

    public static void main(final String[] args) {

        final Gantt demo = new Gantt("Gantt");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}