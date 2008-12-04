/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBean;
import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClient;

/**
 * <p>
 * Simple mock class extends <code>ConfluenceManagementServiceClient</code> for testing.
 * </p>
 * <p>
 * <b>Thread Safety:</b> the class is expected to be used in a manner that is thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceClientMock extends ConfluenceManagementServiceClient {

    /**
     * <p>
     * Construct a <code>ConfluenceManagementServiceClientMock</code> with given wsdl location.
     * </p>
     *
     * @param wsdlDocumentLocation
     *            the location of wsdl file
     */
    public ConfluenceManagementServiceClientMock(String wsdlDocumentLocation) {
        super(wsdlDocumentLocation);
    }

    /**
     * <p>
     * Get a ConfluenceManagementService for client.
     * </p>
     *
     * @return a <code>ConfluenceManagementServiceBean</code> instance with
     *         <code>ConfluenceManagerImplMock</code> instance as the inner ConfluenceManager working.
     */
    @Override
    public ConfluenceManagementService getConfluenceManagementServicePort() {
        try {
            ConfluenceManagementService service = new ConfluenceManagementServiceBean();
            Method method = ConfluenceManagementServiceBean.class.getDeclaredMethod("initialize");
            method.setAccessible(true);
            method.invoke(service);
            return service;
        } catch (SecurityException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (NoSuchMethodException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } catch (InvocationTargetException e) {
            // ignore
        }
        return null;
    }
}