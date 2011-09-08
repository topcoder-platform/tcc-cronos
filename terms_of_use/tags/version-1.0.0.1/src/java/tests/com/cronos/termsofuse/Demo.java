/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cronos.termsofuse.dao.ProjectTermsOfUseDao;
import com.cronos.termsofuse.dao.TermsOfUseDao;
import com.cronos.termsofuse.dao.UserTermsOfUseDao;
import com.cronos.termsofuse.dao.impl.ProjectTermsOfUseDaoImpl;
import com.cronos.termsofuse.dao.impl.TermsOfUseDaoImpl;
import com.cronos.termsofuse.dao.impl.UserTermsOfUseDaoImpl;
import com.cronos.termsofuse.model.TermsOfUse;
import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);
        connection = null;
    }

    /**
     * <p>
     * Demo API usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemo() throws Exception {
        // Create the configuration object
        ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_TERMS);
        // Instantiate the dao implementation from configuration defined above
        TermsOfUseDao termsOfUseDao = new TermsOfUseDaoImpl(configurationObject);

        // Create simple TermsOfUse to persist
        TermsOfUse terms = new TermsOfUse();

        terms.setTermsOfUseTypeId(3);
        terms.setTitle("t5");
        terms.setElectronicallySignable(true);
        terms.setUrl("url5");
        terms.setMemberAgreeable(false);

        // Persist the TermsOfUse
        terms = termsOfUseDao.createTermsOfUse(terms, "");

        // Set terms of use text
        termsOfUseDao.setTermsOfUseText(terms.getTermsOfUseId(), "text5");

        // Get terms of use text. This will return "text5".
        String termsOfUseText = termsOfUseDao.getTermsOfUseText(terms.getTermsOfUseId());

        // Update some information for TermsOfUse
        terms.setMemberAgreeable(true);

        // And update the TermsOfUse
        terms = termsOfUseDao.updateTermsOfUse(terms);

        // Retrieve some terms of use. The third row will be returned
        terms = termsOfUseDao.getTermsOfUse(3);

        // Delete terms of use
        termsOfUseDao.deleteTermsOfUse(5);

        // Retrieve all terms of use. All rows will be returned
        List<TermsOfUse> allTerms = termsOfUseDao.getAllTermsOfUse();

        // Create the configuration object
        configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_USER_TERMS);
        // Instantiate the dao implementation from configuration defined above
        UserTermsOfUseDao userTermsOfUseDao = new UserTermsOfUseDaoImpl(configurationObject);

        // Create user terms of use to user link
        userTermsOfUseDao.createUserTermsOfUse(3, 2);

        // Remove user terms of use to user link
        userTermsOfUseDao.removeUserTermsOfUse(3, 3);

        // Retrieve terms of use. This will return user terms with ids 1 and 2.
        List<TermsOfUse> termsList = userTermsOfUseDao.getTermsOfUseByUserId(1);

        // Retrieve users by terms of use. This will return ids 1 and 3.
        List<Long> userIdsList = userTermsOfUseDao.getUsersByTermsOfUseId(2);

        // Check whether user has terms of use. Will return false
        boolean hasTerms = userTermsOfUseDao.hasTermsOfUse(3, 3);

        // Check whether user has terms of use ban. Will return true
        boolean hasTermsBan = userTermsOfUseDao.hasTermsOfUseBan(1, 3);

        // Create the configuration object
        configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_PROJECT_TERMS);
        // Instantiate the dao implementation from configuration defined above
        ProjectTermsOfUseDao projectTermsOfUseDao = new ProjectTermsOfUseDaoImpl(configurationObject);

        // Create user terms of use to project link
        projectTermsOfUseDao.createProjectRoleTermsOfUse(2, 2, 3, 2, 0);

        // Remove user terms of use to project link
        projectTermsOfUseDao.removeProjectRoleTermsOfUse(2, 2, 3, 0);

        // Get terms of use with non-member-agreeable terms
        // Will return two lists:
        // 1st with ids: 1,2,3
        // 2nd with ids: 1
        Map<Integer, List<TermsOfUse>> termsGroupMap = projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, true);

        // Get terms of use without non-member-agreeable terms
        // Will return one list:
        // 1st with ids: 1
        termsGroupMap = projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, false);
    }
}
