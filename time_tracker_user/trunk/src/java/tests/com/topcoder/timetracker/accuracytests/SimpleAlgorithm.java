/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.accuracytests;

import java.io.InputStream;
import java.io.OutputStream;

import com.topcoder.encryption.AbstractEncryptionAlgorithm;
import com.topcoder.encryption.EncryptionKey;

/**
 * The simple encryption algorithm. It is created for test.
 *
 * @author Chenhong
 * @version 2.0
 */
public class SimpleAlgorithm extends AbstractEncryptionAlgorithm {

    /**
     * Returns the <code>dummy</code> name.
     *
     * @return name.
     */
    public String getName() {
        return "test";
    }

    /**
     * Do nothing.
     */
    public void generateKeys() {
    }

    /**
     * Do nothing.
     *
     * @param Keys
     *            the EncryptionKey instance.
     */
    public void setKeys(EncryptionKey[] keys) {
    }

    /**
     * Returns the given output.
     *
     * @param output
     *            the OutputStream instance.
     * @return output.
     */
    public OutputStream createEncryptionOutputStream(OutputStream output) {
        return output;
    }

    /**
     * Returns the given input.
     *
     * @param input
     *            the InputStream instance.
     * @return input.
     */
    public InputStream createDecryptionInputStream(InputStream input) {
        return input;
    }

}
