import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

public class CabCompany {

    private static final int SHUTTLE_CAPACITY = 15;
    private final String name;
    HashSet<Taxi> taxis = new HashSet<>();
    HashSet<Shuttle> shuttles = new HashSet<>();


    public CabCompany(String name) {
        this.name = name;
    }

    public void addTaxis(HashSet<Taxi> taxis) {
        this.taxis.addAll(taxis);
    }

    public void addShuttles(HashSet<Shuttle> shuttles) {
        this.shuttles.addAll(shuttles);
    }

    public Stream<Taxi> getTaxis() {
        return taxis.stream();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CabCompany that = (CabCompany) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Stream<Shuttle> getShuttles() {
        return shuttles.stream();
    }
}
