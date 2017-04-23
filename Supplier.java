import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String id;
   private List<String> productIds;

   private static final String SUPPLIER_STRING = "S";
   
   public Supplier(String name) {
      this.name = name;
      this.id = SUPPLIER_STRING + (SupplierIdServer.instance()).getId();
      this.productIds = new LinkedList<String>();
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }
  
   public void setName(String newName) {
      name = newName;
   }
  
   public boolean equals(String id) {
      return this.id.equals(id);
   }

   public int getProductCount() {
      return productIds.size();
   }

   public Iterator getProductIds() {
      return productIds.iterator();
   }

   public boolean suppliesProduct(String productId) {
      for (Iterator<String> pids = productIds.iterator(); pids.hasNext();) {
         String pid = pids.next();
         if (pid.equals(productId)) {
            return true;
         }
      }
      return false;
   }

   public boolean addProduct(String productId) {
      return productIds.add(productId);
   }

   public boolean removeProduct(String productId) {
      return productIds.remove(productId);
   }
  
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return String.format("[%s] is a supplier with name: [%s]", id, name);
   }
}
