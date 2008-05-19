/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.topcoder.management.resource.Resource;


/**
 * <p>
 * The purpose to introduce this class is that the <code>Resource</code> <b>DOES NOT</b>
 * have proper getter/setter for the <em>properties</em> map field. Thus, the properties
 * will be lost when marshall/unmarshall between the client and server.
 * </p>
 *
 * <p>
 * To follow the methods contract defined by the <code>ResourceManagerService</code> web
 * service interface, this class directly extends <code>Resource</code> with only an
 * additional properties map field added. And of course, the added properties map is
 * properly defined and can be successfully marshalled/unmarshalled when passing between
 * the client and server.
 * </p>
 *
 * <p>
 * Each time when server sends a <code>Resource</code> to client, or when client sends a
 * <code>Resource</code> to server, they should at first convert the <code>Resource</code>
 * to a <code>CopiedResource</code> object and then sends the <code>CopiedResource</code>
 * object instead. See CS1.3.4.
 * </p>
 *
 * <p>
 * Each time when server receives a <code>Resource</code> from client, or when client receives
 * a <code>Resource</code> from server, the <code>Resource</code> object received is actually
 * an instance of <code>CopiedResource</code>. And the properties map need be synchronized.
 * See CS1.3.4.
 * </p>
 *
 * <p>
 * This class also provides two static methods to address the above functionalities.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe since its super class is not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class CopiedResource extends Resource {

    /**
     * <p>
     * The properties map.
     * </p>
     */
    @SuppressWarnings("unchecked")
    private Map < String, Object > properties = new HashMap();

    /**
     * <p>
     * The given <code>Resource</code> is expected to be type of <code>CopiedResource</code>.
     * If it is null or not type of <code>CopiedResource</code>, then nothing happens.
     * </p>
     *
     * <p>
     * Given the argument represents a non-null <code>CopiedResource</code>, synchronize all the
     * <code>CopiedResource.properties</code> into <code>Resource.properties</code>.
     * </p>
     *
     * <p>
     * Each time when EJB receives a <code>Resource</code> from client, or when client receives
     * a <code>Resource</code> from server, the <code>Resource</code> object received is actually
     * an instance of <code>CopiedResource</code>, and they should call this method to synchronize
     * the properties.
     * </p>
     *
     * @param resource The <code>CopiedResource</code> to copy properties.
     */
    public static final void syncProperties(Resource resource) {
        if (resource != null && resource instanceof CopiedResource) {
            CopiedResource copiedResource = (CopiedResource) resource;
            Map < String, Object > properties = copiedResource.properties;
            for (String key : properties.keySet()) {
                //set into the original properties map
                copiedResource.setProperty(key, properties.get(key));
            }
        }
    }

    /**
     * <p>
     * Copy the given <code>Resource</code> to a new instance of <code>CopiedResource</code>.
     * </p>
     *
     * <p>
     * All the fields are copied. The <code>Resource.properties</code> are
     * copied into <code>CopiedResource.properties</code>.
     * </p>
     *
     * <p>
     * Each time when EJB sends a <code>Resource</code> to client, or when service client
     * sends a <code>Resource</code> to server, they should call this method to convert the
     * <code>Resource</code> to <code>CopiedResource</code>.
     * </p>
     *
     * @param resource The <code>Resource</code> to be copied. Null is returned if given resource is null.
     *
     * @return The copied <code>CopiedResource</code>.
     */
    @SuppressWarnings("unchecked")
    public static final CopiedResource copyResource(Resource resource) {
        if (resource == null) {
            return null;
        }
        CopiedResource copiedResource = new CopiedResource();

        if (resource.getId() > 0) {
            //Only copy the id if it has been set
            copiedResource.setId(resource.getId());
        }

        copiedResource.setPhase(resource.getPhase());
        copiedResource.setProject(resource.getProject());
        copiedResource.setResourceRole(resource.getResourceRole());
        copiedResource.setSubmissions(resource.getSubmissions());
        copiedResource.setCreationUser(resource.getCreationUser());
        copiedResource.setCreationTimestamp(resource.getCreationTimestamp());
        copiedResource.setModificationUser(resource.getModificationUser());
        copiedResource.setModificationTimestamp(resource.getModificationTimestamp());

        //Set the new properties map
        copiedResource.properties = resource.getAllProperties();
        return copiedResource;
    }
}
