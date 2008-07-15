/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.topcoder.search.builder.PersistenceOperationException;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;

/**
 * <p>
 * The mock SearchStrategy class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSearchStrategy implements com.topcoder.search.builder.SearchStrategy {

    /**
     * The mock method.
     * @param arg0
     *            the argument
     * @param arg1
     *            the argument
     * @param arg2
     *            the argument
     * @param arg3
     *            the argument
     */
    public Object search(String arg0, Filter arg1, List arg2, Map arg3) throws PersistenceOperationException,
            UnrecognizedFilterException {
        List<DigitalRunPoints> res = new ArrayList<DigitalRunPoints>();
        DigitalRunPoints drp = new DigitalRunPoints();
        drp.setId(1);
        res.add(drp);
        return res;
    }

}
