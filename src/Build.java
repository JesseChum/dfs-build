import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly
   * shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints
   * nothing.
   *
   * @param vertex the starting vertex
   * @param k      the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if (vertex == null || k <= 0) {
      return;
    }
    printShortWords(vertex, k, new HashSet<Vertex<String>>());

  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (visited.contains(vertex))
      return;

    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
    }

    visited.add(vertex);

    for (var neighbor : vertex.neighbors) {
      printShortWords(neighbor, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own
   * value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> visited = new HashSet<>();
    return longestWordHelper(vertex, visited);
  }

  private static String longestWordHelper(Vertex<String> vertex, Set<Vertex<String>> visited) {
    if (vertex == null)
      return "";

    if (visited.contains(vertex))
      return "";

    visited.add(vertex);

    String longest = vertex.data;

    for (Vertex<String> neighbor : vertex.neighbors) {
      String neighborLongest = longestWordHelper(neighbor, visited);
      if (neighborLongest.length() > longest.length()) {
        longest = neighborLongest;
      }
    }
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex
   * and
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T>    the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopersHelper(vertex, visited);
  }

  private static <T> void printSelfLoopersHelper(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null)
      return;

    if (visited.contains(vertex))
      return;

    visited.add(vertex);

    if (vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }
    for (Vertex<T> neighbor : vertex.neighbors) {
      printSelfLoopersHelper(neighbor, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a
   * series of flights
   * starting from the given airport. If the start and destination airports are
   * the same, returns true.
   *
   * @param start       the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null)
      return false;

    Set<Airport> visited = new HashSet<>();
    return canReachHelper(start, destination, visited);
  }

  private static boolean canReachHelper(Airport current, Airport destination, Set<Airport> visited) {
    if (current == destination)
      return true;

    if (visited.contains(current))
      return false;

    visited.add(current);

    for (Airport connection : current.getOutboundFlights()) {
      if (canReachHelper(connection, destination, visited))
        return true;
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the
   * given starting value.
   * The graph is represented as a map where each vertex is associated with a list
   * of its neighboring values.
   *
   * @param graph    the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T>      the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    if (starting == null || graph == null)
      return new HashSet<>();

    Set<T> visited = new HashSet<>();

    uncreachableHelper(graph, starting, visited);

    Set<T> result = new HashSet<>(graph.keySet());
    result.removeAll(visited);

    return result;
  }

  private static <T> void uncreachableHelper(Map<T, List<T>> graph, T current, Set<T> visited) {
    if (current == null || visited.contains(current))
      return;

    visited.add(current);

    for (T neighbor : graph.getOrDefault(current, new ArrayList<>())) {
      uncreachableHelper(graph, neighbor, visited);
    }
  }
}
