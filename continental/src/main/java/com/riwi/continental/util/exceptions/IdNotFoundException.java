package com.riwi.continental.util.exceptions;

public class IdNotFoundException extends RuntimeException {

    private static final String ERROR_MESSGE = "No hay registros en la entidad %s con el id suministrado";

    public  IdNotFoundException(String nameEntity){
        super(String.format(ERROR_MESSGE,nameEntity));
    }
    
}
