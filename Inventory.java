import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * Collection of products
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Inventory implements Serializable {
   
   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;

   /**
    * Singleton instance of self
    */
   private static Inventory inventory;
   
   /**
    * The data structure used to hold the products, so Inventory can manage
    */
   private List<Product> products = new LinkedList<Product>();
   
   /**
    * Private Inventory constructor
    */
   private Inventory() {}

   /**
    * Singleton Inventory Constructor
    * <p>
    * This grabs, or creates, the Inventory
    * <p>
    * @return the Inventory singleton instance 
    */
   public static Inventory instance() {
      if (inventory == null) {
         return (inventory = new Inventory());
      } else {
         return inventory;
      }
   }

   /**
    * Queries the list of products to find out if the list is empty or not
    * @return      true if there are no products in the list; otherwise, false
    * @pre         None
    * @post        None
    * @see         Product
    */
   public boolean isEmpty() {
      return products.size() == 0;
   }
  
   /**
    * Inserts the given product into the list of products
    * @param       product is the product object to be added to the products list
    * @return      true if the product was successfully added; otherwise, false
    * @pre         None
    * @post        The product specified will exist in the system
    * @see         Product
    */
   public boolean insertProduct(Product product) {
      products.add(product);
      return true;
   }

   /**
    * Queries the list of products
    * @return      An iterator to navigate through the list of products
    * @pre         None
    * @post        None
    * @see         Product
    */
   public Iterator<Product> getProducts() {
      return products.iterator();
   }

   /**
    * Searches for a product's existence within the list of products, based on the parameters given
    * @param       productId of the product to search for within the list of products
    * @return      The found product object if the product is found in the list of products; otherwise, null
    * @pre         None
    * @post        None
    * @see         Product
    */
   public Product find(String productId) {
      for (Iterator<Product> allProducts = products.iterator(); allProducts.hasNext();) {
         Product product = allProducts.next();
         if (product.getId().equals(productId)) {
            return product;
         }
      }
      return null;
   }
  
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return products.toString();
   }

   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @pre         An Inventory object needs to be serialized
    * @post        An Inventory object will have been serialized
    * @see         Warehouse
    */
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(inventory);
      } catch(IOException ioe) {
         System.out.println(ioe);
      }
   }
  
   /**
    * Helper function for the retrieve function in Warehouse, which will be used during serialization
    * @pre         A serialized Inventory object needs to be read
    * @post        An Inventory object will have been read
    * @see         Warehouse
    */
   private void readObject(java.io.ObjectInputStream input) {
      try {
         if (inventory != null) {
            return;
         } else {
            input.defaultReadObject();
            if (inventory == null) {
               inventory = (Inventory) input.readObject();
            } else {
               input.readObject();
            }
         }
      } catch(IOException ioe) {
         System.out.println("in Inventory readObject \n" + ioe);
      } catch(ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
      }
   }
}
