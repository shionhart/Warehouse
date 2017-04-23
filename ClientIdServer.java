import java.io.*;

public class ClientIdServer implements Serializable {

   private static final long serialVersionUID = 1L;
   private  int idCounter;
   private static ClientIdServer server;
   private ClientIdServer() {
      idCounter = 1;
   }
   
   public static ClientIdServer instance() {
      if (server == null) {
         return (server = new ClientIdServer());
      } else {
         return server;
      }
   }
  
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
  
   public static void retrieve(ObjectInputStream input) {
      try {
         server = (ClientIdServer) input.readObject();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } catch(Exception cnfe) {
         cnfe.printStackTrace();
      }
   }
  
   private void writeObject(java.io.ObjectOutputStream output) throws IOException {
      try {
         output.defaultWriteObject();
         output.writeObject(server);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
  
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
