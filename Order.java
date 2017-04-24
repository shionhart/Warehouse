import java.util.*;
import java.io.*;

/**
 * Holds the information for the order
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Order implements Serializable {

   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Used for serialization also
    */
   private static final String ORDER_STRING = "O";

   /**
    * Store the id that was given from the id server
    */
   private String id;

   /**
    * Data structure to hold the records for the order
    */
   private List<Record> items;

   /**
    * Data structure to hold the waitlisted items for this order
    */
   private List<WaitlistItem> waitlistedItems;

   /**
    * Order's client that made the order
    */
   private Client client;

   /**
    * Whether the order is waitlisted or not
    */
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
    * @pre         None
    * @post        None
    * @see         OrderIdServer
    */
   public String getId() {
      return id;
   }

   /**
    * Query the order's client
    * @return      Returns the order's client
    * @pre         None
    * @post        None
    * @see         Order
    */
   public Client getClient() {
      return client;
   }

   /**
    * Adds a record to the order's records
    * @param       record to be added to the order's records
    * @pre         None
    * @post        A record will be added to this order
    * @see         Record
    */
   public void addRecord(Record record) {
      items.add(record);
   }

   /**
    * Adds a waitlisted item to the order's waitlisted items
    * @param       item to be added to the order's waitlisted items
    * @pre         None
    * @post        A waitlisted item will be added to this order
    * @see         WaitlistItem
    */
   public void addWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   /**
    * Remove a waitlisted item from the order's waitlisted items
    * @param       item to be removed from the order's waitlisted items
    * @pre         None
    * @post        A waitlisted item will be removed from this order
    * @see         WaitlistItem
    */
   public void removeWaitlistItem(WaitlistItem item) {
      waitlistedItems.add(item);
   }

   /**
    * Query the order's waitlisted items
    * @return      Returns an in iterator of the order's waitlisted items
    * @pre         None
    * @post        None
    * @see         WaitlistItem
    */
   public Iterator<WaitlistItem> getWaitlistedItems() {
      return waitlistedItems.iterator();
   }

   /**
    * Queries this order's waitlisted items to find out if there are any waitlisted items
    * @return      true if there are waitlisted items; otherwise, false
    * @pre         None
    * @post        None
    * @see         WaitlistItem
    */
   public boolean hasWaitlistedItems() {
      return waitlistedItems.size() != 0;
   }

   /**
    * Query the order's records
    * @return      Returns an in iterator of the order's records
    * @pre         None
    * @post        None
    * @see         Record
    */
   public Iterator<Record> getRecords() {
      return items.iterator();
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format("[%s] is an order with [%d] items", id, items.size());
   }
}
