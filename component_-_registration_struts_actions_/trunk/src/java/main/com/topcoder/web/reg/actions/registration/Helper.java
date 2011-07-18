/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.util.Set;

import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.WebConstants;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.model.RegistrationType;

/**
 * <p>
 * Helper class is used to check arguments. Should only be used in this component.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
final class Helper {
    /**
     * <p>
     * Private constructor. Make sure no instance of this class will be created.
     * </p>
     */
    private Helper() {
        // Empty
    }

    /**
     * <p>
     * Get the template data for CreateAccountAction and ResendAccountActivationEmailAction.
     * </p>
     *
     * @param registrationTypeDAO
     *            the registrationTypeDAO
     * @param activationCode
     *            the activation code
     * @param sessionInfo
     *            the session info
     * @param userRoles
     *            the user roles
     * @return the template data
     */
    static String getTemplateData(RegistrationTypeDAO registrationTypeDAO, String activationCode,
                    SessionInfo sessionInfo, Set<RegistrationType> userRoles) {
        // Get registration types:
        RegistrationType comp = registrationTypeDAO.getCompetitionType();
        RegistrationType tcs = registrationTypeDAO.getSoftwareType();
        RegistrationType hs = registrationTypeDAO.getHighSchoolType();
        RegistrationType corp = registrationTypeDAO.getCorporateType();
        RegistrationType min = registrationTypeDAO.getMinimalType();
        RegistrationType studio = registrationTypeDAO.getStudioType();
        RegistrationType teacher = registrationTypeDAO.getTeacherType();
        RegistrationType openAIM = registrationTypeDAO.getOpenAIMType();
        RegistrationType truveo = registrationTypeDAO.getTruveoType();

        // Build XML template data:
        StringBuilder sb = new StringBuilder();
        sb.append("<DATA>");
        sb.append("<ACTIVATION_CODE>").append(activationCode).append("</ACTIVATION_CODE>");
        sb.append("<PATH>").append(sessionInfo.getAbsoluteServletPath()).append("?module=Activate&amp;")
            .append(WebConstants.ACTIVATION_CODE).append("</PATH>");
        sb.append("<COMP_REGISTRATION_TYPE>").append(comp.getId()).append("</COMP_REGISTRATION_TYPE>");
        sb.append("<HS_REGISTRATION_TYPE>").append(hs.getId()).append("</HS_REGISTRATION_TYPE>");
        sb.append("<TCS_REGISTRATION_TYPE>").append(tcs.getId()).append("</TCS_REGISTRATION_TYPE>");
        sb.append("<CORP_REGISTRATION_TYPE>").append(corp.getId()).append("</CORP_REGISTRATION_TYPE>");
        sb.append("<MIN_REGISTRATION_TYPE>").append(min.getId()).append("</MIN_REGISTRATION_TYPE>");
        sb.append("<STUDIO_REGISTRATION_TYPE>").append(studio.getId()).append("</STUDIO_REGISTRATION_TYPE>");
        sb.append("<TEACHER_REGISTRATION_TYPE>").append(teacher.getId())
            .append("</TEACHER_REGISTRATION_TYPE>");
        sb.append("<OPENAIM_REGISTRATION_TYPE>").append(openAIM.getId())
            .append("</OPENAIM_REGISTRATION_TYPE>");
        sb.append("<TRUVEO_REGISTRATION_TYPE>").append(truveo.getId()).append("</TRUVEO_REGISTRATION_TYPE>");

        for (RegistrationType type : userRoles) {
            sb.append("<USER_REGISTRATION_TYPE>").append(type.getId()).append("</USER_REGISTRATION_TYPE>");
        }
        sb.append("</DATA>");

        return sb.toString();
    }
}
