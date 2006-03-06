/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * XMLValidationOutputFormatter.java
 */
package com.topcoder.apps.screening.applications.specification.impl.formatters;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * This class is a concrete formatter for the validation output. It converts the data to the XML format.
 * </p>
 *
 * <p>
 * Format of the output data:
 *
 * <pre>
 * &lt;validation&gt;
 * &lt;errors&gt;
 * &lt;diagram id=&quot;$diagramId&quot; type=&quot;$diagramType&quot; name=&quot;$diagramName&quot;&gt;
 * &lt;element id=&quot;$elementId&quot; type=&quot;$elementType&quot; name=&quot;$elementName&quot;&gt;
 * &lt;error&gt;details string&lt;/error&gt;
 * &lt;error&gt;...&lt;/error&gt;
 * &lt;/element&gt;
 * ... // more elements
 * &lt;/diagram&gt;
 * ... // more diagrams
 * &lt;/errors&gt;
 * &lt;reports&gt;
 * &lt;diagram id=&quot;$diagramId&quot; type=&quot;$diagramType&quot; name=&quot;$diagramName&quot;&gt;
 * &lt;element id=&quot;$elementId&quot; type=&quot;$elementType&quot; name=&quot;$elementName&quot;&gt;
 * &lt;report&gt;details string&lt;/report&gt;
 * &lt;report&gt;...&lt;/report&gt;
 * &lt;/element&gt;
 * ... // more elements
 * &lt;/diagram&gt;
 * ... // more diagrams
 * &lt;/reports&gt;
 * &lt;/validation&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class XMLValidationOutputFormatter implements ValidationOutputFormatter {

    /**XML definition.*/
    private static final String XML_DEFINITION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /**'Validation' node name.*/
    private static final String VALIDATION_NODE = "validation";

    /**'Errors' node name.*/
    private static final String ERRORS_NODE = "errors";

    /**'Error' node name.*/
    private static final String ERROR_NODE = "error";

    /**'Reports' node name.*/
    private static final String REPORTS_NODE = "reports";

    /**'Report' node name.*/
    private static final String REPORT_NODE = "report";

    /**'Diagram' node name.*/
    private static final String DIAGRAM_NODE = "diagram";

    /**'Element' node name.*/
    private static final String ELEMENT_NODE = "element";

    /**'id' attribute name.*/
    private static final String ID_ATTR = "id";

    /**'type' attribute name.*/
    private static final String TYPE_ATTR = "type";

    /**'name' attribute name.*/
    private static final String NAME_ATTR = "name";

    /**Open bracket.*/
    private static final String OPEN_BRACKET = "<";

    /**Close bracket.*/
    private static final String CLOSE_BRACKET = ">";

    /**Open bracket with slash.*/
    private static final String CLOSE_NODE = "</";

    /**New line symbol.*/
    private static final String NEW_LINE = "\n";

    /**Equality symbol with quot.*/
    private static final String EQUAL = "=\"";

    /**Quot with empty space.*/
    private static final String QUOT = "\" ";

    /**Quot with close bracket.*/
    private static final String QUOT_WITH_BRACKET = "\">";

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public XMLValidationOutputFormatter() {
        // does nothing
    }

    /**
     * <p>
     * The formatter method which converts the given validation output to the XML representation.
     * Note, that returned array contains only one element.
     * </p>
     *
     * <p>
     * Next aggregation is done during formatting:
     * <ul>
     * </ul>
     * <li>all report information is placed under &lt;reports&gt; node and all error information
     *     is placed under &lt;errors&gt; node</li>
     * <li>all errors/reports for a particular diagram should be grouped within the XML node for this diagram
     *     (diagrams with null id are treated as separate)</li>
     * <li>all errors/reports for a particular element should be grouped within the XML node for this element
     *     (which is placed within the XML node for the corresponding diagram; elements with null id are
     *     treated as separate)</li>
     * </p>
     *
     * @param output the data to format
     * @return the formatted output
     * @throws IllegalArgumentException if the array or any item in the array is null
     */
    public String[] format(ValidationOutput[] output) {
        if (output == null) {
            throw new IllegalArgumentException("ValidationOutput array must not be null.");
        }
        for (int i = 0, n = output.length; i < n; i++) {
            if (output[i] == null) {
                throw new IllegalArgumentException("ValidationOutput array must not contain null.");
            }
        }

        /**
         * Algorithm is next:
         * check whether output contains diagram or not; if does not contain, than details message
         * will be put directly under errors/details nodes (we will collect them to errorBuffer/reportBuffer);
         * if diagram exists, firstly we check whether id is null or not. If it is null, than we will add
         * it to errorNullDiagrams/reportNullDiagrams list (diagrams with null id is treatsed as separate).
         * Than we check whether diagram with such id was processed or not; if it was not, than we create
         * new DiagramOutput(it contains all errors/reports and elements, related diagram) instance and
         * put it to map. If it was already processed, we get it from map and call on it method so that
         * to add this output (it may contain detail about this diagram, or about its element).
         * At the end, firstly we wil add details under <errors>(<reports>) node, than add all
         * diagrams with null id, than add rest of diagrams.
         */
        StringBuffer errorBuffer = new StringBuffer();
        StringBuffer reportBuffer = new StringBuffer();
        Map errorDiagrams = new HashMap();
        List errorNullDiagrams = new ArrayList();
        Map reportDiagrams = new HashMap();
        List reportNullDiagrams = new ArrayList();
        for (int i = 0, n = output.length; i < n; i++) {
            if (output[i].getType() == ValidationOutputType.ERROR) {
                processOutput(output[i], errorBuffer, errorDiagrams, errorNullDiagrams, ERROR_NODE);
            } else {
                processOutput(output[i], reportBuffer, reportDiagrams, reportNullDiagrams, REPORT_NODE);
            }
        }

        StringBuffer result = new StringBuffer(XML_DEFINITION);
        //add root node 'Validation'
        result.append(OPEN_BRACKET).append(VALIDATION_NODE).append(CLOSE_BRACKET).append(NEW_LINE);
        //first ERROR outputs are added

        errorNullDiagrams.addAll(errorDiagrams.values());
        fillResult(result, errorBuffer.toString(), errorNullDiagrams, ERRORS_NODE);

        //add REPORT outputs
        reportNullDiagrams.addAll(reportDiagrams.values());
        fillResult(result, reportBuffer.toString(), reportNullDiagrams, REPORTS_NODE);

        //close 'Validation' node
        result.append(CLOSE_NODE).append(VALIDATION_NODE).append(CLOSE_BRACKET);

        return new String[]{result.toString()};
    }

    /**
     * <p>
     * This method will do next:
     * 1. If there is no diagram, than we add to detailsBuffer new ERROR/REPORT node with details.
     * 2. If diagram not null:
     *    a) if diagram's id presents in map, than we get value for this id (it is DiagramOutput object),
     *       and call on it addOutput(output) - this will process in proper way output (if output
     *       has no element, than details message will be input directly to the diagram, if element
     *       exist, than message will be added to element node)
     *    b) if diagram does not exist, than new DiagramOutput object will be created and added
     *       to map (with id as key)
     * </p>
     *
     * @param output ValidationOutput that will be processed
     * @param detailsBuffer buffer that contains ERROR/REPORT details, that concerns not diagram,
     *                      but whole validation (submission)
     * @param diagrams map that store processed diagrams (id->DiagramOutput)
     * @param nullDiagrams list that contains diagrams with null id
     * @param nodeName indicates whether we process ERRORs or REPORTs
     */
    private void processOutput(ValidationOutput output,
                               StringBuffer detailsBuffer,
                               Map diagrams,
                               List nullDiagrams,
                               String nodeName) {
        if (output.getDiagram() == null) {
            if (detailsBuffer.length() != 0) {
                detailsBuffer.append(NEW_LINE);
            }
            detailsBuffer.append(addValueToNode(nodeName, output.getDetails()));
        } else {
            UMLDiagram diagram = output.getDiagram();
            if (diagram.getDiagramId() == null) {
                DiagramOutput diagramOutput = new DiagramOutput(output, nodeName);
                nullDiagrams.add(diagramOutput);
                return;
            }
            Object objDiagram = diagrams.get(diagram.getDiagramId());
            if (objDiagram == null) {
                DiagramOutput diagramOutput = new DiagramOutput(output, nodeName);
                diagrams.put(output.getDiagram().getDiagramId(), diagramOutput);
            } else {
                ((DiagramOutput) objDiagram).addOutput(output);
            }
        }
    }

    /**
     * <p>
     * This method creates &lt;Errors&gt; or &lt;Reports&gt; node, and add all details,
     * diagrams to it.
     * </p>
     *
     * @param result buffer to which we will add new node
     * @param details string that contains errors or reports that concerns whole validation(submission)
     * @param diagrams diagrams that have errors/reports
     * @param nodeName indicates whether we add &lt;Errors&gt; or &lt;Reports&gt; node
     */
    private void fillResult(StringBuffer result,
                            String details,
                            Collection diagrams,
                            String nodeName) {
        result.append(OPEN_BRACKET).append(nodeName).append(CLOSE_BRACKET);
        if (details.length() != 0) {
            result.append(NEW_LINE);
        }
        result.append(details).append(NEW_LINE);
        for (Iterator iter = diagrams.iterator(); iter.hasNext();) {
            DiagramOutput nextDiagram = (DiagramOutput) iter.next();
            result.append(nextDiagram);
        }
        result.append(CLOSE_NODE).append(nodeName).append(CLOSE_BRACKET).append(NEW_LINE);
    }

    /**
     * <p>
     * This method simply create new node with specified name and value.
     * </p>
     *
     * @param nodeName node name
     * @param details node value
     * @return new node
     */
    private String addValueToNode(String nodeName, String details) {
        return new StringBuffer(OPEN_BRACKET).append(nodeName).append(CLOSE_BRACKET).
                append(details).append(CLOSE_NODE).append(nodeName).append(CLOSE_BRACKET).toString();
    }

    /**
     * <p>
     * This class contains all info of &lt;Diagram&gt; node.
     * It was created so that it was easier to process details that conerns the same diagrams
     * (details, that concerns the same element inside diagram too).
     * </p>
     */
    private class DiagramOutput {
        /**Diagram's id.*/
        private String id;

        /**Diagram's type.*/
        private String type;

        /**Diagram's name.*/
        private String name;

        /**Details node name (Error/Report).*/
        private String detailsNodeName;

        /**Diagram's elements.*/
        private Map elements;

        /**Diagram's elements with null id.*/
        private List nullElements;

        /**Details nodes that belongs to this diagram.*/
        private StringBuffer details;

        /**
         * <p>
         * Constructor.
         * </p>
         *
         * @param output ValidationOutput
         * @param detailsNodeName details node name (Error/Report)
         */
        public DiagramOutput(ValidationOutput output, String detailsNodeName) {
            UMLDiagram diagram = output.getDiagram();
            id = diagram.getDiagramId();
            type = diagram.getDiagramType();
            name = diagram.getDiagramName();
            this.detailsNodeName = detailsNodeName;
            elements = new HashMap();
            details = new StringBuffer();
            nullElements = new ArrayList();
            addOutput(output);
        }

        /**
         * <p>
         * This method will do next:
         * 1. If there is no element, than we add to details buffer new ERROR/REPORT node with details.
         * 2. If element not null:
         *    a) if element's id presents in map, than we get value for this id (it is ElementOutput object),
         *       and call on it addDetails(output) - this will add new detail to element
         *    b) if element does not exist, than new ElementOutput object will be created and added
         *       to map (with id as key)
         * </p>
         *
         * @param output ValidationOutput that concerns this diagram
         */
        public void addOutput(ValidationOutput output) {
            //output can contains element or not
            if (output.getElement() == null) {
                //add Error/Report to diagram
                details.append(OPEN_BRACKET).append(detailsNodeName).append(CLOSE_BRACKET).append(output.getDetails()).
                        append(CLOSE_NODE).append(detailsNodeName).append(CLOSE_BRACKET).append("\n");
            } else {
                //store element
                UMLElement element = output.getElement();
                if (element.getElementId() == null) {
                    ElementOutput newElem = new ElementOutput(output, detailsNodeName);
                    nullElements.add(newElem);
                    return;
                }

                Object obj = elements.get(element.getElementId());
                if (obj == null) {
                    ElementOutput newElem = new ElementOutput(output, detailsNodeName);
                    elements.put(element.getElementId(), newElem);
                    return;
                }
                ((ElementOutput) obj).addDetail(output.getDetails());
            }
        }

        /**
         * <p>
         * Returns element's representation as node:
         *
         * &lt;Diagram&gt;
         * &lt;Error&gt; //or Report
         *  Error text
         * &lt;/Error&gt;
         * .........
         * &lt;Elements&gt;
         * &lt;Error&gt; //or Report
         *  Error text
         * &lt;/Error&gt;
         * ..........
         * &lt;/Elements&gt;
         * .......
         * &lt;Diagram&gt;
         * </p>
         *
         * @return diagram as node
         */
        public String toString() {
            StringBuffer result = new StringBuffer();
            result.append(OPEN_BRACKET).append(DIAGRAM_NODE).append(" ").
                    append(ID_ATTR).append(EQUAL).append(id).append(QUOT).
                    append(NAME_ATTR).append(EQUAL).append(name).append(QUOT).
                    append(TYPE_ATTR).append(EQUAL).append(type).append(QUOT_WITH_BRACKET).append(NEW_LINE);
            result.append(details);

            //add elements that has null id
            for (Iterator iter = nullElements.iterator(); iter.hasNext();) {
                result.append(iter.next());
            }

            //add all elements
            for (Iterator iter = elements.values().iterator(); iter.hasNext();) {
                result.append(iter.next());
            }
            result.append(CLOSE_NODE).append(DIAGRAM_NODE).append(CLOSE_BRACKET).append(NEW_LINE);
            return result.toString();
        }
    }

    /**
     * <p>
     * This is a helper class.
     * It is created by DiagramOutput, and has methods so that it was easier to add new
     * error/report to it (amethod addDetails()), and overriden method toString(),
     * that returns &lt;Element&gt; node with all details.
     * </p>
     */
    private class ElementOutput {
        /**Element's id.*/
        private String id;

        /**Element's type.*/
        private String type;

        /**Element's name.*/
        private String name;

        /**Details node name (Error/Report).*/
        private String detailsNodeName;

        /**Details nodes that belongs to this element.*/
        private StringBuffer details;

        /**
         * <p>
         * Constructor.
         * </p>
         *
         * @param output ValidationOutput
         * @param detailsNodeName details node name (Error/Report)
         */
        public ElementOutput(ValidationOutput output, String detailsNodeName) {
            UMLElement element = output.getElement();
            id = element.getElementId();
            type = element.getElementType();
            name = element.getElementName();
            this.detailsNodeName = detailsNodeName;
            details = new StringBuffer();

            addDetail(output.getDetails());
        }

        /**
         * <p>
         * Add next details node to element.
         * </p>
         *
         * @param details details string
         */
        public void addDetail(String details) {
            this.details.append(OPEN_BRACKET).append(detailsNodeName).append(CLOSE_BRACKET).append(details).
                    append(CLOSE_NODE).append(detailsNodeName).append(CLOSE_BRACKET).append(NEW_LINE);
        }

        /**
         * <p>
         * Returns element's representation as node:
         * &lt;Element&gt;
         * &lt;Error&gt; //or Report
         *  Error text
         * &lt;/Error&gt;
         * ..........
         * &lt;/Element&gt;
         * </p>
         *
         * @return element as node
         */
        public String toString() {
            StringBuffer result = new StringBuffer();
            result.append(OPEN_BRACKET).append(ELEMENT_NODE).append(" ").
                    append(ID_ATTR).append(EQUAL).append(id).append(QUOT).
                    append(NAME_ATTR).append(EQUAL).append(name).append(QUOT).
                    append(TYPE_ATTR).append(EQUAL).append(type).append(QUOT_WITH_BRACKET).append(NEW_LINE);
            result.append(details);
            result.append(CLOSE_NODE).append(ELEMENT_NODE).append(CLOSE_BRACKET).append(NEW_LINE);
            return result.toString();
        }
    }
}