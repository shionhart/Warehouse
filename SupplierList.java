import java.util.*;
import java.io.*;

/**
 * Collection of suppliers
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class SupplierList implements Serializable {
   private static final long serialVersionUID = 1L;
   private static SupplierList supplierList;
   private List<Supplier> suppliers = new LinkedList<Supplier>();
   
   /**
    * Private SupplierList constructor
    */
   private SupplierList() {}

   /**
    * Singleton SupplierList Constructor
    * <p>
    * This grabs, or creates, the SupplierList
    * <p>
    * @return the SupplierList singleton instance 
    */
   public static SupplierList instance() {
      if (supplierList == null) {
         return (supplierList = new SupplierList());
      } else {
         return supplierList;
      }
   }

   /**
    * Queries the list of suppliers to find out if the list is empty or not
    * @return      true if there are no suppliers in the list; otherwise, false
    * @see         Supplier
    */
   public boolean isEmpty() {
      return suppliers.size() == 0;
   }

   /**
    * Queries the list of suppliers
    * @return      An iterator to navigate through the list of suppliers
    * @see         Supplier
    */
   public Iterator<Supplier> getSuppliers(){
      return suppliers.iterator();
   }

   /**
    * Inserts the given supplier into the list of suppliers
    * @param       supplier is the supplier object to be added to the suppliers list
    * @return      true is the supplier was successfully added; otherwise, false
    * @see         Supplier
    */
   public boolean insertSupplier(Supplier supplier) {
      return suppliers.add(supplier);
   }

   /**
    * Searches for a supplier's existence within the list of suppliers, based on the parameters given
    * @param       supplierId of the supplier to search for within the list of suppliers
    * @return      The found supplier object if the supplier is found in the list of suppliers; otherwise, null
    * @see         Supplier
    */
   public Supplier find(String supplierId) {
      for (Iterator<Supplier> allSuppliers = suppliers.iterator(); allSuppliers.hasNext();) {
         Supplier supplier = allSuppliers.next();
         if (supplier.getId().equals(supplierId)) {
            return supplier;
         }
      }
      return null;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return suppliers.toString();
   }
  
   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @see Warehouse
    */
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(supplierList);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }

   /**
    * Helper function for the retrieve function in Warehouse, which will be used during serialization
    * @see Warehouse
    */
   private void readObject(java.io.ObjectInputStream input) {
      try {
         if (supplierList != null) {
            return;
         } else {
            input.defaultReadObject();
            if (supplierList == null) {
               supplierList = (SupplierList) input.readObject();
            } else {
               input.readObject();
            }
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
      }
   } 
}