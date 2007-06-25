/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;

import com.cronos.onlinereview.ajax.AjaxRequest;
import com.cronos.onlinereview.ajax.AjaxResponse;
import com.cronos.onlinereview.ajax.AjaxSupportHelper;
import com.cronos.onlinereview.ajax.ConfigurationException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.PhaseTemplate;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * Ajax request handler class which handles the "Load Timeline Template" Ajax operation.
 * this class extends the CommonHandler abstract class, and uses the resource manager defined in its parent class,
 * plus an instance of the PhaseTemplate class in order to implement the "Load Timeline Template" operation.
 *
 * Any successful or failed operation is logged using the Logging Wrapper component.
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is immutable an thread safe. any manager class used by this handler is supposed to be thread safe.
 * </p>
 *
 * @author topgear
 * @author assistant
 * @version 1.0.1
 */
public class LoadTimelineTemplateHandler extends CommonHandler {

    /**
     * Represents the status of success.
     */
    private static final String SUCCESS = "Success";

    /**
     * Represents the status of invalid template name.
     */
    private static final String INVALID_TEMPLATE_NAME_ERROR = "Invalid template name error";

    /**
     * Represents the status of business error.
     */
    private static final String BUSINESS_ERROR = "Business error";

    /**
     * Represents the status of role error.
     */
    private static final String ROLE_ERROR = "Role error";

    /**
     * Represents the status of login error.
     */
    private static final String LOGIN_ERROR = "Login error";

    /**
     * Represents the status of invalid parameter error.
     */
    private static final String INVALID_PARAMETER_ERROR = "Invalid parameter error";

    /**
     * <p>
     * The phase template used to generate timeline data.
     * This variable is immutable, it is initialized by the constructor to a not null PhaseTemplate object,
     * and used by the service method.
     * </p>
     */
    private final PhaseTemplate phaseTemplate;

    /**
     * <p>
     * Creates an instance of this class and initialize its internal state.
     * </p>
     *
     * @throws ConfigurationException if there is a configuration exception
     */
    public LoadTimelineTemplateHandler() throws ConfigurationException {
        phaseTemplate = (PhaseTemplate) AjaxSupportHelper.createObject(PhaseTemplate.class);
    }

    /**
     * <p>
     * Performs the "Load Timeline Template" operation and return the timeline data wrapped
     * in a success Ajax response for successful operation, or an error ajax response otherwise.
     * </p>
     *
     * @return the response to the request
     * @param request the request to service
     * @param userId the id of user who issued this request
     * @throws IllegalArgumentException if the request is null
     */
    public AjaxResponse service(AjaxRequest request, Long userId) {
        if (request == null) {
            throw new IllegalArgumentException("The request can't be null.");
        }

        // check all the required parameters are in the request object
        String name = request.getParameter("TemplateName");
        if (name == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The Template name is not set.", "LoadTimelineTemplate. " + "User id : " + userId);
        }
        if (name.trim().length() == 0) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The Template name should not be empty.", "LoadTimelineTemplate. " + "User id : " + userId);
        }
        Date start = null;
        try {
            start = request.getParameterAsDate("StartDate");
        } catch (ParseException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The StartDate parameter can't be parsed.", "LoadTimelineTemplate. " + "User id : " + userId, e);
        }

        // check the userId for validation
        if (userId == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), LOGIN_ERROR,
                    "Doesn't login or expired.", "LoadTimelineTemplate. " + "User id : " + userId);
        }

        try {
            // check that the user has the global manager role
            if (!checkUserHasGlobalManagerRole(userId.longValue())) {
                // if doesn't have the role, return an error response
                return AjaxSupportHelper.createAndLogError(request.getType(), ROLE_ERROR,
                        "User doesn't have the role.", "LoadTimelineTemplate. " + "User id : " + userId);
            }
        } catch (ResourceException e) {
            // if exception raised, return a business error response
            return AjaxSupportHelper.createAndLogError(request.getType(), BUSINESS_ERROR,
                    "User doesn't have the role.", "LoadTimelineTemplate. " + "User id : " + userId, e);
        }

        // find the template, if not found, return an error response
        String[] names = phaseTemplate.getAllTemplateNames();
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_TEMPLATE_NAME_ERROR,
                    "Can't find the template '" + name + "'.", "LoadTimelineTemplate. " + "User id : " + userId);

        }

        // apply the template
        Project project = null;
        try {
            if (start != null) {
                project = phaseTemplate.applyTemplate(name, start);
            } else {
                project = phaseTemplate.applyTemplate(name);
            }
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), BUSINESS_ERROR, "Can't apply template",
                    "LoadTimelineTemplate. " + "User id : " + userId, e);
        }

        // generate the xml
        String xml = timelineToXml(project);

        return AjaxSupportHelper.createAndLogSucceess(request.getType(), SUCCESS, "succeed to load template",
                xml, "LoadTimelineTemplate. " + "User id : " + userId + " Start : " + start);
    }

    /**
     * <p>
     * Produces the XML representation of the timeline data as described in the TimelineData.xsd file.
     * </p>
     *
     * @return XML string representing the timeline data
     * @param project the project containing the timeline data
     * @throws IllegalArgumentException if project is null
     */
    public static String timelineToXml(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("The project can't be null.");
        }

        // use a string buffer to compose the xml
        StringBuffer sb = new StringBuffer();
        sb.append("<timeline>");
        sb.append("<phases>");

        // loop over all the phases to generate the xml
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; i++) {
            phaseToXml(phases[i], sb);
        }

        sb.append("</phases>");
        sb.append("</timeline>");

        return sb.toString();
    }

    /**
     * Produces the XML representation of the phase data.
     *
     * @param phase the phase data
     * @param sb the string buffer to handle the xml
     */
    private static void phaseToXml(Phase phase, StringBuffer sb) {
        // start the element
        sb.append("<phase type=\"");
        sb.append(phase.getPhaseType().getName());
        sb.append("\" id=\"");
        sb.append(phase.getId());
        sb.append("\">");

        // start date
        sb.append("<start-date>");
        // start date and end date
        if (phase.getFixedStartDate() != null) {
            sb.append(AjaxSupportHelper.dateToString(phase.getFixedStartDate()));
        } else {
            sb.append(AjaxSupportHelper.dateToString(phase.calcStartDate()));
        }
        sb.append("</start-date>");

        // end date
        sb.append("<end-date>");
        sb.append(AjaxSupportHelper.dateToString(phase.calcEndDate()));
        sb.append("</end-date>");

        // the length
        sb.append("<length>");
        sb.append(phase.getLength());
        sb.append("</length>");

        // generate the dependencies if and only if the fixed start date is null
        if (phase.getFixedStartDate() == null) {
            sb.append("<dependencies>");

            Dependency[] dependencies = phase.getAllDependencies();
            for (int i = 0; i < dependencies.length; i++) {
                dependencyToXml(dependencies[i], sb);
            }
            sb.append("</dependencies>");
        }

        sb.append("</phase>");
    }

    /**
     * Convert dependency to an xml.
     *
     * @param dependency the dependency object
     * @param sb the string buffer used for convert
     */
    private static void dependencyToXml(Dependency dependency, StringBuffer sb) {
        sb.append("<dependency>");

        // append the dependency-phase-id
        sb.append("<dependency-phase-id>");
        sb.append(dependency.getDependency().getId());
        sb.append("</dependency-phase-id>");

        // append the dependent-phase-id
        sb.append("<dependent-phase-id>");
        sb.append(dependency.getDependent().getId());
        sb.append("</dependent-phase-id>");

        // append the dependency-phase-start
        sb.append("<dependency-phase-start>");
        sb.append(dependency.isDependencyStart());
        sb.append("</dependency-phase-start>");

        // append the dependent-phase-start
        sb.append("<dependent-phase-start>");
        sb.append(dependency.isDependentStart());
        sb.append("</dependent-phase-start>");

        // append the lag-time
        sb.append("<lag-time>");
        sb.append(dependency.getLagTime());
        sb.append("</lag-time>");

        sb.append("</dependency>");
    }
}
