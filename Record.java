import java.util.*;
import java.io.*;

public class Record implements Serializable {
   private static final long serialVersionUID = 1L;
   private String productId;
   private int quantity;
   private float price;

   public Record(String productId, int quantity, float price) {
      this.productId = productId;
      this.quantity = quantity;
      this.price = price;
   }

   public String getProductId() {
      return productId;
   }

   public float getPrice() {
      return price;
   }

   public int getQuantity() {
      return quantity;
   }

   public String toString() {
      return String.format(
         "product id: [%s], quantity: [%s], and price per item [$%.2f]",
         productId, quantity, price
      );
   }
}