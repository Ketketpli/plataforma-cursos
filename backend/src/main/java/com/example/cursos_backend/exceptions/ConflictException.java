package com.example.cursos_backend.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(){
        super("Conflito com o estado atual do recurso");
    }

    public ConflictException(String message){
        super(message);
    }
}
