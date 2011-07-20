/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.shared.dataAccess.RequestInt;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;

import java.util.HashMap;
import java.util.Map;


/**
 * Mock class for failure tests.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockDataAccess implements DataAccessInt {
    /** Throw exception or not */
    private boolean throwException;

/**
         * The constructor.
         * @param throwException the parameter
         */
    public MockDataAccess(boolean throwException) {
        this.throwException = throwException;
    }

    /**
     * Mock test only.
     *
     * @param request the parameter
     *
     * @return DOCUMENT ME!
     */
    public Map<String, ResultSetContainer> getData(RequestInt request)
        throws Exception {
        if (throwException) {
            throw new Exception();
        }

        Map<String, ResultSetContainer> re = new HashMap<String, ResultSetContainer>();

        if ("1".equals(request.getProperty("cr"))) {
            re.put("Coder_Data", new MockResultSetContainer());
        } else {
            re.put("Coder_Data", new ResultSetContainer());
        }

        return re;
    }
}
