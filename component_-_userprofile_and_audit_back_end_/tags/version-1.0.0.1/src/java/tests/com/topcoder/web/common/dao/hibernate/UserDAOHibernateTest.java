/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.DAOUtil;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicAssignment;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.School;
import com.topcoder.web.common.model.SchoolAssociationType;
import com.topcoder.web.common.model.SchoolType;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserSchool;

/**
 * <p>
 * Unit tests for {@link UserDAOHibernate} class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserDAOHibernateTest extends TestCase {

    /**
     * An instance of UserDAO used for testing.
     */
    private UserDAO instance;

    /**
     * Sets up the environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new UserDAOHibernate();
        HibernateUtils.begin();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
        HibernateUtils.close();
        instance = null;
    }

    /**
     * Test case for {@link UserDAOHibernate#UserDAOHibernate()} constructor. Checks for null after intitialization.
     */
    public void testUserDAOHibernate() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Test case for {@link UserDAOHibernate#UserDAOHibernate(org.hibernate.Session)} constructor. Checks for null after
     * intitialization.
     */
    public void testUserDAOHibernateSession() {
        instance = new UserDAOHibernate(HibernateUtils.getSession());
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Failure Test case for {@link UserDAOHibernate#UserDAOHibernate(org.hibernate.Session)} constructor. Passes a null
     * and expects an IllegalArgumentException.
     */
    public void testUserDAOHibernateSessionFailure() {
        try {
            instance = new UserDAOHibernate(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(Long)} method. Passes a valid value and expects a valid User
     * instance.
     */
    public void testFindLong() {
        User user = instance.find(20l);
        assertTrue("Error getting User record", "dok_tester".equals(user.getHandle()));
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(Long)} method. Passes an invalid value and expects a valid
     * User instance.
     */
    public void testFindLong1() {
        assertNull("Error getting User record", instance.find(-555l));
    }

    /**
     * Failure test case for {@link UserDAOHibernate#find(Long)} method. Passes a null and expects an
     * IllegalArgumentException.
     */
    public void testFindLongFailure() {
        try {
            instance.find((Long) null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean)} method. Passes a valid value and expects a
     * valid User instance.
     */
    public void testFindStringBoolean() {
        User user = instance.find("dok_tester", false);
        assertTrue("Error getting User record", "A".equals(user.getStatus().toString()));
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean)} method. Passes an invalid value and expects
     * a null.
     */
    public void testFindStringBoolean1() {
        User user = instance.find("dok_testerr", false);
        assertNull("Error getting User record", user);
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean)} method. Checks for case sensitive
     * parameter. Expects a null
     */
    public void testFindStringBoolean2() {
        User user = instance.find("dok_Tester", false);
        assertNull("Error getting User record", user);
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean)} method. Passes a null and expects an
     * IllegalArgumentException.
     */
    public void testFindStringBooleanFailure() {
        try {
            instance.find(null, true);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean)} method. Passes an empty string and expects
     * an IllegalArgumentException.
     */
    public void testFindStringBooleanFailure1() {
        try {
            instance.find("   ", true);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean, boolean)}. Expects a valid User instance.
     */
    public void testFindStringBooleanBoolean() {
        User user = instance.find("dok_tester", true, false);
        assertTrue("Error getting User record", "A".equals(user.getStatus().toString()));
    }

    /**
     * Failure test case for {@link UserDAOHibernate#find(String, boolean, boolean)}. Passes a null and expects an
     * Exception
     */
    public void testFindStringBooleanBooleanFailure() {
        try {
            instance.find(null, true, true);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String, boolean, boolean)}. Expects a valid User instance.
     */
    public void testFindStringBooleanBoolean1() {
        User user = instance.find("dok_tester", true, false);
        assertTrue("Error getting User record", "A".equals(user.getStatus().toString()));
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#find(String, String, String, String)} method. Expects a valid User
     * instance.
     */
    public void testFindStringStringStringString() {
        List < User > users = instance.find("dok_tester", null, null, null);
        assertEquals("Error searching users", 1, users.size());
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#find(String, String, String, String)} method. Expects a valid User
     * instance.
     */
    public void testFindStringStringStringString1() {
        List < User > users = instance.find(null, null, "last_name", null);
        assertEquals("Error searching users", 1, users.size());
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#saveOrUpdate(User)} method. Updates the first_name column and verifies
     * it.
     * @throws Exception
     *             to JUnit
     */
    public void testSaveOrUpdateUser() throws Exception {
        User user = instance.find("dok_tester", false);
        user.setFirstName("name_first");
        instance.saveOrUpdate(user);

        tearDown();
        setUp();

        user = instance.find("dok_tester", false);
        assertTrue("Error updating user", "name_first".equals(user.getFirstName()));
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#saveOrUpdate(User)} method. Updates the UserSchool records and verifies
     * it.
     * @throws Exception
     *             to JUnit
     */
    public void testSaveOrUpdateUser1() throws Exception {
        User u = instance.find("dok_tester", true);
        School s = new School();
        String newName = "name" + System.currentTimeMillis();
        s.setName(newName);

        s.setType(DAOUtil.getFactory().getSchoolTypeDAO().find(SchoolType.COLLEGE));

        u.addCreatedSchool(s);

        UserSchool us = new UserSchool();
        us.setSchool(s);
        us.setPrimary(true);
        us.setAssociationType(DAOUtil.getFactory().getSchoolAssociationTypeDAO().find(SchoolAssociationType.STUDENT));

        u.addSchool(us);

        instance.saveOrUpdate(u);

        tearDown();
        setUp();

        User u1 = instance.find(u.getId());
        boolean found = false;
        for (UserSchool s1 : u1.getSchools()) {
            if (s1.getSchool().getName().equals(newName)) {
                HibernateUtils.getSession().delete(s1);
                HibernateUtils.getSession().delete(s1.getSchool());
                found = true;
            }
        }
        assertTrue("new school not associated", found);
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#saveOrUpdate(User)} method. Updates the UserSchool records and verifies
     * it.
     * @throws Exception
     *             to JUnit
     */
    public void testSaveOrUpdate2() throws Exception {
        User u = instance.find("dok_tester", true);

        Address address = new Address();
        address.setAddress1("line1");

        State state = DAOUtil.getFactory().getStateDAO().find("ME");
        address.setState(state);
        u.addAddress(address);
        instance.saveOrUpdate(u);

        List < DemographicResponse > responses = getDemographicResponses(u);
        u.setTransientResponses(responses);

        instance.saveOrUpdate(u);

        assertEquals("couldn't make demographic change", responses.size(), u.getDemographicResponses().size());

        tearDown();
        setUp();

        // remove the Address entry that we added before
        u = instance.find("dok_tester", true);
        u.setAddresses(new HashSet < Address >());
        instance.saveOrUpdate(u);
        HibernateUtils.getSession().delete(address);
        HibernateUtils.getSession().createQuery("delete from DemographicResponse").executeUpdate();
    }

    /**
     * A private method for getting DemographicResponses for all DemographicQuestions.
     * @param u
     *            the User object
     * @return list of valid DemographicResponse instances
     */
    private List < DemographicResponse > getDemographicResponses(User u) {
        HashSet < RegistrationType > h = new HashSet < RegistrationType >();
        h.add(DAOUtil.getFactory().getRegistrationTypeDAO().getCompetitionType());
        List < DemographicAssignment > assignments = DAOUtil.getFactory().getDemographicAssignmentDAO().getAssignments(
                DAOUtil.getFactory().getCoderTypeDAO().find(CoderType.STUDENT), u.getHomeAddress().getState(), h);
        DemographicAssignment da;
        DemographicResponse dr;
        ArrayList < DemographicResponse > responses = new ArrayList < DemographicResponse >();
        for (Iterator < DemographicAssignment > it = assignments.iterator(); it.hasNext();) {
            da = it.next();
            if (da.getQuestion().isMultipleSelect()) {
                for (Iterator < DemographicAnswer > it1 = da.getQuestion().getAnswers().iterator(); it1.hasNext();) {
                    dr = new DemographicResponse();
                    dr.setUser(u);
                    dr.setQuestion(da.getQuestion());
                    dr.setAnswer(it1.next());
                    // dr.setId(new DemographicResponse.Identifier(ret.getId(),
                    // dr.getQuestion().getId(), dr.getAnswer().getId()));
                    responses.add(dr);
                }
            } else if (da.getQuestion().isSingleSelect()) {
                dr = new DemographicResponse();
                dr.setUser(u);
                dr.setQuestion(da.getQuestion());
                Iterator < DemographicAnswer > it1 = da.getQuestion().getAnswers().iterator();
                dr.setAnswer(it1.next());
                // dr.setId(new DemographicResponse.Identifier(ret.getId(),
                // dr.getQuestion().getId(), dr.getAnswer().getId()));
                responses.add(dr);
            } else if (da.getQuestion().isFreeForm()) {
                dr = new DemographicResponse();
                dr.setUser(u);
                dr.setQuestion(da.getQuestion());
                dr.setAnswer(DAOUtil.getFactory().getDemographicAnswerDAO().findFreeForm(da.getQuestion()));
                dr.setResponse("hell");
                // dr.setId(new DemographicResponse.Identifier(ret.getId(),
                // dr.getQuestion().getId(), dr.getAnswer().getId()));
                responses.add(dr);
            }
        }
        return responses;
    }

    /**
     * Accuracy test for {@link UserDAOHibernate#saveOrUpdate(User)} method. Tries to create a new User and verifies it.
     * @throws Exception
     *             to JUnit
     */
    public void testSaveOrUpdate3() throws Exception {
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("delete from User where handle = 'handle'");
        query.executeUpdate();
        tearDown();
        setUp();

        User u = new User();
        u.setHandle("handle");

        Address address = new Address();
        address.setAddress1("line1");

        State state = DAOUtil.getFactory().getStateDAO().find("ME");
        address.setState(state);
        u.addAddress(address);

        List < DemographicResponse > responses = getDemographicResponses(u);
        u.setTransientResponses(responses);
        instance.saveOrUpdate(u);

        tearDown();
        setUp();

        User user = instance.find("handle", true);
        assertEquals("Error creating new user", responses.size(), user.getDemographicResponses().size());

        // delete all the entities that we created before
        HibernateUtils.getSession().delete(user);
        HibernateUtils.getSession().delete(address);
    }

    /**
     * Failure test case for {@link UserDAOHibernate#saveOrUpdate(User)} method. Passes a null and expects an
     * IllegalArgumentException.
     */
    public void testSaveOrUpdateFailure() {
        try {
            instance.saveOrUpdate(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String)} method. Passes a valid email address and expects a
     * valid User instance.
     */
    public void testFindString() {
        User user = instance.find("foo@fooonyou.com");
        assertNotNull("Error getting user by email", user);
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String)} method. Passes a null and expects an
     * IllegalArgumentException.
     */
    public void testFindStringFailure() {
        try {
            instance.find((String) null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#find(String)} method. Passes an empty and expects an
     * IllegalArgumentException.
     */
    public void testFindStringFailure1() {
        try {
            instance.find("     ");
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserDAOHibernate#canChangeHandle(String)} method. Passes a valid value and expects
     * false.
     */
    public void testCanChangeHandle() {
        assertFalse("handle cannot be changed", instance.canChangeHandle("handle"));
    }

    /**
     * Failure test case for {@link UserDAOHibernate#canChangeHandle(String)} method. Passes a null and expects an
     * IllegalArgumentException.
     */
    public void testCanChangeHandleFailure() {
        try {
            instance.canChangeHandle(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserDAOHibernate#canChangeHandle(String)} method. Passes an empty string and expects
     * an IllegalArgumentException.
     */
    public void testCanChangeHandleFailure1() {
        try {
            instance.canChangeHandle("     ");
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}
