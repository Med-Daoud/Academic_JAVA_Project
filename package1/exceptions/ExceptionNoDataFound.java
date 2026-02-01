
package projet_java.package1.exceptions;


public class ExceptionNoDataFound extends Exception{
     public ExceptionNoDataFound(String message) {
        super( message + " Data Not Found !!!");
     }
}
