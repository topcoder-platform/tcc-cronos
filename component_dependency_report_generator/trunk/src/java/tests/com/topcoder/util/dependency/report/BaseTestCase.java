/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Base test case for Unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class BaseTestCase extends TestCase {

    /**
     * <p>
     * Represents int 3.
     * </p>
     */
    public static final int THREE = 3;

    /**
     * <p>
     * Represents int 4.
     * </p>
     */
    public static final int FOUR = 4;

    /**
     * <p>
     * Represents int 5.
     * </p>
     */
    public static final int FIVE = 5;

    /**
     * <p>
     * Represents int 6.
     * </p>
     */
    public static final int SIX = 6;

    /**
     * <p>
     * Represents int 7.
     * </p>
     */
    public static final int SEVEN = 7;

    /**
     * <p>
     * Represents int 8.
     * </p>
     */
    public static final int EIGHT = 8;

    /**
     * <p>
     * Represents int 9.
     * </p>
     */
    public static final int NINE = 9;

    /**
     * <p>
     * Represents int 10.
     * </p>
     */
    public static final int TEN = 10;

    /**
     * <p>
     * The name of report file.
     * </p>
     */
    public static final String FILE = "ReportFile";

    /**
     * <p>
     * The mock of <code>PERSISTENCE</code>.
     * </p>
     */
    public static final DependenciesEntryPersistence PERSISTENCE =
        EasyMock.createNiceMock(DependenciesEntryPersistence.class);

    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    public static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    public static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    public static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    public static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Delete the report file if any.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        File file = new File(FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * <p>
     * Create a new instance of <code>DependenciesEntry</code>.
     * </p>
     *
     * @param name The name of component.
     * @param version The version of component.
     * @param language The language of component.
     * @param dependenciesNum The number of component dependencies.
     *
     * @return The new instance of <code>DependenciesEntry</code>.
     */
    protected DependenciesEntry createDependenciesEntry(String name, String version, ComponentLanguage language
        , int dependenciesNum) {
        Component component = new Component(name, version, language);
        return this.createDependenciesEntry(component, dependenciesNum);
    }


    /**
     * <p>
     * Create a new instance of <code>DependenciesEntry</code>.
     * </p>
     *
     * @param component The component.
     * @param dependenciesNum The number of component dependencies.
     *
     * @return The new instance of <code>DependenciesEntry</code>.
     */
    protected DependenciesEntry createDependenciesEntry(Component component, int dependenciesNum) {

        @SuppressWarnings("unchecked")
        List < ComponentDependency > dependencies = new ArrayList();

        for (int i = 1; i <= dependenciesNum; i++) {
            String name = component.getName() + "_dependency" + i;

            //The DependencyType is INTERNAL for even, EXTERNAL for odd
            DependencyType type = i % 2 == 0 ? DependencyType.INTERNAL : DependencyType.EXTERNAL;

            //The DependencyCategory is COMPILE for even, TEST for odd
            DependencyCategory category = i % 2 == 0 ? DependencyCategory.COMPILE : DependencyCategory.TEST;

            String version = i + ".0";

            dependencies.add(new ComponentDependency(
                name,
                version,
                component.getLanguage(),
                category,
                type,
                (type == DependencyType.INTERNAL ? "tcs" : "third_party") + "/" + name
                + "/" + version + "/" + name
                + (component.getLanguage() == ComponentLanguage.JAVA ? ".jar" : ".dll")));
        }

        DependenciesEntry entry = new DependenciesEntry(component, dependencies);
        return entry;
    }

    /**
     * <p>
     * Create a new instance of <code>ComponentDependency</code>.
     * </p>
     *
     * @param component The dependency component.
     * @param type The dependency type.
     * @param category The dependency category.
     *
     * @return The new instance of <code>ComponentDependency</code>.
     */
    protected ComponentDependency createComponentDependency(Component component, DependencyType type,
        DependencyCategory category) {
        return new ComponentDependency(
                component.getName(),
                component.getVersion(),
                component.getLanguage(),
                category,
                type,
                (type == DependencyType.INTERNAL ? "tcs" : "third_party") + "/" + component.getName()
                + "/" + component.getVersion() + "/" + component.getName()
                + (component.getLanguage() == ComponentLanguage.JAVA ? ".jar" : ".dll"));
    }

    /**
     * <p>
     * Read the content of file.
     * </p>
     *
     * @param file The file to read its content.
     *
     * @return The content of file.
     *
     * @throws Exception to JUnit.
     */
    protected String readFile(String file) throws Exception {
        InputStream input = null;
        if (new File(file).exists()) {
            input = new FileInputStream(file);
        } else {
            input = this.getClass().getResourceAsStream(file);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuffer sb = new StringBuffer();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        input.close();
        return sb.toString().trim();
    }
}
