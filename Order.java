import java.util.*;
import java.io.*;

public class Order implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String ORDER_STRING = "O";
   private String id;
   private List<Record> items;
   
   public Order(String clientId) {
      this.id = ORDER_STRING + (OrderIdServer.instance()).getId();
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

   public String toString() {
      return String.format("[%s] is an order with [%d] items", id, items.size());
   }
}