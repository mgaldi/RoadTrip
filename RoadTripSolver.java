import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class RoadTripSolver {
    private static Map<String, Set<Road>> locationRoads;
    private static Map<String, Set<String>> locationAttractions;

    public RoadTripSolver(){
        locationRoads = RoadTripSolverHelper.getLocationRoads();
        locationAttractions = RoadTripSolverHelper.getLocationAttractions();
    }

    public List<String> route(String origin, String destination, Set<String> attractions) {
        Map<String, Integer> nodesCosts = new HashMap<>();
        if(attractions != null){
            for(String att : attractions)
                attractions.add(att);
        }else{
            attractions = null;
        }


        List<String> path;
        List<String> completePath = new ArrayList<>();
        while(attractions.size() != 0){
            path = findShortestPath(origin, destination, attractions);
            origin = path.get(path.size() - 1);
            attractions.remove(origin);
            completePath.addAll(path);
        }
        path = findShortestPath(origin, destination, attractions);
        completePath.addAll(path);


        return completePath;
    }

    private List<String> findShortestPath(String origin, String destination, Set<String> attractions){
        Map<String, Integer> nodesCosts = new HashMap<>();
        Map<String, String> nodesPath = new HashMap<>();
        Map<String, Boolean> nodesKnown = locationRoads.keySet().stream().collect(Collectors.toMap(String::toString, location -> false));
        String[] keys = locationRoads.keySet().toArray(new String[0]);

        for (String key : keys) {
            nodesPath.put(key, null);
            nodesCosts.put(key, -1);
        }
        nodesCosts.put(origin, 0);

        String vertex;
        do {
            vertex = findLeastCost(nodesKnown, nodesCosts, keys);
            nodesKnown.put(vertex, true);
            Set<Road> temp = locationRoads.get(vertex);
            if(temp == null)
                continue;
            ArrayList<String> adjacents = new ArrayList<>();
            for (Road road : temp) {
                adjacents.add(road.getTo());
            }
            for (String road : adjacents) {
                if (nodesCosts.get(road) > nodesCosts.get(vertex) + findWeight(vertex, road) || nodesCosts.get(road) == -1) {
                    nodesCosts.put(road, nodesCosts.get(vertex) + findWeight(vertex, road));
                    nodesPath.put(road, vertex);
                }
            }
        } while (vertex != null);

        List<String[]> paths = new ArrayList<>();
        for(String attraction : attractions){
            String v = attraction;
            ArrayList<String> pathAttraction = new ArrayList<>();
            pathAttraction.add(v);
            do {
                pathAttraction.add(nodesPath.get(v));
                v = nodesPath.get(v);
            } while (v != null);
            pathAttraction.remove(pathAttraction.size() - 1);
            Collections.reverse(pathAttraction);
            
        }
        int minCost;
        String min = "";
        if (attractions != null) {
            if(attractions.size() > 0){
                min = attractions.iterator().next();
                minCost = nodesCosts.get(min);
                for(String road : attractions){
                    if(nodesCosts.get(road) < minCost){
                        minCost = nodesCosts.get(road);
                        min = road;
                    }


                }
            } else{
                min = destination;
            }
        }else{
            min = destination;
        }

        ArrayList<String> listTest = new ArrayList<>();
        String v = min;
        listTest.add(v);
        do {
            listTest.add(nodesPath.get(v));
            v = nodesPath.get(v);
        } while (v != null);
        listTest.remove(listTest.size() - 1);
        Collections.reverse(listTest);

        return listTest;
    }
    private String findLeastCost(Map<String, Boolean> known, Map<String, Integer> costs, String[] keys) {
        int min = -1, index = -1;

        for (int i = 0; i < keys.length; i++) {
            if (costs.get(keys[i]) != -1 && !known.get(keys[i])) {
                if (min == -1) {
                    min = costs.get(keys[i]);
                    index = i;
                } else if (costs.get(keys[i]) < min) {
                    min = costs.get(keys[i]);
                    index = i;
                }

            }
        }
        return (index == -1) ? null : keys[index];
    }

    private int findWeight(String vertex, String adjacent) {

        for (Road road : locationRoads.get(vertex))
            if (road.getTo().equals(adjacent))
                return road.getMilesDist();


        return 0;
    }

    public  Map<String, Set<Road>> getLocationRoads() {
        return locationRoads;
    }

    public  Map<String, Set<String>> getLocationAttractions() {
        return locationAttractions;
    }
}
