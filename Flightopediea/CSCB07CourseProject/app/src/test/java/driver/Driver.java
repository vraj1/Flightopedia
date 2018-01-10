package driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.List;
import java.util.Scanner;

import clients_test.Client;
import clients_test.ClientList;
import exception.NoSuchClientException;
import flights_test.Flight;
import flights_test.FlightList;
import flights_test.Itinerary;

/** A Driver used for autotesting the project backend. */
public class Driver {

    public static final long MIN_LAYOVER = 30 * 60; // 30 min in seconds
    public static final long MAX_LAYOVER = 6 * 60 * 60; // 6 hours in seconds

    private static final Locale locale = Locale.getDefault();
    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd", locale);
    private static final DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
    private static final DateFormat time = new SimpleDateFormat("HH:mm", locale);

    private static FlightList listFlights = new FlightList();
    private static ClientList listClients = new ClientList();

    /**
     * Uploads client information to the application from the file at the
     * given path.
     * @param path the path to an input text file of client information with
     *     lines in the format:
     *     LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
     *     The ExpiryDate is stored in the format yyyy-MM-dd.
     */
    public static void uploadClientInfo(String path) {
        listClients = new ClientList();
        Scanner fileClient = null;
        try {
            fileClient = new Scanner(new File(path));
        } catch (FileNotFoundException excep) {
            excep.printStackTrace();
        }
        while (fileClient.hasNextLine()) {
            try {
                Client client = new Client(fileClient.nextLine());
                listClients.addClient(client);
            } catch (ParseException excep) {
                excep.printStackTrace();
            }
        }
    }

    /**
     * Uploads flight information to the application from the file at the
     * given path.
     * @param path the path to an input text file of flight information with
     *     lines in the format:
     *     Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price;NumSeats
     *     The dates are in the format yyyy-MM-dd HH:mm; the price has exactly two
     *     decimal places. NumSeats must be a non-negative integer.
     */
    public static void uploadFlightInfo(String path) {
        listFlights = new FlightList();
        Scanner fileFlight = null;
        try {
            fileFlight = new Scanner(new File(path));
        } catch (FileNotFoundException excep) {
            excep.printStackTrace();
        }
        while (fileFlight.hasNextLine()) {
            try {
                listFlights.addFlight(new Flight(fileFlight.nextLine()));
            } catch (ParseException excep) {
                excep.printStackTrace();
            }
        }
    }

    /**
     * Returns the information stored for the client with the given email.
     * @param email the email address of a client
     * @return the information stored for the client with the given email
     *     in this format:
     *     LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
     *     (the ExpiryDate is stored in the format yyyy-MM-dd)
     */
    public static String getClient(String email) {
        try {
            return listClients.getClient(email).toString();
        } catch (NoSuchClientException excep) {
            excep.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all flights that depart from origin and arrive at destination on
     * the given date.
     * @param date a departure date (in the format yyyy-MM-dd)
     * @param origin a flight origin
     * @param destination a flight destination
     * @return the flights that depart from origin and arrive at destination
     *     on the given date formatted in exactly this format:
     *     Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price;Duration
     *     The dates are in the format yyyy-MM-dd HH:mm; the price has exactly 2 decimal places;
     *     and duration is in format HH:mm.
     *
     * @throws ParseException if date cannot be parsed
     */
    public static List<String> getFlights(String date, String origin, String destination)
            throws ParseException {
        // TODO: complete/rewrite this method body
        // The code below gives you the format in which the auto-tester expects the output.
        return listFlights.getFlight(date, origin, destination);
    }

    /**
     * Returns all itineraries that depart from origin and arrive at destination on the given date. If
     * an itinerary contains two consecutive flights F1 and F2, then the destination of F1 should
     * match the origin of F2. To simplify our task, if there are more than MAX_LAYOVER hours or less
     * than MIN_LAYOVER between the arrival of F1 and the departure of F2, then we do not consider
     * this sequence for a possible itinerary.
     *
     * @param date a departure date (in the format yyyy-MM-dd)
     * @param origin a flight original
     * @param destination a flight destination
     * @return itineraries that depart from origin and arrive at destination on the given date with
     *         valid layover. Each itinerary in the output should contain one line per flight, in the
     *         format:
     *         Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination
     *         where departure and arrival date-times are in the format yyyy-MM-dd HH:mm,
     *         followed by total price (on its own line, exactly 2 decimal places),
     *         followed by total duration (on its own line, in the format HH:mm).
     */
    public static List<String> getItineraries(String date, String origin, String destination) {
        // The code below gives you the format in which the auto-tester expects the output,
        // as well as some ideas about the built-in Java classes to handle dates, times, etc.
        // Make sure to read the API carefully: what you need will depend on your design!
        Itinerary itinerary = new Itinerary(listFlights, date, origin, destination,
                MIN_LAYOVER, MAX_LAYOVER);
        return itinerary.getAllPaths();
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted according to total
     * itinerary cost, in non-decreasing order.
     *
     * @param date a departure date (in the format yyyy-MM-dd)
     * @param origin a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of total itinerary cost) in the same format
     *         as in getItineraries.
     */
    public static List<String> getItinerariesSortedByCost(String date, String origin, String destination) {
        Itinerary itinerary = new Itinerary(listFlights, date, origin, destination,
                MIN_LAYOVER, MAX_LAYOVER);
        return itinerary.getItinerariesSortedByCost(itinerary.getAllPaths());
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted according
     * to total itinerary travel time, in non-decreasing order.
     * @param date a departure date (in the format yyyy-MM-dd)
     * @param origin a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of total travel time) in the same format
     *         as in getItineraries.
     */
    public static List<String> getItinerariesSortedByTime(String date, String origin, String destination) {
        Itinerary itinerary = new Itinerary(listFlights, date, origin, destination,
                MIN_LAYOVER, MAX_LAYOVER);
        return itinerary.getItinerariesSortedByTime(itinerary.getAllPaths());
    }
}