import java.util.*;
import java.io.*;

public class Order implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String ORDER_STRING = "O";
   private String id;
   private List<Record> items;
   private List<WaitlistItem> waitlistedItems;
   private Client client;
   private boolean waitlisted;
   
   public Order(Client client) {
      this.id = ORDER_STRING + (OrderIdServer.instance()).getId();
      this.items = new LinkedList<Record>();
      this.waitlistedItems = new LinkedList<WaitlistItem>();
      this.client = client;
   }

   public String getId() {
      return id;
   }

   public Client getClient() {
      return client;
   }

   public void addRecord(Record record) {
      items.add(record);
   }

   public void addWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   public void removeWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   public Iterator<WaitlistItem> getWaitlistedItems() {
      return waitlistedItems.iterator();
   }

   public boolean hasWaitlistedItems() {
      return waitlistedItems.size() != 0;
   }

   public Iterator<Record> getRecords() {
      return items.iterator();
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return String.format("[%s] is an order with [%d] items", id, items.size());
   }
}