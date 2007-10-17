package com.topcoder.mobile.authenticator;

public interface Authenticator {
    // Gets the integer constant representing the type of authenticator
    // implementation
    public int getAuthenticationType();
    // Gets the array of string values to persist for the specific 
    // authenticator type
    public String[] getPersistenceArray();

}
