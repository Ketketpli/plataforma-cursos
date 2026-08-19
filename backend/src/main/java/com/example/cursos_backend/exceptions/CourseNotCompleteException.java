package com.example.cursos_backend.exceptions;

public class CourseNotCompleteException extends RuntimeException{

    public CourseNotCompleteException(){
        super("Curso imcompleto");
    }

    public CourseNotCompleteException(String message){
        super(message);
    }
}
