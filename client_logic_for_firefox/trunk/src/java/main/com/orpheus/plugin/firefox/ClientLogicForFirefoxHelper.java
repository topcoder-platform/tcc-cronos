/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * <p>
 * Defines utilities for persistence classes. Since this class will be accessed via multi-packages, this class is
 * declared in public scope.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class ClientLogicForFirefoxHelper {
    /** Presents the default encoding for the text of url. */
    public static final String DEFAULT_URL_ENCODING = "UTF-8";

    /** The buffer size when reading data from a InputStream instance. */
    private static final int BUFFERSIZE = 1024;

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private ClientLogicForFirefoxHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code> or is empty string.
     */
    public static void validateString(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a double variable. The value cannot be negative.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is negative.
     */
    public static void validateNotNegative(double value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " should not be negative.");
        }
    }

    /**
     * <p>
     * Validates the poll time. The value cannot be less than 1.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is less than 1.
     */
    public static void validatePollTime(int value, String name) {
        if (value < 1) {
            throw new IllegalArgumentException(name + " should not be negative.");
        }
    }

    /**
     * <p>
     * Build the absolute url with given base url and some http query parameters. So if the base url is
     * "http://www.google.com", the query parameter names are ["name1", "name2"] and the query parameter values are
     * ["value1", "value2"], the absolute url will be "http://www.google.com?name1=value1&name2=value2".
     * </p>
     *
     * @param baseUrl the base url, like http://www.google.com.
     * @param queryNames the array of query parameter names.
     * @param queryValues the array of query parameter values.
     *
     * @return the absolute url.
     *
     * @throws UnsupportedEncodingException if the encoding is not supported.
     */
    public static String buildUrl(String baseUrl, String[] queryNames, String[] queryValues)
        throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(baseUrl);

        boolean start = true;

        for (int i = 0; i < queryNames.length; i++) {
            if (queryValues[i] != null) {
                if (start) {
                    buffer.append("?");
                    start = false;
                } else {
                    buffer.append("&");
                }

                buffer.append(URLEncoder.encode(queryNames[i], DEFAULT_URL_ENCODING));
                buffer.append("=").append(URLEncoder.encode(queryValues[i], DEFAULT_URL_ENCODING));
            }
        }

        return buffer.toString();
    }

    /**
     * <p>
     * Returns the content of the given InputStream.
     * </p>
     *
     * @param stream the given InputStream instance to read data from.
     *
     * @return the content of the given InputStream.
     *
     * @throws IOException when any IO related problems happen.
     */
    public static String getInputStreamContent(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFERSIZE];
        int len = 0;

        while ((len = stream.read(data)) != -1) {
            baos.write(data, 0, len);
        }

        return new String(baos.toByteArray());
    }
}
