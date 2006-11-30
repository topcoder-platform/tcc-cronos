/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

/**
 * <p>
 * A simple extension of the {@link User} class that stores the admin user
 * profile information. This class represents the
 * {@link UserConstants#ADMIN_TYPE_NAME} user profile type. It is wrapped inside
 * {@link UserProfileDTO} objects using the {@link UserProfileDTO#ADMIN_KEY}
 * key, and transported between the EJB client and the {@link UserProfileDAO} to
 * facilitate the persistence of the admin user profile information.
 * </p>
 * <p>
 * <code>Admin</code> does not define any additional user information. It
 * simply acts as a marker for identifying an admin profile type.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDTO
 */
public class Admin extends User {

    /**
     * <p>
     * Creates a new <code>Admin</code> object with the specified identifier.
     * </p>
     *
     * @param id the admin user identifier
     */
    public Admin(long id) {
        super(id);
    }

}
