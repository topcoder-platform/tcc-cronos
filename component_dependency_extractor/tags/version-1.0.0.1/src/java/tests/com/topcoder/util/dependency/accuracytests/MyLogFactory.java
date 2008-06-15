/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * Mock log factory for test.
 *
 * @author telly12
 * @version 1.0
 */
public class MyLogFactory implements LogFactory {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public MyLogFactory() {
        // do nothing
    }

    /**
     * <p>
     * Creates a {@link Log} implementation and assigning the name to it.
     * </p>
     *
     * @param name
     *            A possibly null, possibly empty (trim'd) string representing the name assigned to the log
     * @return A non-null Log implementation
     * @throws LogException
     *             if an exception occurs creating the log
     */
    public Log createLog(String name) throws LogException {
        return new MyLog(name);
    }

}
