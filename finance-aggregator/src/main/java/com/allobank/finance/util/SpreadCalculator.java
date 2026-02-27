package com.allobank.finance.util;

public class SpreadCalculator {
    public static double calculateSpread(String username) {
        if (username == null || username.isEmpty()) {
            return 0.0;
        }
        int asciiSum = 0;
        for (char character : username.toCharArray()) {
            asciiSum += (int) character;
        }
        return (asciiSum % 1000) / 100000.0;
    }
}