import java.util.*;
import java.io.*;

/**
 * Holds the information for the record of an order
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Record implements Serializable {
   
   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * The record's product
    */
   private Product product;
   
   /**
    * The amount of the product
    */
   private int quantity;
   
   /**
    * The price per item of the product
    */
   private float price;

   /**
    * Constructor for the Record
    * @param       product of the record
    * @param       quantity of the product of the record
    * @param       price of the product of the record
    * @see         Product
    */
   public Record(Product product, int quantity, float price) {
      this.product = product;
      this.quantity = quantity;
      this.price = price;
   }

   /**
    * Query the record's product
    * @return      Returns the record's product
    * @pre         None
    * @post        None
    * @see         Product
    */
   public Product getProduct() {
      return product;
   }

   /**
    * Query the record's price per product
    * @return      Returns the record's price per product item
    * @pre         None
    * @post        None
    */
   public float getPrice() {
      return price;
   }

   /**
    * Query the record's quantity needed
    * @return      Returns the record's quantity needed
    * @pre         None
    * @post        None
    */
   public int getQuantity() {
      return quantity;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format(
         "product: [%s], quantity: [%s], and price per item [$%.2f]",
         product, quantity, price
      );
   }
}
