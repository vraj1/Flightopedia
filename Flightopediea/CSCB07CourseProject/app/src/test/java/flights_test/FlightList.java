package flights_test;

import exception.NoSuchFlightException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightList {

  private Map<String, Flight> listFlights;
  private Graph graph;

  /**
   * Create a new HashMap of flight number that map to <code>Flight</code>.
   * Create a new <code>Graph</code> to store all flights
   */
  public FlightList() {
    listFlights = new HashMap<String, Flight>();
    graph = new Graph();
  }

  /**
   * Adds a flight to the <code>FlightList</code>.
   * 
   * @param flight
   *          the given <code>Flight</code> to be put into the
   *          <code>FlightList</code>
   */
  public void addFlight(Flight flight) {
    listFlights.put(flight.getFlightNumber(), flight);
  }

  /**
   * Remove the flight with the flight number from <code>FlightList</code>.
   * 
   * @param flightNumber
   *          the flight number of the <code>Flight</code> to be removed from
   *          the <code>FlightList</code>
   * @throws NoSuchFlightException
   *           if <code>Flight</code> does not exist in <code>FlightList</code>
   */
  public void removeFlight(String flightNumber) throws NoSuchFlightException {
    if (!listFlights.containsKey(flightNumber)) {
      throw new NoSuchFlightException(
          "Cannot remove Flight, no such Flight found");
    }
    listFlights.remove(flightNumber);
  }

  /**
   * Give the <code>Flight</code> that has the given flight number.
   * 
   * @param flightNumber
   *          of the <code>Flight</code>
   * @return returns the <code>Flight</code> from the <code>FlightList</code>
   *         given the flightNumber
   * @throws NoSuchFlightException
   *           if <code>Flight</code> does not exist in <code>FlightList</code>
   */
  public Flight getFlightNumber(String flightNumber)
      throws NoSuchFlightException {
    if (!listFlights.containsKey(flightNumber)) {
      throw new NoSuchFlightException(
          "Cannot get Flight, no such Flight found");
    }
    return listFlights.get(flightNumber);
  }

  /**
   * Give the <code>Flight</code> that has the given date, origin, and
   * destination.
   * 
   * @param date
   *          The departure date of a <code>Flight</code>
   * @param origin
   *          The origin of a <code>Flight</code>
   * @param destination
   *          The destination of a <code>Flight</code>
   * @return A List of <code>Flight</code> that meet the given parameters.
   */
  public List<String> getFlight(String date, String origin,
      String destination) {
    List<String> returnFlights = new ArrayList<String>();
    for (Flight flight : listFlights.values()) {
      if (flight.getOrigin().equals(origin)
          && flight.getDestination().equals(destination)
          && (flight.getDepartDate().equals(date))) {
        returnFlights.add(flight.toString());
      }
    }
    return returnFlights;
  }

  /**
   * Return a List of flights which depart form the given origin.
   * 
   * @param origin
   *          the origin of a <code>Flight</code>
   * @return A list of <code>Flight</code>s which depart form the specified
   *         origin
   */
  public List<Flight> getFlightOrigin(String origin) {
    List<Flight> returnFlights = new ArrayList<Flight>();
    for (Flight flight : listFlights.values()) {
      if (flight.getOrigin().equals(origin)) {
        returnFlights.add(flight);
      }
    }
    return returnFlights;
  }

  /**
   * Returns a <code>Graph</code> of <code>Flight</code>s that connect the
   * origin to the destination.
   * 
   * @return a <code>Graph</code> which connect the origin to destination
   */
  public Graph getGraph() {
    for (Flight flight : listFlights.values()) {
      graph.addNode(flight.getDestination());
      graph.addNode(flight.getOrigin());
    }
    for (String location : graph.getLocations()) {
      for (Flight flight : getFlightOrigin(location)) {
        graph.addEdge(location, flight);
      }
    }
    return graph;
  }

  /**
   * Returns a Collections of <code>Flight</code>s in the graph.
   * @return Collections of <code>Flight</code>s in the graph
   */
  public Collection<Flight> values() {
    return listFlights.values();
  }
}
