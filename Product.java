import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String id;
   private List<String> supplierIds;

   private static final String PRODUCT_STRING = "P";


   public Product(String name) {
      this.name = name;
      this.supplierIds = new LinkedList<String>();
      this.id = PRODUCT_STRING + (IdServer.instance()).getId();
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }

   public int getSupplierCount() {
      return supplierIds.size();
   }


   public Iterator getSupplierIds() {
      return supplierIds.iterator();
   }

   public boolean addSupplier(String supplierId) {
      return supplierIds.add(supplierId);
   }

   public boolean removeSupplier(String supplierId) {
      return supplierIds.remove(supplierId);
   }

   public String toString() {
      return "Product id:" + id + " name:" + name + " suppliers:" + supplierIds;
   }
}
