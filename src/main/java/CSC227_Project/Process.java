package CSC227_Project;

public  class Process {
    String pid; // Process ID
    int at; // Arrival Time
    int bt; // Burst Time
    int wt;
    int tat;

    public Process(String pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
    }

    public String getPid() {
        return pid;
    }

    public int getAt() {
        return at;
    }

    public int getBt() {
        return bt;
    }

    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }

    public int getTat() {
        return tat;
    }

    public void setTat(int tat) {
        this.tat = tat;
    }

    @Override
    public String toString() {
        return "Process{" +
                "pid='" + pid + '\'' +
                ", at=" + at +
                ", bt=" + bt +
                '}';
    }
}