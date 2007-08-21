/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import java.security.MessageDigest;

/**
 * <p>Provides support functions for authentication. </p>
 *
 * @author mtong
 * @version 1.0
 */
public class AuthenticationSupport {
    static final String hash_secret = "746c80dbdc541fe829898aa01d9e30118bab5d6b9fe94fd052a40069385f5628";
    
    /**
     * Compute a one-way hash of a string.
     */
    public static String hashPassword(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] plain = (hash_secret + str).getBytes();
        byte[] raw = md.digest(plain);
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < raw.length; i++)
            hex.append(Integer.toHexString(raw[i] & 0xff));
        return hex.toString();
    }
}