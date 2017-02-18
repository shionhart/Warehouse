import java.util.*;
import java.io.*;

public class Invoice implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String INVOICE_STRING = "I";
   private String id;
   private List<Record> items;   
   
   public Invoice(String clientId) {
      this.id = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
      this.items = new LinkedList<Record>();
   }

   public String getId() {
      return id;
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

   public String toString() {
      return String.format("[%s] is an invoice for [%d] items", id, items.size());
   }
}