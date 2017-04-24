import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * Holds the information for the product
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Product implements Serializable {
   
   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Used for serialization also
    */
   private static final String PRODUCT_STRING = "P";

   /**
    * Store the id that was given from the id server
    */
   private String id;

   /**
    * Product's name
    */
   private String name;
   
   /**
    * Product's quantity in stock
    */
   private int quantity;

   /**
    * Product's price
    */
   private float price;

   /**
    * Data structure to hold the supplierIds associated with this product
    */
   private List<String> supplierIds;

   /**
    * Data structure to hold the waitlisted orders for this product
    */
   private Queue<WaitlistItem> waitlistedOrders;

   /**
    * Constructor for the Product
    * @param       name of the product
    * @param       price of the product
    */
   public Product(String name, float price) {
      this.id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
      this.name = name;
      this.price = price;
      this.quantity = 0;
      this.supplierIds = new LinkedList<String>();
      this.waitlistedOrders = new LinkedList<WaitlistItem>();
   }

   /**
    * Query the product's quantity in stock
    * @return      Returns the product's quantity in stock
    * @pre         None
    * @post        None
    */
   public int getQuantity() {
      return quantity;
   }

   /**
    * Set the product's quantity in stock
    * @param      amount is what the product's quantity will be set to
    * @pre        The amount specified is a positive integer
    * @post       The quantity of this product will be set to the amount specified
    */
   public void setQuantity(int amount) {
      quantity = amount;
   }

   /**
    * Increase the product's quantity in stock
    * @param      amount is what the amount the product's quantity will be increased
    * @pre        The amount specified is a positive integer
    * @post       The quantity of this product will be increased the amount specified
    */
   public void increaseQuantity(int amount) {
      quantity += amount;
   }

   /**
    * Query the product's name
    * @return     Returns the product's name
    * @pre        None
    * @post       None
    */
   public String getName() {
      return name;
   }

   /**
    * Query the product's id
    * @return     Returns the product's id
    * @pre        None
    * @post       None
    * @see        ProductIdServer
    */
   public String getId() {
      return id;
   }

   /**
    * Query the product's price
    * @return     Returns the product's price
    * @pre        None
    * @post       None
    */
   public float getPrice() {
      return price;
   }

   /**
    * Query the number of suppliers associated with the product
    * @return     Returns the number of suppliers associated to the product
    * @pre        None
    * @post       None
    */
   public int getSupplierCount() {
      return supplierIds.size();
   }

   /**
    * Queries this product's waitlisted orders to find out if there are any waitlisted orders
    * @return     true if there are waitlisted orders; otherwise, false
    * @pre        None
    * @post       None
    * @see        Order
    */
   public boolean hasWaitlistedOrders() {
      return waitlistedOrders.size() != 0;
   }

   /**
    * Query the product's suppliers
    * @return      Returns an in iterator of the product's suppliers
    * @pre         None
    * @post        None
    * @see         Supplier
    */
   public Iterator<String> getSupplierIds() {
      return supplierIds.iterator();
   }

   /**
    * Queries this product's suppliers to find out a given supplier is associated or not
    * @param       supplierId of the supplier to check if it is a supplier for this product
    * @return      true if the specified supplier exists; otherwise, false
    * @pre         None
    * @post        None
    * @see         Supplier
    */
   public boolean hasSupplier(String supplierId) {
      for (Iterator<String> sids = supplierIds.iterator(); sids.hasNext();) {
         String sid = sids.next();
         if (sid.equals(supplierId)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Adds a supplier to the product's suppliers
    * @param       supplierId to be added to the product's supplier list
    * @return      true if the supplier was successfully added; otherwise, false
    * @pre         None
    * @post        The supplier will be associated with this product
    * @see         Supplier
    */
   public boolean addSupplier(String supplierId) {
      return supplierIds.add(supplierId);
   }

   /**
    * Removes a supplier from the product's suppliers
    * @param       supplierId to be removed from the product's supplier list
    * @return      true if the supplier was successfully removed; otherwise, false
    * @pre         The supplier needs to be associated with this product
    * @post        The supplier will be disassociated from this product
    * @see         Supplier
    */
   public boolean removeSupplier(String supplierId) {
      return supplierIds.remove(supplierId);
   }

   /**
    * Adds a waitlist item to the product's waitlisted items
    * @param       item to be added to the product's waitlisted items
    * @pre         None
    * @post        The waitlist item will be added to this product
    * @see         WaitlistItem
    */
   public void addToWaitlist(WaitlistItem item) {
      waitlistedOrders.add(item);
   }

   /**
    * Queries the next waitlisted item in the queue
    * @return      the next waitlisted item in the queue if one exists; otherwise, null
    * @pre         None
    * @post        None
    * @see         WaitlistItem
    */
   public WaitlistItem getNextWaitlistedOrder() {
      return waitlistedOrders.peek();
   }

   /**
    * Removes a waitlist item from the product's waitlisted items
    * @param       item to be removed from the product's waitlisted items
    * @pre         None
    * @post        The waitlist item will be removed from this product
    * @see         WaitlistItem
    */
   public void removeWaitlistItem(WaitlistItem item) {
      waitlistedOrders.remove(item);
   }

   /**
    * Query the product's waitlisted items
    * @return      Returns an in iterator of the product's waitlisted items
    * @pre         None
    * @post        None
    * @see         WaitlistItem
    */
   public Iterator<WaitlistItem> getWaitlistedOrders(){
      return waitlistedOrders.iterator();
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format(
         "[%s] is a product with name: [%s], price per item: [$%.2f], and quantity: [%s]", 
         id, name, price, quantity
      );
   }
}
