package CSC227_Project;

public  class Process {
    String pID; // CSC227_Project.Process ID
    int arrivalTime; // Arrival Time
    int burstTime; // Burst Time
    int waitingTime;
    int turnAroundTime;
    int completedTime;
    int priority;

    public Process(String pID, int arrivalTime, int burstTime) {
        this.pID = pID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public Process(String pID, int burstTime) {
        this.pID = pID;
        this.burstTime = burstTime;
    }

    public Process(String pID, int burstTime, int priority, int arrivalTime) {
        this.pID = pID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPID() {
        return pID;
    }

    public void getPID(String pID) {
        this.pID = pID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getCompletedTime() {
        return completedTime;
    }

    public void setComplatedTime(int completedTime) {
        this.completedTime = completedTime;
    }

    @Override
    public String toString() {
        return "CSC227_Project.Process{" +
                "pID='" + pID + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", waitingTime=" + waitingTime +
                ", turnAroundTime=" + turnAroundTime +
                ", completedTime=" + completedTime +
                ", priority=" + priority +
                '}';
    }
}