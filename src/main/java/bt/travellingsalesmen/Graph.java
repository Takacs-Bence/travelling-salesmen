package bt.travellingsalesmen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * uses adjacency-list to represent graph, because we will be iterating over neighbours a lot, <br>
 * and it is certain, that all nodes are connected. although that is not enforced at the moment <br>
 * the edges are undirected
 */
public class Graph {

    /**
     * adjacency list
     */
    private final Map<String, List<Connection>> connections;

    private String startingPoint;
    private String destination;

    public Graph() {
        this.connections = new HashMap<>();
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        startingPoint = startingPoint.toUpperCase();
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        destination = destination.toUpperCase();
        this.destination = destination;
    }

    /**
     * registers city in graph
     * @param city to add
     * @throws IllegalArgumentException if the city is already registered
     */
    public void addCity(String city) {
        String key = city.toUpperCase();
        if (connections.get(key) != null) {
            throw new IllegalArgumentException("Duplicate city");
        }
        List<Connection> connectionList = new ArrayList<>();
        connections.put(key, connectionList);
    }

    /**
     * @param from - city associated with connection
     * @param to - connection name
     * @param distance - distance on road (km)
     *
     * @throws IllegalArgumentException if associated city was not listed among the cities
     */
    public void addConnection(String from, String to, Integer distance) {
        String fromCity = from.toUpperCase();
        String toCity = to.toUpperCase();
        Connection conn = new Connection(toCity, distance);
        List<Connection> connectionList = this.connections.get(fromCity);
        if (connectionList == null) {
            throw new IllegalArgumentException("City was not listed in city list");
        }
        connectionList.add(conn);
        this.connections.put(fromCity, connectionList);
    }

    /**
     * @return {@code false} if either the starting point, destination, or connections are missing for the graph
     */
    public boolean isValid() {
        String destination = getDestination();
        String startingPoint = getStartingPoint();
        boolean connectionsEmpty = connections.isEmpty();
        return startingPoint != null && !startingPoint.isEmpty() && destination != null && !destination.isEmpty() && !connectionsEmpty;
    }

    public Set<String> keySet() {
        return this.connections.keySet();
    }

    public List<Connection> getConnections(String city) {
        return this.connections.get(city);
    }

    static class Connection {
        private final String city;
        private final Integer distance;

        public Connection(String city, Integer distance) {
            this.city = city;
            this.distance = distance;
        }

        public String getCity() {
            return city;
        }

        public Integer getDistance() {
            return distance;
        }
    }

}
