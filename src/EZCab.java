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
                            (Taxi) findAvailableVehicle(passenger, getAllTaxis())
                                    .orElse(null);
                    Shuttle shuttle =
                            (Shuttle) findAvailableVehicle(passenger, getAllShuttles())
                                    .orElse(null);
                    IOHelper.printTaxiAndShuttleOptions(passenger, taxi, shuttle);
                    //TODO: Prompt to book a taxi

                }
                case 4 -> {
                    // TODO: Implement option 4 "Show status of Taxi"
                }
                default -> System.out.println("That is not a valid option");
            }
            System.out.println();
        } while(IOHelper.promptForBinaryChoice(
                "Would you like to return to the main menu? [Y/N]", "Y", "N"));
    }

    private static Stream<Vehicle> getAllTaxis() {
        return cabCompanies.values().stream().flatMap(CabCompany::getTaxis);
    }

    private static Stream<Vehicle> getAllShuttles() {
        return cabCompanies.values().stream().flatMap(CabCompany::getShuttles);
    }

    private static Optional<Vehicle> findAvailableVehicle(Passenger passenger,
                                                          Stream<Vehicle> vehicles) {
        return vehicles.filter(vehicle -> vehicle.isAvailableTo(passenger)).findAny();
    }

    private static CabCompany findOrAddCabCompany(CabCompany company) {
        return cabCompanies.putIfAbsent(company.getName(), company) == null ?
                company : cabCompanies.get(company.getName());

    }
}