/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

import com.topcoder.encryption.AbstractEncryptionAlgorithm;
import com.topcoder.encryption.EncryptionException;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;

/**
 * <p>
 * A utility helper class. Contains common method used in this component. Because the functionality is shared
 * between multiple packages, its scope is public.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public final class Utils {

    /**
     * <p>
     * Private constructor to prevent instantination.
     * </p>
     */
    private Utils() {
    }

    /**
     * <p>
     * Checks if given <code>arg</code> is <code>null</code> and if is, IllegalArgumentException will be
     * thrown.
     * </p>
     *
     * @param arg object to check.
     * @param name the name of the argument (used in exception message).
     * @throws IllegalArgumentException if <code>arg</code> is <code>null</code>.
     */
    public static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Checks if given <code>arg</code> is <code>null</code> or empty (trimed) and if is,
     * IllegalArgumentException will be thrown.
     * </p>
     *
     * @param arg object to check.
     * @param name the name of the argument (used in exception message).
     * @param canBeNull flag indicating if null is allowed.
     * @throws IllegalArgumentException if <code>arg</code> is <code>null</code> or empty.
     */
    public static void checkString(String arg, String name, boolean canBeNull) {
        if (!canBeNull) {
            checkNull(arg, name);
        }
        if ((arg != null) && (arg.trim().length() == 0)) {
            throw new IllegalArgumentException(name + " cannot be empty String.");
        }
    }

    /**
     * <p>
     * Checks if given <code>number</code> is <code>positive</code> and if is not, IllegalArgumentException
     * will be thrown.
     * </p>
     *
     * @param number value o check.
     * @param name the name of the argument (used in exception message).
     * @throws IllegalArgumentException if <code>number</code> is not positive.
     */
    public static void checkPositive(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(name + " must be positive.");
        }
    }

    /**
     * <p>
     * Checks if given <code>number</code> is <code>positive</code> and if is not, IllegalArgumentException
     * will be thrown.
     * </p>
     *
     * @param number value o check.
     * @param name the name of the argument (used in exception message).
     * @throws IllegalArgumentException if <code>number</code> is not positive.
     */
    public static void checkPositive(double number, String name) {
        if (number <= 0.0) {
            throw new IllegalArgumentException(name + " must be positive.");
        }
    }

    /**
     * <p>
     * Creates the date range filter from given arguments. If <code>startDate</code> and <code>endDate</code>
     * are both <code>null</code>, IllegalArgumentException will be thrown.
     * </p>
     *
     * @param fieldName the name of the search field.
     * @param startDate the search start date. If null, only endDate will be used.
     * @param endDate the search end date. If null, only startDate will be used.
     * @return the BetweenFilter is both dates are given, GreaterThan if start is not <code>null</code>,
     *         LessThan if endDate is not <code>null</code>.
     */
    public static Filter createRangerFilter(String fieldName, Date startDate, Date endDate) {
        if ((startDate == null) && (endDate == null)) {
            throw new IllegalArgumentException("The start and end dates cannot be both null.");
        }

        if ((startDate != null) && (endDate != null)) {
            if (startDate.after(endDate)) {
                throw new IllegalArgumentException("Start date must be before end date.");
            }
            // between upper lower
            return new BetweenFilter(fieldName, endDate, startDate);
        } else if (startDate != null) {
            return new GreaterThanFilter(fieldName, startDate);
        }
        return new LessThanFilter(fieldName, endDate);

    }

    /**
     * <p>
     * Encrypts the given <code>value</code> using the <code>algorithmName</code>. If <code>algorithmName</code>
     * not exists in {@link EncryptionRepository}, {@link EncryptionException} will be thrown.
     * </p>
     *
     * @param algorithmName the name of the algorithm to be used.
     * @param value the value to be encrypted.
     * @return the encrypted value.
     * @throws EncryptionException if <code>algorithmName</code> not exists or encryption error happen.
     * @throws IllegalArgumentException if any argument is <code>null</code> or empty.
     */
    public static String encrypt(String algorithmName, String value) {
        Utils.checkString(value, "value", false);
        // get the algorithm
        AbstractEncryptionAlgorithm algo = EncryptionRepository.getInstance().retrieveAlgorithm(algorithmName);
        if (algo == null) {
            throw new EncryptionException("No alogrithm with name: " + algorithmName);
        }

        // encrypt and return result.
        return new String(algo.encrypt(value.getBytes()));
    }

    /**
     * <p>
     * Decrypts the given <code>value</code> using the <code>algorithmName</code>. If <code>algorithmName</code>
     * not exists in {@link EncryptionRepository}, {@link EncryptionException} will be thrown.
     * </p>
     *
     * @param algorithmName the name of the algorithm to be used.
     * @param value the value to be encrypted.
     * @return the encrypted value.
     * @throws EncryptionException if <code>algorithmName</code> not exists or encryption error happen.
     * @throws IllegalArgumentException if any argument is <code>null</code> or empty.
     */
    public static String decrypt(String algorithmName, String value) {
        Utils.checkString(value, "value", false);
        // get the algorithm
        AbstractEncryptionAlgorithm algo = EncryptionRepository.getInstance().retrieveAlgorithm(algorithmName);
        if (algo == null) {
            throw new EncryptionException("No alogrithm with name: " + algorithmName);
        }

        // decrypt value
        return new String(algo.decrypt(value.getBytes()));
    }

    /**
     * <p>
     * Checks if causes are not <code>null</code> and returns first array element or <code>null</code>,
     * if array is empty.
     * </p>
     *
     * @param causes the array of exception causes.
     * @return the first <code>causes</code> element or <code>null</code>.
     */
    public static Throwable getFirstThrowable(Throwable[] causes) {
        checkNull(causes, "causes");
        if (causes.length > 0) {
            return causes[0];
        }

        return null;
    }

}
