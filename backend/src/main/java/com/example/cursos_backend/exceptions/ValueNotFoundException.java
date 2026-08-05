package com.example.cursos_backend.exceptions;

public class ValueNotFoundException extends RuntimeException{

    public ValueNotFoundException(){
        super("Valor não encontrada");
    }

    public ValueNotFoundException(String message){
        super(message);
    }
}
