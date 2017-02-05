import java.util.*;
import java.text.*;
import java.io.*;

public class Ui {
   private static Ui ui;
   private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
   private static Warehouse warehouse;

   // Error codes
   private static final int EXIT = 0;
   private static final int ADD_CLIENT = 1;
   private static final int ADD_SUPPLIER = 2;
   private static final int ADD_PRODUCT = 3;
   private static final int SHOW_CLIENTS = 4;
   private static final int SHOW_SUPPLIERS = 5;
   private static final int SHOW_PRODUCTS = 6;
   private static final int ASSOCIATE_PRODUCT_AND_SUPPLIER = 7;
   private static final int DISASSOCIATE_PRODUCT_AND_SUPPLIER = 8;
   private static final int SAVE = 9;
   private static final int RETRIEVE = 10;
   private static final int HELP = 11;

   private Ui() {
      if (yesOrNo("Look for saved data and  use it?")) {
         retrieve();
      } else {
         warehouse = Warehouse.instance();
      }
   }
   
   public static Ui instance() {
      if (ui == null) {
         return ui = new Ui();
      } else {
         return ui;
      }
   }

   public String getToken(String prompt) {
      do {
         try {
            System.out.println(prompt);
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
  
   private boolean yesOrNo(String prompt) {
      String more = getToken(prompt + " (Y|y)[es] or anything else for no");
      if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
         return false;
      }
      return true;
   }

   public int getNumber(String prompt) {
      do {
         try {
            String item = getToken(prompt);
            Integer num = Integer.valueOf(item);
            return num.intValue();
         } catch (NumberFormatException nfe) {
            System.out.println("Please input a number ");
         }
      } while (true);
   }

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

   public void help() {
      System.out.println("Enter a integer number between 0 and 11 as explained below:");
      System.out.println(EXIT + " to exit");
      System.out.println(ADD_CLIENT + " to add a client");
      System.out.println(ADD_SUPPLIER + " to add a supplier");
      System.out.println(ADD_PRODUCT + " to add a product");
      System.out.println(SHOW_CLIENTS + " to list all clients");
      System.out.println(SHOW_SUPPLIERS + " to list all suppliers");
      System.out.println(SHOW_PRODUCTS + " to list all products");
      System.out.println(ASSOCIATE_PRODUCT_AND_SUPPLIER + " to associate a product and supplier");
      System.out.println(DISASSOCIATE_PRODUCT_AND_SUPPLIER + " to disassociate a product and supplier");
      System.out.println(SAVE + " to save data");
      System.out.println(RETRIEVE + " to retrieve");
      System.out.println(HELP + " for help");
   }


   public void addClient() {
      String name = getToken("Enter client name");
      Client result;
      result = warehouse.addClient(name);
      if (result == null) {
         System.out.println("Could not add client");
      }
      System.out.println("Added client [" + result + "]");
   }

   public void addSupplier() {
      String name = getToken("Enter supplier name");
      Supplier result;
      result = warehouse.addSupplier(name);
      if (result == null) {
         System.out.println("Could not add supplier");
      }
      System.out.println("Added supplier [" + result + "]");
   }

   public void addProduct() {
      String name = getToken("Enter product name");
      Product result;
      result = warehouse.addProduct(name);
      if (result == null) {
         System.out.println("Could not add product");
      }
      System.out.println("Added product [" + result + "]");
   }

   public void showClients() {
      Iterator allClient = warehouse.getClients();
      while (allClient.hasNext()){
      Client client = (Client)(allClient.next());
         System.out.println(client);
      }
   }

   public void showSuppliers() {
      Iterator allSupplier = warehouse.getSuppliers();
      while (allSupplier.hasNext()){
      Supplier supplier = (Supplier)(allSupplier.next());
         System.out.println(supplier);
      }
   }

   public void showProducts() {
      Iterator allProduct = warehouse.getProducts();
      while (allProduct.hasNext()){
      Product product = (Product)(allProduct.next());
         System.out.println(product);
      }
   }

   public void associateProductAndSupplier() {
      String supplierId;
      Supplier supplier;
      String productId;
      Product product;

      do {
         supplierId = getToken("Enter supplier id");
         supplier = warehouse.findSupplier(supplierId);

         if (supplier == null) {
            System.out.println("Invalid supplier id, please try again.");
         }
      } while (supplier == null); 

      do {
         productId = getToken("Enter product id");
         product = warehouse.findProduct(productId);

         if (product == null) {
            System.out.println("Invalid product id, please try again.");
         }
      } while (product == null); 

      product = warehouse.associateProductAndSupplier(productId, supplierId);
      System.out.println("Associate complete between product:" + product + " and supplier:" + supplier);
   }

   public void disassociateProductAndSupplier() {
      String supplierId;
      Supplier supplier;
      String productId;
      Product product;

      do {
         supplierId = getToken("Enter supplier id");
         supplier = warehouse.findSupplier(supplierId);

         if (supplier == null) {
            System.out.println("Invalid supplier id, please try again.");
         }
      } while (supplier == null); 

      do {
         productId = getToken("Enter product id");
         product = warehouse.findProduct(productId);

         if (product == null) {
            System.out.println("Invalid product id, please try again.");
         }
      } while (product == null); 

      product = warehouse.disassociateProductAndSupplier(productId, supplierId);
      System.out.println("Disassociate complete between product:" + product + " and supplier:" + supplier);
   }


   public void process() {
      int command;
      help();
      while ((command = getCommand()) != EXIT) {
         switch (command) {
            case HELP:                           help();           break;
            case SAVE:                           save();           break;
            case RETRIEVE:                       retrieve();       break;
            case ADD_CLIENT:                     addClient();      break;
            case ADD_SUPPLIER:                   addSupplier();    break;
            case ADD_PRODUCT:                    addProduct();     break;
            case SHOW_CLIENTS:                   showClients();    break;
            case SHOW_SUPPLIERS:                 showSuppliers();  break;
            case SHOW_PRODUCTS:                  showProducts();   break;
            case ASSOCIATE_PRODUCT_AND_SUPPLIER: associateProductAndSupplier(); break;
            case DISASSOCIATE_PRODUCT_AND_SUPPLIER: disassociateProductAndSupplier(); break;
         }
      }
   }

   private void save() {
      if (warehouse.save()) {
         System.out.println("The warehouse has been successfully saved in the file WarehouseData\n" );
      } else {
         System.out.println("There has been an error in saving\n" );
      }
   }

   private void retrieve() {
      try {
         Warehouse tempWarehouse = Warehouse.retrieve();
         
         if (tempWarehouse != null) {
            System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n" );
            warehouse = tempWarehouse;
         } else {
            System.out.println("File doesnt exist; creating new warehouse" );
            warehouse = Warehouse.instance();
         }
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }

   public static void main(String[] s) {
      Ui.instance().process();
   }
}