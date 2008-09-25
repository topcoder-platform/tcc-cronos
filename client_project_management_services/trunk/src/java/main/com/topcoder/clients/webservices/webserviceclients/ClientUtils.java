/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.MalformedURLException;
import java.net.URL;

import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * Utility class that contain common functionality
 * in webservice client creation.
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is thread safety because it does not contain any mutable property.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
final class ClientUtils {

    /**
     * Private constructor.
     */
    private ClientUtils() {
    }

    /**
     * Create URL from given wsdl location string.
     *
     * @param <T>
     *      The generic class of throwable.
     * @param url
     *      The wsdl location string.
     * @param clazz
     *      The class for exception type to be thrown if fail
     *      create URL instance.
     *
     * @return the URL instance from given wsdl location string.
     *
     * @throws IllegalArgumentException
     *      if <param>url</param> is null or empty string.
     * @throws ClientServiceClientCreationException
     *      if fail create URL instance and given <param>clazz</param>
     *      is this exception to be thrown.
     * @throws ProjectServiceClientCreationException
     *      if fail create URL instance and given <param>clazz</param>
     *      is this exception to be thrown.
     * @throws CompanyServiceClientCreationException
     *      if fail create URL instance and given <param>clazz</param>
     *      is this exception to be thrown.
     * @throws ServiceClientCreationException
     *      if fail create URL instance and given <param>clazz</param>
     *      is this undefined.
     */
    static  <T extends Throwable> URL createURL(String url, Class<T> clazz) {
        try {
            ExceptionUtils.checkNullOrEmpty(url, null, null, "WSDL location cannot be null or empty string.");
            return new URL(url);
        } catch (MalformedURLException e) {
            if (ClientServiceClientCreationException.class.equals(clazz)) {
                throw new ClientServiceClientCreationException("Fail create URL from given wsdl location: " + url, e);
            } else if (CompanyServiceClientCreationException.class.equals(clazz)) {
                throw new CompanyServiceClientCreationException("Fail create URL from given wsdl location: " + url, e);
            } else if (ProjectServiceClientCreationException.class.equals(clazz)) {
                throw new ProjectServiceClientCreationException("Fail create URL from given wsdl location: " + url, e);
            } else {
                throw new ServiceClientCreationException("Fail create URL from given wsdl location: " + url, e);
            }
        }
    }

    /**
     * Check null object.
     *
     * @param <T>
     *      The generic class of object.
     * @param argName
     *      The name of argument.
     * @param obj
     *      The given object to be create.
     *
     * @return the valid object.
     *
     * @throws IllegalArgumentException
     *      if given object is null.
     */
    static <T extends Object> T checkNull(String argName, T obj) {
        ExceptionUtils.checkNull(obj, null, null, argName + " cannot be null.");
        return obj;
    }
}