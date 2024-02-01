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

    public static PathFindStrategy findByName(String name) {
        for (PathFindStrategy strategy : PathFindStrategy.values()) {
            if (strategy.getName().equalsIgnoreCase(name)) {
                return strategy;
            }
        }
        return null;
    }
}
