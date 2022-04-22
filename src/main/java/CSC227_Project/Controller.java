package CSC227_Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

import java.io.File;

public class Controller {
    @FXML
    Label SRTF_File_Label;
    @FXML
    TableView<Process> SRTF_Table;
    @FXML
    TableColumn<CPU, String> SRTF_PID;
    @FXML
    TableColumn<CPU, Integer> SRTF_AT;
    @FXML
    TableColumn<CPU, Integer> SRTF_BT;

    Methods method = new Methods();

    @Override
    public void initialize(URL url, ResourceBundle bd){
        SRTF_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        SRTF_AT.setCellValueFactory(new PropertyValueFactory<>("Arrival_Time"));
        SRTF_BT.setCellValueFactory(new PropertyValueFactory<>("Burst_Time"));
        SRTF_Table.setItems(observableList);
    }

    ObservableList<Process> observableList = FXCollections.observableArrayList(
            new Process("hello",0,5)
    );

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
        }catch (Exception e){
            SRTF_File_Label.setText("No File Selected");
        }
//        SRTF_Table.getItems().addAll(method.processesSRTF);
        ObservableList<Process> contactList = FXCollections.observableArrayList();
        LinkedList<Process> or = method.processesSRTF;
        for (Process process : or) {
            contactList.addAll(FXCollections.observableArrayList(process));
        }
        SRTF_Table.setItems(contactList);
//        SRTF_Table.getItems().add(new Process("hello",0,0));

    }

    class CPU {
        String pid; // Process ID
        int burstTime; // Burst Time
        int arrivalTime; // Arrival Time
        int num_of_processes = 0;

        public CPU(String pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
        }

        public String getPid() {
            return pid;
        }

        public int getBurstTime() {
            return burstTime;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }

    }

    class Process {
        String pid; // Process ID
        int art; // Arrival Time
        int bt; // Burst Time

        public Process(String pid, int bt, int art) {
            this.pid = pid;
            this.bt = bt;
            this.art = art;
        }

        public String getPid() {
            return pid;
        }

        public int getBurstTime() {
            return bt;
        }

        public int getArrivalTime() {
            return art;
        }

        @Override
        public String toString() {
            return "Process{" +
                    "pid='" + pid + '\'' +
                    ", bt=" + bt +
                    ", art=" + art +
                    '}';
        }
    }

    class Methods {

        LinkedList<Process> processesSRTF;// contain an information about every process
        LinkedList<Process> list_of_processesRR;// contain an information about every process
        public Methods() {
            processesSRTF = new LinkedList<Process>();
            list_of_processesRR = new LinkedList<Process>();
        }

        public void readForSRTF(String filePath) {
            String pid; // Process ID
            int bt; // Burst Time
            int art; // Arrival Time
            int counter = 0;
            int successNum =0;
            boolean flag = true;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String read;
                while (((read = reader.readLine()) != null) && counter < 30) {
                    try { // this try will make reading with single point of failure
                        String[] process = read.split(",");
                        pid = process[0];
                        bt = Integer.parseInt(process[1]);
                        art = Integer.parseInt(process[2]);
                        Process p = new Process(pid, bt, art);
                        processesSRTF.add(p);
//                        SRTF_Table.getItems().add(p);
                        successNum++;
                        counter++;
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
            SRTF_File_Label.setText("Number of process read: " + successNum);
        }

//        public boolean readCSVforRR(String filePath) {
//
//            BufferedReader Reader = null;
//            String row = null;
//            boolean flag = true;
//            int pNum = 1;
//
//            // try and catch block to read the file using BufferedReader & FileReader
//            try {
//                Reader = new BufferedReader(new FileReader(filePath));
//            } catch (FileNotFoundException e1) {
//                System.out.println("ERROR --FILE NOT FOUND	");
//                flag = false;
//            } finally {
//                if (flag)
//                    ;
//                else
//                    return false;
//            }
//
//            // try and catch block to read the every row in the file and split it into
//            // attributes then create a row object and store it in a list
//            // if an error occurs flag will be set to false then false will be the returned
//            try {
//                while (true) {
//                    row = Reader.readLine();
//
//                    if (row == null)
//                        break;
//                    if (!Character.isDigit(row.charAt(0)))
//                        continue;
//                    String[] array_of_data = row.split(",");
//
//                    for (int i = 0; i < array_of_data.length; i++) {
//
//                        if (array_of_data[i].isEmpty()) {
//
//                            return false;
//
//                        }
//
//                    }
//
//                    CPU newRow = new CPU(pNum, pNum++, Integer.parseInt(array_of_data[0]));
//
//                    this.list_of_processesRR.add(newRow);
//
//                }
//            } catch (IOException e) {
//                System.out.println("ERROR IN READING LINE FROM THE FILE OCCURS");
//                flag = false;
//            } finally {
//                if (flag)
//                    ;
//                else
//                    return false;
//            }
//
//            // try and catch block to close the buffer
//            try {
//                Reader.close();
//
//            } catch (IOException e) {
//                System.out.println("ERROR IN CLOSING FILE OCCURS");
//                flag = false;
//            } finally {
//                if (flag)
//                    ;
//                else
//                    return false;
//            }
//
//            return true;
//
//        }
//
//        public void print() {
//            for (CPU row : processesSRTF) {
//                System.out.println("PID = " + row.getPid() + ", Arrival Time = " + row.getArrivalTime() + ", Burst Time = "
//                        + row.getBurstTime());
//            }
//        }

        /////////////////////////////////////////////////////

        // SRTF

        static void findWaitingTime(Process proc[], int n, int wt[]) {
            int rt[] = new int[n];

            // Copy the burst time into rt[]
            for (int i = 0; i < n; i++)
                rt[i] = proc[i].bt;

            int complete = 0, t = 0, minm = Integer.MAX_VALUE;
            int shortest = 0, finish_time;
            boolean check = false;

            // Process until all processes gets
            // completed
            while (complete != n) {

                // Find process with minimum
                // remaining time among the
                // processes that arrives till the
                // current time`
                for (int j = 0; j < n; j++) {
                    if ((proc[j].art <= t) && (rt[j] < minm) && rt[j] > 0) {
                        minm = rt[j];
                        shortest = j;
                        check = true;
                    }
                }

                if (!check) {
                    t++;
                    continue;
                }

                // Reduce remaining time by one
                rt[shortest]--;

                // Update minimum
                minm = rt[shortest];
                if (minm == 0)
                    minm = Integer.MAX_VALUE;

                // If a process gets completely
                // executed
                if (rt[shortest] == 0) {

                    // Increment complete
                    complete++;
                    check = false;

                    // Find finish time of current
                    // process
                    finish_time = t + 1;

                    // Calculate waiting time
                    wt[shortest] = finish_time - proc[shortest].bt - proc[shortest].art;

                    if (wt[shortest] < 0)
                        wt[shortest] = 0;
                }
                // Increment time
                t++;
            }
        }

        // Method to calculate turn around time
        static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
            // calculating turnaround time by adding
            // bt[i] + wt[i]
            for (int i = 0; i < n; i++)
                tat[i] = proc[i].bt + wt[i];
        }

        // Method to calculate average time
        static void findavgTime(Process proc[], int n) {
            int wt[] = new int[n], tat[] = new int[n];
            int total_wt = 0, total_tat = 0;

            // Function to find waiting time of all
            // processes
            findWaitingTime(proc, n, wt);

            // Function to find turn around time for
            // all processes
            findTurnAroundTime(proc, n, wt, tat);

            // Display processes along with all
            // details
            System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");

            // Calculate total waiting time and
            // total turnaround time
            for (int i = 0; i < n; i++) {
                total_wt = total_wt + wt[i];
                total_tat = total_tat + tat[i];
                System.out.println(" " + proc[i].pid + "\t\t" + proc[i].bt + "\t\t " + wt[i] + "\t\t" + tat[i]);
            }

            System.out.println("Average waiting time = " + (float) total_wt / (float) n);
            System.out.println("Average turn around time = " + (float) total_tat / (float) n);
        }

        //////////////////////////////////////////////////////

        // Round Robin

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

    }
}