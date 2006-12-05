/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import java.io.InputStream;

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
            return TestHelper.getImageFileStream();
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

}
