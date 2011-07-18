/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.RandomStringImage;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Mock for failure!
 *
 * @author $author$
 * @version $Revision$
  */
public class MockRandomStringImage extends RandomStringImage {
    /**
     * Creates a new MockRandomStringImage object.
     *
     * @param configFile Mock for failure!
     *
     * @throws IllegalArgumentException Mock for failure!
     * @throws InvalidConfigException Mock for failure!
     * @throws IOException Mock for failure!
     */
    public MockRandomStringImage(String configFile)
        throws IllegalArgumentException, InvalidConfigException, IOException {
        super(configFile);
    }

    /**
     * Mock for failure!
     *
     * @param output Mock for failure!
     *
     * @return Mock for failure!
     *
     * @throws IOException Mock for failure!
     */
    public String generateRandomFromDictionaries(OutputStream output)
        throws IOException {
        throw new IOException("error");
    }
}
