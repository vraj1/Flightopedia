package flights_test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** A representation of an itinerary. */
public class Itinerary {

  private Graph graph;
  private FlightList listFlight;
  private String date;
  private String origin;
  private String destination;
  private long minLayover;
  private long maxLayover;
  private ArrayList<ArrayList<Flight>> paths;
  private DateFormat dateTime;

  /**
   * Create new <code>Itinerary</code> which take in the list of flights, date,
   * origin, destination, minimum and maximum layover.
   * 
   * @param listFlights
   *          list of Flights
   * @param date
   *          Departure Date
   * @param origin
   *          Departure Origin
   * @param destination
   *          Arrival Destination
   * @param minLayover
   *          Duration minimum layover
   * @param maxLayover
   *          Duration maximum layover
   */
  public Itinerary(FlightList listFlights, String date, String origin,
      String destination, long minLayover, long maxLayover) {
    this.listFlight = listFlights;
    this.date = date;
    this.origin = origin;
    this.destination = destination;
    this.minLayover = minLayover;
    this.maxLayover = maxLayover;
    dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  }

  /**
   * Creates the Itinerary using list of flights, date, origin, destination,
   * minimum and maximum layover.
   * 
   * @return All the paths.This would be the itinerary
   */
  public List<String> getAllPaths() {
    graph = listFlight.getGraph(); // create a new Graph
    // List of List to store the Paths
    paths = new ArrayList<ArrayList<Flight>>();
    // Loop through every Flight in the starting location and date
    for (Flight flight : graph.getStartFlight(origin, date)) {
      // find the itinerary
      findNode(flight, destination, paths, new LinkedHashSet<Flight>(),
          new LinkedHashSet<String>());
    }

    // convert the List of Flights to the List Strings
    List<String> returnList = new ArrayList<String>();
    for (ArrayList<Flight> flight : paths) {
      String itinerary = "";
      double cost = 0;
      // get first and last flight to check the time difference
      Flight firstFlight = flight.get(0);
      Flight lastFlight = flight.get(flight.size() - 1);
      Date departure = null;
      Date arrival = null;
      try {
        departure = dateTime.parse(firstFlight.getDepartDateTime());
        arrival = dateTime.parse(lastFlight.getArrivalDateTime());
      } catch (ParseException excep) {
        excep.printStackTrace();
      }
      long duration = arrival.getTime() - departure.getTime();
      long hours0 = TimeUnit.MILLISECONDS.toHours(duration);
      long mins0 = TimeUnit.MILLISECONDS.toMinutes(duration) -
              TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
      // loop through each flight and create a String of flight
      for (Flight flight1 : flight) {
        itinerary += (flight1.getItineraryFlight() + "\n");
        cost += flight1.getCost();
      }
      // create a string of one itinerary
      itinerary = itinerary.substring(0, itinerary.length() - 1);
      returnList.add(String.format("%s\n%.2f\n%s", itinerary, cost,
              String.format("%02d:%02d", hours0, mins0)));
    }
    return returnList;
  }

  /**
   * Helper method to Find the itinerary. Recursive Depth First Search
   * Algorithm. https://en.wikipedia.org/wiki/Depth-first_search
   * 
   * @param flight
   *          Flight
   * @param destination
   *          destination of the flight
   * @param paths
   *          paths from the current flight
   * @param pathDestination
   *          visited Destinations
   * @param pathOrigin
   *          visited Origin
   */
  private void findNode(Flight flight, String destination,
      ArrayList<ArrayList<Flight>> paths, LinkedHashSet<Flight> pathDestination,
      LinkedHashSet<String> pathOrigin) {
    // add the current Flight visited
    pathDestination.add(flight);
    // add the origin visited
    pathOrigin.add(flight.getOrigin());
    // if the flight is the destination wanted
    if (flight.getDestination().equals(destination)) {
      // add the path to the List
      paths.add(new ArrayList<Flight>(pathDestination));
      // remove the flight in the destination
      pathDestination.remove(flight);
      return;
    }
    // loop through the destinations of the flight
    for (Flight flight2 : graph.getConnectFlight(flight.getDestination())) {
      // check for min/max layover
      Date departure = null;
      Date arrival = null;
      try {
        arrival = dateTime.parse(flight.getArrivalDateTime());
        departure=  dateTime.parse(flight2.getDepartDateTime());
      } catch (ParseException excep) {
        excep.printStackTrace();
      }
      long duration = departure.getTime() - arrival.getTime();
      long mins =  TimeUnit.MILLISECONDS.toMinutes(duration);
      if(((mins) >= minLayover/60)&&( (mins)<=maxLayover/60)){
        if (!pathDestination.contains(flight2)
            && !pathOrigin.contains(flight2.getDestination())) {
          // recurively call the function if tht path had not been visited
          findNode(flight2, destination, paths, pathDestination, pathOrigin);
        }
      }
    }
    pathDestination.remove(flight);
  }

  /**
   * Use the List of String(list of itinerary) and helper method
   * getItinerariesSort to sort itinerary in non-decreasing order cost.
   * 
   * @param itineraryList
   *          list of itinerary
   * @return the itinerary sorted by cost
   */
  public List<String> getItinerariesSortedByCost(List<String> itineraryList) {
    return getItinerariesSort(itineraryList, 2);
  }

  /**
   *  Use the List of String(list of itinerary) and helper method
   * getItinerariesSort to sort itinerary in non-decreasing order time.
   * 
   * @param itineraryList
   *          list of itinerary
   * @return the itinerary sorted by time
   */
  public List<String> getItinerariesSortedByTime(List<String> itineraryList) {
    return getItinerariesSort(itineraryList, 1);
  }

  /**
   * Helper method to sort the itinerary in Time or Cost format.
   * 
   * @param itineraryList List of Itinerary
   * @param num, 1 represents the Time, 2 represents Cost
   * @return List of String in Sorted Format
   */
  private List<String> getItinerariesSort(List<String> itineraryList, int num) {
    List<Double> countingList = new ArrayList<Double>();
    HashMap<String, Double> costMap = new HashMap<String, Double>();

    for (String itinerary : itineraryList) {
      String[] itinerarySplit = itinerary.split("\n");
      double value = 0;
      if(num == 1){
        value = Double.valueOf(itinerarySplit[itinerarySplit.length - num].replace(":","."));
      }else{
        value = Double.valueOf(itinerarySplit[itinerarySplit.length - num]);
      }
      countingList.add(value);
      costMap.put(itinerary, value);
    }
    Collections.sort(countingList);

    List<String> finalList = new ArrayList<String>();
    for (double value : countingList) {
      for (String string : costMap.keySet()) {
        if (costMap.get(string) == value) {
          finalList.add(string);
          costMap.remove(string);
          break;
        }
      }
    }
    return finalList;
  }
}
