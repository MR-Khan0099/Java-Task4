package example;

public class ChargingExceptions extends Exception {
 private static final long serialVersionUID = 1L;

 public ChargingExceptions(String message) {
     super(message);
 }

 public ChargingExceptions(String message, Throwable cause) {
     super(message, cause);
 }
}


