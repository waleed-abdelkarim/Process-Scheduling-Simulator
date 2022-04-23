package CSC227_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Label SRTF_File_Label;
    @FXML
    private Label SRTF_AWT_Label;
    @FXML
    private Label SRTF_ATAT_Label;
    @FXML
    private TableView<Process> SRTF_Input_Table = new TableView<Process>();
    @FXML
    private TableView<Process> SRTF_Output_Table = new TableView<Process>();
    @FXML
    private TableColumn<Process, String> SRTF_Output_PID;
    @FXML
    private TableColumn<Process, String> SRTF_Input_PID;
    @FXML
    private TableColumn<Process, Integer> SRTF_AT;
    @FXML
    private TableColumn<Process, Integer> SRTF_BT;
    @FXML
    private TableColumn<Process, Integer> SRTF_WT;
    @FXML
    private TableColumn<Process, Integer> SRTF_TAT;
    @FXML
    private ScrollPane SRTF_Gantt;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SRTF_Input_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pid"));
        SRTF_Output_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pid"));
        SRTF_AT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("at"));
        SRTF_BT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("bt"));
        SRTF_WT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("wt"));
        SRTF_TAT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("tat"));
    }

    Methods method = new Methods();

    @FXML
    protected void fileChooser(ActionEvent event) {

    }

    @FXML
    protected void SRTFFileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fc.showOpenDialog(null);
        try {
            method.readForSRTF(file.toPath().toString());
            method.findavgTime();
            SRTF_Input_Table.getItems().addAll(method.processesSRTF);
            SRTF_Output_Table.getItems().addAll(method.processesSRTF);
            for(int i =0;i<method.Boxes.size();i++){
                System.out.print(method.Boxes.get(i).toString());
            }
            Gantt g = new Gantt(method.Boxes);
        } catch (Exception e) {
            e.printStackTrace();
            SRTF_File_Label.setText("No File Selected");
        }

    }

    /**
     * class CPU {
     * String pid; // Process ID
     * int burstTime; // Burst Time
     * int arrivalTime; // Arrival Time
     * int num_of_processes = 0;
     * <p>
     * public CPU(String pid, int arrivalTime, int burstTime) {
     * this.pid = pid;
     * this.burstTime = burstTime;
     * this.arrivalTime = arrivalTime;
     * }
     * }
     **/

    class Methods {

        LinkedList<Process> processesSRTF;// contain an information about every process
        LinkedList<Process> list_of_processesRR;// contain an information about every process
        LinkedList<Box> Boxes;

        public Methods() {
            Boxes = new LinkedList<Box>();
            processesSRTF = new LinkedList<Process>();
            list_of_processesRR = new LinkedList<Process>();
        }

        public void readForSRTF(String filePath) {
            String pid; // Process ID
            int at; // Arrival Time
            int bt; // Burst Time
            int counter = 0;
            int successNum = 0;
            boolean flag = true;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String read;
                while (((read = reader.readLine()) != null) && counter < 30) {
                    try { // this try will make reading with single point of failure
                        //PID,AT,BT
                        String[] process = read.split(",");
                        pid = process[0];
                        at = Integer.parseInt(process[1]);
                        bt = Integer.parseInt(process[2]);
                        Process p = new Process(pid, at, bt);
                        processesSRTF.add(p);
                        successNum++;
                        counter++;
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
            SRTF_File_Label.setText("Number of process read: " + successNum);
        }
        /*
         public boolean readCSVforRR(String filePath) {

         BufferedReader Reader = null;
         String row = null;
         boolean flag = true;
         int pNum = 1;

         // try and catch block to read the file using BufferedReader & FileReader
         try {
         Reader = new BufferedReader(new FileReader(filePath));
         } catch (FileNotFoundException e1) {
         System.out.println("ERROR --FILE NOT FOUND	");
         flag = false;
         } finally {
         if (flag)
         ;
         else
         return false;
         }

         // try and catch block to read the every row in the file and split it into
         // attributes then create a row object and store it in a list
         // if an error occurs flag will be set to false then false will be the returned
         try {
         while (true) {
         row = Reader.readLine();

         if (row == null)
         break;
         if (!Character.isDigit(row.charAt(0)))
         continue;
         String[] array_of_data = row.split(",");

         for (int i = 0; i < array_of_data.length; i++) {

         if (array_of_data[i].isEmpty()) {

         return false;

         }

         }

         CPU newRow = new CPU(pNum, pNum++, Integer.parseInt(array_of_data[0]));

         this.list_of_processesRR.add(newRow);

         }
         } catch (IOException e) {
         System.out.println("ERROR IN READING LINE FROM THE FILE OCCURS");
         flag = false;
         } finally {
         if (flag)
         ;
         else
         return false;
         }

         // try and catch block to close the buffer
         try {
         Reader.close();

         } catch (IOException e) {
         System.out.println("ERROR IN CLOSING FILE OCCURS");
         flag = false;
         } finally {
         if (flag)
         ;
         else
         return false;
         }

         return true;

         }
         **/

        /*
         public void print() {
         for (CPU row : processesSRTF) {
         System.out.println("PID = " + row.getPid() + ", Arrival Time = " + row.getArrivalTime() + ", Burst Time = "
         + row.getBurstTime());
         }
         }
         **/

        /* SRTF **/


        void findWaitingTime(ArrayList<Integer> waitingTime) {
           LinkedList<Integer> remainingTime = new LinkedList<>();

            // Copy the burst time into remainingTime
            for (Process process : processesSRTF) remainingTime.add(process.getBt());

            int complete = 0;
            int time = 0;
            int min = Integer.MAX_VALUE;
            int shortest = 0, finish_time;
            Box b = null;
            // Process until all processes gets completed
            while (complete != processesSRTF.size()) {

                // Find process with minimum remaining time among the processes that arrives till the current time`
                for (int i = 0; i < processesSRTF.size(); i++) {
                    if ((processesSRTF.get(i).getAt() <= time) && (remainingTime.get(i) < min) && remainingTime.get(i) > 0) {
                        min = remainingTime.get(i);
                        shortest = i;
                    }
                }
                boolean exist = false;
                for (Box box : Boxes) {
                    if (box.pid.equalsIgnoreCase(processesSRTF.get(shortest).getPid())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist || !Boxes.getLast().pid.equalsIgnoreCase(processesSRTF.get(shortest).pid)) {
                    b = new Box();
                    b.start = time;
                    b.pid = processesSRTF.get(shortest).getPid();
                    b.finish = time + 1;
                    Boxes.add(b);
                }
                else {
                    assert b != null;
                    b.finish = time+1;
                }


                // Reduce remaining time by one
                remainingTime.set(shortest,remainingTime.get(shortest)-1);

                // If a process gets completely executed
                if (remainingTime.get(shortest) == 0) {
                    min = Integer.MAX_VALUE;
                    // Increment complete
                    complete++;
                    // Find finish time of current process
                    finish_time = time + 1;
                    // Calculate waiting time
                    int wt = finish_time - processesSRTF.get(shortest).getBt() - processesSRTF.get(shortest).getAt();
                    waitingTime.set(shortest, wt);
                    processesSRTF.get(shortest).setWt(wt);
                    if (waitingTime.get(shortest) < 0)
                        waitingTime.set(shortest,0);
                }
                // Increment time
                time++;
            }
        }


        // Method to calculate turn around time
        void findTurnAroundTime(ArrayList<Integer> TurnAroundTime) {
            // calculating turnaround time by adding
            // bt[i] + wt[i]
            int tat;
            for (int i = 0; i < processesSRTF.size(); i++) {
                tat = processesSRTF.get(i).getBt() + processesSRTF.get(i).getWt();
                processesSRTF.get(i).setTat(tat);
                TurnAroundTime.set(i,tat);
            }
        }

        // Method to calculate average time
        void findavgTime() {
            ArrayList<Integer> waitingTime = new ArrayList<>(processesSRTF.size());
            ArrayList<Integer> TurnAroundTime =new ArrayList<>(processesSRTF.size());
            for (int i =0;i<processesSRTF.size();i++){
                waitingTime.add(0);
                TurnAroundTime.add(0);
            }
            int total_wt = 0, total_tat = 0;

            // Function to find waiting time of all
            // processes
            findWaitingTime(waitingTime);

            // Function to find turn around time for
            // all processes
            findTurnAroundTime(TurnAroundTime);
            //             Calculate total waiting time and
//             total turnaround time
            for (int i = 0; i < processesSRTF.size(); i++) {
                total_wt += waitingTime.get(i);
                total_tat += TurnAroundTime.get(i);
            }

            BigDecimal totalWt = new BigDecimal((double) total_wt / (double) processesSRTF.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalTat = new BigDecimal((double) total_tat / (double) processesSRTF.size()).setScale(2, RoundingMode.HALF_EVEN);
            SRTF_AWT_Label.setText(SRTF_AWT_Label.getText() + totalWt +" ms");
            SRTF_ATAT_Label.setText(SRTF_ATAT_Label.getText() + totalTat +" ms");
        }



        /* Round Robin **/

        /*
         static void RoundRobin(Process p[], int qt, int n) {
         int i, count = 0, temp, sq = 0, wt[], tat[], rem_bt[];
         float awt = 0, atat = 0;
         tat = new int[30];
         rem_bt = new int[30];
         wt = new int[30];
         for (i = 0; i < n; i++) {
         rem_bt[i] = p[i].bt;
         }
         while (true) {
         for (i = 0, count = 0; i < n; i++) {
         temp = qt;
         if (rem_bt[i] == 0) {
         count++;
         continue;
         }
         if (rem_bt[i] > qt)
         rem_bt[i] = rem_bt[i] - qt;
         else if (rem_bt[i] >= 0) {
         temp = rem_bt[i];
         rem_bt[i] = 0;
         }
         sq = sq + temp;
         tat[i] = sq;
         }
         if (n == count)
         break;
         }
         System.out.print("--------------------------------------------------------------------------------");
         System.out.print("\nProcess\tBurst Time\tTurnaround Time\tWaiting Time\n");
         System.out.print("--------------------------------------------------------------------------------");
         for (i = 0; i < n; i++) {
         wt[i] = tat[i] - p[i].bt;
         awt = awt + wt[i];
         atat = atat + tat[i];
         System.out.print("\n " + p[i].pid + "\t " + p[i].bt + "\t\t " + tat[i] + "\t\t " + wt[i] + "\n");
         }
         awt = awt / n;
         atat = atat / n;
         System.out.println("\nAverage waiting Time = " + awt + "\n");
         System.out.println("Average turnaround time = " + atat);

         }
         **/
    }

    class Gantt{

        private LinkedList<Controller.Box> drawList;

        public Gantt(LinkedList<Controller.Box> drawList) {
            this.drawList = drawList;
            final GanttCategoryDataset dataset = createDataset();
            final JFreeChart chart = createChart(dataset);
//            final ChartPanel chartPanel = new ChartPanel(chart);
            ScrollPane scroll = new ScrollPane();
            final SwingNode chartSwingNode = new SwingNode();
            chartSwingNode.setContent(
                    new ChartPanel(chart)
            );
            chartSwingNode.getContent().setPreferredSize(new java.awt.Dimension(850, 230));
            SRTF_Gantt.setContent(chartSwingNode);

//            chartPanel.setPreferredSize(new java.awt.Dimension(1100, 200));
        }

        public GanttCategoryDataset createDataset() {
            final TaskSeries s = new TaskSeries("Process");
            Task p ;
            LinkedList<Task> tasks = new LinkedList<>();
            for (int i=0;i<drawList.size();i++){
                System.out.println(drawList.get(i).toString());
            }
            boolean exist = false;
            for (int i=0;i<drawList.size();i++){
                for (int j = 0;j <tasks.size();j++){
                    if (tasks.get(j).getDescription().equalsIgnoreCase(drawList.get(i).pid)){
                        exist = true;
                        break;
                    }
                }
                if(!exist) {
                    p = new Task(drawList.get(i).pid, new SimpleTimePeriod(drawList.get(i).start, drawList.get(i).finish));
                    s.add(p);
                    tasks.add(p);
                }
                else {
                    Task u = null;
                    Task t = null;
                    for (int j = 0;j<tasks.size();j++){
                        if (tasks.get(j).getDescription().equalsIgnoreCase(drawList.get(i).pid)) {
                            u = tasks.get(j);
                            t = tasks.get(j);
                        }
                    }
                    p = new Task(drawList.get(i).pid, new SimpleTimePeriod(drawList.get(i).start, drawList.get(i).finish));
                    assert u != null;
                    u.addSubtask(p);
                    u.addSubtask(t);
                    s.add(u);
                    exist = false;
                }
            }
            final TaskSeriesCollection collection = new TaskSeriesCollection();
            collection.add(s);
            return collection;
        }

        private JFreeChart createChart(final GanttCategoryDataset dataset) {
            final JFreeChart chart = ChartFactory.createGanttChart(
                    "", // chart title
                    "Process", // domain axis label
                    "Time (ms)", // range axis label
                    dataset, // data
                    false, // include legend
                    true, // tooltips
                    false // urls
            );

            CategoryPlot plot = chart.getCategoryPlot();
            DateAxis axis = (DateAxis) plot.getRangeAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("SS"));
            axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND, 2));
            axis.setMinimumDate(new Date(0));
            if(drawList.getLast().finish%2==0)
                axis.setMaximumDate(new Date(drawList.getLast().finish+1));
            else
                axis.setMaximumDate(new Date(drawList.getLast().finish));
            return chart;
        }


    }
    class Box{
        String pid;
        int start;
        int finish;
        boolean draw;

        public Box(){
            draw = false;
        }
        public Box(String pid, int start, int finish) {
            this.pid = pid;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "pid='" + pid + '\'' +
                    ", start=" + start +
                    ", finish=" + finish +
                    '}';
        }
    }
}