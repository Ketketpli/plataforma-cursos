package com.example.cursos_backend.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(){
        super("Email já existente");
    }

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
