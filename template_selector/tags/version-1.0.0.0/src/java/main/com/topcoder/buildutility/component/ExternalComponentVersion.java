/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0
 */
package com.topcoder.buildutility.component;

/**
 * <p>The external component version represents a version of a component that was not created by TopCoder and
 * is not a part of TopCoder's component catalog.</p>
 *
 * <p>NOTE: this is a dummy implementation used to test the Template Selector component.</p>
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class ExternalComponentVersion {

    /**
     * <p>Returns the name of the external component version.</p>
     *
     * @return null always
     */
    public String getName() {
        return null;
    }

    /**
     * <p>Optionally, returns a description of this external component.</p>
     *
     * @return null always
     */
    public String getDescription() {
        return null;
    }

    /**
     * <p>Returns the version of the external component. Note that the version numbering scheme depends on
     * the external component provider and may or may not be the same as TopCoder's</p>
     *
     * @return null always
     */
    public String getVersion() {
        return null;
    }

    /**
     * <p>Represents the name of the file used to include this component within our build scripts (typically a jar
     * file).</p>
     *
     * @return null always
     */
    public String getFileName() {
        return null;
    }

    /**
     * <p>Unique identifier used by our database layer to distinguish between  external component versions.</p>
     *
     * @return 0 always
     */
    public Long getId() {
        return new Long(0);
    }
}
