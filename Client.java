import java.util.*;
import java.io.*;

public class Client implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String CLIENT_STRING = "C";
   private String name;
   private String id;
   private float balance;
   private List<Order> orders;
   private List<Invoice> invoices;
   private List<Transaction> transactions;

   public Client(String name) {
      this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
      this.name = name;
      this.balance = 0;
      this.orders = new LinkedList<Order>();
      this.invoices = new LinkedList<Invoice>();
      this.transactions = new LinkedList<Transaction>();
   }

   public float getBalance() {
      return balance;
   }

   public String getFormattedBalance() {
      return String.format("$%.2f", balance);
   }

   public void charge(float amount) {
      balance += amount;
      transactions.add(new Transaction("BILLING", String.format(
         "Charge was applied for [$%.2f]. Updated balance on account [$%.2f]",
         amount, balance
      )));
   }

   public void acceptPayment(float amount) {
      balance -= amount;
      transactions.add(new Transaction("BILLING", String.format(
         "Payment was received for [$%.2f]. Remaining balance on account [$%.2f]",
         amount, balance
      )));
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }
   
   public void addOrder(Order order) {
      this.orders.add(order);
      transactions.add(new Transaction("ORDER", String.format("Order recieved: [%s]", order)));
   }

   public void addTransaction(Transaction transaction) {
      this.transactions.add(transaction);
   }

   public Iterator<Order> getOrders(){
      return orders.iterator();
   }

   public Iterator<Transaction> getTransactions(){
      return transactions.iterator();
   }

   public boolean hasTransactions() {
      return transactions.size() != 0;
   }

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

   public void addInvoice(Invoice invoice) {
      this.invoices.add(invoice);
      transactions.add(new Transaction("INVOICE", String.format(
         "Invoice created: [%s], total cost: [$%.2f]", invoice, invoice.calculateCost())));
   }

   public Iterator<Invoice> getInvoices(){
      return invoices.iterator();
   }

   public boolean hasOrders() {
      return orders.size() != 0;
   }

   public boolean hasInvoices() {
      return invoices.size() != 0;
   }
  
   public String toString() {
      return String.format("[%s] is a client with name: [%s]", id, name);
   }
}
