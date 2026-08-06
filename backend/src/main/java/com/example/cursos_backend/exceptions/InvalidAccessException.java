package com.example.cursos_backend.exceptions;

public class InvalidAccessException extends RuntimeException{

    public InvalidAccessException(){
        super("Nível de acesso inválido");
    }

    public InvalidAccessException(String message){
        super(message);
    }
}
