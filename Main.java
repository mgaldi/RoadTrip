import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int MIN_ATTRACTIONS = 1, MAX_ATTRACTIONS = 4;

    private static String origin, destination;
    private static Set<String> toVisit = new HashSet<>();

    public static void main(String[] args) {
        RoadTripSolver rts = new RoadTripSolver();
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        final Set<String> locations = rts.getLocationRoads().keySet();
        final Set<String> attractionLocations = rts.getLocationAttractions().keySet();

        final List<String> locationsList = List.copyOf(locations);
        Main.origin = locationsList.get(random.nextInt(locationsList.size()));
        // Just in case the selected destination corresponds to the decided origin
        do {
            Main.destination = locationsList.get(random.nextInt(locationsList.size()));
        } while (Main.destination.equals(Main.origin));

        final List<String> attractionLocationsList = List.copyOf(attractionLocations);
        final int locationsToVisit = random.nextInt(MIN_ATTRACTIONS, MAX_ATTRACTIONS + 1);
        while (Main.toVisit.size() < locationsToVisit) {
            String attractionLocation = attractionLocationsList.get(random.nextInt(attractionLocationsList.size()));
            Main.toVisit.add(attractionLocation);
        }

        System.out.printf("The origin is %s.%n", Main.origin);
        System.out.printf("The destination is %s.%n", Main.destination);
        System.out.println("The attractions to visit are located in:");
        for (int i = 0; i < Main.toVisit.size(); i++)
            System.out.printf("\t%d %s%n", i + 1, List.copyOf(Main.toVisit).get(i));

        ArrayList<String> paths = new ArrayList<>(rts.route(origin, destination, toVisit));
        System.out.println(paths);
    }

}
