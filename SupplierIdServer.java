import java.io.*;

/**
 * Id server used for serialization of the Supplier/SupplierList
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class SupplierIdServer implements Serializable {

   private static final long serialVersionUID = 1L;
   private  int idCounter;
   private static SupplierIdServer server;
   
   /**
    * Private SupplierIdServer constructor
    */
   private SupplierIdServer() {
      idCounter = 1;
   }
   
   /**
    * Singleton SupplierIdServer Constructor
    * <p>
    * This grabs, or creates, the SupplierIdServer
    * <p>
    * @return the SupplierIdServer singleton instance 
    */
   public static SupplierIdServer instance() {
      if (server == null) {
         return (server = new SupplierIdServer());
      } else {
         return server;
      }
   }
  
   /**
    * Queries the next id counter
    * @return      the next id counter
    */
   public int getId() {
      return idCounter++;
   }
   
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString() {
      return ("SupplierIdServer" + idCounter);
   }
  
   /**
    * Retrieve a previously stored state for the SupplierIdServer
    */
   public static void retrieve(ObjectInputStream input) {
      try {
         server = (SupplierIdServer) input.readObject();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }
  
   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @see Warehouse
    * @throws IOException when the serialized output stream fails to write successfully
    */
   private void writeObject(java.io.ObjectOutputStream output) throws IOException {
      try {
         output.defaultWriteObject();
         output.writeObject(server);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
  
   /**
    * Helper function for the retrieve function in Warehouse, which will be used during serialization
    * @see Warehouse
    * @throws IOException when the serialized input stream fails to read successfully
    * @throws ClassNotFoundException when the serialized input stream fails to find the class needed for the next
    *         serialized block
    */
   private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
      try {
         input.defaultReadObject();
         if (server == null) {
            server = (SupplierIdServer) input.readObject();
         } else {
            input.readObject();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}
