package com.example.cursos_backend.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(){
        super("Campo de ordenação inválido");
    }

    public BadRequestException(String message){
        super(message);
    }
}
