package com.sagun.ecommerce_backend.utils;

import java.security.SecureRandom;

public class LoginCodeGenerator {

    public static SecureRandom random = new SecureRandom();

    public static Short generate(){
        double code = 1000 + random.nextDouble(99999);
        return (short) code;
    }

}
