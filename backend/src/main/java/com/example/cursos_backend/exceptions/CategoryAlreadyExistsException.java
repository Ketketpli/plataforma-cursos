package com.example.cursos_backend.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(){
        super("Categoria já existente");
    }

    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
