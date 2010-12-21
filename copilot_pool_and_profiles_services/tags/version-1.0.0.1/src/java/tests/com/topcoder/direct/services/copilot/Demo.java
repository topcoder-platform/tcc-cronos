/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * The demo usage of this component.
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class Demo {

    /**
     * The demo usage of the CopilotProjectPlanServiceImpl.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDemo1() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        CopilotProjectPlanService service = (CopilotProjectPlanService) context.getBean("copilotProjectPlanService");
        // get copilot project plan
        service.getCopilotProjectPlan(1l);
        // create
        service.create(new CopilotProjectPlan());
        // update
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setId(1l);
        service.update(copilotProjectPlan);
        // delete
        service.delete(1l);
        // retrieve
        service.retrieve(1l);
        // retrieveAll
        service.retrieveAll();
    }

    /**
     * The demo usage of the CopilotProfileServiceImpl.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDemo2() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        CopilotProfileService service = (CopilotProfileService) context.getBean("copilotProfileService");
        // get copilot pool members
        service.getCopilotPoolMembers();
        // get copilot profile
        service.getCopilotProfile(1l);
        // get copilot profileDTO
        service.getCopilotProfileDTO(1l);

    }

    /**
     * The demo usage of the CopilotProjectServiceImpl.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDemo3() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        CopilotProjectService service = (CopilotProjectService) context.getBean("copilotProjectService");
        // get copilot ProjectDTO
        service.getCopilotProjectDTO(1l);
        // get copilot projects
        service.getCopilotProjects(1l);
    }

    /**
     * The demo usage of the LookupServiceImpl.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDemo4() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        LookupService service = (LookupService) context.getBean("lookupService");
        // get all copilot profile statuses
        service.getAllCopilotProfileStatuses();
        // get all copilot project statuses
        service.getAllCopilotProjectStatuses();
        // get all copilot types
        service.getAllCopilotTypes();
    }
}
