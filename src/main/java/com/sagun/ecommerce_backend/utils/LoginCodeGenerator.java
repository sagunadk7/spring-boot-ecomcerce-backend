package com.sagun.ecommerce_backend.utils;

import java.security.SecureRandom;

public class LoginCodeGenerator {

    public static SecureRandom random = new SecureRandom();

    public static String generate(){
        int code = random.nextInt(99999);
        return String.valueOf(code);
    }

}
