package com.example.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerator {
    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

    public static String unique() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }

    public static String uniqueUUID() {
        return UUID.randomUUID().toString();
    }

}
