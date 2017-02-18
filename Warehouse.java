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
      } else {
         return warehouse;
      }
   }

   public boolean hasClients() { return !clientList.isEmpty(); }
   public boolean hasProducts() { return !inventory.isEmpty(); }
   public boolean hasSuppliers() { return !supplierList.isEmpty(); }

   public boolean clientHasOrders(String clientId) {
      Client client = clientList.find(clientId);
      return client.hasOrders();
   }

   public boolean clientHasInvoices(String clientId) {
      Client client = clientList.find(clientId);
      return client.hasInvoices();
   }

   public boolean clientHasTransactions(String clientId) {
      Client client = clientList.find(clientId);
      return client.hasTransactions();
   }

   // Method to create a new client and add it to the client list
   public Client addClient(String name) {
   
      // Create a new client using the parameter given
      Client client = new Client(name);
      
      // Pass the new client object to the list
      if (clientList.insertClient(client)) {
         
         // On success return the client object created and inserted
         return client;
      }

      // On failure return null
      return null;
   }

   public Iterator<Client> getClients() {
      return clientList.getClients();
   }

   public String getClientBalanceStr(String clientId) {
      Client client = clientList.find(clientId);
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

   public Client findClient(String clientId) {
      return clientList.find(clientId);
   }

   public Order findClientOrder(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      return client.findOrder(orderId);
   }

   public Invoice findClientInvoice(String clientId, String invoiceId) {
      Client client = clientList.find(clientId);
      return client.findInvoice(invoiceId);
   }

   public boolean hasClientsWithUnpaidBalance() {
      return clientList.hasUnpaid();
   }

   public Iterator<Client> getClientsWithUnpaidBalance() {
      return clientList.getUnpaid();
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

   public Iterator<Supplier> getSuppliers() {
      return supplierList.getSuppliers();
   }

   // Looks in the supplier list for a supplier with a matching supplierId
   // If one is found, return the supplier or null if it isn't found
   public Supplier findSupplier(String supplierId) {
      return supplierList.find(supplierId);
   }

   public Product findProduct(String productId) {
      return inventory.find(productId);
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

   public Iterator<Product> getProducts() {
      return inventory.getProducts();
   }

   public String createOrder(String clientId) {
      Order order = new Order(clientId);
      Client client = clientList.find(clientId);
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

      Record record = new Record(productId, quantity, product.getPrice());
      order.addRecord(record);
      return SUCCESS;
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

   public int processOrder(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return CLIENT_NOT_FOUND;
      }

      Order order = client.findOrder(orderId);
      if (order == null) {
         return ORDER_NOT_FOUND;
      }

      Invoice invoice = new Invoice(clientId);

      for (Iterator<Record> records = order.getRecords(); records.hasNext();) {
         Record record = records.next();
         String productId = record.getProductId();
         int orderQuantity = record.getQuantity();
         Product product = inventory.find(productId);
         int productQuantity = product.getQuantity();

         if (productQuantity < orderQuantity) {
            int difference = orderQuantity - productQuantity;
            WaitlistItem item = new WaitlistItem(clientId, orderId, difference);
            product.addToWaitlist(item);

            if (productQuantity != 0) {
               Record invoiceRecord = new Record(productId, productQuantity, product.getPrice());
               invoice.addRecord(invoiceRecord);
               product.setQuantity(0);
            }
         }
         else {
            int difference = productQuantity - orderQuantity;
            Record invoiceRecord = new Record(productId, orderQuantity, product.getPrice());
            invoice.addRecord(invoiceRecord);
            product.setQuantity(difference);
         }
      }

      if (!invoice.isEmpty()) {
         float total = invoice.calculateCost();
         client.addInvoice(invoice);
         client.charge(total);
      }
      return SUCCESS;
   }

   public boolean productHasWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      return product.hasWaitlistedItems();
   }

   public Iterator<WaitlistItem> getProductWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      return product.getWaitlistedOrders();
   }

   public Iterator<Transaction> getClientTransactions(String clientId) {
      Client client = clientList.find(clientId);
      return client.getTransactions();
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

  
   public String toString() {

      // add what information we want to print in our toString Method
      return "UNIMPLEMENTED toString FOR WAREHOUSE";
   }

   public static void main(String[] s) {
      System.out.println("Welcome to the Warehouse class.");
      System.out.println("------ Warehouse Main is purely for testing purposes -----");

      // Warehouse w = Warehouse.instance();

      // Test case start: Add client(s) to the system and print the client 
      // (manually verify it is there)
      // Note: duplicate data is currently allowed
      // Expected output:
      //
      // ------------------------
      //  Clients in the system:
      //     Client name Steel
      //     Client name Erik
      //     Client name Erik
      //     Client name Garrett
      // ------------------------
      // w.addClient("Steel");
      // w.addClient("Erik");
      // w.addClient("Erik");
      // w.addClient("Garrett");
      // Iterator allOfTheClients = w.getClients();
      // System.out.println("Clients in the system:");
      // while(allOfTheClients.hasNext()) {
      //    System.out.println("\t" + allOfTheClients.next());
      // }
      // Test case end

      // Test case start: Add supplier(s) to the system and print the supplier 
      // (manually verify it is there)
      // Note: duplicate data is currently allowed
      // Expected output:
      //
      // ------------------------
      //  Suppliers in the system:
      //     Supplier id:S5 name:Walmart Manufacturing
      //     Supplier id:S6 name:Monster
      //     Supplier id:S7 name:Blue Microphones
      //     Supplier id:S8 name:Aquafina
      // ------------------------
      // w.addSupplier("Walmart Manufacturing");
      // w.addSupplier("Monster");
      // w.addSupplier("Blue Microphones");
      // w.addSupplier("Aquafina");
      // Iterator allOfTheSuppliers = w.getSuppliers();
      // System.out.println("Suppliers in the system:");
      // while(allOfTheSuppliers.hasNext()) {
      //    System.out.println("\t" + allOfTheSuppliers.next());
      // }
      // Test case end

      // Test case start: Add product(s) to the inventory in the system and print the 
      // product (manually verify it is there)
      // Note: duplicate data is currently allowed
      // Expected output:
      //
      // ------------------------
      //  Products in the system:
      //     Product id:P9 name:Walmart Coffee Mug
      //     Product id:P10 name:Energy Drink
      //     Product id:P11 name:Microphone
      //     Product id:P12 name:Water
      // ------------------------
      // w.addProduct("Walmart Coffee Mug");
      // w.addProduct("Energy Drink");
      // w.addProduct("Microphone");
      // w.addProduct("Water");
      // Iterator allOfTheProducts = w.getProducts();
      // System.out.println("Products in the system:");
      // while(allOfTheProducts.hasNext()) {
      //    System.out.println("\t" + allOfTheProducts.next());
      // }
      // Test case end

      // Test case start: Create supplier, and then search for specific supplier by id, 
      // and print the supplier found
      // Expected output:
      //
      // ------------------------
      //  Created supplier:Supplier id:S13 name:Oracle
      //  Returned supplier:Supplier id:S13 name:Oracle
      // ------------------------
      // Supplier testSupplier = w.addSupplier("Oracle");
      // System.out.println("Created supplier:" + testSupplier);

      // Supplier returnedSupplier = w.findSupplier(testSupplier.getId());
      // System.out.println("Returned supplier:" + returnedSupplier);
      // Note: Real testcase validation can be added if we add an equals method to supplier 
      // that checks the ids of the objects given
      // Test case end

      // Test case start: Assign product to supplier and supplier to product, and verify it worked
      // Expected output:
      //
      // ------------------------
      // ------------------------
      // Supplier s1 = w.addSupplier("SunSystems");
      // Product p1 = w.addProduct("Thin Client Computer");

      // // Check product's supplier count initially
      // System.out.println("Product:" + p1.getName() + " has:" + p1.getSupplierCount() + " suppliers initially");
      
      // // Check supplier's product count initially
      // System.out.println("Supplier:" + s1.getName() + " has:" + s1.getProductCount() + " products initially");

      // w.associateProductAndSupplier(p1.getId(), s1.getId());

      // // Check product's supplier count after addition
      // System.out.println("Product:" + p1.getName() + " has:" + p1.getSupplierCount() + " suppliers after addition");
      
      // // Check supplier's product count after addition
      // System.out.println("Supplier:" + s1.getName() + " has:" + s1.getProductCount() + " products after addition");

      // // Print the product's supplier list
      // System.out.println("Listing suppliers for product:" + p1.getName() + ":");
      // Iterator p1sSuppliers = p1.getSupplierIds();
      // while(p1sSuppliers.hasNext()) {
      //    String p1sSupplierId = (String) p1sSuppliers.next();
      //    Supplier p1Supplier = w.findSupplier(p1sSupplierId);
      //    System.out.println("\t" + p1Supplier);
      // }

      // // Print the supplier's product list
      // System.out.println("Listing products for supplier:" + s1.getName() + ":");
      // Iterator s1sProducts = s1.getProductIds();
      // while(s1sProducts.hasNext()) {
      //    String s1sProductId = (String) s1sProducts.next();
      //    Product s1Product = w.findProduct(s1sProductId);
      //    System.out.println("\t" + s1Product);
      // }
      // Test case end

   }
}