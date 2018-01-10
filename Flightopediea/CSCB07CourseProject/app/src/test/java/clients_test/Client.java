package clients_test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a <code>Client</code> given the client Info.
 * @throws ParseException if the expiry date is not in the right format
 */
public class Client {
  private String lastName;
  private String firstName;
  private String email;
  private String address;
  private String creditCardNumber;
  private Date expiryDate;
  private DateFormat dateTime;

  /**
   * Creates a <code>Client</code> given the client Info. 
   * @param clientInfo String of client information
   * @throws ParseException if the expiry date is not in the right format
   */
  public Client(String clientInfo) throws ParseException {
    dateTime = new SimpleDateFormat("yyyy-MM-dd");

    String[] clientInfoArray = clientInfo.split(";");
    lastName = clientInfoArray[0];
    firstName = clientInfoArray[1];
    email = clientInfoArray[2];
    address = clientInfoArray[3];
    creditCardNumber = clientInfoArray[4];
    expiryDate = dateTime.parse(clientInfoArray[5]);
  }

  @Override
  public String toString() {
    return String.format("%s;%s;%s;%s;%s;%s", lastName, firstName, email,
        address, creditCardNumber, dateTime.format(expiryDate));
  }

  /**
   * Returns the last name of this <code>Client</code>.
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * Sets the value of this <code>Client</code>'s last name to lastName.
   * @param lastName the new last name for this <code>Client</code>
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the first name of this <code>Client</code>.
   * @return the first name of this <code>Client</code>
   */
  public String getFirstNames() {
    return firstName;
  }

  /**
   * Sets the value of this <code>Client</code>'s first name to firstNames.
   * @param firstNames the new first name for this <code>Client</code>
   */
  public void setFirstNames(String firstNames) {
    this.firstName = firstNames;
  }

  /**
   * Returns the email of this <code>Client</code>.
   * @return the email of this <code>Client</code>
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the value of this <code>Client</code>'s email to email.
   * @param email the new email for this <code>Client</code>
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the address of this <code>Client</code>.
   * @return the email of this <code>Client</code>
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the value of this <code>Client</code>'s address to address.
   * @param address the new address for this <code>Client</code>
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the credit card number of this <code>Client</code>.
   * @return the credit card number of this <code>Client</code>
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  /**
   * Sets the value of this <code>Client</code>'s credit card number to creditCardNumber.
   * @param creditCardNumber the new credit card number for this <code>Client</code>
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Returns the expiry date of this <code>Client</code>'s credit card.
   * @return the expiry date of this <code>Client</code>'s credit card
   */
  public Date getExpiryDate() {
    return expiryDate;
  }

  /**
   * Sets the value of this <code>Client</code>'s credit card expiry date to expiryDate.
   * @param expiryDate the new credit card expiry date for this <code>Client</code>
   */
  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * Return the DateFormat.
   * @return DateFormat
   */
  public Date getDateTime() {
    return expiryDate;
  }

  /**
   * Set a new Date.
   * @param dateTime date
   */
  public void setDateTime(DateFormat dateTime) {
    this.dateTime = dateTime;
  }

}
