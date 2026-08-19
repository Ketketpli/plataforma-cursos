package com.example.cursos_backend.exceptions;

public class CourseNotCompleteException extends RuntimeException{

    public CourseNotCompleteException(){
        super("Curso incompleto");
    }

    public CourseNotCompleteException(String message){
        super(message);
    }
}
