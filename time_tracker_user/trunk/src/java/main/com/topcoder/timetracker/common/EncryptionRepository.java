/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.encryption.AbstractEncryptionAlgorithm;

/**
 * <p>
 * This is a singleton class that functions as a repository for the different encryption algorithms which may be
 * used by the assorted Time Tracker User classes.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe - it uses synchronized wrapped around internal map and may be
 * used by multiple threads.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class EncryptionRepository {
    /**
     * <p>
     * This is the singleton instance of EncryptionRepository.
     * </p>
     * <p>
     * Initialized In: getInstance
     * </p>
     * <p>
     * Modified In: Not modified
     * </p>
     * <p>
     * Accessed In: getInstance
     * </p>
     *
     */
    private static EncryptionRepository instance = new EncryptionRepository();

    /**
     * <p>
     * This is a mapping of encryption algorithmNames to their actual AbstractEncryptionAlgorithm instances.
     * </p>
     * <p>
     * Initialized In: Constructor (suggestion implementation is hashMap)
     * </p>
     * <p>
     * Modified In: registerAlgorithm, unregisterAlgorithm, clearAlgorithms
     * </p>
     * <p>
     * Accessed In: retrieveAlgorithm
     * </p>
     *
     */
    private final Map encryptionAlgorithms;

    /**
     * <p>
     * Private constructor to prevent external instantiation.
     * </p>
     *
     */
    private EncryptionRepository() {
        encryptionAlgorithms = Collections.synchronizedMap(new HashMap());
    }

    /**
     * <p>
     * Registers the provided algorithm with the repository.
     * </p>
     *
     *
     * @param algorithmName The name under which the provided algoInstance will be known by.
     * @param algoInstance The EncryptionAlgorithm that will be used.
     * @throws IllegalArgumentException if either parameter is null, or if algorithmName is an empty String.
     */
    public void registerAlgorithm(String algorithmName, AbstractEncryptionAlgorithm algoInstance) {
        Utils.checkString(algorithmName, "algorithmName", false);
        Utils.checkNull(algoInstance, "algoInstance");

        encryptionAlgorithms.put(algorithmName, algoInstance);
    }

    /**
     * <p>
     * Unregisters the provided algorithm with the repository.
     * </p>
     *
     *
     * @param algorithmName The name by which the algorithm to unregister is known by.
     * @return True if the unregistration was successful, false if it wasn't because there was no algorithm
     *         registered under given name.
     * @throws IllegalArgumentException if algorithmName is null or an empty String.
     */
    public boolean unregisterAlgorithm(String algorithmName) {
        Utils.checkString(algorithmName, "algorithmName", false);
        return encryptionAlgorithms.remove(algorithmName) != null;
    }

    /**
     * <p>
     * Retrieves the EncryptionAlgorithm registered under the given name. If no algorithm was registered, then a
     * null is returned.
     * </p>
     *
     *
     * @param algorithmName The name under which the algorithm to retrieve is known by.
     * @return The encryption algorithm with given name.
     * @throws IllegalArgumentException if algorithmName is null or an empty String.
     */
    public AbstractEncryptionAlgorithm retrieveAlgorithm(String algorithmName) {
        Utils.checkString(algorithmName, "algorithmName", false);
        return (AbstractEncryptionAlgorithm) encryptionAlgorithms.get(algorithmName);
    }

    /**
     * <p>
     * Clears all algorithms from the repository.
     * </p>
     *
     */
    public void clearAlgorithms() {
        encryptionAlgorithms.clear();
    }

    /**
     * <p>
     * Retrieves the singleton instance of EncryptionRepostory.
     * </p>
     *
     * @return The singleton instance of EncryptionRepostiory.
     */
    public static EncryptionRepository getInstance() {
        return instance;
    }
}
