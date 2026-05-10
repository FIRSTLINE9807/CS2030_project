class Stats {
    private final double avgWaitingTime;
    private final double totalWaitingTime;
    private final int ncustomersServed;
    private final int ncustomersWhoLeft;

    public Stats() {
        avgWaitingTime = 0.0;
        totalWaitingTime = 0.0;
        ncustomersServed = 0;
        ncustomersWhoLeft = 0;
    }

    private Stats(double avgWaitingTime, double totalWaitingTime, int ncustomersServed, 
        int ncustomersWhoLeft) {
        this.avgWaitingTime = avgWaitingTime;
        this.totalWaitingTime = totalWaitingTime;
        this.ncustomersServed = ncustomersServed;
        this.ncustomersWhoLeft = ncustomersWhoLeft; 
    }

    public Stats customerLeaves() {
        return new Stats(this.totalWaitingTime, 
            this.totalWaitingTime, this.ncustomersServed, this.ncustomersWhoLeft + 1);
    }

    public Stats customerServed(double waitingTime) {
        return new Stats(
            (this.totalWaitingTime + waitingTime) / (double)(this.ncustomersServed + 1), 
            this.totalWaitingTime + waitingTime, this.ncustomersServed + 1, 
            this.ncustomersWhoLeft);
    }
 
    @Override
    public String toString() {
        return "[" + String.format("%.3f", this.avgWaitingTime) + " " + 
            this.ncustomersServed + " " + 
            this.ncustomersWhoLeft + "]";
    }
}
