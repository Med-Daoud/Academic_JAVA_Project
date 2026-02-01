
package projet_java.package2.exceptions;

public class ExceptionNoDataFound extends Exception{
     public ExceptionNoDataFound(String message) {
        super( message + " Data Not Found !!!");
     }
}