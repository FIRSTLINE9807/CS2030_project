class LeaveEvent extends Event {
 
    public LeaveEvent(Event event) { 
        super(event.customer, event.eventTime);
    }
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        return new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.empty(), 
            new Pair<Shop, Stats>(shop, stats.customerLeaves()));
    }
    
    @Override
    public String toString() {
        return String.format("%.3f",this.eventTime) + " " + customer.toString() + " leaves";
    }
}
