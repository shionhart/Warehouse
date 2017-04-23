import java.io.*;

/**
 * Id server used for serialization of the Client/ClientList
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class ClientIdServer implements Serializable {

   private static final long serialVersionUID = 1L;
   private  int idCounter;
   private static ClientIdServer server;
   
   /**
    * Private ClientIdServer constructor
    */
   private ClientIdServer() {
      idCounter = 1;
   }
   
   /**
    * Singleton ClientIdServer Constructor
    * <p>
    * This grabs, or creates, the ClientIdServer
    * <p>
    * @return the ClientIdServer singleton instance 
    */
   public static ClientIdServer instance() {
      if (server == null) {
         return (server = new ClientIdServer());
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
      return ("ClientIdServer" + idCounter);
   }
  
   /**
    * Retrieve a previously stored state for the ClientIdServer
    */
   public static void retrieve(ObjectInputStream input) {
      try {
         server = (ClientIdServer) input.readObject();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }
  
   /**
    * Helper function for the save function in Warehouse, which will be used during serialization
    * @see Warehouse
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
    */
   private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
      try {
         input.defaultReadObject();
         if (server == null) {
            server = (ClientIdServer) input.readObject();
         } else {
            input.readObject();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}
