import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String PRODUCT_STRING = "P";
   private String name;
   
   private String id;
   private int quantity;
   private float price;
   private List<String> supplierIds;
   private Queue<WaitlistItem> waitlistedOrders;


   public Product(String name, float price) {
      this.id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
      this.name = name;
      this.price = price;
      this.quantity = 0;
      this.supplierIds = new LinkedList<String>();
      this.waitlistedOrders = new LinkedList<WaitlistItem>();
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int amount) {
      quantity = amount;
   }

   public void increaseQuantity(int amount) {
      quantity += amount;
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }

   public float getPrice() {
      return price;
   }

   public int getSupplierCount() {
      return supplierIds.size();
   }

   public boolean hasWaitlistedOrders() {
      return waitlistedOrders.size() != 0;
   }

   public Iterator<String> getSupplierIds() {
      return supplierIds.iterator();
   }

   public boolean hasSupplier(String supplierId) {
      for (Iterator<String> sids = supplierIds.iterator(); sids.hasNext();) {
         String sid = sids.next();
         if (sid.equals(supplierId)) {
            return true;
         }
      }
      return false;
   }

   public boolean addSupplier(String supplierId) {
      return supplierIds.add(supplierId);
   }

   public boolean removeSupplier(String supplierId) {
      return supplierIds.remove(supplierId);
   }

   public void addToWaitlist(WaitlistItem item) {
      waitlistedOrders.add(item);
   }

   public WaitlistItem getNextWaitlistedOrder() {
      return waitlistedOrders.peek();
   }

   public void removeWaitlistItem(WaitlistItem item) {
      waitlistedOrders.remove(item);
   }

   public Iterator<WaitlistItem> getWaitlistedOrders(){
      return waitlistedOrders.iterator();
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return String.format(
         "[%s] is a product with name: [%s], price per item: [$%.2f], and quantity: [%s]", 
         id, name, price, quantity
      );
   }
}
