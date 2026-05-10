class ArriveEvent extends Event {

    public ArriveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        return shop.findServer(this.customer).map(x ->  
            x.canServe(this.customer) ? new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(
                new ServeEvent(this, x)),
                new Pair<Shop, Stats>(shop, stats)) : 
                new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(new 
                WaitEvent(this, x)), new Pair<Shop, Stats>(shop, stats)))
            .orElse(new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(new LeaveEvent(
            this)), 
                new Pair<Shop, Stats>(shop, stats))); 

    } 
    
   
   
    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + 
            this.customer.toString() + " arrives";
    }
}
