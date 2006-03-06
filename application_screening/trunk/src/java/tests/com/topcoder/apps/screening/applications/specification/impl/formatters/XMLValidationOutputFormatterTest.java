/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * XMLValidationOutputFormatterTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.formatters;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>XMLValidationOutputFormatter</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XMLValidationOutputFormatterTest extends TestCase {

    /**XMLValidationOutputFormatter instance that will be tested.*/
    private XMLValidationOutputFormatter formatter;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        formatter = new XMLValidationOutputFormatter();
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output) if output array is null.
     * </p>
     */
    public void testFormatIfOutputContainNull() {
        try {
            formatter.format(null);
            fail("ValidationOutput array cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output) if output array contains null.
     * </p>
     */
    public void testFormatIfOutputNull() {
        try {
            ValidationOutput []outputs = new ValidationOutput[3];
            outputs[0] = new ValidationOutput(ValidationOutputType.ERROR, "error details");
            outputs[1] = null;
            formatter.format(outputs);
            fail("ValidationOutput array cannot contain null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain two ERROR's and two REPORT's outpus,
     * that concerns whole validation (submission).
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;first error&lt;/Error&gt;
     * &lt;Error&gt;second error&lt;/Error&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Report&gt;second report&lt;/Report&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat1() {
        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "first error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR, "second error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT, "second report");

        ValidationOutput []outputs = new ValidationOutput[]{report1, error1, error2, report2};
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        assertTrue("Must contain this substring.",
                result.indexOf("<validation><errors><error>first error</error>") != -1);
        assertTrue("Must contain this substring.", result.indexOf("<error>second error</error></errors>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>") != -1);
        assertTrue("Must contain this substring.",
                result.indexOf("<report>second report</report></reports></validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain two ERROR's and two REPORT's outpus,
     * that concerns whole validation (submission), and one ERROR output with diagram and
     * one REPORT output with diagram.
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;first error&lt;/Error&gt;
     * &lt;Error&gt;second error&lt;/Error&gt;
     * &lt;Diagram id=&quot;123&quot; name=&quot;use case diagram name&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram has error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Report&gt;second report&lt;/Report&gt;
     * &lt;Diagram id=&quot;456&quot; name=&quot;activity diagran name&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram is valid&lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat2() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("use case diagram name");
        useCaseDiagram.setDiagramId("123");
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("activity diagram name");
        activityDiagram.setDiagramId("456");

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "first error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR, "second error");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram has error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT, "second report");
        ValidationOutput report3 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is valid");

        ValidationOutput []outputs = new ValidationOutput[]{report1, error1, error2, report2, error3, report3};
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //substring with general errors
        assertTrue("Must contain this substring.", result.indexOf("<validation><errors><error>first error"
                + "</error><error>second error</error>") != -1);

        //substring with error for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"123\" name=\"use case diagram name\" "
                + "type=\"UseCaseDiagram\"><error>use case diagram has error</error></diagram></errors>") != -1);

        //substring with general reports
        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>"
                + "<report>second report</report>") != -1);

        //substring with reports for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"456\" name=\"activity diagram "
                + "name\" type=\"ActivityDiagram\"><report>activity diagram is valid</report></diagram></reports>"
                + "</validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain one ERROR and one REPORT outpus,
     * that concerns whole validation (submission), and two ERRORs outputs for same diagram and
     * two REPORTs outputs for same diagram.
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;general error&lt;/Error&gt;
     * &lt;Diagram id=&quot;123&quot; name=&quot;use case diagram name&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram is bad&lt;/Error&gt;
     * &lt;Error&gt;use case diagram has error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Diagram id=&quot;456&quot; name=&quot;activity diagran name&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram is good&lt;/Report&gt;
     * &lt;Report&gt;activity diagram is valid&lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat3() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("use case diagram name");
        useCaseDiagram.setDiagramId("123");
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("activity diagram name");
        activityDiagram.setDiagramId("456");

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "general error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram is bad");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram has error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is good");
        ValidationOutput report3 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is valid");

        ValidationOutput []outputs = new ValidationOutput[]{report1, error1, error2, report2, error3, report3};
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //substring with general error
        assertTrue("Must contain this substring.",
                result.indexOf("<validation><errors><error>general error</error>") != -1);

        //substring with errors for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"123\" name=\"use case diagram name\" "
                + "type=\"UseCaseDiagram\"><error>use case diagram is bad</error>") != -1);
        assertTrue("Must contain this substring.",
                result.indexOf("<error>use case diagram has error</error></diagram></errors>") != -1);

        //substring with general reports
        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>") != -1);

        //substring with reports for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"456\" name=\"activity diagram "
                + "name\" type=\"ActivityDiagram\"><report>activity diagram is good</report>") != -1);
        assertTrue("Must contain this substring.",
                result.indexOf("<report>activity diagram is valid</report></diagram></reports></validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain one ERROR and one REPORT outpus,
     * that concerns whole validation (submission), and two ERRORs outputs for same diagram and one for another and
     * two REPORTs outputs for same diagram and one for another.
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;general error&lt;/Error&gt;
     * &lt;Diagram id=&quot;1313&quot; name=&quot;use case diagram name 2&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram 2 has error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;Diagram id=&quot;123&quot; name=&quot;use case diagram name&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram is bad&lt;/Error&gt;
     * &lt;Error&gt;use case diagram has error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Diagram id=&quot;6666&quot; name=&quot;activity diagram name 2&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram 2 is valid&lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;Diagram id=&quot;456&quot; name=&quot;activity diagram name&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram is good&lt;/Report&gt;
     * &lt;Report&gt;activity diagram is valid&lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat4() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("use case diagram name");
        useCaseDiagram.setDiagramId("123");
        UMLUseCaseDiagram useCaseDiagram2 = new UMLUseCaseDiagram();
        useCaseDiagram2.setDiagramName("use case diagram name 2");
        useCaseDiagram2.setDiagramId("1313");

        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("activity diagram name");
        activityDiagram.setDiagramId("456");
        UMLActivityDiagram activityDiagram2 = new UMLActivityDiagram();
        activityDiagram2.setDiagramName("activity diagram name 2");
        activityDiagram2.setDiagramId("6666");

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "general error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram is bad");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram has error");
        ValidationOutput error4 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram2, "use case diagram 2 has error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is good");
        ValidationOutput report3 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is valid");
        ValidationOutput report4 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram2, "activity diagram 2 is valid");

        ValidationOutput []outputs = new ValidationOutput[8];
        outputs[0] = report1;
        outputs[1] = error1;
        outputs[2] = error2;
        outputs[3] = report2;
        outputs[4] = error3;
        outputs[5] = report3;
        outputs[6] = error4;
        outputs[7] = report4;
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //substring with general error
        assertTrue("Must contain this substring.", result.indexOf("<validation>"
                + "<errors><error>general error</error>") != -1);

        //substring with diagram with error
        assertTrue("Must contain this substring.",
                result.indexOf("<diagram id=\"1313\" name=\"use case diagram name 2\" type=\"UseCaseDiagram\">"
                        + "<error>use case diagram 2 has error</error></diagram>") != -1);

        //substring with diagram with error
        assertTrue("Must contain this substring.",
                result.indexOf("<diagram id=\"123\" name=\"use case diagram name\" type=\"UseCaseDiagram\">"
                        + "<error>use case diagram is bad</error>"
                        + "<error>use case diagram has error</error>"
                        + "</diagram>") != -1);

        //substring with general reports
        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>") != -1);

        //substring with report for diagram
        assertTrue("Must contain this substring.", result.indexOf("diagram id=\"6666\" name=\"activity diagram "
                + "name 2\" type=\"ActivityDiagram\">"
                + "<report>activity diagram 2 is valid</report>"
                + "</diagram>") != -1);

        //substring with report for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"456\" name=\"activity diagram "
                + "name\" type=\"ActivityDiagram\">"
                + "<report>activity diagram is good</report>"
                + "<report>activity diagram is valid</report>"
                + "</diagram>"
                + "</reports>"
                + "</validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain one ERROR and one REPORT outpus,
     * that concerns whole validation (submission), and one ERROR output with diagram and one ERROR with diagram's
     * element, and one REPORT output with diagram and one REPORt with diagram's element.
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;first error&lt;/Error&gt;
     * &lt;Diagram id=&quot;123&quot; name=&quot;use case diagram name&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram has error&lt;/Error&gt;
     * &lt;Element id=&quot;123456&quot; name=&quot;use case&quot; type=&quot;UseCase&quot;&gt;
     * &lt;Error&gt;use case has error&lt;/Error&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Diagram id=&quot;456&quot; name=&quot;activity diagram name&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram is valid&lt;/Report&gt;
     * &lt;Element id=&quot;456789&quot; name=&quot;action state&quot; type=&quot;ActionState&quot;&gt;
     * &lt;Report&gt;state is invalid&lt;/Report&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat5() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("use case diagram name");
        useCaseDiagram.setDiagramId("123");
        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("use case");
        useCase.setElementId("123456");

        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("activity diagram name");
        activityDiagram.setDiagramId("456");
        UMLState state = new UMLState("456789", UMLStateType.ACTION_STATE, "action state");
        state.setElementType(UMLElementTypes.UML_ACTION_STATE);

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "first error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram has error");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, useCase, "use case has error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is valid");
        ValidationOutput report3 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, state, "state is invalid");

        ValidationOutput []outputs = new ValidationOutput[]{report1, error1, error2, report2, error3, report3};
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //substring with general errors
        assertTrue("Must contain this substring.", result.indexOf("<validation><errors>"
                + "<error>first error</error>") != -1);

        //substring with error for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"123\" name=\"use case "
                + "diagram name\" type=\"UseCaseDiagram\">"
                + "<error>use case diagram has error</error>") != -1);

        //substring with error for element
        assertTrue("Must contain this substring.", result.indexOf("<element id=\"123456\" name=\"use "
                + "case\" type=\"UseCase\">"
                + "<error>use case has error</error>"
                + "</element>"
                + "</diagram>"
                + "</errors>") != -1);

        //substring with general reports
        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>") != -1);

        //substring with reports for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"456\" name=\"activity "
                + "diagram name\" type=\"ActivityDiagram\">"
                + "<report>activity diagram is valid</report>") != -1);

        //substring with report for diagram
        assertTrue("Must contain this substring.", result.indexOf("<element id=\"456789\" name=\"action state\" "
                + "type=\"ActionState\">"
                + "<report>state is invalid</report>"
                + "</element>"
                + "</diagram>"
                + "</reports>"
                + "</validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain one ERROR and one REPORT outpus,
     * that concerns whole validation (submission), and one ERROR output with diagram and two ERRORs with diagram's
     * different elements, and one REPORT output with diagram and one REPORT with diagram's element.
     * </p>
     *
     * <p>
     * Result must look like:
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;first error&lt;/Error&gt;
     * &lt;Diagram id=&quot;123&quot; name=&quot;use case diagram name&quot; type=&quot;UseCaseDiagram&quot;&gt;
     * &lt;Error&gt;use case diagram has error&lt;/Error&gt;
     * &lt;Element id=&quot;123456&quot; name=&quot;use case&quot; type=&quot;UseCase&quot;&gt;
     * &lt;Error&gt;use case has error&lt;/Error&gt;
     * &lt;/Element&gt;
     * &lt;Element id=&quot;123456789&quot; name=&quot;use case 2&quot; type=&quot;UseCase&quot;&gt;
     * &lt;Error&gt;use case 2 has error&lt;/Error&gt;
     * &lt;Error&gt;use case 2 is invalid&lt;/Error&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Report&gt;first report&lt;/Report&gt;
     * &lt;Diagram id=&quot;456&quot; name=&quot;activity diagram name&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;activity diagram is valid&lt;/Report&gt;
     * &lt;Element id=&quot;456789&quot; name=&quot;action state&quot; type=&quot;ActionState&quot;&gt;
     * &lt;Report&gt;state is invalid&lt;/Report&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormat6() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("use case diagram name");
        useCaseDiagram.setDiagramId("123");
        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("use case");
        useCase.setElementId("123456");
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setElementId("123456789");

        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("activity diagram name");
        activityDiagram.setDiagramId("456");
        UMLState state = new UMLState("456789", UMLStateType.ACTION_STATE, "action state");
        state.setElementType(UMLElementTypes.UML_ACTION_STATE);

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "first error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, "use case diagram has error");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, useCase, "use case has error");
        ValidationOutput error4 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, useCase2, "use case 2 has error");
        ValidationOutput error5 = new ValidationOutput(ValidationOutputType.ERROR,
                useCaseDiagram, useCase2, "use case 2 is invalid");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, "activity diagram is valid");
        ValidationOutput report3 = new ValidationOutput(ValidationOutputType.REPORT,
                activityDiagram, state, "state is invalid");

        ValidationOutput []outputs = new ValidationOutput[8];
        outputs[0] = report1;
        outputs[1] = error1;
        outputs[2] = error2;
        outputs[3] = report2;
        outputs[4] = error3;
        outputs[5] = report3;
        outputs[6] = error4;
        outputs[7] = error5;
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //substring with general errors
        assertTrue("Must contain this substring.", result.indexOf("<validation><errors>"
                + "<error>first error</error>") != -1);

        //substring with error for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"123\" name=\"use case "
                + "diagram name\" type=\"UseCaseDiagram\">"
                + "<error>use case diagram has error</error>") != -1);

        //substring with error for element
        assertTrue("Must contain this substring.", result.indexOf("<element id=\"123456\" name=\"use case\" "
                + "type=\"UseCase\">"
                + "<error>use case has error</error>"
                + "</element>") != -1);

        //substring with error for element
        assertTrue("Must contain this substring.", result.indexOf("<element id=\"123456789\" name=\"use case 2\" "
                + "type=\"UseCase\">"
                + "<error>use case 2 has error</error>"
                + "<error>use case 2 is invalid</error>"
                + "</element>"
                + "</diagram>"
                + "</errors>") != -1);

        //substring with general reports
        assertTrue("Must contain this substring.", result.indexOf("<reports><report>first report</report>") != -1);

        //substring with reports for diagram
        assertTrue("Must contain this substring.", result.indexOf("<diagram id=\"456\" name=\"activity "
                + "diagram name\" type=\"ActivityDiagram\">"
                + "<report>activity diagram is valid</report>") != -1);

        //substring with report for diagram
        assertTrue("Must contain this substring.", result.indexOf("<element id=\"456789\" name=\"action state\" "
                + "type=\"ActionState\">"
                + "<report>state is invalid</report>"
                + "</element>"
                + "</diagram>"
                + "</reports>"
                + "</validation>") != -1);
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test diagrams and elements will have null id. This means that all of them will be
     * treated as separate diagram/element.
     * </p>
     *
     * <p>
     * Result must look like:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Diagram id=&quot;null&quot; name=&quot;null&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Error&gt;first diagram's error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;Diagram id=&quot;null&quot; name=&quot;null&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Error&gt;second diagram's error&lt;/Error&gt;
     * &lt;/Diagram&gt;
     * &lt;Diagram id=&quot;null&quot; name=&quot;null&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Element id=&quot;null&quot; name=&quot;null&quot; type=&quot;Pseudostate&quot;&gt;
     * &lt;Error&gt;first element's error&lt;/Error&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;Diagram id=&quot;null&quot; name=&quot;null&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Element id=&quot;null&quot; name=&quot;null&quot; type=&quot;Pseudostate&quot;&gt;
     * &lt;Error&gt;second element's error&lt;/Error&gt;
     * &lt;/Element&gt;
     * &lt;/Diagram&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     */
    public void testFormatIfDiagramNameIdsNull() {
        UMLActivityDiagram diagram1 = new UMLActivityDiagram();
        UMLElement state = new UMLElement();
        state.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLActivityDiagram diagram2 = new UMLActivityDiagram();

        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram1, "first diagram's error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram1, "second diagram's error");
        ValidationOutput error3 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram2, state, "first element's error");
        ValidationOutput error4 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram2, state, "second element's error");

        ValidationOutput []outputs = new ValidationOutput[]{error1, error2, error3, error4};
        String result = formatter.format(outputs)[0].replaceAll("\n", "");

        //perform some checkings
        assertTrue("Must contain substring.",
                result.indexOf("<diagram id=\"null\" name=\"null\" type=\"ActivityDiagram\">"
                        + "<error>first diagram's error</error>"
                        + "</diagram>"
                        + "<diagram id=\"null\" name=\"null\" type=\"ActivityDiagram\">"
                        + "<error>second diagram's error</error>"
                        + "</diagram>") != -1);

        assertTrue("Must contain substring.",
                result.indexOf("<diagram id=\"null\" name=\"null\" type=\"ActivityDiagram\">"
                        + "<element id=\"null\" name=\"null\" type=\"Pseudostate\">"
                        + "<error>first element's error</error>"
                        + "</element>"
                        + "</diagram>") != -1);

        assertTrue("Must contain substring.",
                result.indexOf("<diagram id=\"null\" name=\"null\" type=\"ActivityDiagram\">"
                        + "<element id=\"null\" name=\"null\" type=\"Pseudostate\">"
                        + "<error>second element's error</error>"
                        + "</element>"
                        + "</diagram>") != -1);
    }
}