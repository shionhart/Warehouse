import java.util.*;
import java.io.*;

public class Supplier implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String id;
   private List<String> productIds;

   // Not sure why this is needed yet
   private static final String SUPPLIER_STRING = "S";
   
   public Supplier(String name) {
      this.name = name;
      this.id = SUPPLIER_STRING + (IdServer.instance()).getId();
      this.productIds = new LinkedList<String>();
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }
  
   public void setName(String newName) {
      name = newName;
   }
  
   public boolean equals(String id) {
      return this.id.equals(id);
   }

   public int getProductCount() {
      return productIds.size();
   }

   public Iterator getProductIds() {
      return productIds.iterator();
   }

   public boolean addProduct(String productId) {
      return productIds.add(productId);
   }
  
   public String toString() {
      String string = "Supplier id:" + id + " name:" + name;
      return string;
  }
}
