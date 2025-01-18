package com.nstyle.test.utils;

import java.util.Random;

public class OTPGenerator {
    private OTPGenerator() {}

    private static final Random rand = new Random();

    public static int generateOTP(){
        return rand.nextInt(10000);
    }
}
