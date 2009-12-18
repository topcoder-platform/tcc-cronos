/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import com.topcoder.service.studio.contest.DocumentContentManager;

import java.util.Map;


/**
 * <p>
 * Mock implementation of DocumentContentManager, it's used for failure test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class MockAbstractDocumentContentManager
    implements DocumentContentManager {
    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param attributes not used.
     */
    public MockAbstractDocumentContentManager(Map<String, Object> attributes) {
    }
}
