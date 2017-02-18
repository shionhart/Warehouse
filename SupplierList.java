import java.util.*;
import java.io.*;

public class SupplierList implements Serializable {
   private static final long serialVersionUID = 1L;

   // The "List" is used to maintain the actual supplier list
   private List<Supplier> suppliers = new LinkedList<Supplier>();

   // This variable is used for the singleton "SupplierList" instance
   private static SupplierList supplierList;

   private SupplierList() {}

   // Used to create the singleton instance
   public static SupplierList instance() {
      if (supplierList == null) {
         return (supplierList = new SupplierList());
      } else {
         return supplierList;
      }
   }

   public boolean isEmpty() {
      return suppliers.size() == 0;
   }

   public boolean insertSupplier(Supplier supplier) {
      return suppliers.add(supplier);
   }

   public Iterator<Supplier> getSuppliers(){
      return suppliers.iterator();
   }

   public Supplier find(String supplierId) {
      for (Iterator<Supplier> allSuppliers = suppliers.iterator(); allSuppliers.hasNext();) {
         Supplier supplier = allSuppliers.next();
         if (supplier.getId().equals(supplierId)) {
            return supplier;
         }
      }
      return null;
   }
  
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(supplierList);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }

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

   public String toString() {
      return suppliers.toString();
   }

   public static void main(String[] s) {
      System.out.println("Welcome to the SupplierList class.");
   }
}