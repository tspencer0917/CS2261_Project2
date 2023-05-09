import java.util.Optional;

public class Taxi extends Vehicle {

    private final int STANDARD_FARE = 30;


    private String location;
    private String destination;
    private Passenger passenger;
    private int fare;

    public Taxi(String licensePlate) {
        super(licensePlate);
        this.passenger = null;
        this.fare = STANDARD_FARE;
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

    public String getDestination() {
        return this.destination;
    }
}
