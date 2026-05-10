class ServeEvent extends Event {
    private final Server server;
    private final Event event;

    public ServeEvent(Event event, Server server) {
        super(event.customer, event.eventTime);
        this.event = event;    
        this.server = server;
    }
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        double serviceTime = shop.getTime(); 
        return new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(new DoneEvent(this, 
            this.customer.serveTill(serviceTime))),
            new Pair<Shop, Stats>(shop
            .update(shop.getUpdatedServer(this.server)
            .serve(this.customer, serviceTime)),stats.customerServed(this.customer.waitingTime())));
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + 
            " " + customer.toString() + " serve by " + server.toString();
    }
}
    
