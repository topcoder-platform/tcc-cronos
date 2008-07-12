/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

/**
 * <p>
 * Mock implementation of <code>TrackContestDAO</code>.
 * It has an injection setter <code>setUnitName()</code> which always throws exception.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ErrorTrackContestDAO extends MockTrackContestDAO {

    /**
     * <p>
     * Throws <code>RuntimeException</code> always.
     * </p>
     *
     * @param unitName Useless parameter.
     *
     * @throws RuntimeException always.
     */
    public void setUnitName(String unitName) {
        throw new RuntimeException("mock exception");
    }
}
