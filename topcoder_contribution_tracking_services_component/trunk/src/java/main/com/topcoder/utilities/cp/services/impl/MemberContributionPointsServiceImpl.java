/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Log;
import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;

/**
 * <p>
 * This class is an implementation of MemberContributionPointsService. It uses Hibernate session to access entities in
 * persistence. This class uses Logging Wrapper logger to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xmlns:util=&quot;http://www.springframework.org/schema/util&quot;
 *        xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
 *        xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
 *        http://www.springframework.org/schema/util
 *        http://www.springframework.org/schema/util/spring-util-3.1.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-3.1.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-3.1.xsd&quot;&gt;
 *   &lt;bean id=&quot;dataSource&quot;
 *         class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;&gt;
 *     &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *     &lt;property name=&quot;url&quot;
 *         value=&quot;jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&quot; /&gt;
 *     &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *     &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *   &lt;/bean&gt;
 *   &lt;bean id=&quot;sessionFactory&quot;
 *         class=&quot;org.springframework.orm.hibernate3.LocalSessionFactoryBean&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *     &lt;property name=&quot;mappingResources&quot;&gt;
 *       &lt;list&gt;
 *         &lt;value&gt;mapping/DirectProjectCPConfig.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/MemberContributionPoints.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/ProjectContestCPConfig.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/OriginalPayment.hbm.xml&lt;/value&gt;
 *       &lt;/list&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;hibernateProperties&quot;&gt;
 *       &lt;props&gt;
 *         &lt;prop key=&quot;hibernate.dialect&quot;&gt;org.hibernate.dialect.InformixDialect&lt;/prop&gt;
 *         &lt;prop key=&quot;show_sql&quot;&gt;true&lt;/prop&gt;
 *       &lt;/props&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 *   &lt;bean id=&quot;projectContestCPConfigService&quot;
 *         class=&quot;com.topcoder.utilities.cp.services.impl.ProjectContestCPConfigServiceImpl&quot;&gt;
 *     &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot;/&gt;
 *     &lt;property name=&quot;loggerName&quot; value=&quot;com.topcoder.utilities.cp.services.imp.logger&quot;/&gt;
 *   &lt;/bean&gt;
 *   &lt;!-- transaction management configuration --&gt;
 *   &lt;!-- enable the configuration of transactional behavior based on annotations --&gt;
 *   &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot;/&gt;
 *   &lt;bean id=&quot;transactionManager&quot;
 *         class=&quot;org.springframework.jdbc.datasource.DataSourceTransactionManager&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *   &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Load Spring XML file
 * ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(&quot;beans.xml&quot;);
 *
 * // Get MemberContributionPointsService from Spring context
 * MemberContributionPointsService memberContributionPointsService =
 *     (MemberContributionPointsService) applicationContext.getBean(&quot;memberContributionPointsService&quot;);
 *
 * MemberContributionPoints memberContributionPoints1 = new MemberContributionPoints();
 * memberContributionPoints1.setUserId(1);
 * memberContributionPoints1.setContestId(1);
 * memberContributionPoints1.setContributionPoints(10);
 * memberContributionPoints1.setContributionType(&quot;ct1&quot;);
 *
 * // Create the MemberContributionPoints entity
 * long memberContributionPointsId1 = memberContributionPointsService.create(memberContributionPoints1);
 *
 * MemberContributionPoints memberContributionPoints2 = new MemberContributionPoints();
 * memberContributionPoints2.setUserId(1);
 * memberContributionPoints2.setContestId(2);
 * memberContributionPoints2.setContributionPoints(20);
 * memberContributionPoints2.setContributionType(&quot;ct1&quot;);
 *
 * // Create the MemberContributionPoints entity
 * long memberContributionPointsId2 = memberContributionPointsService.create(memberContributionPoints2);
 *
 * MemberContributionPoints memberContributionPoints3 = new MemberContributionPoints();
 * memberContributionPoints3.setUserId(2);
 * memberContributionPoints3.setContestId(1);
 * memberContributionPoints3.setContributionPoints(30);
 * memberContributionPoints3.setContributionType(&quot;ct1&quot;);
 *
 * // Create the MemberContributionPoints entity
 * long memberContributionPointsId3 = memberContributionPointsService.create(memberContributionPoints3);
 *
 * // Get the MemberContributionPoints entities by user and contest
 * List&lt;MemberContributionPoints&gt; memberContributionPointsList1 =
 *     memberContributionPointsService.getByUserAndContest(1, 1);
 *
 * // Get the MemberContributionPoints entities by user
 * List&lt;MemberContributionPoints&gt; memberContributionPointsList2 =
 *     memberContributionPointsService.getByUserId(1);
 *
 * // Get the MemberContributionPoints entities by direct project
 * List&lt;MemberContributionPoints&gt; memberContributionPointsList3 =
 *     memberContributionPointsService.getByProjectId(1);
 *
 * // Get the MemberContributionPoints entities by contest
 * List&lt;MemberContributionPoints&gt; memberContributionPointsList4 =
 *     memberContributionPointsService.getByContestId(1);
 *
 * memberContributionPoints1 = memberContributionPointsList1.get(0);
 *
 * // Update the MemberContributionPoints entity
 * memberContributionPoints1.setContributionPoints(15);
 * memberContributionPointsService.update(memberContributionPoints1);
 *
 * // Delete the MemberContributionPoints entity
 * memberContributionPointsService.delete(memberContributionPointsId1);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is thread safe when the class is initialized by Spring right after
 * construction and its parameters are never changed after that, all entities passed to this class are used by the
 * caller in thread safe manner (accessed from a single thread only). This class assumes that transactions are managed
 * by Spring.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class MemberContributionPointsServiceImpl extends BaseService implements MemberContributionPointsService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = MemberContributionPointsServiceImpl.class.getName();

    /**
     * Creates an instance of MemberContributionPointsServiceImpl.
     */
    public MemberContributionPointsServiceImpl() {
        // Empty
    }

    /**
     * Gets the MemberContributionPoints entities by user and contest.
     *
     * @param contestId
     *            the ID of the contest.
     * @param userId
     *            the ID of the user.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if userId or contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional(readOnly = true)
    public List<MemberContributionPoints> getByUserAndContest(long userId, long contestId)
        throws ContributionServiceException {
        String signature = CLASS_NAME + ".getByUserAndContest(long userId, long contestId)";

        return getMemberContributionPointsList(signature, getLog(),
            new String[] {"userId", "contestId"},
            new Long[] {userId, contestId}, "getByUserAndContest");
    }

    /**
     * Gets the MemberContributionPoints entities by user.
     *
     * @param userId
     *            the ID of the user.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional(readOnly = true)
    public List<MemberContributionPoints> getByUserId(long userId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".getByUserId(long userId)";

        return getMemberContributionPointsList(signature, getLog(),
            new String[] {"userId"},
            new Long[] {userId}, "getByUserId");
    }

    /**
     * Gets the MemberContributionPoints entities by direct project.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional(readOnly = true)
    public List<MemberContributionPoints> getByProjectId(long directProjectId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".getByProjectId(long directProjectId)";

        return getMemberContributionPointsList(signature, getLog(),
            new String[] {"directProjectId"},
            new Long[] {directProjectId}, "getByProjectId");
    }

    /**
     * Gets the MemberContributionPoints entities by contest.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional(readOnly = true)
    public List<MemberContributionPoints> getByContestId(long contestId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".getByContestId(long contestId)";

        return getMemberContributionPointsList(signature, getLog(),
            new String[] {"contestId"},
            new Long[] {contestId}, "getByContestId");
    }

    /**
     * Creates the MemberContributionPoints entity.
     *
     * @param memberContributionPoints
     *            the MemberContributionPoints entity.
     *
     * @return the ID of the created MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if memberContributionPoints is null or memberContributionPoints#id is positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional
    public long create(MemberContributionPoints memberContributionPoints) throws ContributionServiceException {
        String signature = CLASS_NAME + ".create(MemberContributionPoints memberContributionPoints)";

        return createEntity(signature, memberContributionPoints, "memberContributionPoints",
            memberContributionPoints == null ? 0L : memberContributionPoints.getId(), "memberContributionPoints#id");
    }

    /**
     * Updates the MemberContributionPoints entity.
     *
     * @param memberContributionPoints
     *            the MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if memberContributionPoints is null or memberContributionPoints#id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void update(MemberContributionPoints memberContributionPoints) throws ContributionServiceException {
        String signature = CLASS_NAME + ".update(MemberContributionPoints memberContributionPoints))";

        updateEntity(signature, memberContributionPoints, "memberContributionPoints",
            memberContributionPoints == null ? 0L : memberContributionPoints.getId(), "memberContributionPoints#id");
    }

    /**
     * Deletes the MemberContributionPoints entity.
     *
     * @param id
     *            the ID of the MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void delete(long id) throws ContributionServiceException {
        String signature = CLASS_NAME + ".delete(long id)";

        deleteEntity(signature, MemberContributionPoints.class, id, "id");
    }

    /**
     * Gets the MemberContributionPoints entities.
     *
     * @param signature
     *            the signature.
     * @param log
     *            the logger.
     * @param names
     *            the parameter names.
     * @param values
     *            the parameter values.
     * @param namedQuery
     *            the named query.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if any parameter is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    private List<MemberContributionPoints> getMemberContributionPointsList(String signature, Log log, String[] names,
        Long[] values, String namedQuery) throws ContributionServiceException {
        LoggingWrapperUtility.logEntrance(log, signature, names, values);

        try {
            for (int i = 0; i < names.length; i++) {
                ParameterCheckUtility.checkPositive(values[i], names[i]);
            }

            Query query = getSessionFactory().getCurrentSession().getNamedQuery(namedQuery);

            for (int i = 0; i < names.length; i++) {
                query.setLong(names[i], values[i]);
            }
            List<MemberContributionPoints> result = query.list();
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to get the MemberContributionPoints entities.", e));
        }
    }
}
