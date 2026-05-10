abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;
    private static final double EPSILON = 1E-14;

    public abstract Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats);       
    
    public boolean same_id(Event other) {
        return this.customer.same_id(other.customer);
    }

    public Event(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    public int compareTo(Event event) {
        double d = this.eventTime - event.eventTime;
        if (d < -1.0 * EPSILON) {
            return -1;
        } else if (d > EPSILON) {
            return 1;
        } else {
            if (this.customer.compareTo(event.customer) == -1) {
                return -1;
            } else if (this.customer.compareTo(event.customer) == 1) {
                return 1;
            }         
        }
        return 1;
    }
    
    public abstract String toString();
}
