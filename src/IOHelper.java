import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOHelper {

    static int promptForMenuSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                    Select from the menu.
                    1. Add taxis
                    2. Add shuttles
                    3. Book a taxi
                    4. Show Taxi Status
                    """);
        return promptForIntBetween(1, 4,
                "Enter a selection: ",
                "That is not a valid selection");
    }

    static CabCompany promptForCompanyInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter company name: ");
        return new CabCompany(scanner.nextLine());
    }

    static Passenger promptForPassengerInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();

        return new Passenger(name, location, destination);
    }

    static void printTaxiAndShuttleOptions(Passenger passenger,
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

    static HashSet<Taxi> promptForTaxis() {
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

    static HashSet<Shuttle> promptForShuttles() {
        Scanner scanner = new Scanner(System.in);
        int numberOfShuttles = promptForPositiveInt(
                "Enter number of Shuttles to add: ",
                "That is not a valid number of shuttles");
        HashSet<Shuttle> shuttles = new HashSet<>();
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

    static int promptForPositiveInt(String promptMsg,
                                    String failMsg) {
        while (true) {
            int i = promptForInt(promptMsg, failMsg);
            if (i > 0) {
                return i;
            } else {
                System.out.println(failMsg);
            }
        }
    }

    static int promptForIntBetween(int min, int max,
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

    static int promptForInt(String promptMsg, String failMsg) {
        Scanner scanner = new Scanner(System.in);
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

    public static boolean promptForBinaryChoice(String promptMsg, String affirmStr, String negStr) {
        Scanner scanner = new Scanner(System.in);
        boolean isAffirm, isNeg;
        do {
            System.out.print(promptMsg);
            String input = scanner.nextLine();
            isAffirm = startWithIgnoreCase(input, affirmStr);
            isNeg = startWithIgnoreCase(input, negStr);
            if(isAffirm || isNeg) { break; }
            System.out.println("That is not a valid option");
        } while(true);
        return isAffirm;
    }

    public static boolean startWithIgnoreCase(String string, String prefix) {
        return string.substring(0, prefix.length()).equalsIgnoreCase(prefix);
    }
}
