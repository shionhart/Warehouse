import java.util.*;
import java.io.*;

public class SupplierList implements Serializable {
   private static final long serialVersionUID = 1L;
   private static SupplierList supplierList;
   private List<Supplier> suppliers = new LinkedList<Supplier>();
   private SupplierList() {}

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

   public Iterator<Supplier> getSuppliers(){
      return suppliers.iterator();
   }

   public boolean insertSupplier(Supplier supplier) {
      return suppliers.add(supplier);
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

   public String toString() {
      return suppliers.toString();
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
}