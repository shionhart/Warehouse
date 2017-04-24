import java.util.*;
import java.io.*;

/**
 * Holds the information for the client
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Client implements Serializable {

   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Used for serialization also
    */
   private static final String CLIENT_STRING = "C";
   
   /**
    * Client's name
    */
   private String name;

   /**
    * Store the id that was given from the id server
    */
   private String id;

   /**
    * Store the balance that increases when orders are made and decreases when payments are made
    */
   private float balance;

   /**
    * Data structure to hold the invoices
    */
   private List<Invoice> invoices;

   /**
    * Data structure to hold the transactions
    */
   private List<Transaction> transactions;

   /**
    * Data structure to hold the orders
    */
   private List<Order> orders;

   /**
    * Data structure to hold the waitlisted orders
    */
   private List<Order> waitlistedOrders;

   /**
    * Constructor for the Client
    * @param       name of the client
    */
   public Client(String name) {
      this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
      this.name = name;
      this.balance = 0;
      this.invoices = new LinkedList<Invoice>();
      this.transactions = new LinkedList<Transaction>();
      this.orders = new LinkedList<Order>();
      this.waitlistedOrders = new LinkedList<Order>();
   }

   /**
    * Query the client's name
    * @return      Returns the client's name
    * @pre         None
    * @post        None
    */
   public String getName() {
      return name;
   }

   /**
    * Query the client's id
    * @return      Returns the client's id
    * @pre         None
    * @post        None
    * @see         ClientIdServer
    */
   public String getId() {
      return id;
   }

   /**
    * Query the client's orders
    * @return      Returns an in iterator of the client's orders
    * @pre         None
    * @post        None
    * @see         Order
    */
   public Iterator<Order> getOrders(){ 
      return orders.iterator(); 
   }

   /**
    * Query the client's waitlisted orders
    * @return      Returns an in iterator of the client's waitlisted orders
    * @pre         None
    * @post        None
    * @see         Order
    */
   public Iterator<Order> getWaitlistedOrders(){ 
      return waitlistedOrders.iterator(); 
   }

   /**
    * Query the client's transactions
    * @return      Returns an in iterator of the client's transactions
    * @pre         None
    * @post        None
    * @see         Transaction
    */
   public Iterator<Transaction> getTransactions(){ 
      return transactions.iterator(); 
   }

   /**
    * Query the client's invoices
    * @return      Returns an in iterator of the client's invoices
    * @pre         None
    * @post        None
    * @see         Invoice
    */
   public Iterator<Invoice> getInvoices(){ 
      return invoices.iterator(); 
   }

   /**
    * Query the client's balance
    * @return      Returns the client's balance
    * @pre         None
    * @post        None
    */
   public float getBalance() {
      return balance;
   }

   /**
    * Query the client's balance
    * @return      Returns the client's balance in string format
    * @pre         None
    * @post        None
    */
   public String getFormattedBalance() {
      return String.format("$%.2f", balance);
   }
   
   /**
    * Adds an order to the client's orders
    * @param       order to be added to the client's orders
    * @pre         None
    * @post        The order will be in the system and attached to the client
    * @see         Order
    */
   public void addOrder(Order order) {
      this.orders.add(order);
      transactions.add(new Transaction("ORDER", String.format("Order recieved: [%s]", order)));
   }

   /**
    * Adds a transaction to the client's transactions
    * @param       transaction to be added to the client's transactions
    * @pre         None
    * @post        The transaction will be in the system and attached to the client
    * @see         Transaction
    */
   public void addTransaction(Transaction transaction) {
      this.transactions.add(transaction);
   }

   /**
    * Adds a invoice to the client's invoices
    * @param       invoice to be added to the client's invoices
    * @pre         None
    * @post        The invoice will be in the system and attached to the client
    * @see         Invoice
    */
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
   
   /**
    * Searches for an order within this client's orders
    * @param       orderId of the order to search for within this client's orders
    * @return      The found order object if the order is found within this client's orders; otherwise, null
    * @pre         None
    * @post        None
    * @see         Order
    */
   public Order findOrder(String orderId) {
      for (Iterator<Order> o = orders.iterator(); o.hasNext();) {
         Order order = o.next();
         if (order.getId().equals(orderId)) {
            return order;
         }
      }
      return null;
   }

   /**
    * Searches for an invoice within this client's invoices
    * @param       invoiceId of the invoice to search for within this client's invoices
    * @return      The found invoice object if the invoice is found within this client's invoices; otherwise, null
    * @pre         None
    * @post        None
    * @see         Invoice
    */
   public Invoice findInvoice(String invoiceId) {
      for (Iterator<Invoice> i = invoices.iterator(); i.hasNext();) {
         Invoice invoice = i.next();
         if (invoice.getId().equals(invoiceId)) {
            return invoice;
         }
      }
      return null;
   }
   
   /**
    * Queries this client's orders to find out if there are any orders
    * @return      true if there are orders; otherwise, false
    * @pre         None
    * @post        None
    * @see         Order
    */
   public boolean hasOrders() {
      return orders.size() != 0;
   }

   /**
    * Queries this client's waitlisted orders to find out if there are any waitlisted orders
    * @return      true if there are waitlisted orders; otherwise, false
    * @pre         None
    * @post        None
    * @see         Order
    */
   public boolean hasWaitlistedOrders() {
      return waitlistedOrders.size() != 0;
   }

   /**
    * Queries this client's invoices to find out if there are any invoices
    * @return      true if there are invoices; otherwise, false
    * @pre         None
    * @post        None
    * @see         Invoice
    */
   public boolean hasInvoices() {
      return invoices.size() != 0;
   }

   /**
    * Queries this client's transactions to find out if there are any transactions
    * @return      true if there are transactions; otherwise, false
    * @pre         None
    * @post        None
    * @see         Transaction
    */
   public boolean hasTransactions() {
      return transactions.size() != 0;
   }
   
   /**
    * Change this client the amount specified, which will increase the balance owed
    * @param       amount is the amount that the balance is to increase, or the amount this client is being charged
    * @pre         The amount given is a positive value
    * @post        The balance on the account will be increased the amount given
    */
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

   /**
    * Deduct the amount specified from this client's balance
    * @param       amount is the amount that the balance is to decrease, or the amount this client is paying
    * @pre         The amount specified is less that the balance on the account, and the amount specified is positive
    * @post        The amount specified will be deduced from the balance on the account
    */
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

   /**
    * Fill a waitlisted item, within an order, the given amount specified
    * @param       order to be filled
    * @param       item within the order to be filled 
    * @return      quantity of the product type of item, within the order, that is 
    *              remaining and can be used to fill other's orders
    * @pre         None
    * @post        The waitlisted item will be filled the amount given, and the waitlist item 
    *              will be removed if the quantity given fills the order
    * @see         WaitlistItem
    * @see         Order
    * @see         Invoice
    * @see         Product
    */
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

   /**
    * Attempt to fill the specified item with the product quantities in stock, if there isn't
    * enough to fill the an item the item is turned into a waitlisted item
    * @param       order to be filled
    * @pre         The order is completed and is ready to be processed
    * @post        The order will be processed and filled with the items in stock, items not filled will be waitlisted
    * @see         Invoice
    * @see         Record
    * @see         Order
    * @see         Invoice
    * @see         WaitlistItem
    * @see         Product
    */
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

   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return String.format("[%s] is a client with name: [%s]", id, name);
   }
}
