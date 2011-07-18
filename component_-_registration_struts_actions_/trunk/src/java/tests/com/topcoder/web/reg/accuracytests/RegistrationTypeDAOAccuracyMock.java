/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.model.RegistrationType;
/**
 * <p>
 * A simple accuracy mock of RegistrationTypeDAO.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class RegistrationTypeDAOAccuracyMock implements RegistrationTypeDAO {

    public RegistrationType find(Integer id) {
        return new RegistrationType(RegistrationType.COMPETITION_ID);
    }

    public RegistrationType getCompetitionType() {
        return new RegistrationType(RegistrationType.COMPETITION_ID);
    }

    public RegistrationType getCorporateType() {
        return new RegistrationType(RegistrationType.CORPORATE_ID);
    }

    public RegistrationType getHighSchoolType() {
        return new RegistrationType(RegistrationType.HIGH_SCHOOL_ID);
    }

    public RegistrationType getMinimalType() {
        return new RegistrationType(RegistrationType.MINIMAL_ID);
    }

    public RegistrationType getOpenAIMType() {
        return new RegistrationType(RegistrationType.OPENAIM_ID);
    }

    public List<RegistrationType> getRegistrationTypes() {
        RegistrationType type1 = new RegistrationType(RegistrationType.SOFTWARE_ID);
        RegistrationType type2 = new RegistrationType(RegistrationType.STUDIO_ID);
        return Arrays.asList(type1, type2);
    }

    public RegistrationType getSoftwareType() {
        return new RegistrationType(RegistrationType.SOFTWARE_ID);
    }

    public RegistrationType getStudioType() {
        return new RegistrationType(RegistrationType.STUDIO_ID);
    }

    public RegistrationType getTeacherType() {
        return new RegistrationType(RegistrationType.TEACHER_ID);
    }

    public RegistrationType getTruveoType() {
        return new RegistrationType(RegistrationType.TRUVEO_ID);
    }

}
