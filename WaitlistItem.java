import java.util.*;
import java.io.*;

public class WaitlistItem implements Serializable {
   private static final long serialVersionUID = 1L;
   private Product product;
   private Order order;
   private int quantity;
   

   public WaitlistItem(Product product, Order order, int quantity) {
      this.product = product;
      this.order = order;
      this.quantity = quantity;   
   }

   public Product getProduct() {
      return product;
   }

   public Order getOrder() {
      return order;
   }

   public int getQuantity() {
      return quantity;
   }

   public void decreaseQuantity(int amount) {
      quantity -= amount;
   }

   public String toString() {
      return String.format(
         "[%s] is a waitlist order, for product [%s], for client [%s], for quantity [%d]",
         order, product, order.getClient(), quantity
      );
   }
}