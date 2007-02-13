/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.io.InputStream;
import java.io.OutputStream;

import com.topcoder.encryption.AbstractEncryptionAlgorithm;
import com.topcoder.encryption.EncryptionKey;

/**
 * The mock encryption algorithm.
 * It just return given input, without any encryption.
 *
 * @author assistant
 * @version 1.0
 */
public class MockAlgorithm extends AbstractEncryptionAlgorithm {

    /**
     * Returns the <code>dummy</code> name.
     */
    public String getName() {
        return StressTestHelper.ALGO_NAME;
    }

    /**
     * Do nothing.
     */
    public void generateKeys() {


    }

    /**
     * Do nothing.
     */
    public void setKeys(EncryptionKey[] keys) {

    }

    /**
     * Returns the given output.
     */
    public OutputStream createEncryptionOutputStream(OutputStream output) {
        return output;
    }

    /**
     * Returns the given input.
     */
    public InputStream createDecryptionInputStream(InputStream input) {
        return input;
    }

}
