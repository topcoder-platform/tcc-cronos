/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.serializer;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.List;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.io.XOutputStream;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;

import com.sun.star.text.XText;

import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextField;
import com.sun.star.text.XTextFieldsSupplier;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextTable;
import com.sun.star.uno.Any;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

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

/**
 * <p>
 * This class represents PDF COO Report Serializer. It extends from
 * <code>BaseCOOReportSerializer</code> to inherit the functionality of
 * <code>serializeCOOReportToFile</code>
 * </p>
 * <p>
 * This class serializes the COO Report to PDF file based on the given MS
 * Word/OpenOffice template.
 * </p>
 * <p>
 * It implements serializeCOOReportToByteArray method by utilizing Open Office
 * API 3rd party component to read the template and replacing all the
 * placeholders and finally exporting to PDF.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create PDFCOOReportSerializer from configuration
 * PDFCOOReportSerializer serializer = new PDFCOOReportSerializer(configuration);
 *
 * //generate report to &quot;output.pdf&quot; file.
 * serializer.serializeCOOReportToFile(report, &quot;output.pdf&quot;);
 *
 * //or generate report with default file name like &quot;COO_#projectId.pdf&quot;
 * serializer.serializeCOOReportToFile(report, null);
 *
 * //or generate report to byte array
 * serializer.serializeCOOReportToByteArray(report);
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread safe:</strong> This class is thread safe since it is
 * immutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class PDFCOOReportSerializer extends BaseCOOReportSerializer {

    /**
     * Represent the string for replacing of current date in word template.
     */
    private static final String CURRENTDATE = "CURRENT DATE";

    /**
     * Represent the string for replacing of language  in word template.
     */
    private static final String LANGUAGE = "LANGUAGE";

    /**
     * Represent the Date Format instance for date format conversion.
     */
    private static final DateFormat DATEFORMAT = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);

    /**
     * byte array output stream used to store the result.
     */
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param configuration The configuration object. must not be null.
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     */
    public PDFCOOReportSerializer(ConfigurationObject configuration)
        throws ConfigurationException {
        // delegate check to super class
        super(configuration);
    }

    /**
     * Serialize the given report to byte array.
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
        final String signature = "PDFCOOReportSerializer#serializeCOOReportToByteArray";
        Helper.logEnter(getLogger(), signature);
        Helper.checkNull(getLogger(), "COOReport", report);

        try {
            List<ComponentDependency> dependencies = report.getComponentDependencies();
            // initialize the open office API and get XComponent.
            XComponent xComponent = getXTextDocument();
            // Put the template data into the document.
            XTextFieldsSupplier xTextFieldsSupplier = (XTextFieldsSupplier) UnoRuntime.queryInterface(
                XTextFieldsSupplier.class, xComponent);

            XEnumeration e = xTextFieldsSupplier.getTextFields().createEnumeration();
            // iterate each merge field in template file.
            while (e.hasMoreElements()) {
                Any tf = (Any) e.nextElement();
                XTextField xtf = (XTextField) tf.getObject();
                XPropertySet xPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xtf);
                String fieldCode = (String) xPropertySet.getPropertyValue("FieldCode");
                XTextRange xTextRange = xtf.getAnchor();
                // find the merge field in word template file
                if (fieldCode.trim().startsWith("MERGEFIELD")) {
                    // extract the field string
                    fieldCode = fieldCode.replaceAll("(MERGEFIELD | MERGEFORMAT|\\*|\\\\)", "");
                    fieldCode = fieldCode.replace("\"", "").trim();
                    // process field code.
                    processFieldCode(fieldCode, report, xPropertySet, xComponent, dependencies, xTextRange);
                }
            }
            prepareXStore(xComponent);
            return bos.toByteArray();
        } catch (IOException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("some I/O exception occur.", e));
        } catch (com.sun.star.lang.IllegalArgumentException e) {
            throw Helper.logError(getLogger(),
                new COOReportSerializerException("Illgeal argumet found while serializeCOOReportToByteArray", e));
        } catch (NoSuchElementException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("element not found in XML file", e));
        } catch (WrappedTargetException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("WrappedTargetException occur.", e));
        } catch (UnknownPropertyException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("Unknown Property found.", e));
        } catch (PropertyVetoException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("PropertyVetoException occur.", e));
        } catch (BootstrapException e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("fail to do bootstrap.", e));
        } catch (com.sun.star.uno.Exception e) {
            throw Helper.logError(getLogger(), new COOReportSerializerException("com.sun.star.Exception occur.", e));
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (java.io.IOException e) {
                    // ignore
                }
            }
            Helper.logExit(getLogger(), signature);
        }
    }

    
    /**
     * <p>
     * prepare XStore to export PDF to out put stream.
     * </p>
     *
     * @param xComponent the XComponent.
     * @throws IOException if fail to prepare XStore.
     */
    private void prepareXStore(XComponent xComponent) throws IOException {
        PropertyValue[] storeProperties = new PropertyValue[2];
        XStorable store = (XStorable) UnoRuntime.queryInterface(XStorable.class, xComponent);
        storeProperties[0] = new PropertyValue();
        storeProperties[0].Name = "OutputStream";
        storeProperties[0].Value = new XOutputStreamDelegate();

        // write to PDF format.
        storeProperties[1] = new PropertyValue();
        storeProperties[1].Name = "FilterName";

        // set the property to PDF or MS Word.
        if (getIsPDF()) {
            storeProperties[1].Value = "writer_pdf_Export";
        } else {
            storeProperties[1].Value = "MS Word 97";
        }
        // store the stream to URL
        store.storeToURL("private:stream", storeProperties);
    }

    /**
     * <p>
     * process field code retrieved from template file.
     * </p>
     *
     * @param fieldCode field code to process.
     * @param report the COO report.
     * @param xPropertySet the XPropertySet
     * @param xComponent the XComponent
     * @param dependencies the component dependency list.
     * @param xTextRange as the text range need to be replace.
     * @throws com.sun.star.uno.Exception if fail to process field code.
     */
    private void processFieldCode(String fieldCode, COOReport report,
            XPropertySet xPropertySet, XComponent xComponent,
            List<ComponentDependency> dependencies, XTextRange xTextRange) throws Exception {
        String value = null;
        if (fieldCode.equals("componentName")) {
            value = Helper.setDefaultNA(report.getContestData().getComponentName());
        } else if (fieldCode.equals("contestEndDate")) {
            value = Helper.setDefaultNA(report.getContestData().getContestEndDate().toString());
        } else if (fieldCode.equals(CURRENTDATE)) {
        	value = DATEFORMAT.format(new Date());
        } else if (fieldCode.equals(LANGUAGE)) {
            value = getLanguage();
        } else if (fieldCode.equals("designWinner")) {
            value = Helper.setDefaultNA(report.getContestData().getDesignWinner());
        } else if (fieldCode.equals("designSecondPlace")) {
            value = Helper.setDefaultNA(report.getContestData().getDesignSecondPlace());
        } else if (fieldCode.equals("designReviewer1")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDesignReviewers(), 0));
        } else if (fieldCode.equals("designReviewer2")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDesignReviewers(), 1));
        } else if (fieldCode.equals("designReviewer3")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDesignReviewers(), 2));
        } else if (fieldCode.equals("developmentWinner")) {
            value = Helper.setDefaultNA(report.getContestData().getDevelopmentWinner());
        } else if (fieldCode.equals("developmentSecondPlace")) {
            value = Helper.setDefaultNA(report.getContestData().getDevelopmentSecondPlace());
        } else if (fieldCode.equals("developmentReviewer1")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDevelopmentReviewers(), 0));
        } else if (fieldCode.equals("developmentReviewer2")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDevelopmentReviewers(), 1));
        } else if (fieldCode.equals("developmentReviewer3")) {
            value = Helper.setDefaultNA(getListElement(report.getContestData().getDevelopmentReviewers(), 2));
        } else if (fieldCode.equals("projectId")) {
            value = report.getProjectId() + "";
        } else if (fieldCode.equals("hasdependency")) {
        	if (dependencies == null) {
        		value = "No";
        	} else {
                if (report.isDependenciesError()) {
                    value = "Yes, but cannot get all the dependencies, please check the dependency file.";
                } else if (dependencies.size() == 0) {
                    value = "No";
                } else {
                    value = "Yes";
                }
        	}
        } else if (fieldCode.equals("Dependencies")) {
            // get component list with specific type and category
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies, null, null);
            xPropertySet.setPropertyValue("Content", "Dependencies");
            // generate dependency table report
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_external")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                null, DependencyType.EXTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_external");
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_internal")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                null, DependencyType.INTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_internal");
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_compile_external")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.COMPILE, DependencyType.EXTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_compile_external");
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_test_external")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.TEST, DependencyType.EXTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_test_external");
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_compile_internal")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.COMPILE, DependencyType.INTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_compile_internal");
            generateDependency(component, xComponent, xTextRange);
            return;
        } else if (fieldCode.equals("Dependencies_test_internal")) {
            List<Component> component = Helper.getComponentByTypeAndCategory(dependencies,
                DependencyCategory.TEST, DependencyType.INTERNAL);
            xPropertySet.setPropertyValue("Content", "Dependencies_test_internal");
            generateDependency(component, xComponent, xTextRange);
            return;
        }

        xPropertySet.setPropertyValue("Content", value);
    }

    /**
     * Gets element from list.
     * @param list the list of strings.
     * @param index the index of element to get.
     * @return element from list.
     */
    private String getListElement(List<String> list, int index) {
        if (list == null || index >= list.size()) {
            return "N/A";
        }
        return list.get(index);
    }

    /**
     * generate component dependencies table for report.
     *
     * @param components as the components to report.
     * @param document as the XComponent in open office.
     * @param xTextRange as the xTextRange to replace.
     * @throws com.sun.star.uno.Exception the open office Exception
     */
    private void generateDependency(List<Component> components,
            XComponent document, XTextRange xTextRange) throws Exception {
        // get service factory from document.
        XMultiServiceFactory xMSF = (XMultiServiceFactory) UnoRuntime.queryInterface(
            XMultiServiceFactory.class, document);

        // create table in word file.
        XTextTable xTextTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class,
            xMSF.createInstance("com.sun.star.text.TextTable"));
        xTextTable.initialize(components.size() + 1, 5);

        XTextDocument doc = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, document);

        // Find the right place for Cursor.
        XTextCursor xTC = doc.getText().createTextCursor();
        xTC.gotoRange(xTextRange, false);
        xTC.goRight((short) 2, false);

        doc.getText().insertTextContent( xTC, xTextTable, false );

        XCellRange xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTextTable);

        // generate the title row in table
        setValueForCell(xCellRange, "Name", 0, 0);
        setValueForCell(xCellRange, "Version", 0, 1);
        setValueForCell(xCellRange, "Description", 0, 2);
        setValueForCell(xCellRange, "SourceURL", 0, 3);
        setValueForCell(xCellRange, "Licence", 0, 4);

        // if any field in component is null or empty, report "NONE"
        Helper.resetNullValue(components);
        // generate the component dependency row in table.
        int idx = 1;
        for (Component component : components) {
            if (component.getFullName() != null && !component.getFullName().equals(""))
            {
                setValueForCell(xCellRange, component.getFullName(), idx, 0);
            }
            else
            {
                setValueForCell(xCellRange, component.getName(), idx, 0);
            }
            setValueForCell(xCellRange, component.getVersion(), idx, 1);
            setValueForCell(xCellRange, component.getDescription(), idx, 2);
            setValueForCell(xCellRange, component.getUrl(), idx, 3);
            setValueForCell(xCellRange, component.getLicense(), idx++, 4);
        }
        // Set field of component back to null
        Helper.setBackValue(components);
    }

    /**
     * set value for each cell in table.
     *
     * @param xCellRange the cell range.
     * @param value for each cell.
     * @param i column index
     * @param j row index
     * @throws IndexOutOfBoundsException if index out of bound.
     */
    private void setValueForCell(XCellRange xCellRange, String value, int i,
            int j) throws IndexOutOfBoundsException {
        // locate the cell position
        XCell xCell = xCellRange.getCellByPosition(j, i);
        XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
        XTextCursor xTC = xCellText.createTextCursor();
        // fill the cell with value
        xCellText.getText().insertString(xTC, value, false);
    }

    /**
     * Get a XTextDocument object from word file.
     *
     * @return XTextDocument the XTextDocument object for the word file.
     * @throws BootstrapException if fail to start open office.
     * @throws com.sun.star.uno.Exception if fail to get XComponent
     */
    private XComponent getXTextDocument()
        throws BootstrapException, Exception {
        XComponentContext context = Bootstrap.bootstrap();
        Object desktop = context.getServiceManager().createInstanceWithContext(
            "com.sun.star.frame.Desktop", context);
        XComponentLoader loader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class, desktop);
        PropertyValue[] properties = new PropertyValue[1];
        properties[0] = new PropertyValue();
        properties[0].Name = "Hidden";
        properties[0].Value = Boolean.TRUE;
        String fileURL;
        File file = new File(getTemplateFilename());
        if (file.exists()) {
            // local file exist.
            fileURL = "file:///"
                    + new File(getTemplateFilename()).getAbsolutePath();
        } else {
            // remote file
            fileURL = getTemplateFilename();
        }
        /* load document from URL */
        return loader.loadComponentFromURL(fileURL, "_blank", 0, properties);
    }

    /**
     * <p>
     * The XStoreable can only be store to XOutputStream, So just use a inner
     * XOutputStream class to delegate.
     * </p>
     * <p>
     * <strong>Thread safe:</strong> This class is thread safe since it is
     * immutable.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     *
     */
    private class XOutputStreamDelegate implements XOutputStream {
        /**
         * close output stream.
         *
         * @throws IOException if fail to close output.
         */
        public void closeOutput() throws IOException {
            try {
                bos.close();
            } catch (java.io.IOException e) {
                // impossible for ByteArrayOutputStream
            }
        }

        /**
         * flush output stream.
         *
         * @throws IOException if fail to flush output.
         */
        public void flush() throws IOException {
            try {
                bos.flush();
            } catch (java.io.IOException e) {
                // impossible for ByteArrayOutputStream
            }
        }

        /**
         * write byte array to stream.
         *
         * @param arg0 the byte array to write.
         * @throws IOException if fail to flush output.
         */
        public void writeBytes(byte[] arg0) throws IOException {
            try {
                bos.write(arg0);
            } catch (java.io.IOException e) {
                // impossible for ByteArrayOutputStream
            }
        }
    }
}
