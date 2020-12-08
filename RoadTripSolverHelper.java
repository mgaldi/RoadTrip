import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 It's assumed that every road can be ran both ways the absence of inverse roads from one town to another
 given the lack of inverse roads in the provided `roads.csv`.

 A set is used to represent all the attractions in a given location despite all locations in the
 `attraction.csv` file having a single attraction in them.

 NOTE: the road that went from `Boise ID` to `Boise ID` was removed from the `roads.csv` file due to
 its presence being most likely due to an error, as well as the first line `Attraction, Location`
 from the `attractions.csv` file.
*/
public class RoadTripSolverHelper {
    private static final String ROADS_FILE = "/resources/roads.csv";
    private static final String ATTRACTIONS_FILE = "/resources/attractions.csv";

    private static Map<String, Set<Road>> locationRoads;
    private static Map<String, Set<String>> locationAttractions;

    static {
        try {
            locationRoads = RoadTripSolverHelper.loadRoads(Main.class.getResource(ROADS_FILE));
            locationAttractions = RoadTripSolverHelper.loadAttractions(Main.class.getResource(ATTRACTIONS_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Set<Road>> getLocationRoads() {
        return RoadTripSolverHelper.locationRoads;
    }

    public static Map<String, Set<String>> getLocationAttractions() {
        return RoadTripSolverHelper.locationAttractions;
    }

    private static Map<String, Set<Road>> loadRoads(URL roadsCsv) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(roadsCsv.getFile()))) {
            Map<String, Set<Road>> locationRoads = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] roadValues = line.split(",");
                Road road = new Road()
                        .setFrom(roadValues[0])
                        .setTo(roadValues[1])
                        .setMilesDist(Integer.parseInt(roadValues[2]))
                        .setMinutesDist(Integer.parseInt(roadValues[3]));
                Road inverseRoad = new Road()
                        .setFrom(roadValues[1])
                        .setTo(roadValues[0])
                        .setMilesDist(Integer.parseInt(roadValues[2]))
                        .setMinutesDist(Integer.parseInt(roadValues[3]));
                Set<Road> roads;
                roads = locationRoads.computeIfAbsent(road.getFrom(), k -> new HashSet<>());
                roads.add(road);
                roads = locationRoads.computeIfAbsent(inverseRoad.getFrom(), k -> new HashSet<>());
                roads.add(inverseRoad);
            }
            return locationRoads;
        }
    }

    private static Map<String, Set<String>> loadAttractions(URL attractionsCsv) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(attractionsCsv.getFile()))) {
            Map<String, Set<String>> locationAttractions = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attractionValues = line.split(",");
                String attraction = attractionValues[0], location = attractionValues[1];
                Set<String> attractions = locationAttractions.computeIfAbsent(location, k -> new HashSet<>());
                attractions.add(attraction);
            }
            return locationAttractions;
        }
    }

}
