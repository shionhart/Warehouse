import java.util.*;
import java.io.*;

/**
 * Holds the information for client transactions
 * @author      Shion Steel Hart <shionhart@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Transaction implements Serializable {

   /*
    * Used for serialization
    */
   private static final long serialVersionUID = 1L;

   /**
    * The type of the transaction
    */
   private String type;

   /**
    * The description for the transaction
    */
   private String description;

   /**
    * The date of the transaction occuring
    */
   private Calendar date;

   /**
    * Constructor for the Transaction
    * @param       type of the transaction that is occuring, which can currently be any string
    * @param       description of the transactions that is occuring
    */
   public Transaction (String type, String description) {
      this.type = type;
      this.description = description;
      date = new GregorianCalendar();
      date.setTimeInMillis(System.currentTimeMillis());
   }

   /**
    * Check to see if the date of the transaction matches the Calendar parameter given
    * @param       date a Calendar date object to be checked against the transactions date
    * @return      true if the transaction's date matches the Calendar parameter given
    * @pre         None
    * @post        None
    * @see         Calendar
    */
   public boolean onDate(Calendar date) {
      return ((date.get(Calendar.YEAR)  == this.date.get(Calendar.YEAR)) &&
              (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH)) &&
              (date.get(Calendar.DATE)  == this.date.get(Calendar.DATE)));
   }
  
   /**
    * Query the transaction's type
    * @return      Returns the transaction's type
    * @pre         None
    * @post        None
    */
   public String getType() {
      return type;
   }
   
   /**
    * Query the transaction's description
    * @return      Returns the transaction's description
    * @pre         None
    * @post        None
    */
   public String getDescription() {
      return description;
   }
  
   /**
    * Query the transaction's date
    * @return      Returns the transaction's date in string format
    * @pre         None
    * @post        None
    */
   public String getDate() {
      return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
   }
  
   /**
    * Generate a string when the object is used is scalar context which holds are relevent information
    * @return      Returns the formated string of relevant information about the object
    * @pre         None
    * @post        None
    */
   public String toString(){
      return String.format("%s\t%10s\t%s", getDate(), type, description);
   }
}