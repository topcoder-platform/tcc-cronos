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
 *   &lt;bean id=&quot;memberContributionPointsService&quot;
 *         class=&quot;com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImpl&quot;&gt;
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
 * // Get ProjectContestCPConfigService from Spring context
 * ProjectContestCPConfigService projectContestCPConfigService = (ProjectContestCPConfigService) applicationContext
 *     .getBean(&quot;projectContestCPConfigService&quot;);
 *
 * ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
 * projectContestCPConfig.setContestId(1);
 * projectContestCPConfig.setCpRate(2);
 *
 * OriginalPayment originalPayment = new OriginalPayment();
 * originalPayment.setContestId(1);
 * originalPayment.setPrize1st(500);
 * originalPayment.setPrize2nd(250);
 * originalPayment.setPrize3rd(100);
 * originalPayment.setPrize4th(100);
 * originalPayment.setPrize5th(100);
 * originalPayment.setPrizeCopilot(150);
 * originalPayment.setPrizeMilestone(50);
 * originalPayment.setSpecReviewCost(50);
 * originalPayment.setReviewCost(200);
 * projectContestCPConfig.setOriginalPayment(originalPayment);
 *
 * // Create the ProjectContestCPConfig entity
 * long projectContestCPConfigId = projectContestCPConfigService.create(projectContestCPConfig);
 *
 * // Get the ProjectContestCPConfig entity
 * projectContestCPConfig = projectContestCPConfigService.get(projectContestCPConfigId);
 *
 * // Update the ProjectContestCPConfig entity
 * projectContestCPConfig.setCpRate(3);
 * projectContestCPConfig.getOriginalPayment().setPrize1st(700);
 * projectContestCPConfig.getOriginalPayment().setPrize2nd(350);
 * projectContestCPConfigService.update(projectContestCPConfig);
 *
 * // Delete the ProjectContestCPConfig entity
 * projectContestCPConfigService.delete(projectContestCPConfigId);
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
