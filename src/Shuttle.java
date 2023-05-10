import java.util.*;

public class Shuttle extends Vehicle {

    private static final int DEFAULT_CAPACITY = 15;
    private final int passengerCapacity;
    private final LinkedHashMap<String, Integer> stops = new LinkedHashMap<>();
    private final HashSet<Passenger> passengers = new HashSet<>();

    public Shuttle(String licensePlate) {
        this(licensePlate, DEFAULT_CAPACITY);
    }

    public Shuttle(String licensePlate, int passengerCapacity) {
        super(licensePlate);
        this.passengerCapacity = passengerCapacity;
    }

    public void addStop(String stop, int fare) {
        stops.put(stop, fare);
    }

    @Override
    public boolean isAvailableTo(Passenger passenger) {
        return this.passengers.size() < this.passengerCapacity
                && this.stops.containsKey(passenger.getDestination())
                && this.stops.containsKey(passenger.getLocation());
    }

    // Loops through every stop from the position
    @Override
    public int getFareFor(Passenger passenger) {
        boolean passedStart = false, passedEnd = false;
        int fare = 0;
        while (!passedEnd) {
            for (Map.Entry<String, Integer> entry : stops.entrySet()) {
                if (passedStart && !passedEnd) {
                    fare += entry.getValue();
                } else if (passedStart & passedEnd) {
                    break;
                }
                // || with itself keeps it true once flipped
                passedEnd = passedEnd ||
                        (entry.getKey().equals(passenger.getDestination()) &&
                                passedStart);

                // || with itself keeps it true once flipped
                passedStart = passedStart ||
                        entry.getKey().equals(passenger.getLocation());
            }
        }
        return fare;
    }

    @Override
    public void assign(Passenger passenger) {
        // There should be separate collections of passengers that are on the
        // shuttle and anticipated passengers. anticipated passengers should
        // probably be a hashmap<Location, Passenger[]> or something like that.
        // We're just going to pretend that this makes sense for now.
        passengers.add(passenger);
    }

    @Override
    public boolean isFull() {
        return passengers.size() <= passengerCapacity;
    }

    @Override
    public String getDestination() {
        Iterator<String> iter = this.stops.keySet().iterator();
        while(iter.hasNext()) {
            if (iter.next().equals(this.getLocation()) && iter.hasNext()) {
                return iter.next();
            }
        }
            return this.stops.keySet().stream().findFirst().get();
    }

}
