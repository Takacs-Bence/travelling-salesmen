package bt.travellingsalesmen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ShortestPathApp {

    private static final String INPUT_FILE_PATH = "src/main/resources/input/input.txt";
    private static final String START_CITY_STRING = "/start-cities";
    private static final String START_CONNECTION_STRING = "/start-connections";

    /**
     * implement a shortest-path algorithm <br>
     * will read and process input file from {@link #INPUT_FILE_PATH} <br>
     * the first city in the input file will be the starting city. the last city will be the destination. <br>
     * the output will be pushed to the console - the shortest path and the total distance covered
     * @param args - args[0] shortest-path finding strategy {@link PathFindStrategy}
     */
    public static void main(String... args) {
        PathFindStrategy pathFindStrategy = parsePathFindStrategy(args);
        Graph graph = buildGraph();
        findShortestPath(graph, pathFindStrategy);
    }

    private static PathFindStrategy parsePathFindStrategy(String[] args) {
        // for now only Dijkstra is supported
        return PathFindStrategy.DIJKSTRA;
    }

    /**
     * will parse input file from {@link #INPUT_FILE_PATH}
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
        System.out.println("TODO");
    }
}