package com.topcoder.apps.screening.applications.specification.accuracytests;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SubmissionValidatorTest extends TestCase {

    private SubmissionValidator submissionValidator;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(SubmissionValidatorTest.class);
    }

    protected void setUp() {
        submissionValidator = new SubmissionValidator() {
            public ValidationOutput[] validateSubmission(Submission submission) {
                return null;
            }
        };
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.SubmissionValidator.valid(Object)'
     */
    public final void testValidNull() {
        assertFalse("false must be returned", submissionValidator.valid(null));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.SubmissionValidator.valid(Object)'
     */
    public final void testValidNonCorrectInstance() {
        assertFalse("false must be returned", submissionValidator
                .valid("String"));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.SubmissionValidator.valid(Object)'
     */
    public final void testValidAfterValidateSubmissionFirst() {
        submissionValidator = new SubmissionValidator() {
            public ValidationOutput[] validateSubmission(Submission submission) {
                return new ValidationOutput[] { new ValidationOutput(
                        ValidationOutputType.ERROR, "details") };
            }
        };
        assertFalse("false must be returned", submissionValidator
                .valid(new Submission(
                        new UMLUseCaseDiagram[] { new UMLUseCaseDiagram() },
                        new UMLActivityDiagram[] { new UMLActivityDiagram() })));
    }

    public final void testValidAfterValidateSubmissionSecond() {
        submissionValidator = new SubmissionValidator() {
            public ValidationOutput[] validateSubmission(Submission submission) {
                return new ValidationOutput[] {
                        new ValidationOutput(ValidationOutputType.REPORT,
                                "details1"),
                        new ValidationOutput(ValidationOutputType.ERROR,
                                "details2"),
                        new ValidationOutput(ValidationOutputType.REPORT,
                                "details3") };
            }
        };
        assertFalse("false must be returned", submissionValidator
                .valid(new Submission(
                        new UMLUseCaseDiagram[] { new UMLUseCaseDiagram() },
                        new UMLActivityDiagram[] { new UMLActivityDiagram() })));
    }

    public final void testValidAfterValidateSubmissionThird() {
        submissionValidator = new SubmissionValidator() {
            public ValidationOutput[] validateSubmission(Submission submission) {
                return new ValidationOutput[] { new ValidationOutput(
                        ValidationOutputType.REPORT, "details") };
            }
        };
        assertTrue("true must be returned", submissionValidator
                .valid(new Submission(
                        new UMLUseCaseDiagram[] { new UMLUseCaseDiagram() },
                        new UMLActivityDiagram[] { new UMLActivityDiagram() })));
    }

}