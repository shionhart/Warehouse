import java.util.*;
import java.io.*;
public class Transaction implements Serializable {

   private static final long serialVersionUID = 1L;
   private String type;
   private String description;
   private Calendar date;

   public Transaction (String type, String description) {
      this.type = type;
      this.description = description;
      date = new GregorianCalendar();
      date.setTimeInMillis(System.currentTimeMillis());
   }
   public boolean onDate(Calendar date) {
      return ((date.get(Calendar.YEAR)  == this.date.get(Calendar.YEAR)) &&
              (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH)) &&
              (date.get(Calendar.DATE)  == this.date.get(Calendar.DATE)));
   }
  
   public String getType() {
      return type;
   }
   
   public String getDescription() {
      return description;
   }
  
   public String getDate() {
      return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
   }
  
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    */
   public String toString(){
      return String.format("%s\t%10s\t%s", getDate(), type, description);
   }
}