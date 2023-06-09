import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

public class CabCompany {

    private final String name;
    HashSet<Taxi> taxis = new HashSet<>();
    HashSet<Shuttle> shuttles = new HashSet<>();


    public CabCompany(String name) {
        this.name = name;
    }

    public void addTaxis(HashSet<Taxi> taxis) {
        taxis.forEach(taxi -> taxi.setLocation("%s's Garage".formatted(this.name)));
        this.taxis.addAll(taxis);
    }

    public void addShuttles(HashSet<Shuttle> shuttles) {
        shuttles.forEach(shuttle -> shuttle.setLocation("%s's Garage".formatted(this.name)));
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
