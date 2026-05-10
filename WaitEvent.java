class WaitEvent extends Event {
    private final Server server;
 
    public WaitEvent(Event event, Server server) {
        super(event.customer, event.eventTime);
        this.server = server;
    }    
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        return new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(
            new WaitingEvent(this.server.moveCustomer(this.customer), 
            this.server.queueCustomer(this.customer), this.eventTime)),
            new Pair<Shop, Stats>(shop.update(this.server.queueCustomer(this.customer)), 
            stats));
    }


    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + this.customer.toString() + 
            " waits at " + this.server.toString();
    }
}
