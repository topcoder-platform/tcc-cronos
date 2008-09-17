/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * Mock implementation of <code>OutputStream</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockOutputStream extends OutputStream {

    /**
     * <p>
     * Always throw <code>IOException</code>.
     * </p>
     *
     * @param b Useless parameter.
     *
     * @throws IOException always.
     */
    @Override
    public void write(int b) throws IOException {
        throw new IOException("Mock exception");
    }
}
