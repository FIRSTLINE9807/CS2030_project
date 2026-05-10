class WaitingEvent extends Event {
    private final Server server;

    public WaitingEvent(Customer customer, Server server, double eventTime) {
        super(customer, eventTime);
        this.server = server;
    }
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        Server newServer = shop.getUpdatedServer(this.server); 
        return newServer.canServe(this.customer) ? 
            new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(
                new ServeEvent(this, newServer)), new Pair<Shop, Stats>(shop, stats)) : 
            new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.of(new WaitingEvent(
            newServer.moveForward(this.customer), 
            server, newServer.newEventTime())), new Pair<Shop, Stats>(shop, stats)); 
    }

    @Override
    public String toString() {
        return "";
    }
}

