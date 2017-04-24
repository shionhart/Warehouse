import java.util.*;
import java.io.*;

/**
 * Collection of suppliers
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class ClientList implements Serializable {
   private static final long serialVersionUID = 1L;
   private static ClientList clientList;
   private List<Client> clients = new LinkedList<Client>();
   private List<Client> clientsWithBalance = new LinkedList<Client>();
   
   /**
    * Private ClientList constructor
    */
   private ClientList() {}

   /**
    * Singleton ClientList Constructor
    * <p>
    * This grabs, or creates, the ClientList
    * <p>
    * @return the ClientList singleton instance 
    */
   public static ClientList instance() {
      if (clientList == null) {
         return (clientList = new ClientList());
      } else {
         return clientList;
      }
   }

   /**
    * Queries the list of clients to find out if the list is empty or not
    * @return      true if there are no clients in the list; otherwise, false
    * @see         Supplier
    */
   public boolean isEmpty() {
      return clients.size() == 0;
   }

   /**
    * Inserts the given client into the list of clients
    * @param       client is the client object to be added to the clients list
    * @return      true if the client was successfully added; otherwise, false
    * @see         Client
    */
   public boolean insertClient(Client client) {
      return clients.add(client);
   }

   /**
    * Queries the list of clients
    * @return      An iterator to navigate through the list of clients
    * @see         Client
    */
   public Iterator<Client> getClients(){
      return clients.iterator();
   }

   /**
    * Queries the list of clients and finds out if any have balances on their account
    * @return      true if there is a client with an unpaid balance; otherwise, false
    * @see         Client
    */
   public boolean hasUnpaid() {
      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getBalance() > 0) {
            return true;
         }
      }
      return false;
   }

   /**
    * Queries the list of clients with unpaid balances, after compiling the list
    * @return      An iterator to navigate through the list of clients with unpaid balances
    * @see         Client
    */
   public Iterator<Client> getUnpaid() {

      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getBalance() > 0) {
            clientsWithBalance.add(client);
         }
      }
      return clientsWithBalance.iterator();
   }

   /**
    * Searches for a client's existence within the list of clients, based on the parameters given
    * @param       clientId of the client to search for within the list of clients
    * @return      The found client object if the client is found in the list of clients; otherwise, null
    * @see         Client
    */
   public Client find(String clientId) {
      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getId().equals(clientId)) {
            return client;
         }
      }
      return null;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return clients.toString();
   }
  
   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @see Warehouse
    */
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(clientList);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }

   /**
    * Helper function for the retrieve function in Warehouse, which will be used during serialization
    * @see Warehouse
    */
   private void readObject(java.io.ObjectInputStream input) {
      try {
         if (clientList != null) {
            return;
         } else {
            input.defaultReadObject();
            if (clientList == null) {
               clientList = (ClientList) input.readObject();
            } else {
               input.readObject();
            }
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
      }
   }
}
