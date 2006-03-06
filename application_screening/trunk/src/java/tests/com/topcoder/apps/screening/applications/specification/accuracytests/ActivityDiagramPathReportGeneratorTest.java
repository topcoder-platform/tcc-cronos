/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * ActivityDiagramPathReportGeneratorTest.java
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramPathReportGenerator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLTransition;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

/**
 * <p>
 * Unit tests for <code>ActivityDiagramPathReportGenerator</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActivityDiagramPathReportGeneratorTest extends TestCase {

    /** ActivityDiagramPathReportGenerator instance that will be tested. */
    private ActivityDiagramPathReportGenerator pathReportGenerator;

    /** UMLActivityDiagram that is used for testing. */
    private UMLActivityDiagram activityDiagram;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(ActivityDiagramPathReportGeneratorTest.class);
    }

    /**
     * <p>
     * Set up environment.
     * </p>
     * 
     * @throws Exception
     *             exception
     */
    public void setUp() throws Exception {
        pathReportGenerator = new ActivityDiagramPathReportGenerator();
        activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram.");
        pathReportGenerator = new ActivityDiagramPathReportGenerator();
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if there is no
     * initial states. No paths will be returned.
     * </p>
     */
    public void testValidateActivityDiagramNoInitialState() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);
    
        UMLState f4 = new UMLState("4", UMLStateType.FINAL_STATE, "final1");
        f4.setElementType(UMLElementTypes.UML_FINAL_STATE);
    
        UMLTransition tr1 = new UMLTransition("5", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("6", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("7", "3", "4", "tr3",
                "guardCondition3", "guardEffect3");
    
        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(f4.getElementId(), f4);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be no paths.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if there is no
     * final states. No paths will be returned.
     * </p>
     */
    public void testValidateActivityDiagramIfNoFinalState() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);
    
        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
    
        UMLTransition tr1 = new UMLTransition("5", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("6", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("7", "3", "4", "tr3",
                "guardCondition3", "guardEffect3");
    
        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be no paths.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if there is no
     * action states. No paths will be returned.
     * </p>
     */
    public void testValidateActivityDiagramNoActionStates() {
        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f1 = new UMLState("2", UMLStateType.FINAL_STATE, "final1");
        f1.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLTransition tr1 = new UMLTransition("3", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        activityDiagram.addElement(i1.getElementName(), i1);
        activityDiagram.addElement(f1.getElementName(), f1);
        activityDiagram.addElement(tr1.getElementName(), tr1);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be no paths.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagramFirstPath() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f4 = new UMLState("4", UMLStateType.FINAL_STATE, "final1");
        f4.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("5", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("6", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("7", "3", "4", "tr3",
                "guardCondition3", "guardEffect3");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(f4.getElementId(), f4);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be 1 path.", 1, outputs.length);

        // compare with desired result
        String[] nodes = new String[] {
                i1.getElementName() + " - " + tr1.getGuardCondition(),
                as2.getElementName() + " - " + tr2.getGuardCondition(),
                as3.getElementName() + " - " + tr3.getGuardCondition() };

        String path[] = generatePathes(outputs[0].getDetails());
        assertTrue("Expected and received paths must be equal.", Arrays.equals(
                nodes, path));
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagramSecondPath() {
        UMLState as2 = new UMLState("3", UMLStateType.ACTION_STATE, "state1");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("4", UMLStateType.ACTION_STATE, "state2");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState i2 = new UMLState("2", UMLStateType.INITIAL_STATE, "inital2");
        i2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f5 = new UMLState("5", UMLStateType.FINAL_STATE, "final1");
        f5.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("6", "1", "3", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("7", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("8", "3", "4", "tr3",
                "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("9", "4", "5", "tr4",
                "guardCondition4", "guardEffect4");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(i2.getElementId(), i2);
        activityDiagram.addElement(f5.getElementId(), f5);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be 2 paths.", 2, outputs.length);

        // now put all paths to array and compare with expected paths
        StringArrayList received = getStringArrayList(outputs);

        // desired results:
        // path: I1->AS3->AS4->F5
        String[] expected1 = new String[] {
                i1.getElementName() + " - " + tr1.getGuardCondition(),
                as2.getElementName() + " - " + tr3.getGuardCondition(),
                as3.getElementName() + " - " + tr4.getGuardCondition() };
        // path: I2->AS3->AS4->F5
        String[] expected2 = new String[] {
                i2.getElementName() + " - " + tr2.getGuardCondition(),
                as2.getElementName() + " - " + tr3.getGuardCondition(),
                as3.getElementName() + " - " + tr4.getGuardCondition() };

        StringArrayList expected = new StringArrayList();
        expected.add(expected1);
        expected.add(expected2);

        assertTrue("Expected and received paths must be equal.", expected
                .equals(received));
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagramThirdPath() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f4 = new UMLState("4", UMLStateType.FINAL_STATE, "final1");
        f4.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("5", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("6", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("7", "3", "2", "tr3",
                "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("8", "3", "4", "tr4",
                "guardCondition4", "guardEffect4");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(f4.getElementId(), f4);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be 2 paths.", 2, outputs.length);

        // now put all paths to array and compare with expected paths
        StringArrayList received = getStringArrayList(outputs);

        // desired results:
        // path: I1->AS2->AS3->F4
        String[] expected1 = new String[] {
                i1.getElementName() + " - " + tr1.getGuardCondition(),
                as2.getElementName() + " - " + tr2.getGuardCondition(),
                as3.getElementName() + " - " + tr4.getGuardCondition() };
        // path: I1->AS2->AS3->AS2->AS3->F4
        String[] expected2 = new String[] {
                i1.getElementName() + " - " + tr1.getGuardCondition(),
                as2.getElementName() + " - " + tr2.getGuardCondition(),
                as3.getElementName() + " - " + tr3.getGuardCondition(),
                as2.getElementName() + " - " + tr2.getGuardCondition(),
                as3.getElementName() + " - " + tr4.getGuardCondition() };

        StringArrayList expected = new StringArrayList();
        expected.add(expected1);
        expected.add(expected2);

        assertTrue("Expected and received paths must be equal.", expected
                .equals(received));
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagramFourthPath() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as4 = new UMLState("4", UMLStateType.ACTION_STATE, "state4");
        as4.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as5 = new UMLState("5", UMLStateType.ACTION_STATE, "state5");
        as5.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final1");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1To2 = new UMLTransition("8", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2To3 = new UMLTransition("9", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3To4 = new UMLTransition("10", "3", "4", "tr3",
                "guardCondition3", "guardEffect3");
        UMLTransition tr3To5 = new UMLTransition("11", "3", "5", "tr4",
                "guardCondition4", "guardEffect4");
        UMLTransition tr3To6 = new UMLTransition("12", "3", "6", "tr5",
                "guardCondition5", "guardEffect5");
        UMLTransition tr4To2 = new UMLTransition("13", "4", "2", "tr6",
                "guardCondition6", "guardEffect6");
        UMLTransition tr5To2 = new UMLTransition("14", "5", "2", "tr7",
                "guardCondition7", "guardEffect7");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(as4.getElementId(), as4);
        activityDiagram.addElement(as5.getElementId(), as5);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1To2.getElementId(), tr1To2);
        activityDiagram.addElement(tr2To3.getElementId(), tr2To3);
        activityDiagram.addElement(tr3To4.getElementId(), tr3To4);
        activityDiagram.addElement(tr3To5.getElementId(), tr3To5);
        activityDiagram.addElement(tr3To6.getElementId(), tr3To6);
        activityDiagram.addElement(tr4To2.getElementId(), tr4To2);
        activityDiagram.addElement(tr5To2.getElementId(), tr5To2);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be 3 paths.", 3, outputs.length);

        // now put all paths to array and compare with expected paths
        StringArrayList received = getStringArrayList(outputs);

        // desired results:
        // path: I1->AS2->AS3->F6
        String[] expected1 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To6.getGuardCondition() };
        // path: I1->AS2->AS3->AS4->AS2->AS3->F6
        String[] expected2 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To6.getGuardCondition() };
        // path: I1->AS2->AS3->AS5->AS2->AS3->F6
        String[] expected3 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To6.getGuardCondition() };

        StringArrayList expected = new StringArrayList();
        expected.add(expected1);
        expected.add(expected2);
        expected.add(expected3);

        assertTrue("Expected and received paths must be equal.", expected
                .equals(received));
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagramFifthPath() {
        UMLState as2 = new UMLState("2", UMLStateType.ACTION_STATE, "state2");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("3", UMLStateType.ACTION_STATE, "state3");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as4 = new UMLState("4", UMLStateType.ACTION_STATE, "state4");
        as4.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as5 = new UMLState("5", UMLStateType.ACTION_STATE, "state5");
        as5.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final1");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1To2 = new UMLTransition("8", "1", "2", "tr1",
                "guardCondition1", "guardEffect1");
        UMLTransition tr2To3 = new UMLTransition("9", "2", "3", "tr2",
                "guardCondition2", "guardEffect2");
        UMLTransition tr3To2 = new UMLTransition("10", "3", "2", "tr3",
                "guardCondition3", "guardEffect3");
        UMLTransition tr3To4 = new UMLTransition("11", "3", "4", "tr4",
                "guardCondition4", "guardEffect4");
        UMLTransition tr4To5 = new UMLTransition("12", "4", "5", "tr5",
                "guardCondition5", "guardEffect5");
        UMLTransition tr5To4 = new UMLTransition("13", "5", "4", "tr6",
                "guardCondition6", "guardEffect6");
        UMLTransition tr5To6 = new UMLTransition("14", "5", "6", "tr7",
                "guardCondition7", "guardEffect7");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(as4.getElementId(), as4);
        activityDiagram.addElement(as5.getElementId(), as5);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1To2.getElementId(), tr1To2);
        activityDiagram.addElement(tr2To3.getElementId(), tr2To3);
        activityDiagram.addElement(tr3To2.getElementId(), tr3To2);
        activityDiagram.addElement(tr3To4.getElementId(), tr3To4);
        activityDiagram.addElement(tr4To5.getElementId(), tr4To5);
        activityDiagram.addElement(tr5To4.getElementId(), tr5To4);
        activityDiagram.addElement(tr5To6.getElementId(), tr5To6);
        ValidationOutput[] outputs = pathReportGenerator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        assertEquals("There must be 4 paths.", 4, outputs.length);

        // now put all paths to array and compare with expected paths
        StringArrayList received = getStringArrayList(outputs);

        // desired results:
        // path: I1->AS2->AS3->AS4->AS5->F6
        String[] expected1 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To6.getGuardCondition() };
        // path: I1->AS2->AS3->AS2->AS3->AS4->AS5->F6
        String[] expected2 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To6.getGuardCondition() };
        // path: I1->AS2->AS3->AS4->AS5->AS4->AS5->F6
        String[] expected3 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To6.getGuardCondition() };
        // path: I1->AS2->AS3->AS2->AS3->AS4->AS5->AS4->AS5->F6
        String[] expected4 = new String[] {
                i1.getElementName() + " - " + tr1To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To2.getGuardCondition(),
                as2.getElementName() + " - " + tr2To3.getGuardCondition(),
                as3.getElementName() + " - " + tr3To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To4.getGuardCondition(),
                as4.getElementName() + " - " + tr4To5.getGuardCondition(),
                as5.getElementName() + " - " + tr5To6.getGuardCondition() };

        StringArrayList expected = new StringArrayList();
        expected.add(expected1);
        expected.add(expected2);
        expected.add(expected3);
        expected.add(expected4);

        assertTrue("Expected and received paths must be equal.", expected
                .equals(received));
    }

    /**
     * <p>
     * This method will convert received paths to next representation so that it
     * was easier to compare with predefined paths: all paths will be converted
     * to string array, each element will represent one &lt;Node&gt;.
     * </p>
     * 
     * @param outputs
     *            ValidationOutputs array
     * @return list with string arrays
     */
    private StringArrayList getStringArrayList(ValidationOutput[] outputs) {
        StringArrayList resStringArrayList = new StringArrayList();
        for (int i = 0, n = outputs.length; i < n; i++) {
            resStringArrayList.add(generatePathes(outputs[i].getDetails()));
        }
        return resStringArrayList;
    }

    /**
     * <p>
     * Converts path to string array.
     * </p>
     * 
     * @param path
     *            next path
     * @return all nodes from path
     */
    private String[] generatePathes(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(path.getBytes());
            Document document = builder.parse(is);
            NodeList nodeList = document.getElementsByTagName("Node");
            String[] nodes = new String[nodeList.getLength()];
            for (int i = 0, n = nodeList.getLength(); i < n; i++) {
                nodes[i] = nodeList.item(i).getFirstChild().getNodeValue();
            }
            return nodes;
        } catch (ParserConfigurationException e) {
            // ignore
        } catch (SAXException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
        return new String[0];
    }

    /**
     * <p>
     * This list will contain string arrays. It was developed so that it was
     * easier to compare received and expected paths.
     * </p>
     */
    class StringArrayList extends ArrayList {

        /**
         * <p>
         * Empty constructor.
         * </p>
         */
        public StringArrayList() {
            // empty
        }

        /**
         * <p>
         * Checks whether this list contains such string array or not.
         * </p>
         * 
         * @param elem
         *            element
         * @return true or false
         */
        public boolean contains(Object elem) {
            for (Iterator iter = iterator(); iter.hasNext();) {
                if (Arrays.equals((String[]) elem, (String[]) iter.next())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * <p>
         * Checks whether accepted and current lists are equal or not.
         * </p>
         * 
         * @param obj
         *            list
         * @return true or false
         */
        public boolean equals(Object obj) {
            StringArrayList list = (StringArrayList) obj;
            if (list.size() != size()) {
                return false;
            }

            for (Iterator iter = iterator(); iter.hasNext();) {
                Object next = iter.next();
                if (!list.contains(next)) {
                    return false;
                }
            }
            return true;
        }
    }
}