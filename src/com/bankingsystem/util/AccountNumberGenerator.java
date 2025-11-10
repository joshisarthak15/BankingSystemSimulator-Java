package com.bankingsystem.util;

import java.util.Random;

public class AccountNumberGenerator {
    private static final Random RANDOM = new Random();

    public static String generate(String name){
        String trimmed = name == null ? "" : name.trim();
        String initials = trimmed.isEmpty() ? "AC" : trimmed.replaceAll("\\s+", " ").toUpperCase().split(" ")[0].substring(0, Math.min(2, trimmed.length()));
        int randomNum = RANDOM.nextInt(9000) + 1000;
        return initials + randomNum;
    }
}
