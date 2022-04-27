//package CSC227_Project;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.LinkedList;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.DateAxis;
//import org.jfree.chart.axis.DateTickUnit;
//import org.jfree.chart.axis.DateTickUnitType;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.data.gantt.GanttCategoryDataset;
//import org.jfree.data.gantt.Task;
//import org.jfree.data.gantt.TaskSeries;
//import org.jfree.data.gantt.TaskSeriesCollection;
//import org.jfree.data.time.SimpleTimePeriod;
//import org.jfree.ui.ApplicationFrame;
//
//public class Gantt extends ApplicationFrame {
//
//    private static final long serialVersionUID = 1L;
//    private LinkedList<Controller.Box> drawList;
//
//    public Gantt(String title, LinkedList<Controller.Box> drawList) {
//        super(title);
//        this.drawList = drawList;
//        final GanttCategoryDataset dataset = createDataset();
//        final JFreeChart chart = createChart(dataset);
//        final ChartPanel chartPanel = new ChartPanel(chart);
////        chartPanel.setPreferredSize(new java.awt.Dimension(1100, 200));
//        setContentPane(chartPanel);
//    }
//
//    public GanttCategoryDataset createDataset() {
//        final TaskSeries s = new TaskSeries("Process");
//        Task p ;
//        LinkedList<Task> tasks = new LinkedList<>();
//        for (int i=0;i<drawList.size();i++){
//            System.out.println(drawList.get(i).toString());
//        }
//        boolean exist = false;
//        for (int i=0;i<drawList.size();i++){
//            for (int j = 0;j <tasks.size();j++){
//                if (tasks.get(j).getDescription().equalsIgnoreCase(drawList.get(i).pid)){
//                    exist = true;
//                    break;
//                }
//            }
//            if(!exist) {
//                p = new Task(drawList.get(i).pid, new SimpleTimePeriod(drawList.get(i).start, drawList.get(i).finish));
//                s.add(p);
//                tasks.add(p);
//            }
//            else {
//                Task u = null;
//                Task t = null;
//                for (int j = 0;j<tasks.size();j++){
//                    if (tasks.get(j).getDescription().equalsIgnoreCase(drawList.get(i).pid)) {
//                        u = tasks.get(j);
//                        t = tasks.get(j);
//                    }
//                }
//                p = new Task(drawList.get(i).pid, new SimpleTimePeriod(drawList.get(i).start, drawList.get(i).finish));
//                assert u != null;
//                u.addSubtask(p);
//                u.addSubtask(t);
//                s.add(u);
//                exist = false;
//            }
//        }
//        final TaskSeriesCollection collection = new TaskSeriesCollection();
//        collection.add(s);
//        return collection;
//    }
//
//
//    /*  private static Date date(final int day, final int month, final int year) {
//
//     final Calendar calendar = Calendar.getInstance();
//     calendar.set(year, month, day);
//     final Date result = calendar.getTime();
//     return result;
//
//     */
//    private JFreeChart createChart(final GanttCategoryDataset dataset) {
//        final JFreeChart chart = ChartFactory.createGanttChart(
//                "Gantt ", // chart title
//                "PRO", // domain axis label
//                "TIME (ms)", // range axis label
//                dataset, // data
//                true, // include legend
//                true, // tooltips
//                false // urls
//        );
//
//        CategoryPlot plot = chart.getCategoryPlot();
//        DateAxis axis = (DateAxis) plot.getRangeAxis();
//        axis.setDateFormatOverride(new SimpleDateFormat("SS"));
//        axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND, 2));
//        axis.setMinimumDate(new Date(0));
//        if(drawList.getLast().finish%2==0)
//            axis.setMaximumDate(new Date(drawList.getLast().finish+1));
//        else
//            axis.setMaximumDate(new Date(drawList.getLast().finish));
//        return chart;
//    }
//
//    public static void main(final String[] args) {
//
////        final Gantt demo = new Gantt("Gantt");
////        demo.pack();
////        RefineryUtilities.centerFrameOnScreen(demo);
////        demo.setVisible(true);
//
//    }
//}