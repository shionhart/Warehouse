import java.util.*;
import java.text.*;
import java.io.*;

/**
 * User interface and main driver for the entire application
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Ui {
   private static Ui ui;
   private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
   private static Warehouse warehouse;

   // Prompt codes
   private static final int EXIT                              = 0;
   private static final int ADD_CLIENT                        = 1;
   private static final int ADD_SUPPLIER                      = 2;
   private static final int ADD_PRODUCT                       = 3;
   private static final int SHOW_CLIENTS                      = 4;
   private static final int SHOW_SUPPLIERS                    = 5;
   private static final int SHOW_PRODUCTS                     = 6;
   private static final int LIST_CLIENT_BALANCE               = 7;
   private static final int LIST_CLIENT_ORDERS                = 8;
   private static final int LIST_CLIENT_INVOICES              = 9;
   private static final int LIST_CLIENT_TRANSACTION_HISTORY   = 10;
   private static final int LIST_CLIENT_ORDER_DETAILS         = 11;
   private static final int LIST_CLIENT_INVOICE_DETAILS       = 12;
   private static final int LIST_CLIENTS_WITH_UNPAID_BALANCE  = 13;
   private static final int LIST_CLIENT_WAITLISTED_ORDERS     = 14;
   private static final int LIST_WAITLISTED_PRODUCT_ORDERS    = 15;
   private static final int ASSOCIATE_PRODUCT_AND_SUPPLIER    = 16;
   private static final int DISASSOCIATE_PRODUCT_AND_SUPPLIER = 17;
   private static final int CREATE_CLIENT_ORDER               = 18;
   private static final int ACCEPT_CLIENT_PAYMENT             = 19;
   private static final int ACCEPT_PRODUCT_SHIPMENT           = 20;
   private static final int SAVE                              = 21;
   private static final int RETRIEVE                          = 22;
   private static final int HELP                              = 23;

   /**
    * Private UI constructor
    */
   private Ui() {
      if (yesOrNo("Look for saved data and use it?")) {
         retrieve();
      } else {
         warehouse = Warehouse.instance();
      }
   }
   
   /**
    * Singleton UI Constructor
    * <p>
    * This grabs, or creates, the UI
    * <p>
    * @return the UI singleton instance 
    */
   public static Ui instance() {
      if (ui == null) {
         return ui = new Ui();
      } else {
         return ui;
      }
   }

   /**
    * Prompts the user for input as a reply to the given prompt passed as a parameter, and then gathers the input
    * @param       prompt is the question, or prompt, that the user will see before entering a string reply
    * @pre         None
    * @post        None
    * @return      the input from the user for the given prompt
    * @see         StringTokenizer
    */
   public String getToken(String prompt) {
      do {
         try {
            System.out.println(prompt);
            System.out.print("> ");
            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
            if (tokenizer.hasMoreTokens()) {
               return tokenizer.nextToken();
            }
         } catch (IOException ioe) {
            System.exit(0);
         }
      } while (true);
   }
   
   /**
    * Prompts the user for a yes or no reply to the given prompt passed as a parameter, and then gathers the input
    * @param       prompt is the question, or prompt, that the user will see before entering a string reply
    * @pre         None
    * @post        None
    * @return      true is the answer fits the yes category of options; otherwise false
    */
   private boolean yesOrNo(String prompt) {
      String more = getToken(prompt + " (Y|y)[es] or anything else for no");
      if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
         return false;
      }
      return true;
   }

   /**
    * Prompts the user for an integer number corresponding to the command that they want to run
    * @pre         None
    * @post        None
    */
   public int getCommand() {
      do {
         try {
            int value = Integer.parseInt(getToken("Enter command: " + HELP + " for help"));
            if (value >= EXIT && value <= HELP) {
               return value;
            }
         } catch (NumberFormatException nfe) {
            System.out.println("Enter a number");
         }
      } while (true);
   }

   /**
    * Prints the list of command choices and the integer corresponding to that command
    * @pre         None
    * @post        None
    */
   public void help() {
      System.out.println("Enter a integer number between 0 and 23 as explained below:");
      System.out.println(EXIT                              + " to exit");
      System.out.println(ADD_CLIENT                        + " to add a client");
      System.out.println(ADD_SUPPLIER                      + " to add a supplier");
      System.out.println(ADD_PRODUCT                       + " to add a product");
      System.out.println(SHOW_CLIENTS                      + " to list all clients");
      System.out.println(SHOW_SUPPLIERS                    + " to list all suppliers");
      System.out.println(SHOW_PRODUCTS                     + " to list all products");
      System.out.println(LIST_CLIENT_BALANCE               + " to list a client's balance");
      System.out.println(LIST_CLIENT_ORDERS                + " to list a client's orders");
      System.out.println(LIST_CLIENT_INVOICES              + " to list a client's invoices");
      System.out.println(LIST_CLIENT_TRANSACTION_HISTORY   + " to list all client's transactions");
      System.out.println(LIST_CLIENT_ORDER_DETAILS         + " to list the details of a client's order");
      System.out.println(LIST_CLIENT_INVOICE_DETAILS       + " to list the details of a client's invoice");
      System.out.println(LIST_CLIENTS_WITH_UNPAID_BALANCE  + " to list clients with an unpaid balance");
      System.out.println(LIST_CLIENT_WAITLISTED_ORDERS     + " to list client's waitlisted orders");
      System.out.println(LIST_WAITLISTED_PRODUCT_ORDERS    + " to list a product's waitlisted orders");
      System.out.println(ASSOCIATE_PRODUCT_AND_SUPPLIER    + " to associate a product and supplier");
      System.out.println(DISASSOCIATE_PRODUCT_AND_SUPPLIER + " to disassociate a product and supplier");
      System.out.println(CREATE_CLIENT_ORDER               + " to create a client order");
      System.out.println(ACCEPT_CLIENT_PAYMENT             + " to accept a client's payment");
      System.out.println(ACCEPT_PRODUCT_SHIPMENT           + " to accept a product shipment");
      System.out.println(SAVE                              + " to save data");
      System.out.println(RETRIEVE                          + " to retrieve");
      System.out.println(HELP                              + " for help");
   }

   /**
    * Handles the execution of the command corresponding to the integer given, and will continue to execute commands
    * for the user until the command entered corresponds to the exit command. After the exit button is pressed the 
    * program exits.
    * @pre         None
    * @post        The commands selected will be executed after they were selected
    */
   public void process() {
      int command;
      help();
      while ((command = getCommand()) != EXIT) {
         switch (command) {
            case HELP:                              help();                           break;
            case SAVE:                              save();                           break;
            case RETRIEVE:                          retrieve();                       break;
            case ADD_CLIENT:                        addClient();                      break;
            case ADD_SUPPLIER:                      addSupplier();                    break;
            case ADD_PRODUCT:                       addProduct();                     break;
            case SHOW_CLIENTS:                      showClients();                    break;
            case SHOW_SUPPLIERS:                    showSuppliers();                  break;
            case SHOW_PRODUCTS:                     showProducts();                   break;
            case LIST_CLIENT_TRANSACTION_HISTORY:   getClientTransactionHistory();    break;
            case LIST_CLIENT_ORDER_DETAILS:         getClientOrderDetails();          break;
            case LIST_CLIENT_INVOICE_DETAILS:       getClientInvoiceDetails();        break;
            case LIST_CLIENTS_WITH_UNPAID_BALANCE:  getClientsWithUnpaidBalance();    break;
            case LIST_CLIENT_WAITLISTED_ORDERS:     getClientsWaitlistedOrders();     break;
            case ASSOCIATE_PRODUCT_AND_SUPPLIER:    associateProductAndSupplier();    break;
            case DISASSOCIATE_PRODUCT_AND_SUPPLIER: disassociateProductAndSupplier(); break;
            case LIST_CLIENT_BALANCE:               getClientBalance();               break;
            case LIST_CLIENT_ORDERS:                getClientOrders();                break;
            case LIST_CLIENT_INVOICES:              getClientInvoices();              break;
            case LIST_WAITLISTED_PRODUCT_ORDERS:    getWaitlistedProductOrders();     break;
            case CREATE_CLIENT_ORDER:               createOrder();                    break;
            case ACCEPT_CLIENT_PAYMENT:             acceptClientPayment();            break;
            case ACCEPT_PRODUCT_SHIPMENT:           acceptProductShipment();          break;
         }
      }
   }

   /**
    * Prompts the user for a client name and attempts to add that client to the system
    * @pre         None
    * @post        A client will be added to the system
    */
   public void addClient() {
      String name = getToken("Enter client name");
      Client client = warehouse.addClient(name);
      System.out.println("Added client " + client);
   }

   /**
    * Prompts the user for a supplier name and attempts to add that supplier to the system
    * @pre         None
    * @post        A supplier will be added to the system
    */
   public void addSupplier() {
      String name = getToken("Enter supplier name");
      Supplier supplier = warehouse.addSupplier(name);
      System.out.println("Added supplier " + supplier);
   }

   /**
    * Prompts the user for a product name, and price, and attempts to add that product to the system
    * @pre         None
    * @post        A product will be added to the system
    */
   public void addProduct() {
      String name = getToken("Enter product name");
      float price;
      do {
         String sprice = getToken("Enter product price");
         price = Float.parseFloat(sprice);
         if (price < 0) {
            System.out.println("Invalid product price, price must be greater than 0. Please try again.");
         }
      } while (price < 0); 

      Product product = warehouse.addProduct(name, price);
      System.out.println("Added product " + product);
   }

   /**
    * Prints out a list of clients in the system, if clients are in the system; otherwise, 
    * prints how they are no clients in the system
    * @pre         None
    * @post        None
    */
   public void showClients() {
      if (warehouse.hasClients()) {
         System.out.println("System has client(s):");
         for (Iterator<Client> clients = warehouse.getClients(); clients.hasNext();) {
            Client client = clients.next();
            System.out.println("\t" + client);
         }
      }
      else {
         System.out.println("There are currently no clients in the system.");
      }
   }

   /**
    * Prints out a list of suppliers in the system, if suppliers are in the system; otherwise, 
    * prints how they are no suppliers in the system
    * @pre         None
    * @post        None
    */
   public void showSuppliers() {
      if (warehouse.hasSuppliers()) {
         System.out.println("System has supplier(s):");
         for (Iterator<Supplier> suppliers = warehouse.getSuppliers(); suppliers.hasNext();) {
            Supplier supplier = suppliers.next();
            System.out.println("\t" + supplier);
         }
      }
      else {
         System.out.println("There are currently no suppliers in the system.");
      }
   }

   /**
    * Prints out a list of products in the system, if products are in the system; otherwise, 
    * prints how they are no products in the system
    * @pre         None
    * @post        None
    */
   public void showProducts() {
      if (warehouse.hasProducts()) {
         
         System.out.println("System has product(s):");
         
         for (Iterator<Product> products = warehouse.getProducts(); products.hasNext();) {
            Product product = products.next();
            System.out.println("\t" + product);
         }
      }
      else {
         System.out.println("There are currently no products in the system.");
      }
   }

   /**
    * Prompts the user for a supplier id and product id and associates the two
    * @pre         None
    * @post        None
    */
   public void associateProductAndSupplier() {
      String supplierId;
      Supplier supplier;
      String productId;
      Product product;

      do {
         supplierId = getToken("Enter supplier id, or 'stop' to cancel action");
         supplier = warehouse.findSupplier(supplierId);

         if (supplierId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (supplier == null) {
            System.out.println("Invalid supplier id, please try again or type 'stop' to cancel action.");
         }
      } while (supplier == null); 

      do {
         productId = getToken("Enter product id, or 'stop' to cancel action");
         product = warehouse.findProduct(productId);

         if (productId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (product == null) {
            System.out.println("Invalid product id, please try again or type 'stop' to cancel action.");
         }
      } while (product == null); 

      int retval = warehouse.associateProductAndSupplier(productId, supplierId);
      switch(retval){
         case Warehouse.ALREADY_EXISTS:
            System.out.println(String.format(
               "Supplier [%s] already supplies product [%s]", 
               supplierId, productId
            ));
            break;
         case Warehouse.SUCCESS:
            System.out.println(String.format(
               "Associate complete between product id: [%s] and supplier id: [%s]",
               productId, supplierId
            ));
            break;
         default:
            System.out.println("An error has occurred");
      }
   }

   /**
    * Prompts the user for a supplier id and product id and disassociates the two
    * @pre         None
    * @post        None
    */
   public void disassociateProductAndSupplier() {
      String supplierId;
      Supplier supplier;
      String productId;
      Product product;

      do {
         supplierId = getToken("Enter supplier id, or 'stop' to cancel action");
         supplier = warehouse.findSupplier(supplierId);

         if (supplierId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (supplier == null) {
            System.out.println("Invalid supplier id, please try again or type 'stop' to cancel action.");
         }
      } while (supplier == null); 

      do {
         productId = getToken("Enter product id, or 'stop' to cancel action");
         product = warehouse.findProduct(productId);

         if (productId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (product == null) {
            System.out.println("Invalid product id, please try again or type 'stop' to cancel action.");
         }
      } while (product == null); 

      int retval = warehouse.disassociateProductAndSupplier(productId, supplierId);
      switch(retval){
         case Warehouse.ALREADY_EXISTS:
            System.out.println(String.format(
               "Product [%s] isn't supplied by supplier [%s]", 
               productId, supplierId
            ));
            break;
         case Warehouse.SUCCESS:
            System.out.println(String.format(
               "Disassociate complete between product id: [%s] and supplier id: [%s]",
               productId, supplierId
            ));
            break;
         default:
            System.out.println("An error has occurred");
      }
   }

   /**
    * Prompts the user for a client id and prints the client's balance
    * @pre         None
    * @post        None
    */
   public void getClientBalance() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      String clientBalance = warehouse.getClientBalanceStr(clientId);
      System.out.println(String.format("[%s] has balance [%s]", clientId, clientBalance));
   }

   /**
    * Prompts the user for a client id and prints the client's invoices if the client has invoices; otherwise,
    * prints how the client has no invoices
    * @pre         None
    * @post        None
    */
   public void getClientInvoices() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      // if (warehouse.clientHasInvoices(clientId)) {
      if (client.hasInvoices()) {
         System.out.println("Client id: " + clientId + " has invoice(s)");
         for (Iterator<Invoice> invoices = warehouse.getInvoices(clientId); invoices.hasNext();) {
            Invoice invoice = invoices.next();
            System.out.println("\t" + invoice);
         }
      }
      else {
         System.out.println("Client id: " + clientId + " doesn't have any invoices.");
      }
   }

   /**
    * Prompts the user for a client id and prints the client's orders if the client has orders; otherwise,
    * prints how the client has no orders
    * @pre         None
    * @post        None
    */
   public void getClientOrders() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      // we need to make note why we made this change:
      // it is because by requiring that the user has the client object to findout is they have orders
      // that assumes they have a valid client so no additional checking is needed through the warehouse

      // if (warehouse.clientHasOrders(clientId)) {
      if (client.hasOrders()) {   
         System.out.println("Client id: " + clientId + " has order(s)");
         for (Iterator<Order> orders = warehouse.getOrders(clientId); orders.hasNext();) {
            Order order = orders.next();
            System.out.println("\t" + order);
         }
      }
      else {
         System.out.println("Client " + clientId + " doesn't have any orders.");
      }
   }

   /**
    * Prompts the user for a client id and creates an order. Loops while the user enters product ids to 
    * add to the order. After the user finishes adding products to the order, the order will be processed
    * @pre         None
    * @post        None
    */
   public void createOrder() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      String orderId = warehouse.createOrder(clientId);

      do {
         int quantity;
         String productId = getToken("Enter product id, or 'stop' to cancel action");
         Product product = warehouse.findProduct(productId);

         if (productId.equals("stop")) {

         }
         else if (product == null) {
            System.out.println("Invalid product id, please try again.");
            continue;
         }

         do {
            quantity = Integer.parseInt(getToken("Enter quantity"));

            if (quantity <= 0) {
               System.out.println("Amount must be greater than 0.");
            }
         } while (quantity <= 0);

         // return error code (ex. if 1 then client doesn't exist, if 2 orderId doesn't exist)
         int retval = warehouse.addToOrder(clientId, orderId, productId, quantity);
         switch(retval){
            case Warehouse.CLIENT_NOT_FOUND:
               System.out.println("Unable to add item to order: client id " + clientId + " doesn't exist.");
               break;
            case Warehouse.ORDER_NOT_FOUND:
               System.out.println("Unable to add item to order: order id " + orderId + " doesn't exist.");
               break;
            case Warehouse.PRODUCT_NOT_FOUND:
               System.out.println("Unable to add item to order: product id " + productId + " doesn't exist.");
               break;
            case Warehouse.SUCCESS:
               System.out.println(String.format(
                  "[%d] of product id [%s] was added successfully to client id [%s] order [%s]", 
                  quantity, productId, clientId, orderId
               ));
               break;
            default:
               System.out.println("An error has occurred");
         }
      } while (yesOrNo("Would you like to enter more product ids?")); 

      int retval = warehouse.processClientOrder(clientId, orderId);
      switch(retval){
         case Warehouse.CLIENT_NOT_FOUND:
            System.out.println("Unable to process order: client id " + clientId + " doesn't exist.");
            break;
         case Warehouse.ORDER_NOT_FOUND:
            System.out.println("Unable to process order: order id " + orderId + " doesn't exist.");
            break;
         case Warehouse.SUCCESS:
            System.out.println("Order has been created, processed, and client with id: "
                            + "[" + clientId + "] has been charged for the items fufilled.");
            break;
         default:
            System.out.println("An error has occurred");
      }
   }

   /**
    * Prompts the user for a client id and payment amount. The payment is applied to the client's account
    * @pre         None
    * @post        None
    */
   public void acceptClientPayment() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      String clientBalance = warehouse.getClientBalanceStr(clientId);
      float currentBalance = warehouse.getClientBalance(clientId);
      float payment;
      boolean paymentLtZero;
      boolean paymentGtBalance;
      do {
         String spayment = getToken("Enter payment amount to apply to account");
         payment = Float.parseFloat(spayment);

         // This means payment is less than 0
         paymentLtZero = (Float.compare(payment, 0) < 0);
         
         // this means payment > clientBalance
         paymentGtBalance = (Float.compare(payment, currentBalance) > 0);
         if (paymentLtZero) {
            System.out.println("Invalid payment amount, payment must be greater than 0. Please try again.");
         }
         else if (paymentGtBalance) {
            System.out.println("Invalid payment amount, payment must be less than or equal to clients "
                             + "balance [" + clientBalance + "] Please try again.");
         }
      } while (paymentLtZero && paymentGtBalance); 

      int retval = warehouse.acceptClientPayment(clientId, payment);
      switch(retval){
         case Warehouse.CLIENT_NOT_FOUND:
            System.out.println("Unable to accept payment: client id " + clientId + " doesn't exist.");
            break;
         case Warehouse.SUCCESS:
            System.out.println(String.format(
               "Payment [$%.2f] has been accepted and applyed to client id [%s]'s account.",
               payment, clientId
            ));
            break;
         default:
            System.out.println("An error has occurred");
      }
   }

   /**
    * Prompts the user for a product id and prints the waitlisted orders for that product
    * @pre         None
    * @post        None
    */
   public void getWaitlistedProductOrders() {
      String productId;
      Product product;

      do {
         productId = getToken("Enter product id, or 'stop' to cancel action");
         product = warehouse.findProduct(productId);

         if (productId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (product == null) {
            System.out.println("Invalid product id, please try again or type 'stop' to cancel action.");
         }
      } while (product == null);  

      if (warehouse.productHasWaitlistedOrderItems(productId)) {
         System.out.println(String.format("Product id [%s] has waitlisted order(s):", productId));
         Iterator<WaitlistItem> waitlistedItems = warehouse.getProductWaitlistedOrderItems(productId);
         while(waitlistedItems.hasNext()) {
            WaitlistItem item = waitlistedItems.next();
            System.out.println("\t" + item);
         }
      }
      else {
         System.out.println("Product id [" + productId + "] doesn't have any waitlisted orders.");
      }
   }

   /**
    * Prompts the user for a client id and prints the transaction history for that client
    * @pre         None
    * @post        None
    */
   public void getClientTransactionHistory() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      // if (warehouse.clientHasTransactions(clientId)) {
      if (client.hasTransactions()) {
         System.out.println(String.format("Client [%s] has transaction(s):", clientId));
         Iterator<Transaction> transactions = warehouse.getClientTransactions(clientId);
         while(transactions.hasNext()) {
            Transaction transaction = transactions.next();
            System.out.println("\t" + transaction);
         }
      }
      else {
         System.out.println("Client id [" + clientId + "] doesn't have any transactions.");
      }
   }

   /**
    * Prompts the user for a client id and an order id, then prints the records for that order
    * @pre         None
    * @post        None
    */
   public void getClientOrderDetails() {
      String clientId;
      String orderId;
      Client client;
      Order order;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      do {
         orderId = getToken("Enter order id");
         order = warehouse.findClientOrder(clientId, orderId);

         if (order == null) {
            System.out.println("Invalid order id, please try again.");
         }
      } while (order == null); 

      System.out.println(String.format(
         "Client [%s] has order [%s] with record(s):", 
         clientId,
         order
      ));

      for (Iterator<Record> records = warehouse.getOrderRecords(clientId, orderId); records.hasNext();) {
         Record record = records.next();
         System.out.println("\t" + record);
      }
   }

   /**
    * Prompts the user for a client id and an invoice id, then prints the records for that invoice
    * @pre         None
    * @post        None
    */
   public void getClientInvoiceDetails() {
      String clientId;
      Client client;
      String invoiceId;
      Invoice invoice;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      do {
         invoiceId = getToken("Enter invoice id");
         invoice = warehouse.findClientInvoice(clientId, invoiceId);

         if (invoice == null) {
            System.out.println("Invalid invoice id, please try again.");
         }
      } while (invoice == null); 

      System.out.println(String.format(
         "Client [%s] has invoice [%s] with record(s):", 
         clientId,
         invoice
      ));

      for (Iterator<Record> records = warehouse.getInvoiceRecords(clientId, invoiceId); records.hasNext();) {
         Record record = records.next();
         System.out.println("\t" + record);
      }
   }

   /**
    * Queries the system and prints out a list of clients with an unpaid balance in the system, 
    * if clients with unpaid balances in the system; otherwise, prints how they are no clients with unpaid balances
    * in the system
    * @pre         None
    * @post        None
    */
   public void getClientsWithUnpaidBalance() {
      if (warehouse.hasClientsWithUnpaidBalance()) {
         System.out.println("System has client(s) with unpaid balances:");

         for (Iterator<Client> clients = warehouse.getClientsWithUnpaidBalance(); clients.hasNext();) {
            Client client = clients.next();
            System.out.println("\t" + client);
         }
      }
      else {
         System.out.println("All client's have paid their balance.");
      }
   }

   /**
    * Prompts the user for a client id. Queries the system and prints out the list of waitlisted orders for that client, 
    * if that client has waitlisted orders; otherwise, prints how the client has no waitlisted orders in the system
    * @pre         None
    * @post        None
    */
   public void getClientsWaitlistedOrders() {
      String clientId;
      Client client;

      do {
         clientId = getToken("Enter client id, or 'stop' to cancel action");
         client = warehouse.findClient(clientId);

         if (clientId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (client == null) {
            System.out.println("Invalid client id, please try again or type 'stop' to cancel action.");
         }
      } while (client == null);

      if (warehouse.clientHasWaitlistedOrders(clientId)) {

         System.out.println("Client " + clientId + " has waitlisted order(s):");
         for (Iterator<Order> orders = warehouse.getClientWaitlistedOrders(clientId); orders.hasNext();) {
            Order order = orders.next();
            
            System.out.println("\tOrder " + order.getId() + " has waitlisted items:");
            for (Iterator<WaitlistItem> items = warehouse.getClientWaitlistedOrderItems(clientId, order.getId()); items.hasNext();) {
               WaitlistItem item = items.next();
               System.out.println("\t\t" + item);
            }
         }
      }
      else {
         System.out.println("Client id [" + clientId + "] doesn't have any waitlisted orders.");
      }
   }

   /**
    * Prompts the user for a product id and the quantity of that product id received. Prompts the user if they would 
    * like to fill certain waitlisted orders for a given product. After the items have been cycled over, the remaining
    * amount of the product will be added to the inventories stock
    * @pre         None
    * @post        Waitlisted orders will be filled or the product's stock will increase
    */
   public void acceptProductShipment() {
      int quantity;
      String  productId;
      Product product;

      do {
         productId = getToken("Enter product id, or 'stop' to cancel action");
         product = warehouse.findProduct(productId);

         if (productId.equals("stop")) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (product == null) {
            System.out.println("Invalid product id, please try again or type 'stop' to cancel action.");
         }
      } while (product == null); 

      do {
         quantity = Integer.parseInt(getToken("Enter quantity, or '-1' to cancel action"));

         if (quantity == -1) {
            System.out.println("Action cancelled.");
            return;
         }
         else if (quantity <= 0) {
            System.out.println("Amount must be greater than 0, please try again or type '-1' to cancel action.");
         }
      } while (quantity <= 0);

      if (warehouse.productHasWaitlistedOrderItems(productId)) {
         Iterator<WaitlistItem> waitlistedOrderItems = warehouse.getProductWaitlistedOrderItems(productId);
         while(waitlistedOrderItems.hasNext() && quantity != 0) {
            WaitlistItem item = waitlistedOrderItems.next();

            if (!yesOrNo(String.format("Would you like to fill: [%s]", item))) {
               continue;
            }

            int quantityRemaining = warehouse.processShipment(productId, quantity, item);
            if (quantityRemaining == -1) {
               System.out.println(String.format("An error occurred when filling [%s], skipping..", item));
            }
            else {
               quantity = quantityRemaining;
            }
         }
      }
      else {
         System.out.println("There are no waitlisted items for that product. Adding all to inventory.");
      }

      // update the quantity in the system 
      while (quantity > 0) {
         quantity = warehouse.processShipment(productId, quantity, null);
      }
   }

   /**
    * Helper function to save the system's data into a file
    */
   private void save() {
      if (warehouse.save()) {
         System.out.println("The warehouse has been successfully saved in the file WarehouseData\n");
      } else {
         System.out.println("There has been an error in saving\n");
      }
   }

   /**
    * Helper function to retrieve the system's data from a file
    */
   private void retrieve() {
      try {
         Warehouse tempWarehouse = Warehouse.retrieve();
         
         if (tempWarehouse != null) {
            System.out.println("The warehouse has been successfully retrieved from the file WarehouseData\n");
            warehouse = tempWarehouse;
         } else {
            System.out.println("File doesnt exist; creating new warehouse" );
            warehouse = Warehouse.instance();
         }
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }

   /**
    * Main function that calls the process function to start
    */
   public static void main(String[] s) {
      Ui.instance().process();
   }
}
