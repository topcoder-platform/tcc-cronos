/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

/**
 * <p>
 * This is generic service locator interface for getting service.
 * </p>
 * <p>
 * The concrete service locator should be implemented to specify the service type:
 *
 * <pre>
 *   public class MockProjectServiceServiceLocator implements ServiceLocator&lt;ProjectService&amp;gt{
 *    ...
 * </pre>
 *
 * </p>
 *
 * @author BeBetter
 * @version 1.0
 */
public interface ServiceLocator<T> {
    /**
     * <p>
     * Returns the created service.
     * </p>
     *
     * @return created service
     * @throws Exception if any error occurs while creating service
     */
    public T getService() throws Exception;
}
