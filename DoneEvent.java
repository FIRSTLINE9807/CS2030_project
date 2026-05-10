class DoneEvent extends Event {


    public DoneEvent(Event event, double timeDone) {
        super(event.customer, timeDone);
    }
    
    @Override
    public Pair<Maybe<Event>, Pair<Shop, Stats>> next(Shop shop, Stats stats) {
        return new Pair<Maybe<Event>, Pair<Shop, Stats>>(Maybe.empty(), 
            new Pair<Shop, Stats>(shop, stats));
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + 
            " " + this.customer.toString()  +  " done";
    }
}


