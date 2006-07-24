/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.impl;

/**
 * <p>The mock class of the ExternalObjectImpl class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockExternalObjectImpl extends ExternalObjectImpl {

    /**
     * <p>The ctor delegates to its super.</p>
     *
     * @param id the unique (among objects of this type) identifier of this object.
     *
     * @throws IllegalArgumentException if id is negative.
     */
    public MockExternalObjectImpl(long id) {
        super(id);
    }
}
