class Customer implements Comparable<Customer> {                      
    private final int id;
    private final double arrivalTime;   
    private static final double EPSILON = 1E-15;
    private final int queuePos;
    private final double timeServed;

    public Customer(int id, double arrivalTime) { 
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.queuePos = 0;
        this.timeServed = arrivalTime;
    }

    private Customer(int id, double arrivalTime, int queuePos, double timeServed) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.queuePos = queuePos;
        this.timeServed = timeServed;
    }

    protected Customer goForward(double time) {
        return new Customer(this.id, this.arrivalTime, queuePos - 1, time);
    }
    
    public boolean canBeServed(double time) {
        return timeServed - time >= 0.0 - EPSILON &&
            queuePos <= 1;
    }
    
    protected boolean same_id(Customer other) {
        return this.id == other.id;
    }

    public double serveTill(double serviceTime) {
        return this.timeServed + serviceTime;
    }
    
    public double waitingTime() {
        return this.timeServed - this.arrivalTime;
    }

    protected Customer queue(int position) {
        return new Customer(this.id, this.arrivalTime, position, this.timeServed);
    }
    
    @Override
    public int compareTo(Customer other) {
        if (this.arrivalTime - other.arrivalTime <= EPSILON 
            && this.arrivalTime - other.arrivalTime >= -1.0 * EPSILON) {
            return this.id < other.id ? -1 : 1;
        }
        return this.arrivalTime < other.arrivalTime ? -1 : 1;
    }
    
    @Override
    public String toString() {
        return "customer " + id;
    }
}
