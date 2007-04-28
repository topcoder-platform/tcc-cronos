/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.InvalidCompanyException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.StringMatchType;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectUtilitySessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectUtilitySessionBeanTests extends TestCase {
    /**
     * <p>
     * The ProjectUtilitySessionBean instance for testing.
     * </p>
     */
    private ProjectUtilitySessionBean bean;

    /**
     * <p>
     * The ProjectUtilityDelegate instance for testing.
     * </p>
     */
    private ProjectUtilityDelegate delegate;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.setUpDataBase();

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        bean = new ProjectUtilitySessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("projectDelegateLocalHome",
            ProjectUtilityLocalHome.class, ProjectUtilityLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new ProjectUtilityDelegate("com.topcoder.timetracker.project.ejb.ProjectUtilityDelegate");

    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        delegate = null;
        bean = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectUtilitySessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectUtilitySessionBean#ProjectUtilitySessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectUtilitySessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectUtilitySessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProject(Project,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#addProject(Project,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);

        delegate.addProject(testingProject, true);

        Project actualProject = delegate.getProject(testingProject.getId());

        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProject(Project,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProject_NullProject() throws Exception {
        try {
            delegate.addProject(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProject(Project,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#updateProject(Project,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject("Description");
        delegate.addProject(testingProject, true);

        testingProject.setName("helloWorld");
        testingProject.setSalesTax(0.14);
        testingProject.setClientId(3);

        delegate.updateProject(testingProject, true);

        Project actualProject = delegate.getProject(testingProject.getId());
        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProject(Project,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            delegate.updateProject(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeProject(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#removeProject(long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        delegate.addProject(testingProject, true);

        delegate.removeProject(testingProject.getId(), true);

        Project[] projects = delegate.enumerateProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == testingProject.getId() && projects[i].isActive()) {
                fail("Failed to remove the user with id [" + testingProject.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getProject(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#getProject(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProject() throws Exception {
        Project project = delegate.getProject(1);

        assertEquals("The project names are not equals.", "time tracker", project.getName());
        assertEquals("The project descriptions are not equals.", "time tracker", project.getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, project.getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, project.getTerms().getId());
        assertEquals("The addresses are not equals.", 1, project.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, project.getCompanyId());
        assertEquals("The contacts are not equals.", 1, project.getContact().getId());
        assertEquals("The creation users are not equals.", "", project.getCreationUser());
        assertEquals("The modification users are not equals.", "", project.getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#addProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);

        delegate.addProjects(new Project[] {testingProject}, true);

        Project actualProject = delegate.getProjects(new long[] {testingProject.getId()})[0];

        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullProjects() throws Exception {
        try {
            delegate.addProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_EmptyProjects() throws Exception {
        try {
            delegate.addProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullName() throws Exception {
        Project project = TestHelper.createTestingProject("Name");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the company id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_CompanyIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("CompanyId");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the client id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_ClientIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("ClientId");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullAddress() throws Exception {
        Project project = TestHelper.createTestingProject("Address");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullContact() throws Exception {
        Project project = TestHelper.createTestingProject("Contact");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the contact type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_InvalidContactType() throws Exception {
        Contact contact = TestHelper.createTestingContact();
        contact.setId(2);
        contact.setContactType(ContactType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setContact(contact);

        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the address type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_InvalidAddressType() throws Exception {
        Address address = TestHelper.createTestingAddress();
        address.setId(2);
        address.setAddressType(AddressType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setAddress(address);

        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null payment terms and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullTerms() throws Exception {
        Project project = TestHelper.createTestingProject("PaymentTerm");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null start date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullStartDate() throws Exception {
        Project project = TestHelper.createTestingProject("StartDate");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null end date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullEndDate() throws Exception {
        Project project = TestHelper.createTestingProject("EndDate");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");
        try {
            delegate.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#updateProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject("Description");
        delegate.addProjects(new Project[] {testingProject}, true);

        testingProject.setName("helloWorld");
        testingProject.setSalesTax(0.14);
        testingProject.setClientId(3);

        delegate.updateProjects(new Project[] {testingProject}, true);

        Project actualProject = delegate.getProjects(new long[] {testingProject.getId()})[0];
        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullProjects() throws Exception {
        try {
            delegate.updateProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_EmptyProjects() throws Exception {
        try {
            delegate.updateProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullName() throws Exception {
        Project project = TestHelper.createTestingProject("Name");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the company id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_CompanyIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("CompanyId");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the client id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_ClientIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("ClientId");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullAddress() throws Exception {
        Project project = TestHelper.createTestingProject("Address");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullContact() throws Exception {
        Project project = TestHelper.createTestingProject("Contact");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the contact type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_InvalidContactType() throws Exception {
        Contact contact = TestHelper.createTestingContact();
        contact.setId(2);
        contact.setContactType(ContactType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setContact(contact);

        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the address type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_InvalidAddressType() throws Exception {
        Address address = TestHelper.createTestingAddress();
        address.setId(2);
        address.setAddressType(AddressType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setAddress(address);

        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null payment terms and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullTerms() throws Exception {
        Project project = TestHelper.createTestingProject("PaymentTerm");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null start date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullStartDate() throws Exception {
        Project project = TestHelper.createTestingProject("StartDate");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null end date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullEndDate() throws Exception {
        Project project = TestHelper.createTestingProject("EndDate");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullCreationDate() throws Exception {
        Project project = TestHelper.createTestingProject("CreationDate");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullCreationUser() throws Exception {
        Project project = TestHelper.createTestingProject("CreationUser");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");
        try {
            delegate.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeProjects(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#removeProjects(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        delegate.addProjects(new Project[] {testingProject}, true);

        delegate.removeProjects(new long[] {1, testingProject.getId()}, true);

        Project[] projects = delegate.enumerateProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == 1 && projects[i].isActive()) {
                fail("Failed to remove the user with id [1].");
            }

            if (projects[i].getId() == testingProject.getId() && projects[i].isActive()) {
                fail("Failed to remove the user with id [" + testingProject.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeProjects(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects_NullProjectIds() throws Exception {
        try {
            delegate.removeProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeProjects(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects_NegativeProjectId() throws Exception {
        try {
            delegate.removeProjects(new long[] {-9}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getProjects(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#getProjects(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects() throws Exception {
        Project project = delegate.getProjects(new long[] {1})[0];

        assertEquals("The project names are not equals.", "time tracker", project.getName());
        assertEquals("The project descriptions are not equals.", "time tracker", project.getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, project.getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, project.getTerms().getId());
        assertEquals("The addresses are not equals.", 1, project.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, project.getCompanyId());
        assertEquals("The contacts are not equals.", 1, project.getContact().getId());
        assertEquals("The creation users are not equals.", "", project.getCreationUser());
        assertEquals("The modification users are not equals.", "", project.getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects_NullProjectIds() throws Exception {
        try {
            delegate.getProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects_NegativeProjectId() throws Exception {
        try {
            delegate.getProjects(new long[] {-9});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#searchProjects(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects() throws Exception {
        Filter nameFilter = delegate.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH,
            "time tracker");
        Project[] projects = delegate.searchProjects(nameFilter);

        assertEquals("Only one project should be in the database.", 1, projects.length);

        assertEquals("The project names are not equals.", "time tracker", projects[0].getName());
        assertEquals("The project descriptions are not equals.", "time tracker", projects[0].getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, projects[0].getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, projects[0].getTerms().getId());
        assertEquals("The addresses are not equals.", 1, projects[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, projects[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, projects[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", projects[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", projects[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the search result is empty and verify the return array is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectsForEmptyResult() throws Exception {
        Filter nameFilter = delegate.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH,
            "new project");
        Project[] projects = delegate.searchProjects(nameFilter);

        assertEquals("No project should be matched in the database.", 0, projects.length);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#searchProjects(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects_NullFilter() throws Exception {
        try {
            delegate.searchProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#searchProjects(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjects_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            delegate.searchProjects(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getProjectFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#getProjectFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectFilterFactory() {
        assertNotNull("Failed to get the project filter factory.", delegate.getProjectFilterFactory());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#enumerateProjects() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#enumerateProjects() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects() throws Exception {
        Project[] projects = delegate.enumerateProjects();

        assertEquals("Only one project should be in the database.", 1, projects.length);

        assertEquals("The project names are not equals.", "time tracker", projects[0].getName());
        assertEquals("The project descriptions are not equals.", "time tracker", projects[0].getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, projects[0].getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, projects[0].getTerms().getId());
        assertEquals("The addresses are not equals.", 1, projects[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, projects[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, projects[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", projects[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", projects[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject() throws Exception {
        delegate.addEntryToProject(1, 2, EntryType.EXPENSE_ENTRY, true);

        long[] entryIds = delegate.retrieveEntriesForProject(1, EntryType.EXPENSE_ENTRY);
        assertEquals("Failed to add the entry to the project.", 2, entryIds[1]);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullType() throws Exception {
        try {
            delegate.addEntryToProject(1, 2, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the entity id is unknown and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_UnknownEntityId() throws Exception {
        try {
            delegate.addEntryToProject(1, 5555, EntryType.EXPENSE_ENTRY, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the entity id is unknown and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_UnknownProjectId() throws Exception {
        try {
            delegate.addEntryToProject(456, 2, EntryType.EXPENSE_ENTRY, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the company ids of the project and the entity are not the same
     * and expects InvalidCompanyException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_InvalidCompanyException() throws Exception {
        Project project = TestHelper.createTestingProject(null);
        project.setCompanyId(3);

        delegate.addProject(project, false);

        try {
            delegate.addEntryToProject(project.getId(), 1, EntryType.EXPENSE_ENTRY, true);
            fail("InvalidCompanyException expected.");
        } catch (InvalidCompanyException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeEntryFromProject(long,long,EntryType,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#removeEntryFromProject(long,long,EntryType,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject() throws Exception {
        delegate.removeEntryFromProject(1, 1, EntryType.EXPENSE_ENTRY, false);
        delegate.removeEntryFromProject(1, 1, EntryType.FIXED_BILLING_ENTRY, false);
        delegate.removeEntryFromProject(1, 1, EntryType.TIME_ENTRY, false);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#removeEntryFromProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullType() throws Exception {
        try {
            delegate.removeEntryFromProject(1, 1, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#retrieveEntriesForProject(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#retrieveEntriesForProject(long,EntryType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProject() throws Exception {
        long[] entriesIds = delegate.retrieveEntriesForProject(1, EntryType.EXPENSE_ENTRY);

        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds.length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0]);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#retrieveEntriesForProject(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProject_NullType() throws Exception {
        try {
            delegate.retrieveEntriesForProject(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#retrieveEntriesForClient(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#retrieveEntriesForClient(long,EntryType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForClient() throws Exception {
        long[] entriesIds = delegate.retrieveEntriesForClient(1, EntryType.EXPENSE_ENTRY);

        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds.length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0]);
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#retrieveEntriesForClient(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForClient_NullType() throws Exception {
        try {
            delegate.retrieveEntriesForClient(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#ejbCreate() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        bean.ejbCreate();

        // no assertion here because when the object created in this method cannot be accessed outside
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);

        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        assertNotNull("Failed to get the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests ProjectUtilitySessionBean#getImpl() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilitySessionBean#getImpl() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetImpl() throws Exception {
        assertNotNull("Failed to get the instance correctly.", bean.getImpl());
    }
}