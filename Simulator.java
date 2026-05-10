import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final InfList<Pair<Integer,Double>> arrivals;
    private final Supplier<Double> serviceTime;
    private final PQ<Event> pq;
    private final int qmax;

    Simulator(int numOfServers, int qmax, int numOfCustomers, 
            InfList<Pair<Integer,Double>> arrivals, Supplier<Double> serviceTime) {
        this.qmax = qmax;
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals; 
        this.serviceTime = serviceTime; 
        this.pq = arrivals.map(x -> new ArriveEvent(new Customer(x
            .t().intValue(),
            x.u().doubleValue()), x.u().doubleValue()))
            .reduce(new PQ<Event>((a,b) -> a.compareTo(b)), (a,b) -> a.add(b));
    }

  
    Maybe<State> run() {
        State init = new State(pq, new Shop(numOfServers, this.serviceTime, this.qmax));

        return InfList.iterate(init, s -> s.next()).filter(s -> !s.ifPresent()).findFirst()
            .map(result -> result.appendStats()).or(() -> Maybe.empty()); 
    }
}
