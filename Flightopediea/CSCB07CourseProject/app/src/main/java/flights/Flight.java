package flights;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a <code>Flight</code> given the flight Info.
 * 
 *           String of flight information
 * @throws ParseException
 *           if departDateTime and arrivalDateTime is not in the right format
 */
public class Flight {

  private String flightNumber;
  private Date departDate;
  private Date arrivalDate;
  private Date arrivalTime;
  private Date departTime;
  private String airline;
  private String origin;
  private String destination;
  private double cost;
  private int numSeats;
  private DateFormat time;
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
    time = new SimpleDateFormat("HH:mm");
    date = new SimpleDateFormat("yyyy-MM-dd");

    String[] flightInfoArray = flightInfo.split(";");
    flightNumber = flightInfoArray[0];
    departDate = date.parse(flightInfoArray[1].split(" ")[0]);
    departTime = time.parse(flightInfoArray[1].split(" ")[1]);
    arrivalDate = date.parse(flightInfoArray[2].split(" ")[0]);
    arrivalTime = time.parse(flightInfoArray[2].split(" ")[1]);
    airline = flightInfoArray[3];
    origin = flightInfoArray[4];
    destination = flightInfoArray[5];
    cost = Double.valueOf(flightInfoArray[6]);
    numSeats = Integer.valueOf(flightInfoArray[7]);
  }

  /**
   * Creates a new <code>Flight</code>.
   */
  public Flight(){
    time = new SimpleDateFormat("HH:mm");
    date = new SimpleDateFormat("yyyy-MM-dd");
  }

  @Override
  public String toString() {
    return String.format("%s;%s;%s;%s;%s;%s;%.2f", flightNumber,
            getDepatureDateTime(),getArrivalDateTime(), airline, origin, destination, cost);
  }

  /**
   * Return the format of the Flight to store in Itinerary.
   *
   * @return flightNumber the new flight number for this <code>Flight</code>
   */
  public String getItineraryFlight() {
    return String.format("%s;%s;%s;%s;%s;%s", flightNumber,
            getDepatureDateTime(), getArrivalDate(), airline, origin, destination);
  }

  /**
   * Display the flight information for the <code>Flight</code>.
   * @return a string showing the information of this <code>Flight</code>
   */
  public String displayFlightString(){
    return String.format("%s %s\n%s: %s\n%s: %s\nCost: %.2f\nSeats: %d", flightNumber, airline,
            origin,getDepatureDateTime(),destination,getArrivalDateTime(), cost,numSeats);
  }

  /**
   * Gets the departure date and time of this <code>Flight</code>.
   * @return the string showing the date and time of the departure of this <code>Flight</code>
     */
  public String getDepatureDateTime(){
    return String.format("%s %s",date.format(departDate),time.format(departTime));
  }

  /**
   * Gets the arrival date and time of this <code>Flight</code>.
   * @return a string showing the date and time of the arrival of this <code>Flight</code>
     */
  public String getArrivalDateTime(){
    return String.format("%s %s",date.format(arrivalDate),time.format(arrivalTime));
  }

  /**
   * Set the number of seats in this <code>Flight</code>.
   * @param seats the number of seats in the flight.
     */
  public void setNumSeats(int seats){
    numSeats = seats;
  }

  /**
   * Get the number of seats in this <code>Flight</code>.
   * @return the number of seats in this <code>Flight</code>
     */
  public int getNumSeats(){
    return numSeats;
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
  public String getDepartDate() {
    return date.format(departDate);
  }

  /***
   * Get the departure time of this <code>Flight</code>.
   * @return the departure time of this <code>Flight</code>.
     */
  public String getDepartTime(){
    return time.format(departTime);
  }

  /**
   * Sets the value of this <code>Flight</code>'s departure date/time to
   * departDateTime.
   * 
   * @param departDate
   *          the new departure date/time for this <code>Flight</code>
   */
  public void setDepartDate(String departDate) throws ParseException {
    this.departDate = date.parse(departDate);
  }

  /**
   * Set the departure time of this <code>Flight</code>.
   * @param departTime the departure time of the flight to be set
   * @throws ParseException if the given departure time cannot be parsed
     */
  public void setDepartTime(String departTime) throws ParseException {
    this.departTime = time.parse(departTime);
  }

  /**
   * Returns the arrival date of this <code>Flight</code>.
   * 
   * @return the arrival date of this <code>Flight</code>
   */
  public String getArrivalDate() {
    return date.format(arrivalDate);
  }

  /**
   * Sets the value of this <code>Flight</code>'s arrival date/time to
   * arrivalDateTime.
   * 
   * @param arrivalDate
   *          the new arrival date/time for this <code>Flight</code>
   */
  public void setArrivalDate(String arrivalDate) throws ParseException {
    this.arrivalDate = date.parse(arrivalDate);
  }

  /**
   * Return the arrival time of this <code>Flight</code>.
   * @return the arrival time of this <code>Flight</code>.
     */
  public String getArrivalTime(){
    return time.format(arrivalTime);
  }

  /**
   * Set the arrival time of this <code>Flight</code>.
   * @param arrivalTime the arrival time of the flight to be set
   * @throws ParseException if the arrival time cannot be parsed
     */
  public void setArrivalTime(String arrivalTime) throws ParseException {
    this.arrivalTime = time.parse(arrivalTime);
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
