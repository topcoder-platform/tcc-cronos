/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.springframework.test.context.ContextConfiguration;

import com.topcoder.service.actions.GetDocumentFileContestAction;

/**
 * Mock <code>GetDocumentFileContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FailureGetDocumentFileContestAction extends GetDocumentFileContestAction {

    /**
     * Document id.
     */
    public static long documentId;

    /**
     * Gets project id.
     * @return project id
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * Prepares the action.
     */
    public void prepare() {
        super.prepare();
        this.setDocumentId(this.documentId);
    }
}
