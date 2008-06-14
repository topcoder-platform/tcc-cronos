/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;
import com.topcoder.util.dependency.report.impl.CsvDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator;
import com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtility;


/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Demo API usage.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemoAPI() throws Exception {
        //Create configuration object for generator
        ConfigurationObject config = new DefaultConfigurationObject("config");
        //Include only internal components in the report
        config.setPropertyValue("dependency_types", "internal");
        //Include only compile dependencies in the report
        config.setPropertyValue("dependency_categories", "compile");
        //don't include information about dependency type and category in the report
        config.setPropertyValue("include_dependency_type", "false");
        config.setPropertyValue("include_dependency_category", "false");
        //Include information about dependency path in the report
        config.setPropertyValue("include_dependency_path", "true");

        //Get dependency entries persistence (see docs of Component Dependency Extractor)
        DependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence("/dependencies.xml");

        //Get dependency entries from persistence
        List < DependenciesEntry > entries = persistence.load();

        //Create XML report generator for entries list
        DependencyReportGenerator xmlGenerator = new XmlDependencyReportGenerator(entries, config);

        //Create CSV report generator for dependencies persistence
        DependencyReportGenerator csvGenerator = new CsvDependencyReportGenerator(persistence, config);

        //Create HTML report generator for entries list
        DependencyReportGenerator htmlGenerator = new HtmlDependencyReportGenerator(entries, config);

        //Get XML report for all available components as a string, include indirect dependencies
        String report = xmlGenerator.generateAll(true);
        System.out.println(report);

        //Create a list of component identifiers those must be included in the report
        List < String > componentIds = Arrays.asList(
            new String[]{"java-logging_wrapper-2.0.0", "dot_net-command_line_executor-1.0"});

        //Generate CSV report for the specified components, don't include indirect dependencies
        //Save the generated report to report.csv
        csvGenerator.generate(componentIds, "test_files/report.csv", false);
        System.out.println(this.readFile("test_files/report.csv"));

        //Create output stream for HTML report
        OutputStream reportOutputStream = new ByteArrayOutputStream();

        //Generate and save HTML report for specified component to the given output stream
        //Include indirect dependencies in the generated report
        htmlGenerator.generate(componentIds, reportOutputStream, true);
        System.out.println(reportOutputStream.toString());

    }

    /**
     * <p>
     * Demo indirect dependencies.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemoIndirect() throws Exception {

        //Construct the dependency entries
        Component componentA = new Component("A", "1.0", ComponentLanguage.JAVA);
        ComponentDependency componentB = new ComponentDependency("B", "1.0", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "PathB");
        ComponentDependency componentC = new ComponentDependency("C", "1.0", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "PathC");

        List < ComponentDependency > dependencyA = new ArrayList<ComponentDependency>();
        dependencyA.add(componentB);
        DependenciesEntry entryA = new DependenciesEntry(componentA, dependencyA);
        List < ComponentDependency > dependencyB = new ArrayList<ComponentDependency>();
        dependencyB.add(componentC);
        DependenciesEntry entryB = new DependenciesEntry(componentB, dependencyB);
        List < DependenciesEntry > entries = new ArrayList<DependenciesEntry>();
        entries.add(entryA);
        entries.add(entryB);

        //Create configuration object for generator
        ConfigurationObject config = new DefaultConfigurationObject("config");

        //Don't include information about dependency type, category
        config.setPropertyValue("include_dependency_type", "false");
        config.setPropertyValue("include_dependency_category", "false");

        //Create CSV report generator
        DependencyReportGenerator csvGenerator = new CsvDependencyReportGenerator(entries, config);

        //Generate the report for the component A with indirect dependencies
        List < String > componentIds = Arrays.asList(new String[]{"java-A-1.0"});
        String report = csvGenerator.generate(componentIds, true);

        //Print the report to the standard output
        System.out.println(report);

        //The code above should produce the following output:
        //java-A-1.0,B-1.0,C-1.0
    }

    /**
     * <p>
     * Demo command line utility.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemoCommandLineUtility() throws Exception {
        //This command line can be used to print out the usage string:
        DependencyReportGeneratorUtility.main(new String[]{"-help"});

        //If all required configuration for the utility is stored in file config.xml,
        //then the application can be executed without additional arguments:
        DependencyReportGeneratorUtility.main(new String[]{});

        //To use the custom configuration file the user can use "-c" switch:
        DependencyReportGeneratorUtility.main(new String[]{"-c", "custom_config.xml"});

        //The user can specify the input and output file names:
        DependencyReportGeneratorUtility.main(new String[]{"-i", "/dependencies.xml", "-o", "test_files/report.html"});

        //The user can specify the custom persistence options:
        DependencyReportGeneratorUtility.main(
            new String[]{"-pclass", CustomDependenciesEntryPersistence.class.getName(), "-i", "custom.dat"
            });

        //Generator implementation class can be specified as command line argument:
        DependencyReportGeneratorUtility.main(
            new String[]{"-gclass", MockDependencyReportGenerator.class.getName()});

        //For easiness "-f" switch can be used to specify one of XML, CSV and HTML output report formats:
        DependencyReportGeneratorUtility.main(new String[]{"-f", "html", "-o", "test_files/report.html"});

        //To specify that only external component dependencies must be included in the report the user can
        //use "-dtype" switch:
        DependencyReportGeneratorUtility.main(new String[]{"-dtype", "external"});

        //To specify that only test dependencies must be included in the report the user can use "-dcat" switch:
        DependencyReportGeneratorUtility.main(new String[]{"-dcat", "test"});

        //To include in the report direct dependencies only this argument can be used:
        DependencyReportGeneratorUtility.main(new String[]{"-noindirect"});

        //To generate report for Java Logging Wrapper 2.0.0 and Command Line Executor 1.0 only the user can use
        //"-id" switch in the following way:
        DependencyReportGeneratorUtility.main(new String[]{"-id",
            "dot_net-command_line_executor-1.0;java-logging_wrapper-2.0.0"});

        //Usually the command line will look like the following one:
        DependencyReportGeneratorUtility.main(new String[]{
            "-c", "custom_config.xml",
            "-f", "xml",
            "-i", "/dependencies.xml",
            "-o", "test_files/report.xml",
            "-id", "dot_net-command_line_executor-1.0;java-logging_wrapper-2.0.0"});
    }
}
