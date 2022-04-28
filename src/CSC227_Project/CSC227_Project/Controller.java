package CSC227_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {

    public TableColumn<Process, Integer> SRTF_WT;
    public TableColumn<Process, Integer> SRTF_TAT;
    public TableColumn<Process, Integer> SRTF_CT;
    public TableColumn<Process, Integer> SRTF_AT;
    public  TableColumn<Process, Integer> SRTF_BT;
    public Label SRTF_File_Label;
    public TableView<Process> SRTF_Output_Table;
    public TableColumn<Process, String> SRTF_Output_PID;
    public TableView<Process> SRTF_Input_Table;
    public TableColumn<Process, String> SRTF_Input_PID;
    public Label SRTF_AWT_Label;
    public Label SRTF_ATAT_Label;
    public Label SRTF_ACT_Label;
    public ScrollPane SRTF_Gantt;
    // RR
    public TableColumn<Process, Integer> RR_WT;
    public TableColumn<Process, Integer> RR_TAT;
    public TableColumn<Process, Integer> RR_CT;
    public TableColumn<Process, Integer> RR_AT;
    public TableColumn<Process, Integer> RR_BT;
    public Label RR_File_Label;
    public TableView<Process> RR_Output_Table;
    public TableColumn<Process, String> RR_Output_PID;
    public TableView<Process> RR_Input_Table;
    public TableColumn<Process, String> RR_Input_PID;
    public Label RR_AWT_Label;
    public Label RR_ATAT_Label;
    public Label RR_ACT_Label;
    public TextField RR_Input;
    public Label RR_Quantum_Label;
    public ScrollPane RR_Gantt;
    //    Priority
    public Label Priority_File_Label;
    public TableView<Process> Priority_Output_Table;
    public TableColumn<Process, String> Priority_Output_PID;
    public TableColumn<Process, Integer> Priority_WT;
    public TableColumn<Process, Integer> Priority_TAT;
    public TableColumn<Process, Integer> Priority_CT;
    public TableView<Process> Priority_Input_Table;
    public TableColumn<Process, String> Priority_Input_PID;
    public TableColumn<Process, Integer> Priority_AT;
    public TableColumn<Process, Integer> Priority_BT;
    public TableColumn<Process, Integer> Priority_P;
    public Label Priority_AWT_Label;
    public Label Priority_ATAT_Label;
    public Label Priority_ACT_Label;
    public ScrollPane Priority_Gantt;

    private boolean first = true;
    private Gantt g = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SRTF_Input_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        SRTF_Output_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        SRTF_AT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
        SRTF_BT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
        SRTF_WT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
        SRTF_TAT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnAroundTime"));
        SRTF_CT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("completedTime"));
        RR_Input_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        RR_Output_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        RR_AT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
        RR_BT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
        RR_WT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
        RR_TAT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnAroundTime"));
        RR_CT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("completedTime"));
        Priority_Input_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        Priority_Output_PID.setCellValueFactory(new PropertyValueFactory<Process, String>("pID"));
        Priority_AT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
        Priority_BT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
        Priority_WT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
        Priority_TAT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnAroundTime"));
        Priority_CT.setCellValueFactory(new PropertyValueFactory<Process, Integer>("completedTime"));
        Priority_P.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
    }


    Methods method = new Methods();

    @FXML
    protected void SRTFFileChooser(ActionEvent event) {
        if (!first){
            restart();
        }
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fc.showOpenDialog(null);
        try {
            method.readForSRTF(file.toPath().toString());
            method.SRTF();
            SRTF_Input_Table.getItems().addAll(method.processesSRTF);
            SRTF_Output_Table.getItems().addAll(method.processesSRTF);
            g = new Gantt(method.Boxes,method.processesSRTF.size(),SRTF_Gantt);
        } catch (Exception e) {
            SRTF_File_Label.setText("No File Selected");
        }
        first = false;

    }
    @FXML
    protected void  RRFileChooser(ActionEvent event){
        if (!first){
            restart();
        }
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fc.showOpenDialog(null);
        try {
            method.readForRR(file.toPath().toString());
            RR_Input_Table.getItems().addAll(method.processesRR);
            RR_Output_Table.getItems().addAll(method.processesRR);
        } catch (Exception e) {
            RR_File_Label.setText("No File Selected");
        }
        first = false;
    }

    @FXML
    protected void RunRR(ActionEvent event){
        if (!first){
            runRestart();
        }
        try {
            int quantum = Integer.parseInt(RR_Input.getText());
            method.RR(quantum);
            RR_Input_Table.refresh();
            RR_Output_Table.refresh();
            g = new Gantt(method.Boxes,method.processesRR.size(),RR_Gantt);
            first = false;
            RR_Quantum_Label.setText("Your Quantum number is "+ quantum);
        }catch (Exception e){
            RR_Quantum_Label.setText("Please enter valid quantum number");
        }


    }

    @FXML
    protected void PriorityFileChooser(ActionEvent event) {
        if (!first){
            restart();
        }
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fc.showOpenDialog(null);
        try {
            method.readForPriority(file.toPath().toString());
            method.Priority();
            Priority_Input_Table.getItems().addAll(method.processesPriority);
            Priority_Output_Table.getItems().addAll(method.processesPriority);
            g = new Gantt(method.Boxes,method.processesPriority.size(),Priority_Gantt);
        } catch (Exception e) {
            Priority_File_Label.setText("No File Selected");
        }
        first = false;
    }

    private void runRestart(){
        RR_AWT_Label.setText("Average waiting time:  ");
        RR_ATAT_Label.setText("Average turn around time:  ");
        RR_ACT_Label.setText("Average completion time:  ");
        RR_Gantt.setContent(null);
        method.Boxes.clear();
    }
    private void restart(){
        SRTF_File_Label.setText("There is no file selected");
        SRTF_AWT_Label.setText("Average waiting time:  ");
        SRTF_ACT_Label.setText("Average completion time:  ");
        SRTF_ATAT_Label.setText("Average turn around time:  ");
        SRTF_Input_Table.getItems().clear();
        SRTF_Output_Table.getItems().clear();
        SRTF_Gantt.setContent(null);
        method.processesSRTF.clear();
        method.Boxes.clear();
        RR_File_Label.setText("There is no file selected");
        RR_Output_Table.getItems().clear();
        RR_Input_Table.getItems().clear();
        RR_AWT_Label.setText("Average waiting time:  ");
        RR_ATAT_Label.setText("Average turn around time:  ");
        RR_ACT_Label.setText("Average completion time:  ");
        RR_Input.clear();
        RR_Quantum_Label.setText("There is no quantum number");
        method.processesRR.clear();
        RR_Gantt.setContent(null);
        Priority_File_Label.setText("There is no file selected");
        Priority_AWT_Label.setText("Average waiting time:  ");
        Priority_ACT_Label.setText("Average completion time:  ");
        Priority_ATAT_Label.setText("Average turn around time:  ");
        Priority_Input_Table.getItems().clear();
        Priority_Output_Table.getItems().clear();
        Priority_Gantt.setContent(null);
        method.processesPriority.clear();
    }

    class Methods {

        LinkedList<Process> processesSRTF;// contain an information about every process
        LinkedList<Process> processesRR;// contain an information about every process
        LinkedList<Process> processesPriority;
        LinkedList<Box> Boxes;

        public Methods() {
            Boxes = new LinkedList<Box>();
            processesSRTF = new LinkedList<Process>();
            processesRR = new LinkedList<Process>();
            processesPriority = new LinkedList<Process>();
        }

        public void readForSRTF(String filePath) {
            String pId; // CSC227_Project.Process ID
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
                        pId = process[0];
                        at = Integer.parseInt(process[1]);
                        bt = Integer.parseInt(process[2]);
                        Process p = new Process(pId, at, bt);
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

        public void readForRR(String filePath) {
            String pId; // CSC227_Project.Process ID
            int bt; // Burst Time
            int counter = 0;
            int successNum = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String read;
                while (((read = reader.readLine()) != null) && counter < 30) {
                    try { // this try will make reading with single point of failure
                        //PID,AT,BT
                        pId = read;
                        bt = Integer.parseInt(reader.readLine());
                        Process p = new Process(pId, bt);
                        processesRR.add(p);
                        successNum++;
                        counter++;
                        RR_File_Label.setText("Number of process read: " + successNum);
                    } catch (Exception e) {
                        RR_File_Label.setText("File read with some record missing");
                    }
                }
            } catch (Exception e) {
                RR_File_Label.setText("No File Selected");
            }

        }

        public void readForPriority(String filePath){
            String pId; // CSC227_Project.Process ID
            int bt; // Burst Time
            int pr;
            int successNum = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String read;
                while (((read = reader.readLine()) != null) && successNum < 30) {
                    try { // this try will make reading with single point of failure
                        //PID,AT,BT
                        pId = read;
                        read = reader.readLine();
                        String[] l = read.split(",");
                        bt = Integer.parseInt(l[0]);
                        pr = Integer.parseInt(l[1]);
                        if (pr < 1 || pr > 4)
                            break;
                        Process p = new Process(pId, bt, pr, 0);
                        processesPriority.add(p);
                        successNum++;
                        Priority_File_Label.setText("Number of process read: " + successNum);
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception e) {
                Priority_File_Label.setText("No File Selected");
            }
        }


        /* SRTF **/
        void SRTF() {
            LinkedList<Integer> remainingTime = new LinkedList<>();

            for (Process process : processesSRTF) {
                remainingTime.add(process.getBurstTime());
            }

            int complete = 0, timer = 0, shortest = 0;
            int min = Integer.MAX_VALUE;
            int finishTime;
            Box b = null;

            while (complete != processesSRTF.size()) { // to ensure all processes completed

                for (int i = 0; i < processesSRTF.size(); i++) { // find minimum remaining time
                    if ((processesSRTF.get(i).getArrivalTime() <= timer) && (remainingTime.get(i) < min) && remainingTime.get(i) > 0) {
                        min = remainingTime.get(i);
                        shortest = i;
                    }
                }
                { // this block will help us make the Gantt Chart
                    boolean exist = false;
                    for (Box box : Boxes) {
                        if (box.pid.equalsIgnoreCase(processesSRTF.get(shortest).getPID())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist || !Boxes.getLast().pid.equalsIgnoreCase(processesSRTF.get(shortest).getPID())) {
                        b = new Box();
                        b.start = timer;
                        b.pid = processesSRTF.get(shortest).getPID();
                        b.finish = timer + 1;
                        Boxes.add(b);
                    } else {
                        assert b != null;
                        b.finish = timer + 1;
                    }
                }

                remainingTime.set(shortest, remainingTime.get(shortest) - 1);

                if (remainingTime.get(shortest) == 0) { // if process finished
                    min = Integer.MAX_VALUE;
                    complete++;
                    finishTime = timer + 1; // finish time of process
                    int wt = finishTime - processesSRTF.get(shortest).getBurstTime() - processesSRTF.get(shortest).getArrivalTime();
                    int tat = processesSRTF.get(shortest).getBurstTime() + wt;
                    processesSRTF.get(shortest).setTurnAroundTime(tat);
                    processesSRTF.get(shortest).setWaitingTime(wt);
                    processesSRTF.get(shortest).setCompletedTime(finishTime);
                    if (processesSRTF.get(shortest).getWaitingTime() < 0)
                        processesSRTF.get(shortest).setWaitingTime(0);
                }
                // Increment timer
                timer++;
            }
            // to calculate the average
            int totalWt = 0, totalTat = 0, totalCt = 0;

            for (Process process : processesSRTF) {
                totalWt += process.getWaitingTime();
                totalTat += process.getTurnAroundTime();
                totalCt += process.getCompletedTime();

            }

            BigDecimal totalCT = new BigDecimal((double) totalCt / (double) processesSRTF.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalTAT = new BigDecimal((double) totalTat / (double) processesSRTF.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalWT = new BigDecimal((double) totalWt / (double) processesSRTF.size()).setScale(2, RoundingMode.HALF_EVEN);
            SRTF_AWT_Label.setText(SRTF_AWT_Label.getText() + totalWT + " ms");
            SRTF_ATAT_Label.setText(SRTF_ATAT_Label.getText() + totalTAT + " ms");
            SRTF_ACT_Label.setText(SRTF_ACT_Label.getText() + totalCT + " ms");

        }

        /* Round Robin **/

        void RR(int quantum) {
            LinkedList<Integer> remainingTime = new LinkedList<>();
            for (Process process : processesRR) {
                remainingTime.add(process.getBurstTime());
            }
            int complete = 0, timer = 0;
            Box b = null;
            while (complete != processesRR.size()) { // to ensure all processes completed
                for (int i = 0; i < processesRR.size(); i++) {
                    if (remainingTime.get(i) > 0) { // process not completed
                        if (remainingTime.get(i) > quantum) { // not last round
                            remainingTime.set(i, (remainingTime.get(i) - quantum));
                            b = new Box();
                            b.start = timer;
                            b.pid = processesRR.get(i).getPID();
                            timer+=quantum;
                            b.finish = timer;
                            Boxes.add(b);
                        }
                        else { // last round (completed)
                            b = new Box();
                            b.start = timer;
                            b.pid = processesRR.get(i).getPID();
                            timer += remainingTime.get(i);
                            b.finish = timer;
                            Boxes.add(b);
                            remainingTime.set(i, 0);
                            complete++;
                            int wt = b.finish - processesRR.get(i).getBurstTime() - processesRR.get(i).getArrivalTime();
                            int tat = processesRR.get(i).getBurstTime() + wt;
                            processesRR.get(i).setTurnAroundTime(tat);
                            processesRR.get(i).setWaitingTime(wt);
                            processesRR.get(i).setCompletedTime(b.finish);
                            if (processesRR.get(i).getWaitingTime() < 0)
                                processesRR.get(i).setWaitingTime(0);
                        }
                    }
                }
            }
            // to calculate the average
            int totalWt = 0, totalTat = 0, totalCt = 0;
            for (Process process : processesRR) {
                totalWt += process.getWaitingTime();
                totalTat += process.getTurnAroundTime();
                totalCt += process.getCompletedTime();
            }

            BigDecimal totalCT = new BigDecimal((double) totalCt / (double) processesRR.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalTAT = new BigDecimal((double) totalTat / (double) processesRR.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalWT = new BigDecimal((double) totalWt / (double) processesRR.size()).setScale(2, RoundingMode.HALF_EVEN);
            RR_AWT_Label.setText(RR_AWT_Label.getText() + totalWT + " ms");
            RR_ATAT_Label.setText(RR_ATAT_Label.getText() + totalTAT + " ms");
            RR_ACT_Label.setText(RR_ACT_Label.getText() + totalCT + " ms");
        }

        void Priority() {
            int complete = 0, timer = 0;
            Box b = null;
            processesPriority.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });
            while (complete != processesPriority.size()) { // to ensure all processes completed
                for (Process process : processesPriority) {
                    b = new Box();
                    b.start = timer;
                    b.pid = process.getPID();
                    timer += process.getBurstTime();
                    b.finish = timer;
                    Boxes.add(b);
                    complete++;
                    int wt = b.start - process.getArrivalTime();
                    int tat = process.getBurstTime() + wt;
                    process.setTurnAroundTime(tat);
                    process.setWaitingTime(wt);
                    process.setCompletedTime(b.finish);
                    if (process.getWaitingTime() < 0)
                        process.setWaitingTime(0);
                }
                }
            // to calculate the average
            int totalWt = 0, totalTat = 0, totalCt = 0;

            for (Process process : processesPriority) {
                totalWt += process.getWaitingTime();
                totalTat += process.getTurnAroundTime();
                totalCt += process.getCompletedTime();
            }

            BigDecimal totalCT = new BigDecimal((double) totalCt / (double) processesPriority.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalTAT = new BigDecimal((double) totalTat / (double) processesPriority.size()).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal totalWT = new BigDecimal((double) totalWt / (double) processesPriority.size()).setScale(2, RoundingMode.HALF_EVEN);
             Priority_AWT_Label.setText(Priority_AWT_Label.getText() + totalWT + " ms");
             Priority_ATAT_Label.setText(Priority_ATAT_Label.getText() + totalTAT + " ms");
             Priority_ACT_Label.setText(Priority_ACT_Label.getText() + totalCT + " ms");
        }
        }
    class Gantt{

        private LinkedList<Controller.Box> drawList;
        private int numberOfProcess;
        private int lastMS;

        public Gantt(LinkedList<Controller.Box> drawList, int n, ScrollPane sp) {
            this.drawList = drawList;
            this.numberOfProcess = n;
            this.lastMS = drawList.getLast().finish;
            final GanttCategoryDataset dataset = createDataset();
            final JFreeChart chart = createChart(dataset);
            final SwingNode chartSwingNode = new SwingNode();
            chartSwingNode.setContent(
                    new ChartPanel(chart)
            );
            int width;
            int height;
            if (numberOfProcess <=5)
                height = 240;
            else if (numberOfProcess <=10)
                height = 340;
            else if (numberOfProcess <=15)
                height = 440;
            else if (numberOfProcess <=20)
                height = 540;
            else if (numberOfProcess <=25)
                height = 690;
            else
                height = 790;

            if (lastMS <=50)
                width = 800;
            else if (lastMS <=100)
                width = 1200;
            else if (lastMS <=150)
                width = 1800;
            else if (lastMS <=200)
                width = 2400;
            else if (lastMS <=250)
                width = 3000;
            else
                width = 2800;
            chartSwingNode.getContent().setPreferredSize(new Dimension(width, height));
            sp.setContent(chartSwingNode);
            sp.setPrefSize(width+7, height+7);
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            sp.setMaxSize((size.width-50), (size.height/2.0));
        }

        public GanttCategoryDataset createDataset() {
            final TaskSeries s = new TaskSeries("Process");
            Task p ;
            LinkedList<Task> tasks = new LinkedList<>();
            boolean exist = false;
            for (Box box : drawList) {
                for (Task task : tasks) {
                    if (task.getDescription().equalsIgnoreCase(box.pid)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    p = new Task(box.pid, new SimpleTimePeriod(box.start, box.finish));
                    s.add(p);
                    tasks.add(p);
                } else {
                    Task u = null;
                    Task t = null;
                    for (Task task : tasks) {
                        if (task.getDescription().equalsIgnoreCase(box.pid)) {
                            u = task;
                            t = task;
                        }
                    }
                    p = new Task(box.pid, new SimpleTimePeriod(box.start, box.finish));
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
            chart.setBorderVisible(true);

            CategoryPlot plot = chart.getCategoryPlot();
            DateAxis axis = (DateAxis) plot.getRangeAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("SS"));
            axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND, 2));
            axis.setMinimumDate(new Date(0));
            if(drawList.getLast().finish%2==0)
                axis.setMaximumDate(new Date(drawList.getLast().finish));
            else
                axis.setMaximumDate(new Date(drawList.getLast().finish+1));
            GanttRenderer renderer = (GanttRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(true);
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