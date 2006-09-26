/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.xmi.parser.XMIParser;
import com.topcoder.util.xmi.parser.data.UMLDiagramTypes;
import com.topcoder.util.xmi.parser.handler.DefaultXMIHandler;

/**
 * <p>
 * This rule checks an extracted XMI file for the presence of any necessary
 * diagrams. The Topcoder XMI Parser is used to detect the presence of these
 * diagrams.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class PoseidonDiagramRule implements ScreeningRule {

    /**
     * Represents the Log instance for this class to log additional information
     * to.
     */
    private static final Log LOG;

    /**
     * Static constructor.
     */
    static {
        // try to create a Log instance
        try {
            LOG = LogFactory.getLogWithExceptions(PoseidonDiagramRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + PoseidonDiagramRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * This is the minimum count of use case diagrams that need to be present
     * for this rule.
     * </p>
     */
    private final int useCaseDiagramCount;

    /**
     * <p>
     * This is the minimum count of class diagrams that need to be present for
     * this rule.
     * </p>
     */
    private final int classDiagramCount;

    /**
     * <p>
     * This is the minimum count of sequence diagrams that need to be present
     * for this rule.
     * </p>
     */
    private final int sequenceDiagramCount;

    /**
     * <p>
     * Constructor that produces the rule with the necessary diagram counts to
     * be used.
     * </p>
     * @param useCaseDiagramCount
     *            The minimum number of use case diagrams to consider the
     *            screened xmi file valid.
     * @param classDiagramCount
     *            The minimum number of class diagrams to consider the screened
     *            xmi file valid.
     * @param sequenceDiagramCount
     *            The minimum number of sequence diagrams to consider the
     *            screened xmi file valid.
     */
    public PoseidonDiagramRule(int useCaseDiagramCount, int classDiagramCount,
        int sequenceDiagramCount) {

        this.useCaseDiagramCount = useCaseDiagramCount;
        this.classDiagramCount = classDiagramCount;
        this.sequenceDiagramCount = sequenceDiagramCount;
    }

    /**
     * <p>
     * Performs screening on the specified screening task. This implementation
     * will expect the context to contain the extracted PROJ3 file from the
     * unzipped ZUML.
     * </p>
     * <p>
     * Generally speaking this method does the following things:
     * <ol>
     * <li>Retrieve the unzipped PROJ3 file from PoseidonExtractRule.XMI_KEY.</li>
     * <li>Use the XMI Parser to retrieve the number of use case diagrams,
     * class diagrams and sequence diagrams.</li>
     * <li>Compare them against the required useCaseDiagramCount,
     * classDiagramCount and sequenceDiagramCount. </li>
     * <li>If any of them fail to meet the expected count (they are less than
     * the expected count), then create a failed RuleResult with detail message
     * for each one.</li>
     * <li>Otherwise, create a successful RuleResult.</li>
     * <li>Log this method according to the CS section 1.3.3.</li>
     * </ol>
     * </p>
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public RuleResult[] screen(ScreeningTask screeningTask, Map context) {
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("context should not be null.");
        }

        RuleUtils.beginScreeningLogging(LOG, screeningTask);
        RuleResult[] ruleResults = screenWithoutLog(screeningTask, context);
        RuleUtils.endScreeningLogging(LOG, ruleResults);

        return ruleResults;
    }

    /**
     * Performs screening on the specified screening task without log
     * information.
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     */
    private RuleResult[] screenWithoutLog(ScreeningTask screeningTask, Map context) {

        try {
            // get the proj3 file
            File xmiFile = (File) RuleUtils.getContextProperty(context,
                PoseidonExtractRule.XMI_KEY, File.class);

            // parse the proj3 file
            XMIParser xmiParser = new XMIParser();
            xmiParser.parse(xmiFile);

            // Get the default XMI handler
            DefaultXMIHandler handler = (DefaultXMIHandler) xmiParser.getHandler();

            // this array list contains failure results
            ArrayList resultList = new ArrayList();

            // check # of use case diagram
            int ucdCount = handler.getCountOfDiagramType(UMLDiagramTypes.UML_USECASE_DIAGRAM);
            if (ucdCount < useCaseDiagramCount) {
                resultList.add(new RuleResult(false, "The number of use case diagrams [" + ucdCount
                    + "] is less than required [" + useCaseDiagramCount + "]."));
            }

            // check # of class diagram
            int cdCount = handler.getCountOfDiagramType(UMLDiagramTypes.UML_CLASS_DIAGRAM);
            if (cdCount < classDiagramCount) {
                resultList.add(new RuleResult(false, "The number of class diagrams [" + cdCount
                    + "] is less than required [" + classDiagramCount + "]."));
            }

            // check # of sequence diagram
            int sdCount = handler.getCountOfDiagramType(UMLDiagramTypes.UML_SEQUENCE_DIAGRAM);
            if (sdCount < sequenceDiagramCount) {
                resultList.add(new RuleResult(false, "The number of sequence diagrams [" + sdCount
                    + "] is less than required [" + sequenceDiagramCount + "]."));
            }

            // check if any of them fails to meet the expected count
            if (resultList.size() != 0) {
                return (RuleResult[]) resultList.toArray(new RuleResult[0]);
            }

            // all meet the expected counts
            return new RuleResult[] {new RuleResult(true,
                "Ok. The numbers of use case diagram, class diagram and sequence diagram ["
                    + ucdCount + ", " + cdCount + ", " + sdCount
                    + "] all meet the expected counts [" + useCaseDiagramCount + ", "
                    + classDiagramCount + ", " + sequenceDiagramCount + "].")};
        } catch (Throwable e) {
            return new RuleResult[] {new RuleResult(e)};
        }
    }

    /**
     * <p>
     * Cleans up any resources related to this screening rule.
     * </p>
     * <p>
     * This rule does not use any resources that need to be cleaned, and so this
     * method remains empty.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        // do nothing
    }
}
