/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * ActionContext class wraps the HttpServletRequest, HttpServletResponse objects
 * and a map of attributes. It is created in the FrontController and will be
 * used in implementations of Action, Handler and Result interfaces. The
 * attributes map of the ActionContext could be used to store temporary results
 * and final results. For example, if the action to execute has two handlers,
 * the first handler could store an object into attributes map to be further
 * processed in the second handler. One ActionContext object is created for each
 * user request, so this class is used in a thread-safe way (just like the
 * HttpServletRequest object). This class doesn't need to be synchronized.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 2.0
 */
public class ActionContext {

    /**
     * <p>
     * Represents the attributes map. Its key is of String type, and its value
     * is of Object type. It is initialized in place, and populated in the
     * setAttribute method. It could be empty. Its key must be non-null,
     * non-empty string, and its value must be non-null.
     * </p>
     */
    private final Map attributes = new HashMap();

    /**
     * <p>
     * Represents the HttpServletRequest object. It is initialized in
     * constructor, and the reference is never changed later. It is not null.
     * </p>
     */
    private final HttpServletRequest req;

    /**
     * <p>
     * Represents the HttpServletResponse object. It is initialized in
     * constructor, and the reference is never changed later. It is not null.
     * </p>
     */
    private final HttpServletResponse resp;

    /**
     * <p>
     * Constructor with the HttpServletRequest and HttpServletResponse objects.
     * </p>
     * @param req the HttpServletRequest object.
     * @param resp the HttpServletResponse object.
     * @throws IllegalArgumentException if either arg is null.
     */
    public ActionContext(HttpServletRequest req, HttpServletResponse resp) {

        this.req = req;
        this.resp = resp;
    }

    /**
     * <p>
     * Set attribute pair to the attributes map. Old value will be replaced if
     * the key already exists.
     * </p>
     * @param key the attribute key.
     * @param value the attribute value.
     * @throws IllegalArgumentException if either arg is null, or the key is
     *         empty string.
     */
    public void setAttribute(String key, Object value) {
 
        this.attributes.put(key, value);
    }

    /**
     * <p>
     * Return the attribute value corresponding to the specified key.
     * </p>
     * @return the attribute value to the key, or null if the key doesn't exist.
     * @param key the attribute key.
     * @throws IllegalArgumentException if the key is null or empty string.
     */
    public Object getAttribute(String key) {
    
        return this.attributes.get(key);
    }

    /**
     * <p>
     * return the wrapped HttpServletRequest object.
     * </p>
     * @return the wrapped HttpServletRequest object.
     */
    public HttpServletRequest getRequest() {
        return this.req;
    }

    /**
     * <p>
     * return the wrapped HttpServletResponse object.
     * </p>
     * @return the wrapped HttpServletResponse object.
     */
    public HttpServletResponse getResponse() {
        return this.resp;
    }
}