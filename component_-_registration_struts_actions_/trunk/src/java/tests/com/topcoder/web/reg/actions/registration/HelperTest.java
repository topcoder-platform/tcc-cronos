/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.Test;

import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.model.RegistrationType;

/**
 * <p>
 * Unit tests for class <code>PreCreateAccountAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class HelperTest {

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCaptchaImageGenerator()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTemplateData() {
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createNiceMock(RegistrationTypeDAO.class);
        RegistrationType type = new RegistrationType();
        EasyMock.expect(registrationTypeDAO.getCompetitionType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getCorporateType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getHighSchoolType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getMinimalType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getOpenAIMType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getSoftwareType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getStudioType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getTeacherType()).andReturn(type);
        EasyMock.expect(registrationTypeDAO.getTruveoType()).andReturn(type);
        SessionInfo sessionInfo = EasyMock.createNiceMock(SessionInfo.class);

        EasyMock.replay(registrationTypeDAO, sessionInfo);

        String result = Helper.getTemplateData(registrationTypeDAO, "activationCode", sessionInfo,
            new HashSet<RegistrationType>());
        assertTrue("The result is not empty", (result.length() > 1));

        EasyMock.verify(registrationTypeDAO, sessionInfo);
    }

}
