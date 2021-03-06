import java.util.*;
import java.io.*;


/**
 * Holds the information for waitlisted items for an order
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class WaitlistItem implements Serializable {

   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * The product that is being waited for
    */
   private Product product;

   /**
    * The order waiting on the product
    */
   private Order order;

   /**
    * The quantity of the product that is being waited for
    */
   private int quantity;
   
   /**
    * Constructor for the WaitlistItem
    * @param       product that is out of stock
    * @param       order that is waiting on the product that is out of stock
    * @param       quantity of the product that the order is waiting on
    */
   public WaitlistItem(Product product, Order order, int quantity) {
      this.product = product;
      this.order = order;
      this.quantity = quantity;   
   }

   /**
    * Query the product that is being waited for by the order
    * @return      Returns the product object that is being waited for
    * @pre         None
    * @post        None
    * @see         Product
    */
   public Product getProduct() {
      return product;
   }

   /**
    * Query the order is waiting on a product to be in stock
    * @return      Returns the order object that is waiting on the product to be in stock
    * @pre         None
    * @post        None
    * @see         Order
    */
   public Order getOrder() {
      return order;
   }

   /**
    * Query the amount of the product that the order is waiting for
    * @return      Returns the quantity of the product that is being waited for
    * @pre         None
    * @post        None
    */
   public int getQuantity() {
      return quantity;
   }

   /**
    * Decrease the amount of the product that the orders is waiting for
    * @pre         quantity of the waitlist item is greater than zero
    * @post        The quantity will be reduced the amount specified
    */
   public void decreaseQuantity(int amount) {
      quantity -= amount;
   }

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format(
         "[%s] is a waitlist order, for product [%s], for client [%s], for quantity [%d]",
         order, product, order.getClient(), quantity
      );
   }
}
