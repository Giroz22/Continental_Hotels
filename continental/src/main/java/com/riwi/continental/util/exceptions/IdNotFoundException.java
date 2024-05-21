package com.riwi.continental.util.exceptions;

public class IdNotFoundException extends RuntimeException {

    private static final String ERROR_MESSGE = "No registers in the entity %s with the Id supplied";

    public  IdNotFoundException(String nameEntity){
        super(String.format(ERROR_MESSGE,nameEntity));
    }
    
}
