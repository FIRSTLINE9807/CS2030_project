import java.util.Scanner;
import java.util.function.Supplier;

void main() {
    Scanner sc = new Scanner(System.in);
    int numOfServers = sc.nextInt();
    int qmax = 0;
    int numOfCustomers = sc.nextInt();
  
    Supplier<Double> serviceTime = () -> sc.nextDouble();
   
    sc.nextLine(); // removes trailing newline
    InfList<Pair<Integer,Double>> arrivals = InfList.iterate(1, x -> x + 1)
        .limit(numOfCustomers)
        .map(x -> new Pair<>(sc.nextInt(), sc.nextDouble()));
   
    new Simulator(numOfServers, qmax, numOfCustomers, arrivals, serviceTime)
        .run().ifPresent(x -> System.out.println(x));
}
