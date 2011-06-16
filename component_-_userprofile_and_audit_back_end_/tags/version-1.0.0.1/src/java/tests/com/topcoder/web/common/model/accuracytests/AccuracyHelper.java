/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.reg.Constants;

/**
 * <p>
 * This is helper class for Accuracy tests.
 * </p>
 * @author sokol
 * @version 1.0
 */
public final class AccuracyHelper {

    /**
     * <p>
     * Represents HQL query for deleting emails.
     * </p>
     */
    private static final String HQL_DELETE_FROM_EMAILS = "delete from Email e where address like '%"
            + AccuracyHelper.EMAIL_ADDRESS + "%'";

    /**
     * <p>
     * Represents SQL query for deleting users.
     * </p>
     */
    private static final String HQL_DELETE_FROM_USER = "delete from User u where u.handle like '%"
            + AccuracyHelper.HANDLE + "%'";

    /**
     * <p>
     * Represents HQL query for deleting demographic responses.
     * </p>
     */
    private static final String HQL_DELETE_FROM_DEMOGRAPHIC_RESPONSE = "delete from DemographicResponse";

    /**
     * <p>
     * Represents HQL query for deleting audits.
     * </p>
     */
    private static final String HQL_DELETE_AUDIT = "delete from AuditRecord ar where ar.handle like '%"
            + AccuracyHelper.HANDLE + "%'";

    /**
     * <p>
     * Represents the User handle name constant.
     * </p>
     */
    static final String HANDLE = "Accuracy";

    /**
     * <p>
     * Represents user email address constant.
     * </p>
     */
    static final String EMAIL_ADDRESS = "email@example.org";

    /**
     * <p>
     * Represents audit operation type constant.
     * </p>
     */
    static final String OPERATION_TYPE = "delete";

    /**
     * <p>
     * Represents active status code.
     * </p>
     */
    static final Character ACTIVE_STATUS = Constants.ACTIVE_STATI[1];

    /**
     * <p>
     * Represents datasource name for accuracy tests.
     * </p>
     */
    static final String ACCURACY_DATASOURCE = "userds";

    /**
     * <p>
     * Prevents from instantiating.
     * </p>
     */
    private AccuracyHelper() {
    }

    /**
     * <p>
     * Creates User instance in persistence.
     * </p>
     * @param handle
     *            the User handle
     * @param emailAddress
     *            the User email address
     * @param saveToDatabase
     *            the flag that shows whether to save to database or not
     * @return created User instance in persistence
     */
    static User createUserInPersistence(String handle, String emailAddress, boolean saveToDatabase) {
        User user = new User();
        user.setHandle(handle);
        user.setStatus('A');
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("accuracyFirst");
        userProfile.setMiddleName("accuracyMiddle");
        userProfile.setLastName("accuracyLast");
        user.setUserProfile(userProfile);
        userProfile.setUser(user);
        Set < Email > emailAddresses = new HashSet < Email >();
        Email email = new Email();
        email.setUser(user);
        email.setAddress(emailAddress);
        email.setPrimary(true);
        email.setEmailTypeId(Email.TYPE_ID_PRIMARY);
        email.setStatusId(Email.STATUS_ID_ACTIVE);
        emailAddresses.add(email);
        user.setEmailAddresses(emailAddresses);
        if (saveToDatabase) {
            HibernateUtils.begin();
            HibernateUtils.getSession().save(user);
            HibernateUtils.getSession().flush();
            HibernateUtils.commit();
        }
        return user;
    }

    /**
     * <p>
     * Ends Hibernate transaction with flush and commit.
     * </p>
     */
    static void endTransaction() {
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
    }

    /**
     * <p>
     * Creates AuditRecord instance and saved it into database.
     * </p>
     * @param handle
     *            the audit record handle
     * @param operationType
     *            the audit record operation type
     * @param saveToDatabase
     *            the flag that shows whether to save to database or not
     * @return created AuditRecord instance
     */
    static AuditRecord creatAuditRecord(String handle, String operationType, boolean saveToDatabase) {
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setHandle(handle);
        auditRecord.setOperationType(operationType);
        auditRecord.setIpAddress("127.0.0.1");
        auditRecord.setTimestamp(new Date());
        if (saveToDatabase) {
            HibernateUtils.begin();
            HibernateUtils.getSession().save(auditRecord);
            HibernateUtils.getSession().flush();
            HibernateUtils.commit();
        }
        return auditRecord;
    }

    /**
     * Creates and adds demographic response to given user.
     * @param user
     *            the user to add demographic response
     * @param responseText
     *            the response text
     * @return newly created DemographicResponse instance for given user
     */
    static DemographicResponse createDemographicResponse(User user, String responseText) {
        DemographicResponse response = new DemographicResponse();
        response.setUser(user);
        response.setResponse(responseText);
        DemographicQuestion question = new DemographicQuestion();
        response.setQuestion(question);
        question.setId(0L);
        question.setSelectable(DemographicQuestion.FREE_FORM);
        DemographicAnswer answer = new DemographicAnswer();
        response.setAnswer(answer);
        answer.setId(0L);
        List < DemographicResponse > transientResponses = new ArrayList < DemographicResponse >();
        transientResponses.add(response);
        user.setTransientResponses(transientResponses);
        return response;
    }

    /**
     * <p>
     * Clears database user, user_profile, mail and user_audit tables.
     * </p>
     */
    static void clearDatabase() {
        HibernateUtils.begin();
        HibernateUtils.getSession().createQuery(HQL_DELETE_FROM_DEMOGRAPHIC_RESPONSE).executeUpdate();
        HibernateUtils.getSession().createQuery(HQL_DELETE_FROM_USER).executeUpdate();
        HibernateUtils.getSession().createQuery(HQL_DELETE_FROM_EMAILS).executeUpdate();
        HibernateUtils.getSession().createQuery(HQL_DELETE_AUDIT).executeUpdate();
        AccuracyHelper.endTransaction();
    }

    /**
     * <p>
     * Retrieves User from database for given handle.
     * </p>
     * @param handle
     *            the user handle
     * @return retrieved User from database for given handle
     */
    static User getUser(String handle) {
        HibernateUtils.closeSession();
        HibernateUtils.begin();
        User result = (User) HibernateUtils.getSession().createQuery("from User u where u.handle = ?").setString(0,
                handle).uniqueResult();
        AccuracyHelper.endTransaction();
        return result;
    }

    /**
     * <p>
     * Retrieves UserProfile from database for given user id.
     * </p>
     * @param userId
     *            the user id
     * @return retrieved UserProfile from database for given user id
     */
    static UserProfile getUserProfile(long userId) {
        HibernateUtils.closeSession();
        HibernateUtils.begin();
        UserProfile result = (UserProfile) HibernateUtils.getSession().createQuery(
                "from UserProfile up where up.user.id = ?").setLong(0, userId).uniqueResult();
        AccuracyHelper.endTransaction();
        return result;
    }

    /**
     * <p>
     * Retrieves User property value from database for given user id.
     * </p>
     * @param userId
     *            the user id
     * @param property
     *            the property that should be retrieved from user table
     * @return retrieved User property value from database for given user id
     */
    static Object getUserProperty(long userId, String property) {
        HibernateUtils.closeSession();
        HibernateUtils.begin();
        Object result = HibernateUtils.getSession().createSQLQuery(
                "select " + property + " from user u where u.user_id = ?").setLong(0, userId).uniqueResult();
        AccuracyHelper.endTransaction();
        return result;
    }
}
