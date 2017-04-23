import java.util.*;
import java.io.*;

public class Record implements Serializable {
   private static final long serialVersionUID = 1L;

   // private String productId;
   private Product product;
   private int quantity;
   private float price;

   // possibly maintain if record is filled or not

   public Record(Product product, int quantity, float price) {
      this.product = product;
      this.quantity = quantity;
      this.price = price;
   }

   public Product getProduct() {
      return product;
   }

   public float getPrice() {
      return price;
   }

   public int getQuantity() {
      return quantity;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return String.format(
         "product: [%s], quantity: [%s], and price per item [$%.2f]",
         product, quantity, price
      );
   }
}