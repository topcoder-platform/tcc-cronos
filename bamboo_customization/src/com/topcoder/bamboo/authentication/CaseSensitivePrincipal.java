/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.authentication;

import java.security.Principal;

/**
 * A principal that stores the real userName without converting to lowercase. This is useful to retrieve TopCoder's
 * username.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class CaseSensitivePrincipal implements Principal {

    /**
     * 
     */
    private final TopCoderAuthenticator topCoderAuthenticator;

    /**
     * Bamboo's principal being wrapped by this entity.
     */
    private final Principal wrapped;

    /**
     * The case-sensitive user name.
     */
    private final String realUserName;

    /**
     * Creates a CaseSensitivePrincipal wrapping an existing principal and using the real user name (without
     * converting it to lower case).
     * 
     * @param wrapped the wrapped principal object.
     * @param realUserName the case-sensitive user name.
     * @param topCoderAuthenticator TODO
     */
    public CaseSensitivePrincipal(TopCoderAuthenticator topCoderAuthenticator, Principal wrapped,
        String realUserName) {
        this.topCoderAuthenticator = topCoderAuthenticator;
        this.wrapped = wrapped;
        this.realUserName = realUserName;
    }

    /**
     * Returns the case-sensitive user name.
     * 
     * @return the case-sensitive user name.
     */
    public String getRealUserName() {
        return realUserName;
    }

    /**
     * Returns the name of this principal.
     * 
     * @return the name of this principal.
     */
    public String getName() {
        return wrapped.getName();
    }

    /**
     * Returns a string representation of this principal.
     * 
     * @return a string representation of this principal.
     */
    public String toString() {
        return "wrapped: " + wrapped.toString() + " realUserName: " + getRealUserName();
    }

    /**
     * Returns a hashcode for this principal.
     * 
     * @return a hashcode for this principal.
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.realUserName == null) ? 0 : this.realUserName.hashCode());
        result = prime * result + ((this.wrapped == null) ? 0 : this.wrapped.hashCode());
        return result;
    }

    /**
     * Compares this principal to the specified object. Returns true if the object passed in matches the principal
     * represented by this implementation.
     * 
     * @param another principal to compare with.
     * @return true if the principal passed in is the same as that encapsulated by this principal, and false
     *         otherwise.
     */
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null) {
            return false;
        }
        if (this.topCoderAuthenticator.getClass() != another.getClass()) {
            return false;
        }
        final CaseSensitivePrincipal other = (CaseSensitivePrincipal) another;
        if (this.realUserName == null) {
            if (other.realUserName != null) {
                return false;
            }
        } else if (!this.realUserName.equals(other.realUserName)) {
            return false;
        }
        if (this.wrapped == null) {
            if (other.wrapped != null) {
                return false;
            }
        } else if (!this.wrapped.equals(other.wrapped)) {
            return false;
        }
        return true;
    }

}
