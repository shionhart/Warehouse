import java.util.*;
import java.io.*;

public class Invoice implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String INVOICE_STRING = "I";
   private String id;
   private List<Record> items; 
   private Client client;  
   
   public Invoice(Client client) {
      this.id = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
      this.items = new LinkedList<Record>();
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

   public Iterator<Record> getRecords() {
      return items.iterator();
   }

   public boolean isEmpty() {
      return items.size() == 0;
   }

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
    */
   public String toString() {
      return String.format("[%s] is an invoice for [%d] item(s)", id, items.size());
   }
}