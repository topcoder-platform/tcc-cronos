/*
 * TemplateSelectorFailureTest.java    Created on 2005-7-7
 *
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 * 
 * @author qiucx0161
 *
 * @version 1.0
 */
package com.topcoder.buildutility.failuretests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.topcoder.buildutility.TemplateSelectionAlgorithm;
import com.topcoder.buildutility.TemplateSelector;
import com.topcoder.buildutility.component.ComponentVersion;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Test <code>TemplateSelector</code> class in failure cases. In this class,
 * </code>selectTemplates</code> and <code>findMatchingTemplateHierarchy</code>
 * will be tested with invalid parameter.
 */
public class TemplateSelectorFailureTest extends TestCase {

    /**
     * The <code>ComponentVersion</code> instance is used in <code>selectTemplates</code>instance,
     * it will be initialized in setup.
     */
    private ComponentVersion version = null;
    
    /**
     * The <code>ComponentVersion</code> instance is used in <code>selectTemplates</code>instance,
     * it will be initialized in setup.
     */
    private TemplateHierarchy hierarchy = null;
    
    /**
     * The <code>Mathod</code>instance is used to test the protected <code>findMatchingTemplateHierarchy</code>
     * method, it will be initialized in setup.
     */
    private Method findMatchingTemplateHierarchy = null;
    
    /**
     * The <code>TemplateSelectionAlgorithm</code>instance is used to call its methods.
     * And it will be initialized in setup
     *
     */
    private TemplateSelectionAlgorithm selector = null;

    /**
     * Setup the test environment. It will initialize the <code>
     * TemplateSelectorAlgorithm</code>instance.
     */
    protected void setUp() throws Exception {
        // Initialize 'selector' first, and then initialize Mathod instance.
        selector = new TemplateSelector();
        findMatchingTemplateHierarchy =
            selector.getClass().getDeclaredMethod("findMatchingTemplateHierarchy",
                    new Class[]{ComponentVersion.class, TemplateHierarchy.class});
        
        version = new ComponentVersion(888888);
        hierarchy = new TemplateHierarchy(999999, "Failure test", -1);
        
    }

    /**
     * Test <code>selectTemplates</code>for null <code>ComponentVersion</code>.
     * NPE is expected.
     *
     */
    public void testTemplateSelectorNullParam1() {
        try {
            selector.selectTemplates(null, hierarchy);
            fail("NPE is expected for null ComponentVersion.");
        } catch (NullPointerException npe) {
            System.err.println(npe);
        } catch (Exception e) {
            fail("Cannot reach here for null ComponentVersion.");
        }
    }
    
    /**
     * Test <code>selectTemplates</code>for null <code>TemplateHierarchy</code>.
     * NPE is expected.
     *
     */
    public void testTemplateSelectorNullParam2() {
        try {
            selector.selectTemplates(version, null);
            fail("NPE is expected for null TemplateHierarchy.");
        } catch (NullPointerException npe) {
            System.err.println(npe);
        } catch (Exception e) {
            fail("Cannot reach here for null TemplateHierarchy.");
        }
    }
    
    /**
     * <p>
     * Test <code>findMatchingTemplateHierarchy</code> with null <code>ComponentVersion</code>.
     * NPE is expected.
     * </p>
     */
    public void testFindMatchingTemplateHierarchyNullParam1() throws Exception {

        try {
            // Should set the accessible to true.
            findMatchingTemplateHierarchy.setAccessible(true);
            findMatchingTemplateHierarchy.invoke(selector, new Object[]{null, hierarchy});
            fail("NPE is expected for null ComponentVersion.");
        } catch (InvocationTargetException ite) {
            assertTrue("NPE is expected for null ComponentVersion.",
                    ite.getTargetException()
                    instanceof NullPointerException);
            System.out.println(ite.getTargetException());
        }
    }
    
    /**
     * <p>
     * Test <code>findMatchingTemplateHierarchy</code> with null <code>TemplateHierarchy</code>.
     * NPE is expected.
     * </p>
     */
    public void testFindMatchingTemplateHierarchyNullParam2() throws Exception {

        try {
            // Should set the accessible to true.
            findMatchingTemplateHierarchy.setAccessible(true);
            findMatchingTemplateHierarchy.invoke(selector, new Object[]{version, null});
            fail("NPE is expected for null TemplateHierarchy.");
        } catch (InvocationTargetException ite) {
            assertTrue("NPE is expected for null TemplateHierarchy.",
                    ite.getTargetException()
                    instanceof NullPointerException);
            System.out.println(ite.getTargetException());
        }
    }


    /**
     * Test <code>selectTemplates</code> method when the <code>findMatchingTemplateHierarchy</code>
     * will return <code>null</code>, the length of returned array should equal to zero.
     *
     */
    public void testSelectTemplatesWithNullHierarchy() {
        ComponentVersion componentVersion = new ComponentVersion(777777);
        hierarchy.addTemplate(new Template(1, "One", "One", "One", "One"));
        hierarchy.addTemplate(new Template(2, "Two", "Two", "Two", "Two"));
        hierarchy.addTemplate(new Template(3, "Three", "Three", "Three", "Three"));

        assertTrue("The lenth of gottn template[] should equal to 3.",
                hierarchy.getTemplates().length == 3);
        assertTrue("The length of selected template[] should equal to zero.",
            new MockTemplateSelector().selectTemplates(componentVersion, hierarchy).length == 0);
    }
    
    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(TemplateSelectorFailureTest.class);
    }
}
