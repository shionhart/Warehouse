import java.util.*;
import java.io.*;

/**
 * Holds the information for the supplier
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Supplier implements Serializable {
   
   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Used for serialization also
    */
   private static final String SUPPLIER_STRING = "S";

   /**
    * Store the id that was given from the id server
    */
   private String id;
   
   /**
    * The product's name
    */
   private String name;
   
   /**
    * Data structure to hold the productIds associated with this Supplier
    */
   private List<String> productIds;
   
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
    * @pre         None
    * @post        None
    */
   public String getName() {
      return name;
   }

   /**
    * Query the supplier's id
    * @return      Returns the supplier's id
    * @see         SupplierIdServer
    * @pre         None
    * @post        None
    */
   public String getId() {
      return id;
   }
  
   /**
    * Set the supplier's name
    * @param      newName is what the supplier will be called after this method is called
    * @pre         None
    * @post        The name of the supplier will be set to the newName specified
    */
   public void setName(String newName) {
      name = newName;
   }
  
   /**
    * Compare a given id to the id of this supplier
    * @param       id to be compared against this supplier's id
    * @return      Returns the supplier's id
    * @pre         None
    * @post        None
    * @see         SupplierIdServer
    */
   public boolean equals(String id) {
      return this.id.equals(id);
   }

   /**
    * Query the supplier's product list and count the number of elements
    * @return      Returns the supplier's product count
    * @pre         None
    * @post        None
    * @see         Product
    */
   public int getProductCount() {
      return productIds.size();
   }

   /**
    * Query the supplier's product ids
    * @return      Returns an in iterator of the supplier's product ids
    * @pre         None
    * @post        None
    * @see         Product
    */
   public Iterator getProductIds() {
      return productIds.iterator();
   }

   /**
    * Query the supplier's product ids to see if this supplier supplies a given product id
    * @param       productId of the product to compare against the supplier's supplied products
    * @return      true if the given productId is in the product id list; otherwise, false
    * @pre         None
    * @post        None
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
    * @pre         The product and supplier are not associated
    * @post        The product will be associated with the supplier
    * @see         Product
    */
   public boolean addProduct(String productId) {
      return productIds.add(productId);
   }

   /**
    * Removes a product from the supplier's product id list
    * @param       productId to be removed from the supplier's product id list
    * @pre         The product and supplier are already associated
    * @post        The product will be disassociated with the supplier
    * @see         Product
    */
   public boolean removeProduct(String productId) {
      return productIds.remove(productId);
   }
  
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format("[%s] is a supplier with name: [%s]", id, name);
   }
}
