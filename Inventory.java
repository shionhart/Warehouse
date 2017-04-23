import java.util.*;
import java.lang.*;
import java.io.*;

public class Inventory implements Serializable {
   private static final long serialVersionUID = 1L;
   private static Inventory inventory;
   private List<Product> products = new LinkedList<Product>();
   private Inventory() {}

   public static Inventory instance() {
      if (inventory == null) {
         return (inventory = new Inventory());
      } else {
         return inventory;
      }
   }

   public boolean isEmpty() {
      return products.size() == 0;
   }
  
   public boolean insertProduct(Product product) {
      products.add(product);
      return true;
   }

   public Iterator<Product> getProducts() {
      return products.iterator();
   }

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
    */
   public String toString() {
      return products.toString();
   }

   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(inventory);
      } catch(IOException ioe) {
         System.out.println(ioe);
      }
   }
  
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
