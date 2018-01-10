package client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

/**
 * Client class.
 */
public class Client {

  private String lastName;
  private String firstName;
  private String email;
  private String address;
  private String creditCardNumber;
  private Date expiryDate;
  private String password;
  private DateFormat date;
  private TreeSet<String> flights;

  /**
   * Creates a <code>Client</code> given the client Info.
   */
  public Client(String clientInfo) throws ParseException {
    String[] clientInfoArray = clientInfo.split(";");
    date = new SimpleDateFormat("yyyy-MM-dd");
    lastName = clientInfoArray[0];
    firstName = clientInfoArray[1];
    email = clientInfoArray[2];
    address = clientInfoArray[3];
    creditCardNumber = clientInfoArray[4];
    expiryDate = date.parse(clientInfoArray[5]);
    password = "12345";
    flights = new TreeSet<>();
  }

  public Client() {
    date = new SimpleDateFormat("yyyy-MM-dd");
    flights = new TreeSet<>();
  }
  /**
   * Returns the flights of this <code>Client</code>.
   *
   * @return flights for the client
   */
  public TreeSet<String> getItinerary(){
    return  flights;
  }

  /**
   * Adds an itinerary to this <code>Client</code>.
   *
   * @param iti a new itinerary for this <code>Client</code>
   */
  public void addItinerary(String iti){
    flights.add(iti);
  }

  /**
   * Sets an itinerary to this <code>Client</code>.
   *
   * @param itinerary an itinerary to be set to this <code>Client</code>
   */
  public void setItinerary(TreeSet<String> itinerary){
    flights = itinerary;
  }

  @Override
  public String toString() {
    return String.format("%s;%s;%s;%s;%s;%s", lastName, firstName, email,
            address, creditCardNumber, date.format(expiryDate));
  }

  /**
   * Used to display the <code>Client</code>'s information.
   *
   * @return the information of this <code>Client</code>
   */
  public String getDisplayFormat() {
    return String.format("%s\n%s %s\n%s\nCreditCard: %s\nExp: %s", email,
            firstName, lastName, address, creditCardNumber, date.format(expiryDate));
  }

  /**
   * Returns the last name of this <code>Client</code>.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * Sets the value of this <code>Client</code>'s last name to lastName.
   *
   * @param lastName the new last name for this <code>Client</code>
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the first name of this <code>Client</code>.
   *
   * @return the first name of this <code>Client</code>
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the value of this <code>Client</code>'s first name to firstNames.
   *
   * @param firstNames the new first name for this <code>Client</code>
   */
  public void setFirstName(String firstNames) {
    this.firstName = firstNames;
  }

  /**
   * Returns the email of this <code>Client</code>.
   *
   * @return the email of this <code>Client</code>
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the value of this <code>Client</code>'s email to email.
   *
   * @param email the new email for this <code>Client</code>
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the address of this <code>Client</code>.
   *
   * @return the email of this <code>Client</code>
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the value of this <code>Client</code>'s address to address.
   *
   * @param address the new address for this <code>Client</code>
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the credit card number of this <code>Client</code>.
   *
   * @return the credit card number of this <code>Client</code>
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  /**
   * Sets the value of this <code>Client</code>'s credit card number to creditCardNumber.
   *
   * @param creditCardNumber the new credit card number for this <code>Client</code>
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Returns the expiry date of this <code>Client</code>'s credit card.
   *
   * @return the expiry date of this <code>Client</code>'s credit card
   */
  public String getExpiryDate() {
    return date.format(expiryDate);
  }

  /**
   * Sets the value of this <code>Client</code>'s credit card expiry date to expiryDate.
   *
   * @param expiryDate the new credit card expiry date for this <code>Client</code>
   */
  public void setExpiryDate(String expiryDate) throws ParseException{
    this.expiryDate = date.parse(expiryDate);
  }

  /**
   * Sets the password for this <code>Client</code>.
   *
   * @param password for this <code>Client</code>
   * @return the email of this <code>Client</code>
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the password for this <code>Client</code>.
   *
   * @return the password of this <code>Client</code>
   */
  public String getPassword() {
    return password;
  }
}