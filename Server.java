
class Server {
    private final int id;
    private final double endTime;
    private final int maxQLength;
    private final int queueLength;

    public Server(int id) { 
        this.id = id;
        this.endTime = 0.0;
        this.maxQLength = 0;
        this.queueLength = 0;
    }
    
    public Server(int id, int maxQLength) {
        this.id = id;
        this.endTime = 0.0;
        this.maxQLength = maxQLength;
        this.queueLength = 0;
    }

    private Server(int id, double endTime, int maxQLength, int queueLength) {
        this.id = id;
        this.endTime = endTime;
        this.maxQLength = maxQLength;
        this.queueLength = queueLength < 0 ? 0 : queueLength;
    }
     
    Server serve(Customer customer, double serviceTime) {
        return new Server(this.id, customer.serveTill(serviceTime), this.maxQLength, 
            this.queueLength - 1);
    }                
    
    public Customer moveForward(Customer customer) {
        return customer.goForward(this.endTime);
    }
    
    double newEventTime() {
        return this.endTime;
    }

    Server queueCustomer(Customer customer) {
        return new Server(this.id, this.endTime, this.maxQLength, this.queueLength + 1);
    }
    
    protected Customer moveCustomer(Customer customer) {
        return customer.queue(this.queueLength + 1);
    }

    boolean same_id(Server other) {
        return other.id == this.id;
    }

   
    boolean canServe(Customer customer) {
        return customer.canBeServed(this.endTime);
    }    
    
    boolean canQueue() {
        return this.queueLength < this.maxQLength;
    }

    @Override
    public String toString() {
        return "server " + id;
    }
}
