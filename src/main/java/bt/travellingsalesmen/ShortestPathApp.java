package bt.travellingsalesmen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPathApp {

    private static final String INPUT_FILE_PATH = "src/main/resources/input/input.txt";
    private static final String START_CITY_STRING = "/start-cities";
    private static final String START_CONNECTION_STRING = "/start-connections";

    /**
     * implement a shortest-path algorithm <br>
     * will read and process input file from {@link #INPUT_FILE_PATH} <br>
     * the first city in the input file will be the starting city. the last city will be the destination. <br>
     * the output will be pushed to the console - the shortest path and the total distance covered
     *
     * @param args - args[0] shortest-path finding strategy {@link PathFindStrategy}
     */
    public static void main(String... args) {
        PathFindStrategy pathFindStrategy = parsePathFindStrategy(args);
        Graph graph = buildGraph();
        findShortestPath(graph, pathFindStrategy);
    }

    private static PathFindStrategy parsePathFindStrategy(String[] args) {
        String alg = args[0];
        if (alg != null) {
            PathFindStrategy pathFindStrategy = PathFindStrategy.findByName(alg);
            if (pathFindStrategy != null) {
                return pathFindStrategy;
            } else {
                System.err.println("No path finding strategy of " + alg + ". Defaulting to " + PathFindStrategy.DIJKSTRA.getName());
            }
        }
        return PathFindStrategy.DIJKSTRA;
    }

    /**
     * will parse input file from {@link #INPUT_FILE_PATH}
     *
     * @return {@link Graph}
     */
    private static Graph buildGraph() {
        Graph graph = new Graph();
        BufferedReader inputReader = null;
        try {
            boolean citiesReached = false;
            boolean connectionsReached = false;
            inputReader = new BufferedReader(new FileReader("./" + INPUT_FILE_PATH));
            String lineString = inputReader.readLine();

            while (lineString != null) {

                // register the cities
                if (!citiesReached && lineString.equalsIgnoreCase(START_CITY_STRING)) {
                    citiesReached = true;
                    int cityCount = Integer.parseInt(inputReader.readLine().trim());
                    for (int i = 0; i < cityCount; i++) {
                        String cityName = inputReader.readLine();
                        if (i == 0) {
                            graph.setStartingPoint(cityName);
                        } else if (i == cityCount - 1) {
                            graph.setDestination(cityName);
                        }
                        graph.addCity(cityName);
                    }
                }

                // add connections from cities
                if (!connectionsReached && lineString.equalsIgnoreCase(START_CONNECTION_STRING)) {
                    connectionsReached = true;
                    int connCount = Integer.parseInt(inputReader.readLine().trim());
                    for (int j = 0; j < connCount; j++) {
                        String connection = inputReader.readLine();
                        String[] cItems = connection.split("\\s+");
                        graph.addConnection(cItems[0], cItems[1], Integer.valueOf(cItems[2]));
                    }
                }


                // keep reading the file until we reach EOF in loop
                lineString = inputReader.readLine();
            }


        } catch (FileNotFoundException e) {
            System.err.println("Input file should be present at " + INPUT_FILE_PATH);
        } catch (IOException e) {
            System.err.println(e.getMessage());


        } finally {
            // try close the inputReader once
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    System.err.println("Input reader resource could not be closed.");
                }
            }
        }

        boolean valid = graph.isValid();
        if (!valid) {
            throw new IllegalStateException("Graph is not valid. Check input file for missing/false cities and connections");
        }
        return graph;
    }

    private static void findShortestPath(Graph graph, PathFindStrategy pathFindStrategy) {
        if (pathFindStrategy == PathFindStrategy.DIJKSTRA) {
            findWithDijkstra(graph);
        } else {
            System.out.println("Unsupported pathfinding strategy");
        }
    }

    private static void findWithDijkstra(Graph graph) {
        /*
        the nodes are the vertices of the graph - it has the same properties as the edge, namely city and distance.
        the difference is, for an edge the distance is measured between two cities, and for a node it is the aggregated
        distance from the starting point
         */
        Queue<Graph.Connection> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Graph.Connection::getDistance));


        // distance map to track tentative distance from starting point
        Map<String, Integer> distances = new HashMap<>();
        // track the path we are taking
        Map<String, String> roadTravelled = new HashMap<>();

        for (String city : graph.keySet()) {
            // adding 0 weight for the starting point and max (infinite) weight to every other city
            distances.put(city, city.equalsIgnoreCase(graph.getStartingPoint()) ? 0 : Integer.MAX_VALUE);
        }

        priorityQueue.offer(new Graph.Connection(graph.getStartingPoint(), 0));

        while (!priorityQueue.isEmpty()) {
            Graph.Connection current = priorityQueue.poll();

            if (current.getCity().equalsIgnoreCase(graph.getDestination())) {
                // we have reached the destination, stop searching
                break;
            }

            for (Graph.Connection neighbor : graph.getConnections(current.getCity())) {
                String neighborCity = neighbor.getCity();
                int newDistance = current.getDistance() + neighbor.getDistance();

                if (newDistance < distances.get(neighborCity)) {
                    distances.put(neighborCity, newDistance);
                    roadTravelled.put(neighborCity, current.getCity());
                    priorityQueue.offer(new Graph.Connection(neighborCity, newDistance));
                }
            }
        }

        printResult(graph, distances, roadTravelled);
    }

    private static void printResult(Graph graph, Map<String, Integer> distances, Map<String, String> roadTravelled) {
        String destination = graph.getDestination();
        List<String> path = new ArrayList<>();

        // reconstruct the path from the destination to the starting point
        while (roadTravelled.containsKey(destination)) {
            path.add(0, destination);
            destination = roadTravelled.get(destination);
        }

        // add the starting point to the path
        path.add(0, graph.getStartingPoint());

        // print the result
        System.out.println("Shortest Path: " + String.join(" -> ", path) + ", Distance: " + distances.get(graph.getDestination()) + " km");
    }

}