package com.cpack.service;

import java.util.Random;

public class SixRandom {
    private final static String CL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String getRandString(int length)
    {
        String sixRandom = "";
        Random f = new Random();
        for(int i=0;i<length;i++)
        {
            sixRandom += CL.charAt(Math.abs(f.nextInt())%CL.length());
        }
        return sixRandom;
    }

}
