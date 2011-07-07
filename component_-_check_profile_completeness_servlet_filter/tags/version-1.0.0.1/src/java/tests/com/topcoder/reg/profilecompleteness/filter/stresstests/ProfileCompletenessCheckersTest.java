/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.stresstests;

import static com.topcoder.reg.profilecompleteness.filter.TestHelper.toSet;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.BaseProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.CompetitionProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.CopilotProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.DirectProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.ForumProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.JiraProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.OnlineReviewProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.SVNProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.StudioProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.VMProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.WikiProfileCompletenessChecker;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.model.UserSecurityKey;

/**
 * <p>
 * Stress test cases for this component.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class ProfileCompletenessCheckersTest extends TestCase {
    /**
     * Represents the <code>ProfileCompletenessChecker</code> instance on which to perform tests.
     */
    private BaseProfileCompletenessChecker checker;

    /**
     * Represents the <code>Log</code> instance used for tests.
     */
    private Log log;

    /**
     * Represents the <code>User</code> instance used for tests.
     */
    private User user;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        log = new BasicLogFactory(System.err).createLog("test");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to jUnit.
     */
    @After
    public void tearDown() throws Exception {
        log = null;
        user = null;
        checker = null;
    }

    /**
     * The test suite.
     * @return the test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProfileCompletenessCheckersTest.class);
    }

    /**
     * Stress test for CompetitionProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testCompetitionProfileCompletenessChecker_Competitor() throws Exception {
        checker = new CompetitionProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with CompetitionProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for CopilotProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testCopilotProfileCompletenessChecker_Competitor() throws Exception {
        checker = new CopilotProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with CopilotProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for DirectProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testDirectProfileCompletenessChecker_Customer() throws Exception {
        checker = new DirectProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with DirectProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for ForumProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testForumProfileCompletenessChecker_Competitor() throws Exception {
        checker = new ForumProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with ForumProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for ForumProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testForumProfileCompletenessChecker_Customer() throws Exception {
        checker = new ForumProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with ForumProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for JiraProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testJiraProfileCompletenessChecker_Competitor() throws Exception {
        checker = new JiraProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with JiraProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for JiraProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testJiraProfileCompletenessChecker_Customer() throws Exception {
        checker = new JiraProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with JiraProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for OnlineReviewProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testOnlineReviewProfileCompletenessChecker_Competitor() throws Exception {
        checker = new OnlineReviewProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with OnlineReviewProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for OnlineReviewProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testOnlineReviewProfileCompletenessChecker_Customer() throws Exception {
        checker = new OnlineReviewProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with OnlineReviewProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for StudioProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testStudioProfileCompletenessChecker_Competitor() throws Exception {
        checker = new StudioProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with StudioProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for SVNProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testSVNProfileCompletenessChecker_Competitor() throws Exception {
        checker = new SVNProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with SVNProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for SVNProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testSVNProfileCompletenessChecker_Customer() throws Exception {
        checker = new SVNProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with SVNProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for VMProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testVMProfileCompletenessChecker_Competitor() throws Exception {
        checker = new VMProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with VMProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for VMProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testVMProfileCompletenessChecker_Customer() throws Exception {
        checker = new VMProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with VMProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Stress test for WikiProfileCompletenessChecker with 1000 competitors.
     * @throws Exception to JUnit
     */
    @Test
    public void testWikiProfileCompletenessChecker_Competitor() throws Exception {
        checker = new WikiProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.COMPETITION_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 competitor profiles with WikiProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");

    }

    /**
     * Stress test for WikiProfileCompletenessChecker with 1000 customers.
     * @throws Exception to JUnit
     */
    @Test
    public void testWikiProfileCompletenessChecker_Customer() throws Exception {
        checker = new WikiProfileCompletenessChecker();
        checker.setLog(log);
        user = createUser(RegistrationType.CORPORATE_ID);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertTrue("Profile is expected to be complete.", checker.isProfileComplete(user));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Checking 1000 customer profiles with WikiProfileCompletenessChecker cost : "
                + (endTime - startTime) + " millis");
    }

    /**
     * Create an instance of User.
     * @param registrationTypeId the registration type ID
     * @return an instance of User
     */
    private static User createUser(final int registrationTypeId) {
        User user = new User();

        // Sets name.
        user.setFirstName("first");
        user.setLastName("last");

        // Sets phone numbers.
        Phone phone = new Phone();
        phone.setNumber("123456789");
        Set<Phone> phoneNumbers = new HashSet<Phone>();
        phoneNumbers.add(phone);
        user.setPhoneNumbers(phoneNumbers);

        // Sets addresses.
        Address address = new Address();
        address.setAddress1("address1");
        address.setCity("city");

        Country country = new Country();
        country.setName("US");
        address.setCountry(country);

        Set<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        user.setAddresses(addresses);

        // Sets security groups
        UserGroup group = new UserGroup();
        group.setSecurityGroup(new MockSecurityGroup(registrationTypeId));
        Set<UserGroup> groups = new HashSet<UserGroup>();
        groups.add(group);
        user.setSecurityGroups(groups);

        // Sets security key
        UserSecurityKey key = new UserSecurityKey();
        key.setSecurityKey("security");
        user.setUserSecurityKey(key);

        // Sets time zone
        user.setTimeZone(new TimeZone());

        if (registrationTypeId == RegistrationType.CORPORATE_ID) {
            user.setTimeZone(new TimeZone());
            Company company = new Company();
            company.setName("TC");
            Contact contact = new Contact();
            contact.setCompany(company);
            user.setContact(contact);

            DemographicResponse response = new DemographicResponse();
            response.setResponse("res");
            DemographicQuestion question = new DemographicQuestion();
            question.setId(1L);
            response.setQuestion(question);
            user.setDemographicResponses(toSet(response));
        } else if (registrationTypeId == RegistrationType.COMPETITION_ID) {
            CoderType coderType = new CoderType();
            coderType.setId(CoderType.PROFESSIONAL);

            Coder coder = new Coder();
            coder.setCoderType(coderType);
            coder.setCompCountry(country);

            user.setCoder(coder);
        }
        return user;
    }

    /**
     * Mock RegistrationType for stress tests.
     * @author Thinfox
     * @version 1.0
     */
    private static class MockRegistrationType extends RegistrationType {
        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The registration type ID.
         */
        private Integer id;

        /**
         * Creates an instance of RegistrationType.
         * @param id the registration type ID
         */
        public MockRegistrationType(Integer id) {
            this.id = id;
        }

        /**
         * Overrides the getId method.
         * @return the registration type ID
         */
        @Override
        public Integer getId() {
            return id;
        }
    };

    /**
     * Mock SecurityGroup for stress tests.
     * @author Thinfox
     * @version 1.0
     */
    private static class MockSecurityGroup extends SecurityGroup {
        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The registration types.
         */
        private Set<RegistrationType> registrationTypes;

        /**
         * Creates an instance of RegistrationType.
         * @param registrationTypeId the registration type ID
         */
        public MockSecurityGroup(Integer registrationTypeId) {
            registrationTypes = new TreeSet<RegistrationType>();
            registrationTypes.add(new MockRegistrationType(registrationTypeId));
        }

        /**
         * Overrides the getRegistrationTypes method.
         * @return the registration type ID
         */
        @Override
        public Set<RegistrationType> getRegistrationTypes() {
            return registrationTypes;
        }
    };
}
