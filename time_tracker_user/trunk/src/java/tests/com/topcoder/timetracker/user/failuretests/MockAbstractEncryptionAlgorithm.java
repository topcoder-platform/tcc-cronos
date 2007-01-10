/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.io.InputStream;
import java.io.OutputStream;

import com.topcoder.encryption.AbstractEncryptionAlgorithm;
import com.topcoder.encryption.EncryptionException;
import com.topcoder.encryption.EncryptionKey;

/**
 * <p>
 * A mock AbstractEncryptionAlgorithm. Used for testing of non-abstract methods.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class MockAbstractEncryptionAlgorithm extends AbstractEncryptionAlgorithm {

    /**
     * <p>
     * Abstract method stub. Does nothing.
     * </p>
     */
    public String getName() {
        return null;
    }

    /**
     * <p>
     * Abstract method stub. Does nothing.
     * </p>
     */
    public void generateKeys() {
    }

    /**
     * <p>
     * Abstract method stub. Does nothing.
     * </p>
     */
    public void setKeys(EncryptionKey[] arg0) {
    }

    /**
     * <p>
     * Abstract method stub. Does nothing.
     * </p>
     */
    public OutputStream createEncryptionOutputStream(OutputStream arg0) throws EncryptionException {
        return null;
    }

    /**
     * <p>
     * Abstract method stub. Does nothing.
     * </p>
     */
    public InputStream createDecryptionInputStream(InputStream arg0) throws EncryptionException {
        return null;
    }
}
