
class State {

    private final Shop shop; 
    private final String finalString;
    private final PQ<Event> pq;
    private final boolean eventPresent;
    private final Stats stats;

    public State(PQ<Event> pq, Shop shop) {
        this.shop = shop;
        this.pq = pq;
        this.finalString = "";
        this.eventPresent = pq.isEmpty() ? false : true;
        this.stats = new Stats();
    }

    private State(Shop newShop, String finalString,
            PQ<Event> pq, Maybe<Event> maybeNext, boolean eventPresent, Stats stats) {
        this.shop = newShop;
        this.eventPresent = eventPresent; 
        this.pq = maybeNext.map(nextEvent -> pq.poll().u().add(nextEvent)).orElse(pq.poll().u());
        this.finalString = finalString;
        this.stats = stats; 
    }
    
    private State(PQ<Event> pq, Shop shop, String finalString, boolean eventPresent, Stats stats) {
        this.shop = shop;
        this.pq = pq;
        this.finalString = finalString;
        this.eventPresent = eventPresent;
        this.stats = stats; 
    }  
    

    public State next() {
      
        return this.pq.poll().t().map(event -> { 
            Pair<Maybe<Event>, Pair<Shop, Stats>> pair = event.next(this.shop, this.stats);
            String newString = event.toString().isEmpty() ? this.finalString : 
                this.finalString + event.toString() + '\n';
            return new State(pair.u().t(), 
            newString, this.pq, pair.t(), true, pair.u().u()); })
            .orElse(new State(this.pq, this.shop, this.finalString, false, this.stats)); 
    }
    
    public State appendStats() {
        return new State(this.pq, this.shop, 
            this.finalString + this.stats.toString(), false, this.stats);
    }

    boolean isEmpty() {
        return !this.eventPresent;
    }
    
    boolean ifPresent() {
        return this.eventPresent;
    }

    @Override
    public String toString() {
        return finalString;
    }    
}
