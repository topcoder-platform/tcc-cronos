/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TextValidationOutputFormatterTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.formatters;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * <p>
 * Unit tests for <code>TextValidationOutputFormatter</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TextValidationOutputFormatterTest extends TestCase {

    /**TextValidationOutputFormatter instance that will be tested.*/
    private TextValidationOutputFormatter formatter;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        formatter = new TextValidationOutputFormatter();
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
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: first error
     * OutputType: error
     * Details: second error
     * OutputType: report
     * Details: second report
     * </p>
     */
    public void testFormat1() {
        ValidationOutput error1 = new ValidationOutput(ValidationOutputType.ERROR, "first error");
        ValidationOutput error2 = new ValidationOutput(ValidationOutputType.ERROR, "second error");
        ValidationOutput report1 = new ValidationOutput(ValidationOutputType.REPORT, "first report");
        ValidationOutput report2 = new ValidationOutput(ValidationOutputType.REPORT, "second report");

        ValidationOutput []outputs = new ValidationOutput[]{report1, error1, error2, report2};
        String result[] = formatter.format(outputs);

        //create expected string array
        String []expected = new String[4];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: first error";
        expected[2] = "OutputType: error\nDetails: second error";
        expected[3] = "OutputType: report\nDetails: second report";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain two ERRORs and two REPORTs outpus,
     * that concerns whole validation (submission), and one ERROR output with diagram and
     * one REPORT output with diagram.
     * </p>
     *
     * <p>
     * Result must look like:
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: first error
     * OutputType: error
     * Details: second error
     * OutputType: report
     * Details: second report
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is valid
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
        String result[] = formatter.format(outputs);

        //create expected string array
        String []expected = new String[6];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: first error";
        expected[2] = "OutputType: error\nDetails: second error";
        expected[3] = "OutputType: report\nDetails: second report";
        expected[4] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram has error";
        expected[5] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is valid";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
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
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: general error
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram is bad
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is good
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is valid
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
        String []result = formatter.format(outputs);

        //create expected string array
        String []expected = new String[6];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: general error";
        expected[2] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram is bad";
        expected[3] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is good";
        expected[4] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram has error";
        expected[5] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is valid";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
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
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: general error
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram is bad
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is good
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is valid
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name 2(1313)
     * Details: use case diagram 2 has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name 2(6666)
     * Details: activity diagram 2 is valid
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
        String []result = formatter.format(outputs);

        //create expected string array
        String []expected = new String[8];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: general error";
        expected[2] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram is bad";
        expected[3] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is good";
        expected[4] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram has error";
        expected[5] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is valid";
        expected[6] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name 2(1313)\n"
                + "Details: use case diagram 2 has error";
        expected[7] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name 2(6666)\n"
                + "Details: activity diagram 2 is valid";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
    }

    /**
     * <p>
     * Tests method format(ValidationOutput[] output).
     * </p>
     *
     * <p>
     * In this test output array will contain one ERROR and one REPORT outpus,
     * that concerns whole validation (submission), and one ERROR output with diagram and one ERROR with diagram's
     * element, and one REPORT output with diagram and one REPORT with diagram's element.
     * </p>
     *
     * <p>
     * Result must look like:
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: first error
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is valid
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Element: UseCase use case(123456)
     * Details: use case has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Element: ActionState action state(456789)
     * Details: state is invalid
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
        String []result = formatter.format(outputs);

        //create expected string array
        String []expected = new String[6];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: first error";
        expected[2] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram has error";
        expected[3] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is valid";
        expected[4] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Element: UseCase use case(123456)\n"
                + "Details: use case has error";
        expected[5] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Element: ActionState action state(456789)\n"
                + "Details: state is invalid";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
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
     *
     * OutputType: report
     * Details: first report
     * OutputType: error
     * Details: first error
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Details: use case diagram has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Details: activity diagram is valid
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Element: UseCase use case(123456)
     * Details: use case has error
     * OutputType: report
     * Diagram: ActivityDiagram activity diagram name(456)
     * Element: ActionState action state(456789)
     * Details: state is invalid
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Element: UseCase use case 2(123456789)
     * Details: use case 2 has error
     * OutputType: error
     * Diagram: UseCaseDiagram use case diagram name(123)
     * Element: UseCase use case 2(123456789)
     * Details: use case 2 is invalid
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
        String []result = formatter.format(outputs);

        //create expected string array
        String []expected = new String[8];
        expected[0] = "OutputType: report\nDetails: first report";
        expected[1] = "OutputType: error\nDetails: first error";
        expected[2] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Details: use case diagram has error";
        expected[3] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Details: activity diagram is valid";
        expected[4] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Element: UseCase use case(123456)\n"
                + "Details: use case has error";
        expected[5] = "OutputType: report\nDiagram: ActivityDiagram activity diagram name(456)\n"
                + "Element: ActionState action state(456789)\n"
                + "Details: state is invalid";
        expected[6] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Element: UseCase use case 2(123456789)\n"
                + "Details: use case 2 has error";
        expected[7] = "OutputType: error\nDiagram: UseCaseDiagram use case diagram name(123)\n"
                + "Element: UseCase use case 2(123456789)\n"
                + "Details: use case 2 is invalid";
        assertTrue("Received and expected arrays must be equal.", Arrays.equals(result, expected));
    }
}