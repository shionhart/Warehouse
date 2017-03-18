import java.util.*;
import java.io.*;

public class Client implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String CLIENT_STRING = "C";
   private String name;
   private String id;
   private float balance;
   private List<Invoice> invoices;
   private List<Transaction> transactions;
   private List<Order> orders;
   private List<Order> waitlistedOrders;


   public Client(String name) {
      this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
      this.name = name;
      this.balance = 0;
      this.invoices = new LinkedList<Invoice>();
      this.transactions = new LinkedList<Transaction>();
      this.orders = new LinkedList<Order>();
      this.waitlistedOrders = new LinkedList<Order>();
   }

   // ---- get methods ---- 
   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }

   public Iterator<Order> getOrders(){ 
      return orders.iterator(); 
   }

   public Iterator<Order> getWaitlistedOrders(){ 
      return waitlistedOrders.iterator(); 
   }

   public Iterator<Transaction> getTransactions(){ 
      return transactions.iterator(); 
   }

   public Iterator<Invoice> getInvoices(){ 
      return invoices.iterator(); 
   }

   public float getBalance() {
      return balance;
   }

   public String getFormattedBalance() {
      return String.format("$%.2f", balance);
   }
   // ---- end get methods ---- 
   
   // ---- add methods ---- 
   public void addOrder(Order order) {
      this.orders.add(order);
      transactions.add(new Transaction("ORDER", String.format("Order recieved: [%s]", order)));
   }

   public void addTransaction(Transaction transaction) {
      this.transactions.add(transaction);
   }

   public void addInvoice(Invoice invoice) {
      this.invoices.add(invoice);
      transactions.add(new Transaction(
         "INVOICE", 
         String.format(
            "Invoice created: [%s], total cost: [$%.2f]", 
            invoice, 
            invoice.calculateCost()
         )
      ));
   }
   // ---- end add methods ----

   // ---- find methods ---- 
   public Order findOrder(String orderId) {
      for (Iterator<Order> o = orders.iterator(); o.hasNext();) {
         Order order = o.next();
         if (order.getId().equals(orderId)) {
            return order;
         }
      }
      return null;
   }

   public Invoice findInvoice(String invoiceId) {
      for (Iterator<Invoice> i = invoices.iterator(); i.hasNext();) {
         Invoice invoice = i.next();
         if (invoice.getId().equals(invoiceId)) {
            return invoice;
         }
      }
      return null;
   }
   // ---- end find methods ---- 

   // ---- has methods ---- 
   public boolean hasOrders() {
      return orders.size() != 0;
   }

   public boolean hasWaitlistedOrders() {
      return waitlistedOrders.size() != 0;
   }

   public boolean hasInvoices() {
      return invoices.size() != 0;
   }

   public boolean hasTransactions() {
      return transactions.size() != 0;
   }
   // ---- end has methods ---- 
  

   // ---- action methods ----
   public void charge(float amount) {
      balance += amount;
      transactions.add(new Transaction(
         "BILLING", 
         String.format(
            "Charge was applied for [$%.2f]. Updated balance on account [$%.2f]",
            amount, 
            balance
         )
      ));
   }

   public void acceptPayment(float amount) {
      balance -= amount;
      transactions.add(new Transaction("BILLING", 
         String.format(
            "Payment was received for [$%.2f]. Remaining balance on account [$%.2f]",
            amount, 
            balance
         )
      ));
   }

   // returns remaining quantity to be used to fill other others
   public int processWaitlistedOrderItem(Order order, WaitlistItem item, int quantity) {

      Invoice invoice = new Invoice(this);
      int wantedQuantity = item.getQuantity();
      int remainingQuantity;

      // not enough to fill entirely
      if (wantedQuantity > quantity) {

         Record invoiceRecord = new Record(item.getProduct(), quantity, item.getProduct().getPrice());
         invoice.addRecord(invoiceRecord);

         // reduce the amount the order is waiting for
         item.decreaseQuantity(quantity);
         remainingQuantity = 0;
      }

      // enough to fill entirely
      else {
         
         Record invoiceRecord = new Record(item.getProduct(), wantedQuantity, item.getProduct().getPrice());
         invoice.addRecord(invoiceRecord);
         remainingQuantity = quantity - wantedQuantity;

         // remove the waitlisted item from the order
         order.removeWaitlistItem(item);
   
         // if is complete filled, remove from waitlisted orders 
         if (!order.hasWaitlistedItems()) {
            waitlistedOrders.remove(order);
         }
      }

      // charge for amount filled
      float total = invoice.calculateCost();
      addInvoice(invoice);
      charge(total);
      return remainingQuantity;
   }

   public void processOrder(Order order) {

      Invoice invoice = new Invoice(this);
      Iterator<Record> records = order.getRecords();
      while(records.hasNext()) {

         Record record = records.next();
         Product product = record.getProduct();
         int orderQuantity = record.getQuantity();
         int productQuantity = product.getQuantity();
   
         // not enough in stock
         if (productQuantity < orderQuantity) {

            // if product quantity not zero, create invoice
            if (productQuantity != 0) {
               Record invoiceRecord = new Record(product, productQuantity, product.getPrice());
               invoice.addRecord(invoiceRecord);
               product.setQuantity(0);
            }
            
            int difference = orderQuantity - productQuantity;
            WaitlistItem item = new WaitlistItem(product, order, difference);

            // add item to order waitlist
            order.addWaitlistItem(item);

            // add order to client's waitlisted orders
            waitlistedOrders.add(order);

            // add waitlist item to product
            product.addToWaitlist(item);
      
         }
   
         // enough in stock
         else {
            int difference = productQuantity - orderQuantity;
            Record invoiceRecord = new Record(product, orderQuantity, product.getPrice());
            invoice.addRecord(invoiceRecord);
            product.setQuantity(difference);
         }
      }
   
      if (!invoice.isEmpty()) {
         float total = invoice.calculateCost();
         addInvoice(invoice);
         charge(total);
      }
   }

   public String toString() {
      return String.format("[%s] is a client with name: [%s]", id, name);
   }
   // ---- end action methods ----

}
