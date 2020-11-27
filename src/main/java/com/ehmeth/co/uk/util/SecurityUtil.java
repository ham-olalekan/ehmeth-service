package com.ehmeth.co.uk.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {

    private static final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String pin) {
        if (StringUtil.isBlank(pin))
            return null;

        return bCryptPasswordEncoder.encode(pin);
    }

    public static boolean passwordMatches(String rawPin, String hashPin) {
        return bCryptPasswordEncoder.matches(rawPin, hashPin);
    }
}

