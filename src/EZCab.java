import java.util.*;
import java.util.stream.Stream;

public class EZCab {

    private static final HashMap<String, CabCompany> cabCompanies =
            new HashMap<>();

    public static void main(String[] args) {
        do {
            CabCompany cabCompany;
            int selection = IOHelper.promptForMenuSelection();
            switch (selection) {
                case 1 -> {
                    cabCompany = IOHelper.promptForCompanyInfo();
                    cabCompany = findOrAddCabCompany(cabCompany);
                    cabCompany.addTaxis(IOHelper.promptForTaxis());
                }
                case 2 -> {
                    cabCompany = IOHelper.promptForCompanyInfo();
                    cabCompany = findOrAddCabCompany(cabCompany);
                    cabCompany.addShuttles(IOHelper.promptForShuttles());
                }
                case 3 -> {
                    Passenger passenger = IOHelper.promptForPassengerInfo();
                    Taxi taxi =
                            (Taxi) findAvailableVehicle(passenger,
                                    getAllTaxis())
                                    .orElse(null);
                    Shuttle shuttle =
                            (Shuttle) findAvailableVehicle(passenger,
                                    getAllShuttles())
                                    .orElse(null);
                    IOHelper.printTaxiAndShuttleOptions(passenger, taxi,
                            shuttle);
                    if (taxi == null && shuttle == null) {
                        System.out.println("Sorry, there are no available " +
                                "rides. Try again later.");
                        continue;
                    } else if (shuttle == null && IOHelper.confirmBooking(taxi)) {
                        taxi.assign(passenger);
                    } else if (taxi == null && IOHelper.confirmBooking(shuttle)) {
                        passenger.book(shuttle);
                    } else {
                        Vehicle vehicleToBook = IOHelper.promptForBinaryChoice(
                                "Taxi or Shuttle? [T/S]: ", "T", "S")
                                ? taxi : shuttle;
                        assert vehicleToBook != null;
                        IOHelper.confirmBooking(vehicleToBook);
                    }
                }
                case 4 -> {
                    Vehicle vehicleToCheck =
                            IOHelper.promptForLicensePlate(cabCompanies).orElse(null);
                    if (vehicleToCheck != null) {
                        System.out.printf(
                                """
                                        Vehicle: %s
                                        Location: %s
                                        Destination: %s
                                        Status: %s
                                        %n""", vehicleToCheck.getLicensePlate(),
                        vehicleToCheck.getLocation(),
                        vehicleToCheck.getDestination(),
                        (vehicleToCheck.isFull() ?
                                "No space available" :
                                "Space available"));
                    } else {
                        System.out.println("We don't have a record of that vehicle");
                    }
                }
                default -> System.out.println("That is not a valid option");
            }
            System.out.println();
        } while (IOHelper.promptForBinaryChoice(
                "Would you like to return to the main menu? [Y/N]: ", "Y", "N"));
    }

    private static Stream<Vehicle> getAllTaxis() {
        return cabCompanies.values().stream().flatMap(CabCompany::getTaxis);
    }

    private static Stream<Vehicle> getAllShuttles() {
        return cabCompanies.values().stream().flatMap(CabCompany::getShuttles);
    }

    private static Optional<Vehicle> findAvailableVehicle(Passenger passenger,
                                                          Stream<Vehicle> vehicles) {
        return vehicles.filter(vehicle -> vehicle.isAvailableTo(passenger))
                .findAny();
    }

    private static CabCompany findOrAddCabCompany(CabCompany company) {
        return cabCompanies.putIfAbsent(company.getName(), company) == null ?
                company : cabCompanies.get(company.getName());

    }
}