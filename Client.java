import java.util.*;
import java.io.*;

public class Client implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String id;

   private static final String CLIENT_STRING = "C";
   
   public Client(String name) {
      this.name = name;
      this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }
  
   public void setName(String newName) {
      name = newName;
   }
  
   public boolean equals(String id) {
      return this.id.equals(id);
   }
  
   public String toString() {
      String string = "Client id:" + id + " name:" + name;
      return string;
  }
}
