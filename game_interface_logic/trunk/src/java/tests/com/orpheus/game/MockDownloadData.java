/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.io.InputStream;
import java.io.FileInputStream;

import com.topcoder.web.frontcontroller.results.ContentRetrievalException;
import com.topcoder.web.frontcontroller.results.DownloadData;

/**
 * This is a dummy class of <code>DownloadData</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDownloadData implements DownloadData {
    /**
     * get content.
     * @return InputStream
     * @throws ContentRetrievalException if retrieve fail
     */
    public InputStream getContent() throws ContentRetrievalException {
        try {
            return getImageFileStream();
        } catch (Exception e) {
            throw new ContentRetrievalException("", e);
        }
    }

    /**
     * get type.
     *
     * @return type
     */
    public String getMediaType() {

        return null;
    }

    /**
     * get name.
     *
     * @return name
     */
    public String getSuggestedName() {

        return null;
    }

    /**
     * This method return a Imgae file stream. Please remember to close file
     * stream after using it.
     *
     * @return a Image InputStream
     * @throws Exception to Junit
     */
    private final InputStream getImageFileStream() throws Exception {
        FileInputStream imageFileStream = new FileInputStream("test_files/bee.gif");
        return imageFileStream;
    }
}
