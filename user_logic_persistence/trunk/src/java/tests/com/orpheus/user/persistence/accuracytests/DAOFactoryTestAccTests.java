/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests;

import com.orpheus.user.persistence.DAOFactory;
import com.orpheus.user.persistence.PendingConfirmationDAO;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.impl.SQLServerPendingConfirmationDAO;
import com.orpheus.user.persistence.impl.SQLServerUserProfileDAO;
import org.apache.cactus.ServletTestCase;

/**
 * Accuracy test cases for <code>DAOFactory</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class DAOFactoryTestAccTests extends ServletTestCase {
    /**
     * <p>
     * The number of times to invoke test method.
     * </p>
     */
    private static final int LOOP = 10;

    /**
     * <p>
     * Accuracy tests of the getPendingConfirmationDAO() method.
     * The returned instance type should be SQLServerPendingConfirmationDAO.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPendingConfirmationDAO() throws Exception {
        for (int i = 0; i < LOOP; i++) {
            PendingConfirmationDAO result = DAOFactory.getPendingConfirmationDAO();
            assertTrue("Expected an SQLServerPendingConfirmationDAO instance.",
                    result instanceof SQLServerPendingConfirmationDAO);
        }
    }

    /**
     * <p>
     * Accuracy tests of the getUserProfileDAO() method.
     * The returned instance type should be SQLServerUserProfileDAO.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserProfileDAO() throws Exception {
        for (int i = 0; i < LOOP; i++) {
            UserProfileDAO result = DAOFactory.getUserProfileDAO();
            assertTrue("Expected an SQLServerUserProfileDAO instance.",
                    result instanceof SQLServerUserProfileDAO);
        }
    }
}
