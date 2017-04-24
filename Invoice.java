import java.util.*;
import java.io.*;

/**
 * Holds the information for the invoice
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Invoice implements Serializable {

   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Used for serialization also
    */
   private static final String INVOICE_STRING = "I";
   
   /**
    * Store the id that was given from the id server
    */
   private String id;

   /**
    * Data structure to hold the records of the invoice
    */
   private List<Record> items; 

   /**
    * The client for this invoice
    */
   private Client client;  
   
   /**
    * Constructor for the Client
    * @param       client with which the invoice is associated with
    */
   public Invoice(Client client) {
      this.id = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
      this.items = new LinkedList<Record>();
      this.client = client;
   }

   /**
    * Query the invoice's id
    * @return      Returns the invoice's id
    * @pre         None
    * @post        None
    * @see         InvoiceIdServer
    */
   public String getId() {
      return id;
   }

   /**
    * Query the invoice's client
    * @return      Returns the invoice's client
    * @pre         None
    * @post        None
    * @see         Client
    */
   public Client getClient() {
      return client;
   }

   /**
    * Adds a record to the invoice's records
    * @param       record to be added to the invoice's record
    * @pre         None
    * @post        The added to the invoice
    * @see         Record
    */
   public void addRecord(Record record) {
      items.add(record);
   }

   /**
    * Query the invoice's records
    * @return      Returns an in iterator of the invoice's records
    * @pre         None
    * @post        None
    * @see         Record
    */
   public Iterator<Record> getRecords() {
      return items.iterator();
   }

   /**
    * Queries this invoice's records to find out if there are any records
    * @return      true if there are records; otherwise, false
    * @pre         None
    * @post        None
    * @see         Record
    */
   public boolean isEmpty() {
      return items.size() == 0;
   }

   /**
    * Calculate the total cost of all the records within the invoice
    * @return      The total cost of all records summed up in the invoice
    * @pre         None
    * @post        None
    * @see         Record
    */
   public float calculateCost() {
      float total = 0;
      for (Iterator<Record> records = items.iterator(); records.hasNext();) {
         Record record = records.next();
         total += (record.getPrice() * record.getQuantity());
      }
      return total;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format("[%s] is an invoice for [%d] item(s)", id, items.size());
   }
}
