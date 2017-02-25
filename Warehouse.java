import java.util.*;
import java.text.*;
import java.io.*;

public class Warehouse implements Serializable {

   private static final long serialVersionUID = 1L;
   private static Warehouse warehouse;
   private ClientList clientList;
   private SupplierList supplierList;
   private Inventory inventory;

   // Error codes
   public static final int SUCCESS            = 0;
   public static final int OPERATION_FAILED   = 1;
   public static final int CLIENT_NOT_FOUND   = 2;
   public static final int SUPPLIER_NOT_FOUND = 3;
   public static final int PRODUCT_NOT_FOUND  = 4;
   public static final int ORDER_NOT_FOUND    = 5;
   public static final int INVOICE_NOT_FOUND  = 6;
   public static final int ALREADY_EXISTS     = 7;

   // Warehouse constructor
   private Warehouse() {
      inventory = Inventory.instance();
      supplierList = SupplierList.instance();
      clientList = ClientList.instance();
   }

   // Singleton accessor for the warehouse instance
   public static Warehouse instance() {
      if (warehouse == null) {

         // instantiate all singletons
         ProductIdServer.instance(); 
         SupplierIdServer.instance(); 
         ClientIdServer.instance(); 
         OrderIdServer.instance(); 
         InvoiceIdServer.instance(); 

         return (warehouse = new Warehouse());
      } 
      else {
         return warehouse;
      }
   }

   // Has methods
   public boolean hasClients() { return !clientList.isEmpty(); }
   public boolean hasProducts() { return !inventory.isEmpty(); }
   public boolean hasSuppliers() { return !supplierList.isEmpty(); }
   public boolean hasClientsWithUnpaidBalance() { return clientList.hasUnpaid(); }

   public boolean clientHasOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasOrders();
   }

   public boolean clientHasWaitlistedOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasWaitlistedOrders();
   }

   public boolean clientHasInvoices(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasInvoices();
   }

   public boolean clientHasTransactions(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasTransactions();
   }

   // Add methods
   public Client addClient(String name) {
      Client client = new Client(name);

      if (clientList.insertClient(client)) {         
         return client;
      }
      return null;
   }


   // Iterator methods
   public Iterator<Client> getClients() { 
      return clientList.getClients(); 
   }
   
   public Iterator<Client> getClientsWithUnpaidBalance() { 
      return clientList.getUnpaid(); 
   }

   public Iterator<Supplier> getSuppliers() {
      return supplierList.getSuppliers();
   }

   public Iterator<Product> getProducts() {
      return inventory.getProducts();
   }

   public Iterator<Record> getOrderRecords(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      Order order = client.findOrder(orderId);
      return order.getRecords();
   }

   public Iterator<Record> getInvoiceRecords(String clientId, String invoiceId) {
      Client client = clientList.find(clientId);
      Invoice invoice = client.findInvoice(invoiceId);
      return invoice.getRecords();
   }

   public Iterator<Order> getOrders(String clientId) {
      Client client = clientList.find(clientId);
      return client.getOrders();
   }

   public Iterator<Invoice> getInvoices(String clientId) {
      Client client = clientList.find(clientId);
      return client.getInvoices();
   }

   public Iterator<WaitlistItem> getProductWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      if (product == null) {
         return null;
      }
      return product.getWaitlistedOrders();
   }

   public Iterator<Transaction> getClientTransactions(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getTransactions();
   }

   public Iterator<Order> getClientWaitlistedOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getWaitlistedOrders();
   }

   public Iterator<WaitlistItem> getClientWaitlistedOrderItems(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      Order order = client.findOrder(orderId);
      if (order == null) {
         return null;
      }
      return order.getWaitlistedItems();
   }

   // Find methods
   public Client findClient(String clientId) {
      return clientList.find(clientId);
   }

   public Order findClientOrder(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.findOrder(orderId);
   }

   public Invoice findClientInvoice(String clientId, String invoiceId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.findInvoice(invoiceId);
   }

   public Supplier findSupplier(String supplierId) {
      return supplierList.find(supplierId);
   }

   public Product findProduct(String productId) {
      return inventory.find(productId);
   }


   public String getClientBalanceStr(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getFormattedBalance();
   }

   public float getClientBalance(String clientId) {
      Client client = clientList.find(clientId);
      return client.getBalance();
   }

   public int acceptClientPayment(String clientId, float clientPayment) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return CLIENT_NOT_FOUND;
      }

      client.acceptPayment(clientPayment);
      return SUCCESS;
   }

   

   

   public Supplier addSupplier(String name) {
   
      // Create a new supplier using the parameter given
      Supplier supplier = new Supplier(name);
      
      // Pass the new supplier object to the list
      if (supplierList.insertSupplier(supplier)) {
         
         // On success return the supplier object created and inserted
         return supplier;
      }

      // On failure return null
      return null;
   }

   public int associateProductAndSupplier(String productId, String supplierId) {
      Product product = inventory.find(productId);
      Supplier supplier = supplierList.find(supplierId);

      if (product == null) {
         return PRODUCT_NOT_FOUND;
      }
      else if (supplier == null) {
         return SUPPLIER_NOT_FOUND;
      }
      else if (product.hasSupplier(supplierId) && supplier.suppliesProduct(productId)) {
         return ALREADY_EXISTS;
      }
      else if (product.addSupplier(supplierId) && supplier.addProduct(productId)) {
         return SUCCESS;
      }
      else {
         return OPERATION_FAILED;
      }
   }

   public int disassociateProductAndSupplier(String productId, String supplierId) {
      Product product = inventory.find(productId);
      Supplier supplier = supplierList.find(supplierId);

      if (product == null) {
         return PRODUCT_NOT_FOUND;
      }
      else if (supplier == null) {
         return SUPPLIER_NOT_FOUND;
      }
      else if (!product.hasSupplier(supplierId) && !supplier.suppliesProduct(productId)) {
         return ALREADY_EXISTS;
      }
      else if (product.removeSupplier(supplierId) && supplier.removeProduct(productId)) {
         return SUCCESS;
      }
      else {
         return OPERATION_FAILED;
      }
   }

   public Product addProduct(String name, float price) {
   
      // Create a new product using the parameter given
      Product product = new Product(name, price);
      
      // Pass the new product object to the list
      if (inventory.insertProduct(product)) {
         
         // On success return the product object created and inserted
         return product;
      }

      // On failure return null
      return null;
   }

   

   // change to int?
   public String createOrder(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      Order order = new Order(client);
      client.addOrder(order);
      return order.getId();
   }

   public int addToOrder(String clientId, String orderId, String productId, int quantity) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return CLIENT_NOT_FOUND;
      }

      Order order = client.findOrder(orderId);
      if (order == null) {
         return ORDER_NOT_FOUND;
      }

      Product product = inventory.find(productId);
      if (product == null) {
         return PRODUCT_NOT_FOUND;
      }

      Record record = new Record(product, quantity, product.getPrice());
      order.addRecord(record);
      return SUCCESS;
   }

   

   public int processClientOrder(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return CLIENT_NOT_FOUND;
      }

      Order order = client.findOrder(orderId);
      if (order == null) {
         return ORDER_NOT_FOUND;
      }

      client.processOrder(order);
      return SUCCESS;
   }
    

   public boolean productHasWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      if (product == null) {
         return false;
      }

      return product.hasWaitlistedOrders();
   }

   public int acceptProductShipment(String productId, int quantity) {
      Product product = inventory.find(productId);
      if (product == null) {
         return PRODUCT_NOT_FOUND;
      }
      else if (quantity <= 0) {
         return OPERATION_FAILED;
      }

      // set amount remaining amount to the quantity given
      int quantityRemaining = quantity;

      // continue until there aren't waitlisted orders or the shipment quantity is empty
      while (product.hasWaitlistedOrders() && (quantityRemaining > 0)) {
         WaitlistItem item = product.getNextWaitlistedOrder();
         Client client = item.getOrder().getClient();
         quantityRemaining = client.processWaitlistedOrderItem(item.getOrder(), item, quantityRemaining);

         if (quantityRemaining > 0) {
            product.popWaitlistedOrder();
         }
      }

      // all waitlisted orders have been processed, increase product quantity with remaining value
      if (quantityRemaining > 0) {
         product.increaseQuantity(quantityRemaining);
      }

      return SUCCESS;
   }

   public void fufillProductWaitlistedOrders(String productId) {

   }

   // Method for retrieving the serialized object
   public static Warehouse retrieve() {
      try {
         FileInputStream file = new FileInputStream("WarehouseData");
         ObjectInputStream input = new ObjectInputStream(file);
         input.readObject();
         ProductIdServer.retrieve(input);
         SupplierIdServer.retrieve(input);
         ClientIdServer.retrieve(input);
         OrderIdServer.retrieve(input);
         InvoiceIdServer.retrieve(input);         
         return warehouse;
      } catch(IOException ioe) {
         ioe.printStackTrace();
         return null;
      } catch(ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
         return null;
      }
   }
   
   // Method for saving the serialized object
   public static boolean save() {
      try {
         FileOutputStream file = new FileOutputStream("WarehouseData");
         ObjectOutputStream output = new ObjectOutputStream(file);
         output.writeObject(warehouse);
         output.writeObject(ProductIdServer.instance());
         output.writeObject(SupplierIdServer.instance());
         output.writeObject(ClientIdServer.instance());
         output.writeObject(OrderIdServer.instance());
         output.writeObject(InvoiceIdServer.instance());
         return true;
      } catch(IOException ioe) {
         ioe.printStackTrace();
         return false;
      }
   }
  
   // Write the object to a serialized format
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(warehouse);
      } catch(IOException ioe) {
         System.out.println(ioe);
      }
   }
  
   // Read the serialized object retrieved
   private void readObject(java.io.ObjectInputStream input) {
      try {
         input.defaultReadObject();
         if (warehouse == null) {
            warehouse = (Warehouse) input.readObject();
         } else {
            input.readObject();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}