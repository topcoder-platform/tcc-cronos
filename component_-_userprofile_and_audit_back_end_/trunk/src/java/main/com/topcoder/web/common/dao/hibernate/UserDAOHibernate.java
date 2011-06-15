/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.web.common.dao.DAOUtil;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.UserSchoolDAO;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserSchool;
import com.topcoder.web.reg.Constants;

/**
 * <p>
 * This class is an implementation of UserDAO that manages User entities in persistence using Hibernate. Currently
 * change of handle is not allowed for all users, thus canChangeHandle() always returns false.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and thread safe when entities passed to it are used by the
 * caller in thread safe manner. It's assumed that transactions are managed externally.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong> <code>
 * <pre>
 * //instantiate the class
 * UserDAO userDAO = new UserDAOHibernate();
 *
 * // Get the User instance with given id
 *   User user = userDAO.find(id);
 *
 * // Save the updated User object
 *   userDAO.saveOrUpdate(user);
 *
 * // Get the User instance with given handle
 * User user = userDAO.find("handle");
 * </pre>
 * </code>
 * </p>
 * @author saarixx, kalc
 * @version 1.0
 */
public class UserDAOHibernate extends Base implements UserDAO {

    /**
     * <p>
     * Creates an instance of UserDAOHibernate.
     * </p>
     */
    public UserDAOHibernate() {
        super();
    }

    /**
     * <p>
     * Creates an instance of UserDAOHibernate using the given Hibernate session.
     * </p>
     * @param session
     *            the Hibernate session
     * @throws IllegalArgumentException
     *             if session is null
     */
    public UserDAOHibernate(Session session) {
        super(Helper.checkParam(session));
    }

    /**
     * <p>
     * Retrieves the user with the specified ID.
     * </p>
     * @param id
     *            the ID of the user to be retrieved
     * @return the user with the specified ID (null if not found)
     * @throws IllegalArgumentException
     *             if id is null
     */
    public User find(Long id) {
        ParameterCheckUtility.checkNotNull(id, "id");
        return (User) super.find(User.class, id);
    }

    /**
     * <p>
     * Retrieves the user with the specified user name. This method matches both active and inactive users.
     * </p>
     * @param userName
     *            the user name (handle)
     * @param ignoreCase
     *            true if case insensitive comparison must be used, false otherwise
     * @return the matched user (null if not found)
     * @throws IllegalArgumentException
     *             if userName is null/empty
     */
    public User find(String userName, boolean ignoreCase) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(userName, "userName");
        return find(userName, ignoreCase, false);
    }

    /**
     * <p>
     * Retrieves the user with the specified user name. If activeRequired is true, only active user is matched.
     * </p>
     * @param userName
     *            the user name (handle)
     * @param ignoreCase
     *            true if case insensitive comparison must be used, false otherwise
     * @param activeRequired
     *            true if only active user should be retrieved, false if both active and inactive users should be
     *            checked
     * @return the matched user (null if not found)
     * @throws IllegalArgumentException
     *             if userName is null/empty
     */
    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(userName, "userName");
        StringBuilder query = new StringBuilder();
        query.append("FROM User WHERE ");
        if (ignoreCase) {
            query.append("handle_lower ");
        } else {
            query.append("handle ");
        }
        query.append(" = ? ");
        if (activeRequired) {
            query.append(" AND status = ?");
        }
        Query q = session.createQuery(query.toString());
        if (ignoreCase) {
            q.setString(0, userName.toLowerCase());
        } else {
            q.setString(0, userName);
        }
        if (activeRequired) {
            q.setString(1, String.valueOf(Constants.ACTIVE_STATI[1]));
        }
        return (User) q.uniqueResult();
    }

    /**
     * <p>
     * Retrieves the users with the specified handle, first name, last name and primary email address. All parameters
     * are optional. Case insensitive comparison is used. If none found, an empty list is returned.
     * </p>
     * @param handle
     *            the handle (null/empty if should be ignored)
     * @param firstName
     *            the first name (null/empty if should be ignored)
     * @param lastName
     *            the last name (null/empty if should be ignored)
     * @param email
     *            the primary email address (null/empty if should be ignored)
     * @return the retrieved users that meet the specified criteria (not null, doesn't contain null)
     */
    public List < User > find(String handle, String firstName, String lastName, String email) {
        Criteria crit = session.createCriteria(User.class);
        if (handle != null && handle.length() > 0) {
            crit.add(Restrictions.sqlRestriction("lower({alias}.handle)=lower(?)", handle, Hibernate.STRING));
        }

        if (email != null && email.length() > 0) {
            crit.createCriteria("emailAddresses").add(
                    Restrictions.sqlRestriction("lower({alias}.address)=lower(?)", email, Hibernate.STRING)).add(
                    Restrictions.eq("primary", Boolean.TRUE));
        }

        if ((firstName != null && firstName.length() > 0 || (lastName != null && lastName.length() > 0))) {
            crit = crit.createCriteria("userProfile");
        }

        if (firstName != null && firstName.length() > 0) {
            crit.add(Restrictions.sqlRestriction("lower({alias}.first_name)=lower(?)", firstName, Hibernate.STRING));
        }

        if (lastName != null && lastName.length() > 0) {
            crit.add(Restrictions.sqlRestriction("lower({alias}.last_name)=lower(?)", lastName, Hibernate.STRING));
        }

        return crit.list();
    }

    /**
     * <p>
     * Saves or update the given user.
     * </p>
     * @param user
     *            the user entity to be saved to updated
     * @throws IllegalArgumentException
     *             if user is null
     */
    public void saveOrUpdate(User user) {
        ParameterCheckUtility.checkNotNull(user, "user");
        UserSchoolDAO usd = DAOUtil.getFactory().getUserSchoolDAO();
        if (user.getId() == null) {
            DemographicResponse tr;
            DemographicResponse dr;
            for (Iterator < DemographicResponse > it = user.getTransientResponses().iterator(); it.hasNext();) {
                tr = it.next();
                dr = new DemographicResponse();
                dr.setAnswer(tr.getAnswer());
                dr.setQuestion(tr.getQuestion());
                dr.setResponse(tr.getResponse());
                dr.setUser(user);
                dr.getId().setAnswer(tr.getAnswer());
                dr.getId().setQuestion(tr.getQuestion());
                dr.getId().setUser(user);
                user.addDemographicResponse(dr);
            }
            super.saveOrUpdate(user);

            for (UserSchool us : user.getSchools()) {
                usd.saveOrUpdate(us);
            }

        } else {
            // don't need to worry about anything that is already in the db.
            DemographicResponse temp;

            for (Iterator < DemographicResponse > it = user.getDemographicResponses().iterator(); it.hasNext();) {
                temp = it.next();
                if (temp.getQuestion().isFreeForm() || temp.getQuestion().isSingleSelect()) {
                    user.removeTransientResponse(temp);
                }

            }

            DemographicResponse dr;
            DemographicResponse badResponse;
            Set < DemographicQuestion > processedQuestions = new HashSet < DemographicQuestion >();
            DemographicResponse goodResponse;
            for (Iterator < DemographicResponse > it = user.getTransientResponses().iterator(); it.hasNext();) {
                dr = it.next();
                if (dr.getQuestion().isSingleSelect()) {
                    badResponse = findResponse(user.getDemographicResponses(), dr.getQuestion());
                    if (badResponse != null) {
                        user.removeDemographicResponse(badResponse);
                        badResponse.setUser(null);
                    }
                    dr.setUser(user);
                    dr.getId().setUser(user);
                    dr.getId().setQuestion(dr.getQuestion());
                    dr.getId().setAnswer(dr.getAnswer());
                    user.addDemographicResponse(dr);

                } else if (dr.getQuestion().isFreeForm()) {
                    badResponse = findResponse(user.getDemographicResponses(), dr.getQuestion());
                    if (badResponse == null) {
                        dr.setUser(user);
                        dr.getId().setUser(user);
                        dr.getId().setQuestion(dr.getQuestion());
                        dr.getId().setAnswer(dr.getAnswer());
                        user.addDemographicResponse(dr);
                    } else {
                        badResponse.setResponse(dr.getResponse());
                    }
                } else if (!processedQuestions.contains(dr.getQuestion())) {

                    Set < DemographicResponse > currResponses = findResponses(user.getDemographicResponses(), dr
                            .getQuestion());
                    Set < DemographicResponse > newResponses = findResponses(user.getTransientResponses(), dr
                            .getQuestion());
                    // remove any responses from the database that the user hasn't chosen
                    for (Iterator < DemographicResponse > itr = currResponses.iterator(); itr.hasNext();) {
                        badResponse = itr.next();
                        if (!newResponses.contains(badResponse)) {
                            user.removeDemographicResponse(badResponse);
                            badResponse.setUser(null);
                        }
                    }
                    // add any responses to the db that the user has chosen that are not current there
                    for (Iterator < DemographicResponse > itr = newResponses.iterator(); itr.hasNext();) {
                        goodResponse = itr.next();
                        if (!currResponses.contains(goodResponse)) {
                            goodResponse.setUser(user);
                            goodResponse.getId().setUser(user);
                            goodResponse.getId().setQuestion(goodResponse.getQuestion());
                            goodResponse.getId().setAnswer(goodResponse.getAnswer());
                            user.addDemographicResponse(goodResponse);
                        }
                    }
                    processedQuestions.add(dr.getQuestion());
                }
            }
            super.saveOrUpdate(user);
            // can't figure out how to map this so that the changes get propegated through user, so doing it explicitly
            for (UserSchool us : user.getSchools()) {
                usd.saveOrUpdate(us);
            }

        }
    }

    /**
     * <p>
     * Finds the first response for the specified demographic question.
     * </p>
     * @param responses
     *            the responses
     * @param question
     *            the demographic question
     * @return the first response for the specified demographic question (null if not found)
     */
    private DemographicResponse findResponse(Collection < DemographicResponse > responses,
            DemographicQuestion question) {
        boolean found = false;
        DemographicResponse ret = null;
        for (Iterator < DemographicResponse > it = responses.iterator(); it.hasNext() && !found;) {
            ret = it.next();
            found = ret.getQuestion().equals(question);
        }
        if (found) {
            return ret;
        } else {
            return null;
        }
    }

    /**
     * <p>
     * Finds all responses for the specified demographic question. If none found, this method returns an empty set.
     * </p>
     * <p>
     * <strong>Changes:</strong>
     * <li>Renamed parameter "q" to "question".</li>
     * <li>Specified generic parameter for "responses" parameter type.</li>
     * <li>Specified generic parameter for the return type.</li>
     * <li>Specified generic parameters for all generic types in the code.</li>
     * <li>Removed log.debug() calls.</li>
     * </p>
     * @param responses
     *            the responses
     * @param question
     *            the demographic question
     * @return the list with all responses for the specified demographic question (not null, doesn't contain null)
     */
    private Set < DemographicResponse > findResponses(Collection < DemographicResponse > responses,
            DemographicQuestion question) {
        HashSet < DemographicResponse > ret = new HashSet < DemographicResponse >();
        DemographicResponse response;
        for (Iterator < DemographicResponse > it = responses.iterator(); it.hasNext();) {
            response = it.next();
            if (response.getQuestion().equals(question)) {
                ret.add(response);
            }
        }
        return ret;
    }

    /**
     * <p>
     * Retrieves the user with the specified primary email address.
     * </p>
     * @param email
     *            the primary email address of the user
     * @return the user with the specified email (null if not found)
     * @throws IllegalArgumentException
     *             if email is null/empty
     */
    public User find(String email) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(email, "email");
        List < User > users = find(null, null, null, email);
        return (users.isEmpty() ? null : users.get(0));
    }

    /**
     * <p>
     * Checks whether the handle of the user can be changed. Currently change of handle is not allowed for all users,
     * thus this method always returns false.
     * </p>
     * @param handle
     *            the current handle of the user
     * @return true if the handle of the user can be changed, false otherwise (this implementation always returns false)
     * @throws IllegalArgumentException
     *             if handle is null/empty
     */
    public boolean canChangeHandle(String handle) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle");
        return false;
    }
}
