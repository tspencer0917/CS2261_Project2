import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EZCab {

    private static final HashMap<String, CabCompany> cabCompanies =
            new HashMap<String, CabCompany>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int selection = -1;
        boolean displayMainMenu = true;

        while (displayMainMenu) {
            System.out.print("Select from the menu.\n" +
                    "1. Add taxis\n" +
                    "2. Add shuttles\n" +
                    "3. Book a taxi\n" +
                    "4. Show Taxi Status\n");
            selection = promptForIntBetween(1, 4,
                    "Enter a selection: ",
                    "That is not a valid selection");
            CabCompany cabCompany;
            int i;
            switch (selection) {
                case 1 -> {
                    // TODO: TEST: Find out if entering 0 for number of taxis
                    //  causes weird behavior
                    cabCompany = promptForCompanyInfo();
                    cabCompany = findOrAddCabCompany(cabCompany);
                    cabCompany.addTaxis(promptForTaxis());
                }
                case 2 -> {
                    // TODO: BUGFIX: Entering 0 for number of shuttles makes
                    //  weird behavior.
                    cabCompany = promptForCompanyInfo();
                    cabCompany = findOrAddCabCompany(cabCompany);
                    cabCompany.addShuttles(promptForShuttles());
                }
                case 3 -> {
                    Passenger passenger = promptForPassengerInfo();
                    Taxi taxi =
                            (Taxi) findAvailableVehicle(passenger, getAllTaxis())
                                    .orElse(null);
                    Shuttle shuttle =
                            (Shuttle) findAvailableVehicle(passenger, getAllShuttles())
                                    .orElse(null);
                    printTaxiAndShuttleOptions(passenger, taxi, shuttle);
                    //TODO: Prompt to book a taxi
                }
                case 4 -> {
                    // TODO: Implement option 4 "Show status of Taxi"
                }
                default -> {
                    System.out.println("That is not a valid option");
                }
            }
            System.out.println("Would you like to return to the main menu?");
            displayMainMenu = scanner.nextLine().toLowerCase().startsWith("y");
        }
    }

    private static Stream<Vehicle> getAllTaxis() {
        return cabCompanies.values().stream().flatMap(CabCompany::getTaxis);
    }

    private static Stream<Vehicle> getAllShuttles() {
        return cabCompanies.values().stream().flatMap(CabCompany::getShuttles);
    }

    private static CabCompany promptForCompanyInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter company name: ");
        return new CabCompany(scanner.nextLine());
    }

    private static Passenger promptForPassengerInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();

        return new Passenger(name, location, destination);
    }

    private static void printTaxiAndShuttleOptions(Passenger passenger,
                                                   Taxi taxi,
                                                   Shuttle shuttle){
        System.out.println(
                taxi != null ?
                        "Taxi %s is available, fare will be $%d".formatted(
                                taxi.getLicensePlate(),
                                taxi.getFareFor(passenger)
                        ) : "Sorry, there are no available taxis"
        );
        System.out.println(
                shuttle != null ?
                        "Shuttle %s is available, fare will be $%d".formatted(
                                shuttle.getLicensePlate(),
                                shuttle.getFareFor(passenger)
                        ) :
                        "Sorry, no shuttles are available between" +
                                " %s and %s".formatted(
                                        passenger.getLocation(),
                                        passenger.getDestination()
                                )
        );
    }

    private static HashSet<Taxi> promptForTaxis() {
        int numberOfTaxis = promptForPositiveInt(
                "How many taxis do you want to add?\n",
                "That is not a valid number of taxis"
        );
        System.out.println("Enter taxi plate numbers:");
        Scanner scanner = new Scanner(System.in);
        HashSet<Taxi> taxis = new HashSet<>();
        for (int i=0; i < numberOfTaxis; i++) {
            String licensePlate = scanner.nextLine();
            taxis.add(new Taxi(licensePlate));
        }
        return taxis;
    }

    private static HashSet<Shuttle> promptForShuttles() {
        Scanner scanner = new Scanner(System.in);
        int numberOfShuttles = promptForPositiveInt(
                "Enter number of Shuttles to add: ",
                "That is not a valid number of shuttles");
        HashSet<Shuttle> shuttles = new HashSet<Shuttle>();
        for (int i = 0; i < numberOfShuttles; i++) {
            System.out.printf("Enter plate number for shuttle %d/%d: ", i+1,
                    numberOfShuttles);
            Shuttle shuttle = new Shuttle(scanner.nextLine());
            int numberOfStops = promptForPositiveInt("Enter number of " +
                    "stops: ", "That is not a valid number");
            System.out.println("Enter route stops with fare:");
            for (int j = 0; j < numberOfStops; j++) {
                String s = scanner.nextLine();
                shuttle.addStop(s.substring(0, s.indexOf(':')),
                        Integer.parseInt(s.substring(s.indexOf(':') + 1)));
            }
            shuttles.add(shuttle);
        }
        return shuttles;
    }

    private static Optional<Vehicle> findAvailableVehicle(Passenger passenger,
                                                          Stream<Vehicle> vehicles) {
        return vehicles.filter(vehicle -> vehicle.isAvailableTo(passenger)).findAny();
    }

    private static int promptForPositiveInt(String promptMsg,
                                            String failMsg) {
        while (true) {
            int i = promptForInt(promptMsg, failMsg);
            if (i >= 0) {
                return i;
            } else {
                System.out.println(failMsg);
            }
        }
    }

    private static int promptForIntBetween(int min, int max,
                                           String promptMsg,
                                           String failMsg) {
        while (true) {
            int i = promptForInt(promptMsg, failMsg);
            if (i >= min && i <= max) {
                return i;
            } else {
                System.out.println(failMsg);
            }
        }
    }

    private static int promptForInt(String promptMsg, String failMsg) {
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean haveValidInt = false;
        while (true) {
            System.out.print(promptMsg);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(failMsg);
                scanner = new Scanner(System.in);
            }
        }
    }

    private static CabCompany findOrAddCabCompany(CabCompany company) {
        return cabCompanies.putIfAbsent(company.getName(), company) == null ?
                company : cabCompanies.get(company.getName());

    }
}