import java.util.*;
import java.text.*;
import java.io.*;


/**
 * The Facade to the entire system
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Warehouse implements Serializable {

   private static final long serialVersionUID = 1L;
   private static Warehouse warehouse;
   private ClientList clientList;
   private SupplierList supplierList;
   private Inventory inventory;

   /*
    * Simple int error codes used to send information back to the UI
    */
   public static final int SUCCESS            = 0;
   public static final int OPERATION_FAILED   = 1;
   public static final int CLIENT_NOT_FOUND   = 2;
   public static final int SUPPLIER_NOT_FOUND = 3;
   public static final int PRODUCT_NOT_FOUND  = 4;
   public static final int ORDER_NOT_FOUND    = 5;
   public static final int INVOICE_NOT_FOUND  = 6;
   public static final int ALREADY_EXISTS     = 7;

   /**
    * Private Singleton Warehouse constructor
    * This grabs, or creates, the following instances: 
    * Inventory, SupplierList, and ClientList
    */
   private Warehouse() {
      inventory = Inventory.instance();
      supplierList = SupplierList.instance();
      clientList = ClientList.instance();
   }

   /**
    * Singleton Warehouse Constructor
    * <p>
    * This grabs, or creates, the following instances: 
    * ProductIdServer, SupplierIdServer, ClientIdServer, OrderIdServer, and 
    * InvoiceIdServer. All of which are managed by the Warehouse.
    * <p>
    * @return the Warehouse singleton instance 
    */
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

   /**
    * Queries the client list to find out if it has clients
    * @return      true if there are clients; otherwise, false
    * @see         Client
    * @see         ClientList
    */
   public boolean hasClients() { return !clientList.isEmpty(); }

   /**
    * Queries the inventory to find out if it has products in the inventory
    * @return      true if there are products; otherwise, false
    * @see         Product
    * @see         Inventory
    */
   public boolean hasProducts() { return !inventory.isEmpty(); }

   /**
    * Queries the supplier list to find out if it has suppliers
    * @return      true if there are suppliers; otherwise, false
    * @see         Supplier
    * @see         SupplierList
    */
   public boolean hasSuppliers() { return !supplierList.isEmpty(); }

   /**
    * Queries the client list and returns whether or not there are any clients 
    * with an unpaid balance on their respective accounts, provided any clients
    * exist in the client list.
    * @return      true if the client exists and has a balance; otherwise, false
    * @see         Client
    * @see         ClientList
    * @see         Order
    */
   public boolean hasClientsWithUnpaidBalance() { return clientList.hasUnpaid(); }


   /**
    * Queries the given client and returns whether or not they have any open 
    * orders, provided the client exists in the client list
    * @param       clientId to be checked if they have orders
    * @return      true if the client exists and has open orders; otherwise, false
    * @see         Client
    * @see         ClientList
    * @see         Order
    */
   public boolean clientHasOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasOrders();
   }

   /**
    * Queries the given client and returns whether or not they have any open 
    * wait-listed orders, provided the client exists
    * @param       clientId to be checked if they have waitlisted orders
    * @return      true if the client exists and has open orders; otherwise, false
    * @see         Client
    * @see         ClientList
    * @see         Order
    * @see         WaitlistItem
    */
   public boolean clientHasWaitlistedOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasWaitlistedOrders();
   }

   /**
    * Queries the given client and returns whether or not they have any invoices,
    * provided the client exists
    * @param       clientId to be checked if they have invoices
    * @return      true if the client exists and has invoices; otherwise, false
    * @see         Client
    * @see         ClientList
    * @see         Invoice
    */
   public boolean clientHasInvoices(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasInvoices();
   }

   /**
    * Queries the given client and returns whether or not they have any completed
    * transactions, provided the client exists
    * @param       clientId to be checked if they have transactions
    * @return      true if the client exists and has completed transactions; otherwise, false
    * @see         Client
    * @see         ClientList
    * @see         Transaction
    */
   public boolean clientHasTransactions(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return false;
      }
      return client.hasTransactions();
   }

   /**
    * Creates a client based on the parameters given and adds that client to the client list
    * @param       name of the client to be added to the client list
    * @return      The created client object if the insertion into the client list was successful; otherwise, null
    * @see         Client
    * @see         ClientList
    */
   public Client addClient(String name) {
      Client client = new Client(name);

      if (clientList.insertClient(client)) {         
         return client;
      }
      return null;
   }

   /**
    * Queries the client list for the clients in the system
    * @return      An iterator to navigate through the clients in the system
    * @see         Client
    * @see         ClientList
    */
   public Iterator<Client> getClients() { 
      return clientList.getClients(); 
   }
   
   /**
    * Queries the client list for the clients, that have an unpaid balance, in the system
    * @return      An iterator to navigate through the clients, that have an unpaid balance, in the system
    * @see         Client
    * @see         ClientList
    */
   public Iterator<Client> getClientsWithUnpaidBalance() { 
      return clientList.getUnpaid(); 
   }

   /**
    * Queries the supplier list for the suppliers in the system
    * @return      An iterator to navigate through the suppliers in the system
    * @see         Supplier
    * @see         SupplierList
    */
   public Iterator<Supplier> getSuppliers() {
      return supplierList.getSuppliers();
   }

   /**
    * Queries the inventory for the products in the system
    * @return      An iterator to navigate through the products in the system
    * @see         Product
    * @see         Inventory
    */
   public Iterator<Product> getProducts() {
      return inventory.getProducts();
   }

   /**
    * Queries the records for a given client and client's order
    * @param       clientId of the client to search for, and to search within for the order
    * @param       orderId of the order to search for within the client
    * @return      An iterator to navigate through the records of a given client's order, 
    *              provided the client, and order for that client, exist in the system
    * @see         Client
    * @see         ClientList
    * @see         Order
    * @see         Record
    */
   public Iterator<Record> getOrderRecords(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      Order order = client.findOrder(orderId);
      return order.getRecords();
   }

   /**
    * Queries the records for a given client and client's invoice
    * @param       clientId of the client to search for, and to search within for the invoice
    * @param       invoiceId of the invoice to search for within the client
    * @return      An iterator to navigate through the records of a given client's invoice, 
    *              provided the client, and order for that client, exist in the system
    * @see         Client
    * @see         ClientList
    * @see         Invoice
    * @see         Record
    */
   public Iterator<Record> getInvoiceRecords(String clientId, String invoiceId) {
      Client client = clientList.find(clientId);
      Invoice invoice = client.findInvoice(invoiceId);
      return invoice.getRecords();
   }

   /**
    * Queries the order for a given client
    * @param       clientId of the client to search for, and return all orders for that client
    * @return      An iterator to navigate through the orders for a client,
    *              provided the client exists in the system
    * @see         Client
    * @see         ClientList
    * @see         Order
    */
   public Iterator<Order> getOrders(String clientId) {
      Client client = clientList.find(clientId);
      return client.getOrders();
   }

   /**
    * Queries the invoices for a given client
    * @param       clientId of the client to search for, and return all invoice for that client
    * @return      An iterator to navigate through the invoices for a client,
    *              provided the client exists in the system
    * @see         Client
    * @see         ClientList
    * @see         Invoice
    */
   public Iterator<Invoice> getInvoices(String clientId) {
      Client client = clientList.find(clientId);
      return client.getInvoices();
   }

   /**
    * Queries the waitlisted orders for a given product
    * @param       productId of the product to search for, and return all waitlisted orders for that product
    * @return      An iterator to navigate through the waitlisted orders for a product,
    *              provided the product exists in the system
    * @see         Product
    * @see         WaitlistItem
    */
   public Iterator<WaitlistItem> getProductWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      if (product == null) {
         return null;
      }
      return product.getWaitlistedOrders();
   }

   /**
    * Queries the transactions for a given client
    * @param       clientId of the client to search for, and return all transactions for that client
    * @return      An iterator to navigate through the transactions for a client,
    *              provided the client exists in the system
    * @see         Client
    * @see         ClientList
    * @see         Transaction
    */
   public Iterator<Transaction> getClientTransactions(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getTransactions();
   }

   /**
    * Queries the waitlisted orders for a given client
    * @param       clientId of the client to search for, and return all waitlisted orders for that client
    * @return      An iterator to navigate through the waitlisted orders for a client,
    *              provided the client exists in the system
    * @see         Client
    * @see         ClientList
    * @see         Order
    * @see         WaitlistItem
    */
   public Iterator<Order> getClientWaitlistedOrders(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getWaitlistedOrders();
   }

   /**
    * Queries the waitlisted items for client's order
    * @param       clientId of the client to search for, and to search within for the order
    * @param       orderId of the order to search for within the client, to find the waitlisted order items
    * @return      An iterator to navigate through the waitlisted items for a client's order
    *              provided the client, and the client's order, exist in the system
    * @see         Client
    * @see         ClientList
    * @see         Order
    * @see         WaitlistItem
    */
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

   /**
    * Searches for a client's existence in the system, based on the parameters given
    * @param       clientId of the client to search for within the client list
    * @return      The found client object if the client is found in the client list; otherwise, null
    * @see         Client
    * @see         ClientList
    */
   public Client findClient(String clientId) {
      return clientList.find(clientId);
   }

   /**
    * Searches for a client order's existence in the system, based on the parameters given
    * @param       clientId of the client to search for within the client list
    * @param       orderId of the order to search for within the client
    * @return      The found client's order object if the client, and order, are found in the system; otherwise, null
    * @see         Client
    * @see         ClientList
    * @see         Order
    */
   public Order findClientOrder(String clientId, String orderId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.findOrder(orderId);
   }

   /**
    * Searches for a client invoice's existence in the system, based on the parameters given
    * @param       clientId of the client to search for within the client list
    * @param       invoiceId of the invoice to search for within the client
    * @return      The found client's invoice object if the client, and invoice, are found in the system; otherwise, null
    * @see         Client
    * @see         ClientList
    * @see         Invoice
    */
   public Invoice findClientInvoice(String clientId, String invoiceId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.findInvoice(invoiceId);
   }

   /**
    * Searches for a supplier's existence in the system, based on the parameters given
    * @param       supplierId of the supplier to search for within the supplier list
    * @return      The found supplier object if the supplier is found in the supplier list; otherwise, null
    * @see         Supplier
    * @see         SupplierList
    */
   public Supplier findSupplier(String supplierId) {
      return supplierList.find(supplierId);
   }

   /**
    * Searches for a product's existence in the system, based on the parameters given
    * @param       productId of the product to search for within the inventory
    * @return      The found product object if the product is found in the inventory; otherwise, null
    * @see         Product
    * @see         Inventory
    */
   public Product findProduct(String productId) {
      return inventory.find(productId);
   }

   /**
    * Queries the client's balance in string format for a given client
    * @param       clientId of the client whos balance will be retrieved
    * @return      The client's balance in string format if the client is in the client list; otherwise null
    * @see         Client
    */
   public String getClientBalanceStr(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      return client.getFormattedBalance();
   }

    /**
    * Queries the client's balance for a given client
    * @param       clientId of the client whos balance will be retrieved
    * @return      The client's balance if the client is in the client list; otherwise null
    * @see         Client
    */
   public float getClientBalance(String clientId) {
      Client client = clientList.find(clientId);
      return client.getBalance();
   }

   /**
    * Accepts a client's payment and applies it to their account
    * @param       clientId of the client whos balance will be reduced based on the payment amount given
    * @param       clientPayment the amount wanted to be applied to the client's account
    * @return      Static int status of the transaction which will be SUCCESS if the client is in the client list; otherwise CLIENT_NOT_FOUND
    * @see         Client
    */
   public int acceptClientPayment(String clientId, float clientPayment) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return CLIENT_NOT_FOUND;
      }

      client.acceptPayment(clientPayment);
      return SUCCESS;
   }

   /**
    * Creates a supplier based on the parameters given and adds that supplier to the supplier list
    * @param       name of the supplier to be added to the supplier list
    * @return      The created supplier object if the insertion into the supplier list was successful; otherwise, null
    * @see         Supplier
    * @see         SupplierList
    */
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

   /**
    * Associates a product and supplier within the system
    * @param       productId of the product to be associated to the supplier
    * @param       supplierId of the supplier to be associated to the product
    * @return      Static int status of the transaction (which includes: PRODUCT_NOT_FOUND, SUPPLIER_NOT_FOUND, ALREADY_EXISTS, SUCCESS, OPERATION_FAILED)
    * @see         Product
    * @see         Inventory
    * @see         Supplier
    * @see         SupplierList
    */
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

   /**
    * Disassociate a product and supplier within the system
    * @param       productId of the product to be disassociated to the supplier
    * @param       supplierId of the supplier to be disassociated to the product
    * @return      Static int status of the transaction (which includes: PRODUCT_NOT_FOUND, SUPPLIER_NOT_FOUND, ALREADY_EXISTS, SUCCESS, OPERATION_FAILED)
    * @see         Product
    * @see         Inventory
    * @see         Supplier
    * @see         SupplierList
    */
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

   /**
    * Creates a product based on the parameters given and adds that product to the inventory
    * @param       name of the product to be added to the inventory
    * @param       price of the product to be added to the inventory
    * @return      The created product object if the insertion into the inventory was successful; otherwise, null
    * @see         Product
    * @see         Inventory
    */
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

   /**
    * Creates an empty order for a client
    * @param       clientId of the client that is having the empty order created
    * @return      The created order's id will be created if the order was created and associated with the client successfully; otherwise, null
    * @see         Client
    * @see         Order
    */
   public String createOrder(String clientId) {
      Client client = clientList.find(clientId);
      if (client == null) {
         return null;
      }
      Order order = new Order(client);
      client.addOrder(order);
      return order.getId();
   }

   /**
    * Adds a given product, with associated quantity, to a client's order
    * @param       clientId of the client that is having the product added to their order
    * @param       orderId of the client's order that is having the product added to
    * @param       productId of the product that is to be added to the order
    * @param       quantity of the product to add to the client's order
    * @return      Static int status of the transaction (which includes: CLIENT_NOT_FOUND, PRODUCT_NOT_FOUND, ORDER_NOT_FOUND, SUCCESS)
    * @see         Client
    * @see         Product
    * @see         Order
    */
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

   /**
    * Finalize the client's order after all items have been added to the order
    * @param       clientId of the client that is having their order finalized 
    * @param       orderId of the client's order that is being finalized 
    * @return      Static int status of the transaction (which includes: CLIENT_NOT_FOUND, ORDER_NOT_FOUND, SUCCESS)
    * @see         Client
    * @see         Product
    * @see         Order
    */
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
    
   /**
    * Queries the given product and returns whether or not they have any waitlisted orders,
    * provided the product exists.
    * @param       productId to be checked if it has waitlisted orders
    * @return      true if the product exists and has waitlisted orders; otherwise, false
    * @see         Product
    * @see         Inventory
    * @see         WaitlistItem
    */
   public boolean productHasWaitlistedOrderItems(String productId) {
      Product product = inventory.find(productId);
      if (product == null) {
         return false;
      }

      return product.hasWaitlistedOrders();
   }

   /**
    * Process a shipment for a given quality of a product, provided the product exists.
    * @param       productId of the product for the received shipment
    * @param       quantity of the product received in the shipment
    * @param       item which is a specific waitlisted order to be filled; if null, the products will 
    *              fill the next waitlisted order in line
    * @return      the remaining quantity of the product, from the shipment, that can be used elsewhere
    * @see         Product
    * @see         Inventory
    * @see         WaitlistItem
    */
   public int processShipment(String productId, int quantity, WaitlistItem item) {
      Product product = inventory.find(productId);
      if (product == null) {
         return -1;
      }

      if (!productHasWaitlistedOrderItems(productId)) {
         
         // fill update inventory quantity
         product.increaseQuantity(quantity);
         return quantity;
      }
      
      if (item == null) {

         // fill first item in queue
         item = product.getNextWaitlistedOrder();
      }

      Client client = item.getOrder().getClient();
      quantity = client.processWaitlistedOrderItem(item.getOrder(), item, quantity);

      if (quantity > 0) {
         product.removeWaitlistItem(item);
      }
      return quantity;
   }
   
   /**
    * Retrieve a previously stored state for the Warehouse from a file called WarehouseData 
    * that holds the serialized Warehouse object
    * @return      the stored Warehouse object that was retrieved 
    * @see         ProductIdServer
    * @see         SupplierIdServer
    * @see         ClientIdServer
    * @see         OrderIdServer
    * @see         InvoiceIdServer
    */
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
   
   /**
    * Store the Warehouse's state into a file called WarehouseData, in a serialized format
    * @return      true is the storing of the serialized object was successful; otherwise, false 
    * @see         ProductIdServer
    * @see         SupplierIdServer
    * @see         ClientIdServer
    * @see         OrderIdServer
    * @see         InvoiceIdServer
    */
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
  
   /**
    * Helper function for the save function, which will be used during serialization
    */
   private void writeObject(java.io.ObjectOutputStream output) {
      try {
         output.defaultWriteObject();
         output.writeObject(warehouse);
      } catch(IOException ioe) {
         System.out.println(ioe);
      }
   }
  
   /**
    * Helper function for the retrieve function, which will be used during serialization
    */
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
