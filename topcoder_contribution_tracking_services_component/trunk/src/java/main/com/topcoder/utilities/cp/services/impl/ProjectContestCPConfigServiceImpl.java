/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;

/**
 * <p>
 * This class is an implementation of ProjectContestCPConfigService. It uses Hibernate session to access entities in
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
 *
 *   &lt;bean id=&quot;dataSource&quot;
 *         class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;&gt;
 *     &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *     &lt;property name=&quot;url&quot;
 *         value=&quot;jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&quot; /&gt;
 *     &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *     &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;sessionFactory&quot;
 *         class=&quot;org.springframework.orm.hibernate3.LocalSessionFactoryBean&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *     &lt;property name=&quot;mappingResources&quot;&gt;
 *       &lt;list&gt;
 *         &lt;value&gt;mapping/DirectProjectCPConfig.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/MemberContributionPoints.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/ProjectContestCPConfig.hbm.xml&lt;/value&gt;
 *       &lt;/list&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;hibernateProperties&quot;&gt;
 *       &lt;props&gt;
 *         &lt;prop key=&quot;hibernate.dialect&quot;&gt;org.hibernate.dialect.InformixDialect&lt;/prop&gt;
 *         &lt;prop key=&quot;show_sql&quot;&gt;true&lt;/prop&gt;
 *       &lt;/props&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;memberContributionPointsService&quot;
 *         class=&quot;com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImpl&quot;&gt;
 *     &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot;/&gt;
 *     &lt;property name=&quot;loggerName&quot; value=&quot;com.topcoder.utilities.cp.services.imp.logger&quot;/&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;!-- transaction management configuration --&gt;
 *
 *   &lt;!-- enable the configuration of transactional behavior based on annotations --&gt;
 *   &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot;/&gt;
 *
 *   &lt;bean id=&quot;transactionManager&quot;
 *         class=&quot;org.springframework.jdbc.datasource.DataSourceTransactionManager&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *   &lt;/bean&gt;
 *
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
public class ProjectContestCPConfigServiceImpl extends BaseService implements ProjectContestCPConfigService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectContestCPConfigServiceImpl.class.getName();

    /**
     * Creates an instance of ProjectContestCPConfigServiceImpl.
     */
    public ProjectContestCPConfigServiceImpl() {
        // Empty
    }

    /**
     * Creates the ProjectContestCPConfig entity.
     *
     * @param config
     *            the ProjectContestCPConfig entity.
     *
     * @return the ID of the created ProjectContestCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional
    public long create(ProjectContestCPConfig config) throws ContributionServiceException {
        String signature = CLASS_NAME + ".create(ProjectContestCPConfig config)";

        return createEntity(signature, config, "config", config == null ? 0L : config.getContestId(),
            "config#contestId");
    }

    /**
     * Updates the ProjectContestCPConfig entity.
     *
     * @param config
     *            the ProjectContestCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#contestId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void update(ProjectContestCPConfig config) throws ContributionServiceException {
        String signature = CLASS_NAME + ".update(ProjectContestCPConfig config)";

        updateEntity(signature, config, "config", config == null ? 0L : config.getContestId(),
            "config#contestId");
    }

    /**
     * Deletes the ProjectContestCPConfig entity.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void delete(long contestId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".delete(long contestId)";

        deleteEntity(signature, ProjectContestCPConfig.class, contestId, "contestId");
    }

    /**
     * Gets the ProjectContestCPConfig entity by id.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @return the ProjectContestCPConfig entity or <code>null</code> if the entity doesn't exist.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional(readOnly = true)
    public ProjectContestCPConfig get(long contestId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".get(long contestId)";

        return getEntity(signature, ProjectContestCPConfig.class, contestId, "contestId");
    }
}
