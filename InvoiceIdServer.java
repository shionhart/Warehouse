import java.io.*;

public class InvoiceIdServer implements Serializable {

   private static final long serialVersionUID = 1L;
   private  int idCounter;
   private static InvoiceIdServer server;
   private InvoiceIdServer() {
      idCounter = 1;
   }
   
   public static InvoiceIdServer instance() {
      if (server == null) {
         return (server = new InvoiceIdServer());
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
      return ("InvoiceIdServer" + idCounter);
   }
  
   public static void retrieve(ObjectInputStream input) {
      try {
         server = (InvoiceIdServer) input.readObject();
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
            server = (InvoiceIdServer) input.readObject();
         } else {
            input.readObject();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
}
