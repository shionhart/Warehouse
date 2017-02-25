import java.util.*;
import java.io.*;

public class ClientList implements Serializable {
   private static final long serialVersionUID = 1L;
   private static ClientList clientList;
   private List<Client> clients = new LinkedList<Client>();
   private List<Client> clientsWithBalance = new LinkedList<Client>();
   private ClientList() {}

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
   // compile unpaid list and return the iterator for it
   public Iterator<Client> getUnpaid() {

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

   public String toString() {
      return clients.toString();
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
}