import java.util.*;
import java.io.*;

public class WaitlistItem implements Serializable {
   private static final long serialVersionUID = 1L;

   private String clientId;
   private String orderId;
   private int quantity;

   public WaitlistItem(String clientId, String orderId, int quantity) {
      this.clientId = clientId;
      this.orderId = orderId;
      this.quantity = quantity;
   }

   public String getClientId() {
      return clientId;
   }

   public String getOrderId() {
      return orderId;
   }

   public int getQuantity() {
      return quantity;
   }

   public String toString() {
      return String.format(
         "[%s] is a waitlisted order, for client [%s], for quantity [%d]",
         orderId, clientId, quantity
      );
   }
}