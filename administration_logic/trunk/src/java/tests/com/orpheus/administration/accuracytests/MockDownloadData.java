/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.topcoder.web.frontcontroller.results.ContentRetrievalException;
import com.topcoder.web.frontcontroller.results.DownloadData;

/**
 * Mock class
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDownloadData implements DownloadData {
    /**
     * Mock method.
     *
     * @throws ContentRetrievalException the exectpion
     * @return the inputstream
     */
    public InputStream getContent() throws ContentRetrievalException {
        try {
            return new FileInputStream(new File("test_files/accuracytests/1.jpg"));
        } catch (FileNotFoundException e) {
            throw new ContentRetrievalException("file not found.");
        }
    }

    /**
     * Mock method
     *
     * @return the string
     */
    public String getMediaType() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return the string
     */
    public String getSuggestedName() {
        return null;
    }

}
