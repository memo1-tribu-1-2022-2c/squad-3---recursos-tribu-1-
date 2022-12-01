
package ar.uba.fi.recursos.exceptions;

public class OverlappingDatesException extends RuntimeException {
    public OverlappingDatesException(){
        System.out.println("Invalid dates");
    }
}