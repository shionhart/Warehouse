import java.util.*;
import java.io.*;

/**
 * Holds the information for the order
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Order implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String ORDER_STRING = "O";
   private String id;
   private List<Record> items;
   private List<WaitlistItem> waitlistedItems;
   private Client client;
   private boolean waitlisted;
   
   /**
    * Constructor for the Order
    * @param       client that the order is associated to
    * @see         Client
    */
   public Order(Client client) {
      this.id = ORDER_STRING + (OrderIdServer.instance()).getId();
      this.items = new LinkedList<Record>();
      this.waitlistedItems = new LinkedList<WaitlistItem>();
      this.client = client;
   }

   /**
    * Query the order's id
    * @return      Returns the orders's id
    * @see         OrderIdServer
    */
   public String getId() {
      return id;
   }

   /**
    * Query the order's client
    * @return      Returns the order's client
    * @see         Order
    */
   public Client getClient() {
      return client;
   }

   /**
    * Adds a record to the order's records
    * @param       record to be added to the order's records
    * @see         Record
    */
   public void addRecord(Record record) {
      items.add(record);
   }

   /**
    * Adds a waitlisted item to the order's waitlisted items
    * @param       item to be added to the order's waitlisted items
    * @see         WaitlistItem
    */
   public void addWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   /**
    * Remove a waitlisted item from the order's waitlisted items
    * @param       item to be removed from the order's waitlisted items
    * @see         WaitlistItem
    */
   public void removeWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   /**
    * Query the order's waitlisted items
    * @return      Returns an in iterator of the order's waitlisted items
    * @see         WaitlistItem
    */
   public Iterator<WaitlistItem> getWaitlistedItems() {
      return waitlistedItems.iterator();
   }

   /**
    * Queries this order's waitlisted items to find out if there are any waitlisted items
    * @return      true if there are waitlisted items; otherwise, false
    * @see         WaitlistItem
    */
   public boolean hasWaitlistedItems() {
      return waitlistedItems.size() != 0;
   }

   /**
    * Query the order's records
    * @return      Returns an in iterator of the order's records
    * @see         Record
    */
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