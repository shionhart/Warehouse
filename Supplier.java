import java.util.*;
import java.io.*;

/**
 * Holds the information for the supplier
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Supplier implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String id;
   private List<String> productIds;
   private static final String SUPPLIER_STRING = "S";
   
   /**
    * Constructor for the Record
    * @param       name of the supplier
    */
   public Supplier(String name) {
      this.name = name;
      this.id = SUPPLIER_STRING + (SupplierIdServer.instance()).getId();
      this.productIds = new LinkedList<String>();
   }

   /**
    * Query the supplier's name
    * @return      Returns the supplier's name
    */
   public String getName() {
      return name;
   }

   /**
    * Query the supplier's id
    * @return      Returns the supplier's id
    * @see         SupplierIdServer
    */
   public String getId() {
      return id;
   }
  
   /**
    * Set the supplier's name
    * @param      newName is what the supplier will be called after this method is called
    */
   public void setName(String newName) {
      name = newName;
   }
  
   /**
    * Compare a given id to the id of this supplier
    * @param       id to be compared against this supplier's id
    * @return      Returns the supplier's id
    * @see         SupplierIdServer
    */
   public boolean equals(String id) {
      return this.id.equals(id);
   }

   /**
    * Query the supplier's product list and count the number of elements
    * @return      Returns the supplier's product count
    * @see         Product
    */
   public int getProductCount() {
      return productIds.size();
   }

   /**
    * Query the supplier's product ids
    * @return      Returns an in iterator of the supplier's product ids
    * @see         Product
    */
   public Iterator getProductIds() {
      return productIds.iterator();
   }

   /**
    * Query the supplier's product ids to see if this supplier supplies a given product id
    * @param       productId of the product to compare against the supplier's supplied products
    * @return      true if the given productId is in the product id list; otherwise, false
    * @see         Product
    */
   public boolean suppliesProduct(String productId) {
      for (Iterator<String> pids = productIds.iterator(); pids.hasNext();) {
         String pid = pids.next();
         if (pid.equals(productId)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Adds a product to the supplier's product id list
    * @param       productId to be added to the supplier's product id list
    * @see         Product
    */
   public boolean addProduct(String productId) {
      return productIds.add(productId);
   }

   /**
    * Removes a product from the supplier's product id list
    * @param       productId to be removed from the supplier's product id list
    * @see         Product
    */
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
