public class Taxi extends Vehicle {

    private final int STANDARD_FARE = 30;

    // Should be refactored to something that describes a point in space.
    // like an object with an x and y coordinate that can be compared for
    // distance. locations could be put in a hashmap
    private String destination;
    private Passenger passenger;
    private int fare;

    public Taxi(String licensePlate) {
        super(licensePlate);
        this.passenger = null;
        this.fare = STANDARD_FARE;
    }

    public String getDestination() {
        return this.destination;
    }

    @Override
    public boolean isAvailableTo(Passenger passenger) {
        //TODO Make distance-dependent (a taxi too far away is not available)
        return this.passenger == null ||
                this.getDestination().equals(passenger.getDestination());
    }

    @Override
    public int getFareFor(Passenger passenger) {
        //TODO Make it return a distance-based value.
        return fare;
    }

    @Override
    public void assign(Passenger passenger) {
        this.passenger = passenger;
        this.setDestination(passenger.getLocation());
    }

    @Override
    public boolean isFull() {
        return this.passenger != null;
    }


    public void setDestination(String destination) {
        this.destination = destination;
    }
}
