/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.entities.OriginalPayment;
import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class Demo extends BaseUnitTests {
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
     * Demo API usage of <code>DirectProjectCPConfigService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoDirectProjectCPConfigService() throws Exception {
        // Load Spring XML file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        // Get DirectProjectCPConfigService from Spring context
        DirectProjectCPConfigService directProjectCPConfigService =
            (DirectProjectCPConfigService) applicationContext.getBean("directProjectCPConfigService");

        DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
        directProjectCPConfig.setDirectProjectId(1);
        directProjectCPConfig.setUseCP(true);
        directProjectCPConfig.setAvailableImmediateBudget(1);

        // Create the DirectProjectCPConfig entity
        long directProjectCPConfigId = directProjectCPConfigService.create(directProjectCPConfig);

        // Get the DirectProjectCPConfig entity
        directProjectCPConfig = directProjectCPConfigService.get(directProjectCPConfigId);

        // Update the DirectProjectCPConfig entity
        directProjectCPConfig.setAvailableImmediateBudget(10);
        directProjectCPConfigService.update(directProjectCPConfig);

        // Get the available initial payments
        double availableInitialPayments = directProjectCPConfigService
            .getAvailableInitialPayments(directProjectCPConfigId);

        // Delete the DirectProjectCPConfig entity
        directProjectCPConfigService.delete(directProjectCPConfigId);
    }

    /**
     * <p>
     * Demo API usage of <code>ProjectContestCPConfigService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoProjectContestCPConfigService() throws Exception {
        // Load Spring XML file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        // Get ProjectContestCPConfigService from Spring context
        ProjectContestCPConfigService projectContestCPConfigService =
            (ProjectContestCPConfigService) applicationContext.getBean("projectContestCPConfigService");

        ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
        projectContestCPConfig.setContestId(1);
        projectContestCPConfig.setCpRate(2);

        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(1);
        originalPayment.setPrize1st(500);
        originalPayment.setPrize2nd(250);
        originalPayment.setPrize3rd(100);
        originalPayment.setPrize4th(100);
        originalPayment.setPrize5th(100);
        originalPayment.setPrizeCopilot(150);
        originalPayment.setPrizeMilestone(50);
        originalPayment.setSpecReviewCost(50);
        originalPayment.setReviewCost(200);
        projectContestCPConfig.setOriginalPayment(originalPayment);

        // Create the ProjectContestCPConfig entity
        long projectContestCPConfigId = projectContestCPConfigService.create(projectContestCPConfig);

        // Get the ProjectContestCPConfig entity
        projectContestCPConfig = projectContestCPConfigService.get(projectContestCPConfigId);

        // Update the ProjectContestCPConfig entity
        projectContestCPConfig.setCpRate(3);
        projectContestCPConfig.getOriginalPayment().setPrize1st(700);
        projectContestCPConfig.getOriginalPayment().setPrize2nd(350);
        projectContestCPConfigService.update(projectContestCPConfig);

        // Delete the ProjectContestCPConfig entity
        projectContestCPConfigService.delete(projectContestCPConfigId);
    }

    /**
     * <p>
     * Demo API usage of <code>MemberContributionPointsService</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoMemberContributionPointsService() throws Exception {
        // Load Spring XML file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        // Get MemberContributionPointsService from Spring context
        MemberContributionPointsService memberContributionPointsService =
            (MemberContributionPointsService) applicationContext.getBean("memberContributionPointsService");

        MemberContributionPoints memberContributionPoints1 = new MemberContributionPoints();
        memberContributionPoints1.setUserId(1);
        memberContributionPoints1.setContestId(1);
        memberContributionPoints1.setContributionPoints(10);
        memberContributionPoints1.setContributionType("ct1");

        // Create the MemberContributionPoints entity
        long memberContributionPointsId1 = memberContributionPointsService.create(memberContributionPoints1);

        MemberContributionPoints memberContributionPoints2 = new MemberContributionPoints();
        memberContributionPoints2.setUserId(1);
        memberContributionPoints2.setContestId(2);
        memberContributionPoints2.setContributionPoints(20);
        memberContributionPoints2.setContributionType("ct1");

        // Create the MemberContributionPoints entity
        long memberContributionPointsId2 = memberContributionPointsService.create(memberContributionPoints2);

        MemberContributionPoints memberContributionPoints3 = new MemberContributionPoints();
        memberContributionPoints3.setUserId(2);
        memberContributionPoints3.setContestId(1);
        memberContributionPoints3.setContributionPoints(30);
        memberContributionPoints3.setContributionType("ct1");

        // Create the MemberContributionPoints entity
        long memberContributionPointsId3 = memberContributionPointsService.create(memberContributionPoints3);

        // Get the MemberContributionPoints entities by user and contest
        List<MemberContributionPoints> memberContributionPointsList1 = memberContributionPointsService
            .getByUserAndContest(1, 1);

        // Get the MemberContributionPoints entities by user
        List<MemberContributionPoints> memberContributionPointsList2 = memberContributionPointsService.getByUserId(1);

        // Get the MemberContributionPoints entities by direct project
        List<MemberContributionPoints> memberContributionPointsList3 = memberContributionPointsService
            .getByProjectId(1);

        // Get the MemberContributionPoints entities by contest
        List<MemberContributionPoints> memberContributionPointsList4 = memberContributionPointsService
            .getByContestId(1);

        memberContributionPoints1 = memberContributionPointsList1.get(0);

        // Update the MemberContributionPoints entity
        memberContributionPoints1.setContributionPoints(15);
        memberContributionPointsService.update(memberContributionPoints1);

        // Delete the MemberContributionPoints entity
        memberContributionPointsService.delete(memberContributionPointsId1);
    }
}
