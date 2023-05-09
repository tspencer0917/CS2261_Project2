import java.util.Objects;

public class Passenger {

    private final String name;
    private String location;
    private String destination;

    public Passenger(String name, String location, String destination) {
        this.name = name;
        this.location = location;
        this.destination = destination;
    }

    public String getLocation() {
        return location;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return name.equals(passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
