/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

/**
 * The map from message AttributeScope to property name. The AttributeScope is used to retrieve the message property
 * value from the request parameter or attribute, or session attribute, or application context(servletcontext)
 * attribute, or ActionContext attribute by the AttributeScope#getAttribute().  This class is immutable and thread safe.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AttributeScope {
    /** Represents the scope of action_context. */
    private static final String SCOPE_ACTION_CONTEXT = "action_context";

    /** Represents the scope of application. */
    private static final String SCOPE_APPLICATION = "application";

    /** Represents the scope of session. */
    private static final String SCOPE_SESSION = "session";

    /** Represents the scope of request. */
    private static final String SCOPE_REQUEST = "request";

    /** Represents the scope of request_parameter. */
    private static final String SCOPE_PARAMETER = "request_parameter";

    /**
     * Represents the attribute name used to get the value from the specified scope. It's set in the constructor,
     * non-null and non-empty after set.
     */
    private final String attribute;

    /**
     * Represents the scope to get the value. In version 1.0, it must be one of the follow values: request_parameter
     * request session application action_context It's set in the constructor, immutable after set.
     */
    private String scope;

    /**
     * Create the AttributeScope with given attribute name and scope.
     *
     * @param attribute the property's name
     * @param scope the scope from where the property can be obtain
     *
     * @throws IllegalArgumentException if argument is null, empty string, or scope is invalid
     */
    public AttributeScope(String attribute, String scope) {
        ParameterCheck.checkNullEmpty("attribute", attribute);
        ParameterCheck.checkNullEmpty("scope", scope);

        this.attribute = attribute;
        this.scope = scope.toLowerCase();

        if (!(this.scope.equals(SCOPE_PARAMETER) || this.scope.equals(SCOPE_REQUEST)
                || this.scope.equals(SCOPE_SESSION) || this.scope.equals(SCOPE_APPLICATION)
                || this.scope.equals(SCOPE_ACTION_CONTEXT))) {
            throw new IllegalArgumentException("scope must be either:" + SCOPE_PARAMETER + "|" + SCOPE_REQUEST + "|"
                + SCOPE_SESSION + "|" + SCOPE_APPLICATION + "|" + SCOPE_ACTION_CONTEXT);
        }
    }

    /**
     * Get the attribute name
     *
     * @return String attribute name
     */
    public String getAttribute() {
        return this.attribute;
    }

    /**
     * Get the scope In version 1.0, it must be one of the follow values: request_parameter request session application
     * action_context
     *
     * @return String the scope
     */
    public String getScope() {
        return this.scope;
    }
}
