import java.util.*;
import java.io.*;

public class ClientList implements Serializable {
   private static final long serialVersionUID = 1L;

   // The "List" is used to maintain the actual client list
   private List<Client> clients = new LinkedList<Client>();
   private List<Client> clientsWithBalance = new LinkedList<Client>();

   // This variable is used for the singleton "ClientList" instance
   private static ClientList clientList;

   private ClientList() {}

   // Used to create the singleton instance
   public static ClientList instance() {
      if (clientList == null) {
         return (clientList = new ClientList());
      } else {
         return clientList;
      }
   }

   public boolean isEmpty() {
      return clients.size() == 0;
   }

   public boolean insertClient(Client client) {
      return clients.add(client);
   }

   public Iterator<Client> getClients(){
      return clients.iterator();
   }

   public boolean hasUnpaid() {
      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getBalance() > 0) {
            return true;
         }
      }
      return false;
   }

   // terrible design, because it is extremely inefficient
   public Iterator<Client> getUnpaid() {
      // compile unpaid list and return the iterator for it

      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getBalance() > 0) {
            clientsWithBalance.add(client);
         }
      }
      return clientsWithBalance.iterator();
   }

   public Client find(String clientId) {
      for (Iterator<Client> allClients = clients.iterator(); allClients.hasNext();) {
         Client client = allClients.next();
         if (client.getId().equals(clientId)) {
            return client;
         }
      }
      return null;
   }
  
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(clientList);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }

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

   public String toString() {
      return clients.toString();
   }

   public static void main(String[] s) {
      System.out.println("Welcome to the ClientList class.");
   }
}