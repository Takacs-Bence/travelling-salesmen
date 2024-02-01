package bt.travellingsalesmen;

/**
 * Algorithm type to find the shortest-path
 */
public enum PathFindStrategy {

    DIJKSTRA("Dijkstra"),
    BELLMANFORD("Bellman-Ford"),
    ASTAR("A*");

    PathFindStrategy(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return this.name;
    }
}
