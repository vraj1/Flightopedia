package clients_test;

import exception.NoSuchClientException;

import java.util.HashMap;

/** Creates a list of <code>Clients</code>. */
public class ClientList {
  private HashMap<String, Client> clientMap;

  /**
   * Creates a <code>ClientList</code> using a Hash Map in the format Client's
   * email , <code>Client</code>.
   */
  public ClientList() {
    clientMap = new HashMap<String, Client>();
  }

  /**
   * Add the new <code>Client</code> to the <code>ClientList</code>. If such client
   * exists, replace the information.
   */
  public void addClient(Client client) {
    clientMap.put(client.getEmail(), client);
  }

  /**
   * Remove the client with the email from the <code>ClientList</code>.
   * 
   * @param email
   *          the email of the <code>Client</code> to be removed from the
   *          <code>ClientList</code>
   * @throws NoSuchClientException
   *           if <code>Client</code> does not exist in <code>ClientList</code>
   */
  public void removeClient(String email) throws NoSuchClientException {
    if (!(clientMap.containsKey(email))) {
      throw new NoSuchClientException("Client Does Not Exist");
    }
    clientMap.remove(email);
  }

  /**
   * Give the <code>Client</code> that has the given email.
   * 
   * @param email
   *          of the <code>Client</code>
   * @return returns the <code>Client</code> from the <code>ClientList</code>
   *         given the email
   * @throws NoSuchClientException
   *           if <code>Client</code> does not exit in <code>ClientList</code>
   */
  public Client getClient(String email) throws NoSuchClientException {
    if (!(clientMap.containsKey(email))) {
      throw new NoSuchClientException("Client Does Not Exist");
    }
    return clientMap.get(email);
  }

}
