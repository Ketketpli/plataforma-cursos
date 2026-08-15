package com.example.cursos_backend.exceptions;

public class EnrollmentAlreadyExistException extends RuntimeException{

    public EnrollmentAlreadyExistException(){
        super("Matricula já existente");
    }

    public EnrollmentAlreadyExistException(String message){
        super(message);
    }
}
