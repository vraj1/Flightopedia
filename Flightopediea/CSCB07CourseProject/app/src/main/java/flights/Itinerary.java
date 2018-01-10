package flights;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Creates an <code>Itinerary</code> that will hold an ArrayList of flights.
 */
public class Itinerary {

    private static final DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm",
            Locale.getDefault());
    private ArrayList<ArrayList<Flight>> path;
    private FlightDataBase flightDataBase;

    /**
     * Gets the flight database.
     * @param context context
     */
    public Itinerary(Context context){
        flightDataBase = new FlightDataBase(context);
    }

    /**
     * Gets the itinerary from the given information.
     * @param origin of the flight
     * @param destination of the final flight
     * @param date the flight will start from
     * @param format flag that is used
     * @return ArrayList of flights that compose an itinerary
     */
    public ArrayList<String> getIntinerary(String origin, String destination
            , String date, boolean format) {
        path = new ArrayList<ArrayList<Flight>>();
        for (Flight flight : flightDataBase.getFlights(origin, null, date)) {
            findPaths(flight,destination,path,new LinkedHashSet<Flight>(),
                    new LinkedHashSet<String>());
        }
        return getItineraryTimeCost(path,format);
    }

    /**
     * Gets an ArrayList of flights and sorts by cost.
     * @param paths All of the itineraries
     * @param format flag that is used
     * @return the ArrayList of itineraries ordered by cost
     */
    private ArrayList<String> getItineraryTimeCost(ArrayList<ArrayList<Flight>> paths, boolean format){
        // convert the List of Flights to the List Strings
        ArrayList<String> returnList = new ArrayList<String>();
        for (ArrayList<Flight> flight : paths) {
            String itinerary = "";
            double cost = 0;
            // get first and last flight to check the time difference
            Flight firstFlight = flight.get(0);
            Flight lastFlight = flight.get(flight.size() - 1);
            Date departure = null;
            Date arrival = null;
            try {
                departure = dateTime.parse(firstFlight.getDepatureDateTime());
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
                if (!format) {
                    itinerary += (flight1.getItineraryFlight() + "\n");
                } else {
                    itinerary += (flight1.displayFlightString() + "\n->\n");
                }
                cost += flight1.getCost();
            }
            // create a string of one itinerary
            if (!format) {
                itinerary = itinerary.substring(0, itinerary.length() - 1);
                returnList.add(String.format("%s\n%.2f\n%s", itinerary,
                        cost, String.format("%02d:%02d", hours0, mins0)));
            } else {
                itinerary = itinerary.substring(0, itinerary.length() - 4);
                returnList.add(String.format("%s\n\nTotal Cost: %.2f\nTotal Time: %s", itinerary,
                        cost, String.format("%02d:%02d", hours0, mins0)));
            }
        }
        return returnList;
    }

    /**
     * Finds the all the possible itineraires within the MIN_LAYOVER_TIME and MAX_LAYOVER_TIME and
     * the given flight information from the user.
     * @param flight flight to begin with
     * @param destination of the flight
     * @param paths list of flights
     * @param pathDestination destination of the path
     * @param pathOrigin origin of the path
     */
    private void findPaths(Flight flight, String destination,
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
        for (Flight flight2 : flightDataBase.getFlights(flight.getDestination(),null,null)) {

            Date departure = null;
            Date arrival = null;
            try {
                arrival = dateTime.parse(flight.getArrivalDateTime());
                departure=  dateTime.parse(flight2.getDepatureDateTime());
            } catch (ParseException excep) {
                excep.printStackTrace();
            }
            long duration = departure.getTime() - arrival.getTime();
            Log.d("duration: ", String.valueOf(duration));
            long mins =  TimeUnit.MILLISECONDS.toMinutes(duration);
            Log.d("mins: ", String.valueOf(mins));
            if(((mins) >= 30)&&( (mins)<=360)){
                if (!pathDestination.contains(flight2)
                        && !pathOrigin.contains(flight2.getDestination())) {
                    findPaths(flight2, destination, paths, pathDestination, pathOrigin);
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
    public ArrayList<String> getItinerariesSortedByCost(ArrayList<String> itineraryList, boolean format) {
        return getItinerariesSort(itineraryList, 2,format);
    }

    /**
     *  Use the List of String(list of itinerary) and helper method
     * getItinerariesSort to sort itinerary in non-decreasing order time.
     *
     * @param itineraryList
     *          list of itinerary
     * @return the itinerary sorted by time
     */
    public ArrayList<String> getItinerariesSortedByTime(ArrayList<String> itineraryList, boolean format) {
        return getItinerariesSort(itineraryList, 1, format);
    }

    /**
     * Helper method to sort the itinerary in Time or Cost format.
     *
     * @param itineraryList List of Itinerary
     * @param num, 1 represents the Time, 2 represents Cost
     * @return List of String in Sorted Format
     */
    private ArrayList<String> getItinerariesSort(ArrayList<String> itineraryList, int num, boolean format) {
        List<Double> countingList = new ArrayList<>();
        HashMap<String, Double> costMap = new HashMap<String, Double>();

        for (String itinerary : itineraryList) {

            double value = 0;
            String[] itinerarySplit = itinerary.split("\n");
            if(format){
                if(num == 1){
                    value = Double.valueOf(itinerarySplit[itinerarySplit.length - num].split(" ")[2].replace(":","."));
                }else{
                    value = Double.valueOf(itinerarySplit[itinerarySplit.length - num].split(" ")[2]);
                }
            }else{
                value = Double.valueOf(itinerarySplit[itinerarySplit.length - num]);
            }
            countingList.add(value);
            costMap.put(itinerary, value);
        }
        Collections.sort(countingList);
        Log.d("value: ", countingList.toString());

        ArrayList<String> finalList = new ArrayList<String>();
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
