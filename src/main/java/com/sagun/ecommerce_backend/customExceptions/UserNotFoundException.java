package com.sagun.ecommerce_backend.customExceptions;

public class UserNotFoundException extends  RuntimeException{

    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }

}
