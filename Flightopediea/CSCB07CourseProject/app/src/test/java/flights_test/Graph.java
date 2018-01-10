package flights_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates a <code>Graph</code> where each node is a location connected by
 * flights which are edges.
 */
public class Graph {
  private Map<String, List<Flight>> graph;

  /**
   * Initialize a HashMap which maps Location to list of <code>Flight</code>
   * this creates a graph of locations which are nodes and flights that connect
   * those nodes which are the edges.
   */
  public Graph() {
    graph = new HashMap<String, List<Flight>>();
  }

  /**
   * Adds a new <code>Node</code> with the given location and value to this
   * <code>Graph</code>, if there is not a <code>Node</code> with the given
   * location in this <code>Graph</code>.
   * 
   * @param location
   *          the location of the new <code>Node</code>
   */
  public void addNode(String location) {
    if (!graph.containsKey(location)) {
      graph.put(location, new ArrayList<Flight>());
    }
  }

  /**
   * Adds an edge(<code>Flight</code>) between the given nodes(locations) in
   * this <code>Graph</code>. If there is already an edge between node1 and
   * node2, does nothing.
   * 
   * @param location
   *          Flight Location
   * @param flight
   *          Flight
   */
  public void addEdge(String location, Flight flight) {
    graph.get(location).add(flight);
  }

  /**
   * Returns a list of <code>Flight</code>s which start from the specified
   * location on a specified date.
   * 
   * @param location
   *          the location of the <code>Flight</code>s
   * @param date
   *          the departure date of the <code>Flight</code>s
   * @return Return a list of <code>Flight</code>s which meet the specified
   *         parameters of location and departure date
   */
  public List<Flight> getStartFlight(String location, String date) {
    List<Flight> flights = new ArrayList<Flight>();
    for (Flight flight : graph.get(location)) {
      if (flight.getDepartDate().equals(date)) {
        flights.add(flight);
      }
    }
    return flights;
  }

  /**
   * Returns a List of <code>Flight</code>s which connect to the specified
   * location(node).
   * 
   * @param location
   *          the location of the <code>Flight</code>
   * @return A list of <code>Flight</code>s which are connected to the location
   */
  public List<Flight> getConnectFlight(String location) {
    return graph.get(location);
  }

  @Override
  public String toString() {
    return graph.toString();
  }

  /**
   * Returns a set of all the location that are within the <code>Graph</code>.
   * 
   * @return A set of all the location(nodes)
   */
  public Set<String> getLocations() {
    return graph.keySet();
  }
}
