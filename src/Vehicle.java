import java.util.Objects;

public abstract class Vehicle {

    private final String licensePlate;
    private String location;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLocation() {
        return location;
    }

    public void goTo(String location) {
        this.location = location;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public abstract boolean isAvailableTo(Passenger passenger);

    public abstract int getFareFor(Passenger passenger);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return licensePlate.equals(vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }
}
