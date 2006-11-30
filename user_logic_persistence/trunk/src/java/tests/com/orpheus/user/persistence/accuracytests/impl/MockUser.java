package com.orpheus.user.persistence.accuracytests.impl;

import com.orpheus.user.persistence.impl.User;

/**
 * Mock class to expose User's protected constructor.
 * Used in accuracy test cases.
 */
public class MockUser extends User {
    /**
     * <p>
     * Creates a new <code>User</code> object with the specified user
     * identifier.
     * </p>
     *
     * @param id the user identifier
     */
    public MockUser(long id) {
        super(id);
    }
}
