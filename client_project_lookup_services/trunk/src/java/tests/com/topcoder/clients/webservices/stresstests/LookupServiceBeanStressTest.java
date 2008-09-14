/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.stresstests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Random;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.ClientManagerMock;
import com.topcoder.clients.webservices.beans.CompanyManagerMock;
import com.topcoder.clients.webservices.beans.LookupServiceBean;
import com.topcoder.clients.webservices.beans.ProjectManagerMock;
import com.topcoder.clients.webservices.beans.Roles;
import com.topcoder.clients.webservices.beans.SessionContextMock;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;


/**
 * Stress test for class <code>LookupServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class LookupServiceBeanStressTest extends TestCase {
    /**
     * <p>
     * Bean instance used in tests.
     * </p>
     */
    private LookupServiceBean bean;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new LookupServiceBean();
        bean.setVerboseLogging(true);

        // Initialize required resources
        this.setField("clientManagerFile", "config.properties");
        this.setField("clientManagerNamespace", "LookupServiceBean");
        this.setField("companyManagerFile", "config.properties");
        this.setField("companyManagerNamespace", "LookupServiceBean");
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "LookupServiceBean");
        this.setField("userMappingRetrieverFile", "config.properties");
        this.setField("userMappingRetrieverNamespace", "LookupServiceBean");
        this.setField("clientAndProjectUserRole", Roles.USER);

        Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[] { Roles.USER });
        this.setField("sessionContext", context);

        // Set default managers behavior
        ClientManagerMock.setFail(false);
        CompanyManagerMock.setFail(false);
        ProjectManagerMock.setFail(false);
    }

    /**
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * Stress Test for the functionality of class <code>LookupServiceBean</code>.
     *
     * @throws Exception to JUnit
     */
    public void testLookupServiceBean_Stress() throws Exception {
        Random random = new Random();

        StressTestHelper.start();

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            int v = Math.abs(random.nextInt()) % 21;

            try {
                if (v == 0) {
                    bean.retrieveClient(1);
                } else if (v == 1) {
                    bean.retrieveAllClients();
                } else if (v == 2) {
                    bean.searchClientsByName("name");
                } else if (v == 3) {
                    bean.searchClients(new NullFilter("test"));
                } else if (v == 4) {
                    bean.getClientStatus(1);
                } else if (v == 5) {
                    bean.getAllClientStatuses();
                } else if (v == 6) {
                    bean.getClientsForStatus(new ClientStatus());
                } else if (v == 7) {
                    bean.retrieveProject(1);
                } else if (v == 8) {
                    bean.retrieveProjectsForClient(new Client());
                } else if (v == 9) {
                    bean.retrieveAllProjects();
                } else if (v == 10) {
                    bean.searchProjectsByName("name");
                } else if (v == 11) {
                    bean.searchProjects(new NullFilter("test"));
                } else if (v == 12) {
                    bean.getProjectStatus(1);
                } else if (v == 13) {
                    bean.getAllProjectStatuses();
                } else if (v == 14) {
                    bean.getProjectsForStatus(new ProjectStatus());
                } else if (v == 15) {
                    bean.retrieveCompany(1);
                } else if (v == 16) {
                    bean.retrieveAllCompanies();
                } else if (v == 17) {
                    bean.searchCompaniesByName("name");
                } else if (v == 18) {
                    bean.searchCompanies(new NullFilter("test"));
                } else if (v == 19) {
                    bean.getClientsForCompany(new Company());
                } else if (v == 20) {
                    bean.getProjectsForCompany(new Company());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StressTestHelper.printResult("testLookupServiceBean_Stress");
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @param fieldValue The field value to be set.
     *
     * @throws Exception to JUnit
     */
    private void setField(String fieldName, Object fieldValue)
        throws Exception {
        Field field = LookupServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }
}
