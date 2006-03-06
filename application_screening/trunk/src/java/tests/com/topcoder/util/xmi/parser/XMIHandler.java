/*
 * @(#)XMIHandler.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser;

import org.w3c.dom.Node;


/**
 * <p>
 * XMIHandler is the interface implemented by all the handlers of XMI Parser
 * component. XMIParser deals with handlers only through this interface.
 * </p>
 *
 * <p>
 * XMIHander has only one method <code>handleNode()</code> which handles DOM
 * node in XMI.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public interface XMIHandler {
    /**
     * <p>
     * Handles thd DOM node in XMI and type of the node which matched the
     * diagram/element types specified in <code>UMLDiagramTypes</code>/
     * <code>UMLElementTypes</code> classes. Implementations can choose which
     * nodes to handle. It returns a status string which will be logged by the
     * XMIParser.
     * </p>
     *
     * @param node - xmi node
     * @param nodeType - the type of the node
     *
     * @return status string
     *
     * @throws XMIHandlerException - if for any reason, this handler cannot
     *         handle the node
     */
    String handleNode(Node node, String nodeType) throws XMIHandlerException;
}
