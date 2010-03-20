/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.serializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportSerializerException;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.impl.BaseCOOReportSerializer;
import com.topcoder.management.contest.coo.impl.ConfigurationException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Loop;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.NodeListUtility;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSourceException;

/**
 * <p>
 * This class represents XML COO Report Serializer. It extends from
 * <code>BaseCOOReportSerializer</code> to inherit the functionality of
 * <code>serializeCOOReportToFile</code>.
 * </p>
 * <p>
 * This class serializes the COO Report to XML file based on the given XML
 * template.
 * </p>
 * <p>
 * It implements <code>serializeCOOReportToByteArray</code> method by
 * utiilizing Document Generator component to read the XML template file and
 * replace the placeholders.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create XMLCOOReportSerializer from configuration
 * XMLCOOReportSerializer serializer = new XMLCOOReportSerializer(configuration);
 *
 * //generate report to &quot;output.xml&quot; file.
 * serializer.serializeCOOReportToFile(report, &quot;output.xml&quot;);
 *
 * //or generate report with default file name like &quot;COO_#projectId.xml&quot;
 * serializer.serializeCOOReportToFile(report, null);
 *
 * //or generate report to byte array
 * serializer.serializeCOOReportToByteArray(report);
 *
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread Safe:</strong> This class is thread safe since it is
 * immutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class XMLCOOReportSerializer extends BaseCOOReportSerializer {
    /**
     * Constructor.
     *
     * @param configuration The configuration object. must not be null.
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public XMLCOOReportSerializer(ConfigurationObject configuration)
        throws ConfigurationException {
        // delegate check to super class.
        super(configuration);
    }

    /**
     * <p>
     * Serialize the given report to byte array.
     * </p>
     * <p>
     * Open the XML template in templateFileName, use Document Generator to
     * replace placeholders and return the modified template.
     * </p>
     *
     * @param report The COO Report to be serialized. must not be null.
     * @return The byte array representing the serialized report
     * @throws COOReportSerializerException if any exception occurred when
     *             performing this method
     *
     */
    public byte[] serializeCOOReportToByteArray(COOReport report)
        throws COOReportSerializerException {
        // signature for logging.
        final String signature = "XMLCOOReportSerializer#serializeCOOReportToByteArray";
        Helper.logEnter(getLogger(), signature);
        Helper.checkNull(getLogger(), "report", report);
        List<Component> dependencyComponents;
        try {
            // initialize DocumentGenerator
            DocumentGenerator generator = new DocumentGenerator();

            // Create Template from DocumentGenerator.
            FileTemplateSource source = new FileTemplateSource();
            Template template = generator.getTemplate(source, this.getTemplateFilename());
            // get the list of fields
            TemplateFields fields = generator.getFields(template);

            // get the list of nodes
            Node[] nodes = fields.getNodes();

            // get dependencies
            List<ComponentDependency> dependencies = report.getComponentDependencies();
            dependencyComponents = new ArrayList<Component>();
            for (ComponentDependency dep : dependencies) {
                dependencyComponents.add(dep.getComponent());
            }

            // loop through each node and apply the template
            for (int i = 0; i < nodes.length; i++) {
                // ordinary field, replace the value with property
                if (nodes[i] instanceof Field) {
                    Field field = (Field) nodes[i];
                    processField(field, report);
                } else if (nodes[i] instanceof Loop) {
                    // only supports dependencies. In this case,
                    // populate the dependencies
                    Loop field = (Loop) nodes[i];
                    processLoop(field, dependencies);
                }
            }
            // Set it back to null
            Helper.setBackValue(dependencyComponents);
            // return the result
            return generator.applyTemplate(fields).getBytes();
        } catch (TemplateSourceException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("TemplateSourceException occur", e));
        } catch (TemplateFormatException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("TemplateFormatException occur", e));
        } catch (TemplateDataFormatException e) {
            throw Helper.logError(getLogger(),
                new COOReportSerializerException("invalid tempalte data format for xml serialization", e));
        } finally {
            Helper.logExit(getLogger(), signature);
        }
    }

    /**
     * <p>
     * process the ordinary field for COO report.
     * </p>
     *
     * @param field to process.
     * @param report the COO report.
     */
    private void processField(Field field, COOReport report) {
        if (field.getName().equals("projectId")) {
            field.setValue(report.getProjectId() + "");
        } else if (field.getName().equals("componentName")) {
            field.setValue(Helper.setDefaultNA(report.getContestData().getComponentName()));
        } else if (field.getName().equals("hasdependency")) {
            if (report.isDependenciesError()) {
                field.setValue("Yes, but cannot get all the dependencies, please check the dependency file.");
            } else if (report.getComponentDependencies().size() > 0) {
                field.setValue("Yes");
            } else {
                field.setValue("No");
            }
        } else if (field.getName().equals("contestEndDate")) {
            Date date = report.getContestData().getContestEndDate();
            if (date == null) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(date.toString()));
            }
        } else if (field.getName().equals("designWinner")) {
            field.setValue(Helper.setDefaultNA(report.getContestData().getDesignWinner()));
        } else if (field.getName().equals("designSecondPlace")) {
            field.setValue(Helper.setDefaultNA(report.getContestData().getDesignSecondPlace()));
        } else if (field.getName().equals("designReviewer1")) {
            List<String> reviewers = report.getContestData().getDesignReviewers();
            if (reviewers == null || reviewers.size() == 0) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(0)));
            }
        } else if (field.getName().equals("designReviewer2")) {
            List<String> reviewers = report.getContestData().getDesignReviewers();
            if (reviewers == null || reviewers.size() <= 1) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(1)));
            }
        } else if (field.getName().equals("designReviewer3")) {
            List<String> reviewers = report.getContestData().getDesignReviewers();
            if (reviewers == null || reviewers.size() <= 2) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(2)));
            }
        } else if (field.getName().equals("developmentWinner")) {
            field.setValue(Helper.setDefaultNA(report.getContestData().getDevelopmentWinner()));
        } else if (field.getName().equals("developmentSecondPlace")) {
            field.setValue(Helper.setDefaultNA(report.getContestData().getDevelopmentSecondPlace()));
        } else if (field.getName().equals("developmentReviewer1")) {
            List<String> reviewers = report.getContestData().getDevelopmentReviewers();
            if (reviewers == null || reviewers.size() == 0) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(0)));
            }
        } else if (field.getName().equals("developmentReviewer2")) {
            List<String> reviewers = report.getContestData().getDevelopmentReviewers();
            if (reviewers == null || reviewers.size() <= 1) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(1)));
            }
        } else if (field.getName().equals("developmentReviewer3")) {
            List<String> reviewers = report.getContestData().getDevelopmentReviewers();
            if (reviewers == null || reviewers.size() <= 2) {
                field.setValue("N/A");
            } else {
                field.setValue(Helper.setDefaultNA(reviewers.get(2)));
            }
        }

    }

    /**
     * <p>
     * process the Loop field for COO report.
     * </p>
     *
     * @param field the loop field to process.
     * @param dependencies the component dependency list.
     */
    private void processLoop(Loop field, List<ComponentDependency> dependencies) {
        // get element name.
        String name = field.getLoopElement();
        if (name.equals("dependencies")) {
            // get component from specific type and category.
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies, null, null);
            // populate loop
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-external")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                null, DependencyType.EXTERNAL);
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-internal")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                null, DependencyType.INTERNAL);
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-compile-external")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.COMPILE, DependencyType.EXTERNAL);
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-compile-internal")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.COMPILE, DependencyType.INTERNAL);
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-external-test")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.TEST, DependencyType.EXTERNAL);
            NodeListUtility.populateLoop(field, component);
        } else if (name.equals("dependencies-internal-test")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.TEST, DependencyType.INTERNAL);
            NodeListUtility.populateLoop(field, component);
        }
    }

}
