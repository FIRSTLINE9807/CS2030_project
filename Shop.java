import java.util.function.Supplier;

class Shop {

    private final int nservers;
    private final Supplier<Double> commonservicetime;
    private final InfList<Server> serverList; 
    private final int qmax;

    public Shop(int nservers, Supplier<Double> commonservicetime) {
        this.nservers = nservers;
        this.commonservicetime = commonservicetime;
        this.qmax = 0;
        this.serverList = InfList.iterate(1, x -> x + 1)
            .limit(nservers).map(i -> new Server(i));
    }

    public Shop(int nservers, Supplier<Double> commonservicetime, int qmax) {
        this.nservers = nservers;
        this.commonservicetime = commonservicetime;
        this.qmax = qmax;
        this.serverList = InfList.iterate(1, x -> x + 1)
            .limit(nservers).map(i -> new Server(i, qmax));
    }



    
    private Shop(Supplier<Double> commonservicetime, int nservers, InfList<Server> serverList, 
        int qmax) {
        this.nservers = nservers;
        this.commonservicetime = commonservicetime;
        this.qmax = qmax;
        this.serverList = serverList;
    }

    Shop update(Server server) { 
        return new Shop(this.commonservicetime, this.nservers,  
            serverList.map(x -> x.same_id(server) ? server : x), this.qmax);
    }       

    public Maybe<Server> findServer(Customer customer) {
        return serverList.filter(x -> x.canServe(customer)).findFirst().or(
            () -> serverList.filter(y -> y.canQueue()).findFirst());
    }
    
    Server getUpdatedServer(Server server) {
        return serverList.filter(s -> s.same_id(server)).findFirst().orElse(server);
    }

    double getTime() {
        return this.commonservicetime.get();
    }

    @Override
    public String toString() {
         
        return "Shop:" + serverList.map(x -> "<" + x.toString() + ">")
            .reduce("", (x, y) -> x + y);  
    }
}
