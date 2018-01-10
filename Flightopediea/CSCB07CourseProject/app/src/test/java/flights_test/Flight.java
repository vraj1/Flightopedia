package flights_test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Creates a <code>Flight</code> given the flight Info.
 * 
 *
 * @throws ParseException
 *           if departDateTime and arrivalDateTime is not in the right format
 */
public class Flight {

  private String flightNumber;
  private Date departDateTime;
  private Date arrivalDateTime;
  private String airline;
  private String origin;
  private String destination;
  private double cost;
  private DateFormat dateTime;
  private DateFormat date;

  /**
   * Creates a <code>Flight</code> given the flight Info.
   * 
   * @param flightInfo
   *          String of flight information.
   * @throws ParseException
   *           if departDateTime and arrivalDateTime is not in the right format
   */
  public Flight(String flightInfo) throws ParseException {
    dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    date = new SimpleDateFormat("yyyy-MM-dd");

    String[] flightInfoArray = flightInfo.split(";");
    flightNumber = flightInfoArray[0];
    departDateTime = dateTime.parse(flightInfoArray[1]);
    arrivalDateTime = dateTime.parse(flightInfoArray[2]);
    airline = flightInfoArray[3];
    origin = flightInfoArray[4];
    destination = flightInfoArray[5];
    cost = Double.valueOf(flightInfoArray[6]);
  }

  @Override
  public String toString() {
    long duration = arrivalDateTime.getTime() - departDateTime.getTime();
    long hours0 = TimeUnit.MILLISECONDS.toHours(duration);
    long mins0 = TimeUnit.MILLISECONDS.toMinutes(duration) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
    return String.format("%s;%s;%s;%s;%s;%s;%.2f;%s", flightNumber,
        dateTime.format(departDateTime), dateTime.format(arrivalDateTime),
        airline, origin, destination, cost,String.format("%02d:%02d", hours0, mins0));
  }

  /**
   * Return the format of the Flight to store in Itinerary.
   * 
   * @return flightNumber the new flight number for this <code>Flight</code>
   */
  public String getItineraryFlight() {
    return String.format("%s;%s;%s;%s;%s;%s", flightNumber,
        dateTime.format(departDateTime), dateTime.format(arrivalDateTime),
        airline, origin, destination);
  }

  /**
   * Return the Flight Number.
   * 
   * @return String FlightNumber
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * Sets the value of this <code>Flight</code>'s flight number to flightNumber
   * .
   * 
   * @param flightNumber
   *          the new flight number for this <code>Flight</code>
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  /**
   * Returns the departure date&time of this <code>Flight</code>.
   * 
   * @return the departure date&time of this <code>Flight</code>
   */
  public String getDepartDateTime() {
    return dateTime.format(departDateTime);
  }

  /**
   * Returns the departure date of this <code>Flight</code>.
   * 
   * @return the departure date of this <code>Flight</code>
   */
  public String getDepartDate() {
    return date.format(departDateTime);
  }

  /**
   * Sets the value of this <code>Flight</code>'s departure date/time to
   * departDateTime.
   * 
   * @param departDateTime
   *          the new departure date/time for this <code>Flight</code>
   */
  public void setDepartDateTime(String departDateTime) throws ParseException {
    this.departDateTime = dateTime.parse(departDateTime);
  }

  /**
   * Returns the arrival date/time of this <code>Flight</code>.
   * 
   * @return the arrival date/time of this <code>Flight</code>
   */
  public String getArrivalDateTime() {
    return dateTime.format(arrivalDateTime);
  }

  /**
   * Sets the value of this <code>Flight</code>'s arrival date/time to
   * arrivalDateTime.
   * 
   * @param arrivalDateTime
   *          the new arrival date/time for this <code>Flight</code>
   */
  public void setArrivalDateTime(String arrivalDateTime) throws ParseException {
    this.arrivalDateTime = dateTime.parse(arrivalDateTime);
  }

  /**
   * Return the Airline Name.
   * 
   * @return AirLine Name
   */
  public String getAirline() {
    return airline;
  }

  /**
   * Sets the value of this <code>Flight</code>'s airline to airline.
   * 
   * @param airline
   *          the new airline for this <code>Flight</code>
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * Returns the origin of this <code>Flight</code>.
   * 
   * @return the origin of this <code>Flight</code>
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Sets the value of this <code>Flight</code>'s origin to origin.
   * 
   * @param origin
   *          the new origin for this <code>Flight</code>
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * Returns the destination of this <code>Flight</code>.
   * 
   * @return the destination of this <code>Flight</code>
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the value of this <code>Flight</code>'s destination to destination.
   * 
   * @param destination
   *          the new destination for this <code>Flight</code>
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Returns the cost of this <code>Flight</code>.
   * 
   * @return the cost of this <code>Flight</code>
   */
  public double getCost() {
    return cost;
  }

  /**
   * Sets the value of this <code>Flight</code>'s cost to cost.
   * 
   * @param cost
   *          the new cost for this <code>Flight</code>
   */
  public void setCost(double cost) {
    this.cost = cost;
  }
}
