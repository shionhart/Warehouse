import java.io.*;

/**
 * Id server used for serialization of the Product/Inventory
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class ProductIdServer implements Serializable {

   /**
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * User to manager the number of ids in the server
    */
   private  int idCounter;
   
   /**
    * Singleton instance of self
    */
   private static ProductIdServer server;

   /**
    * Private ProductIdServer constructor
    */
   private ProductIdServer() {
      idCounter = 1;
   }
   
   /**
    * Singleton ProductIdServer Constructor
    * <p>
    * This grabs, or creates, the ProductIdServer
    * <p>
    * @return the ProductIdServer singleton instance 
    */
   public static ProductIdServer instance() {
      if (server == null) {
         return (server = new ProductIdServer());
      } else {
         return server;
      }
   }
  
   /**
    * Queries the next id counter
    * @return      the next id counter
    * @pre         None
    * @post        None
    */
   public int getId() {
      return idCounter++;
   }
   
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString() {
      return ("ProductIdServer" + idCounter);
   }
  
   /**
    * Retrieve a previously stored state for the ProductIdServer
    * @pre         None
    * @post        None
    */
   public static void retrieve(ObjectInputStream input) {
      try {
         server = (ProductIdServer) input.readObject();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }
  
   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @throws      IOException when the serialized output stream fails to write successfully
    * @pre         This id server needs to be written
    * @post        This id server will have been written
    * @see         Warehouse
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
    * @throws      IOException when the serialized input stream fails to read successfully
    * @throws      ClassNotFoundException when the serialized input stream fails to find the class needed for the next
    *              serialized block
    * @pre         This id server needs to be read
    * @post        This id server will have been read
    * @see         Warehouse
    */
   private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
      try {
         input.defaultReadObject();
         if (server == null) {
            server = (ProductIdServer) input.readObject();
         } else {
            input.readObject();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}
