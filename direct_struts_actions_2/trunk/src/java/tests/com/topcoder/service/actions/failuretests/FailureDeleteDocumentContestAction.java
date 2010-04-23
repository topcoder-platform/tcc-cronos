/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.springframework.test.context.ContextConfiguration;

import com.topcoder.service.actions.DeleteDocumentContestAction;

/**
 * Mock <code>DeleteDocumentContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FailureDeleteDocumentContestAction extends DeleteDocumentContestAction {

    /**
     * Document id.
     */
    public static long documentId;

    /**
     * Contest id.
     */
    public static long contestId;

    /**
     * Gets contest id.
     * @return contest id.
     */
    public long getContestId() {
        return contestId;
    }

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
        this.setContestId(this.contestId);
        this.setDocumentId(this.documentId);
    }
}
