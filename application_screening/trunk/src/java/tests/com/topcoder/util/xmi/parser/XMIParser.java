/*
 * @(#)XMIParser.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;


/**
 * <p>
 * <code>XMIParser</code> is the main parser application which implements the
 * Parser interface. It reads the configuration, determines the handlers
 * available for each type of node and executes a specific method handleNode
 * on the handlers. It also maintains a single instances of XMIHandler
 * objects. There may exists separate XMIParser instances for each file
 * parsed. The configuration namespace determines which set of handlers to
 * use. The parser uses this namespace to get the configuration using the
 * configuration manager. If no namespace is set, the default namespace
 * &quot;com.topcoder.util.xmi.parser.default.handler&quot; is used.
 * </p>
 *
 * <p>
 * <code>XMIParser</code> keeps records for handler registry. The registry is
 * loaded once when the instance is created, it prevents load the
 * configuartion many times. The client could change the configuration by
 * modifying the config file, providing an new configuration namespace, or
 * calling the methods
 * <code>registerHandler()</code>,<code>deleteHandler()</code>.
 * </p>
 *
 * <p>
 * <code>XMIParser</code> also keeps cache for the handler objects created
 * during processing. Since the handler contains information about the node
 * processed, client can get the handler object after processing using the
 * <code>getHandler(diagramType)</code> or <code>getHandler()</code> method
 * and then get the diagram information fom the handler. At most one instance
 * of handler of a certain type will be created.The handler instance created
 * is added to this map when a node of DOM type registered to this kind
 * handler is met in the input.
 * </p>
 *
 * <p>
 * The <code>XMIParser</code> first get all UML diagram node from the input,
 * and then get the UML elements and associates them with the previously
 * parsed diagram. When processing the diagram/element nodes, only the nodes
 * with registered handler is handled, others are ignored.
 * </p>
 *
 * <p>
 * The class also throws an <code>XMIParserException</code> by wrapping any
 * internal exceptions like <code>XMLHandlerException</code>, or any parsing
 * exception.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class XMIParser implements Parser {
    /** The tag name of Diagram used in XMI file. */
    private static final String DIAGRAM_TAG = "UML:Diagram";

    /** The tag name of Model used in XMI file. */
    private static final String MODEL_TAG = "UML:Model";

    /** The tag name of ownedElement used in XMI file. */
    private static final String OWNED_ELEMENT_TAG = "UML:Namespace.ownedElement";

    /** The tag name of the element that contains diagram type information. */
    private static final String DIAGRAM_TYPE_TAG = "UML:SimpleSemanticModelElement";

    /** The attribute name of diagram type. */
    private static final String TYPE_ATTR = "typeInfo";

    /** Message used when cannot parse the input file. */
    private static final String EXCEPTION_MESSAGE = "Parse failed!";

    /** Message used when can not config the parser. */
    private static final String CONFIG_EXCEPTION = "Can not config the XMIParser!";

    /** Message used when can not create the builder. */
    private static final String BUILDER_EXCEPTION = "Can not create the builder to parse!";

    /** Message used when the String to be parsed is null. */
    private static String STRING_NULL = "The string to parse should not be null!";

    /** Message used when the type of registerHandler() is null. */
    private static String TYPE_NULL = "The type is null!";

    /** Message used when the handler of registerHandler() is null. */
    private static String HANDLER_NULL = "The handler is null!";

    /** The default namespace for configuration. */
    private String namespace = "com.topcoder.util.xmi.parser.default.handler";

    /**
     * The map in which the key is the qualified handler class name and the
     * value is an XMIHandler object of the corresponding handler.
     */
    private Map handlerMap = null;

    /**
     * The map with with key - node type and value - handler name. The
     * configuration properties are read into this map once during startup.
     */
    private Map handlerRegister = null;

    /** Builder used to build document to parse. */
    private DocumentBuilder builder = null;

    /**
     * <p>
     * Default Constructor of XMIParser. Creates an XMIParser with default
     * configuration namespace
     * </p>
     *
     * @throws XMIParserException - if cannot create the XMIParser for any
     *         reason, e.g. cannot find the default config file, the config
     *         file is not well-formated, etc.
     */
    public XMIParser() throws XMIParserException {
        configXMIParser();
    }

    /**
     * <p>
     * Creates an XMIParser with the specified configuration namespace. It
     * loads the configuration from the namespace, and configs the created
     * XMIParser. If the namespace is null, the default namespace is assumed.
     * </p>
     *
     * @param ns - the specified namespace.
     *
     * @throws XMIParserException - if cannot create the XMIParser for any
     *         reason, e.g. cannot find the default config file, the config
     *         file is not well-formatted, etc.
     */
    public XMIParser(String ns) throws XMIParserException {
        // Check for null argument
        // if ns is null, default namespace will be used
        if (ns != null) {
            namespace = ns;
        }

        configXMIParser();
    }

    /**
     * <p>
     * Parses a given XMI file. Any exception that during operations including
     * XMIHandlerException is caught and rethrown as XMIParserException still
     * preserving the original exception as 'cause'.
     * </p>
     *
     * <p>
     * The parsing process is described in the class documentation.
     * </p>
     *
     * @param file - XMI file to be parsed
     *
     * @throws XMIParserException - if any exception is caught when processing
     *         the input, and the exception is wrapped as the cause of
     *         XMIParserException.
     * @throws IllegalArgumentException - if file is null.
     */
    public void parse(File file) throws XMIParserException {
        try {
            parseDocument(builder.parse(file));
        } catch (SAXException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * <p>
     * Parses a given XMI FileInputStream. Any exception that during operations
     * including XMIHandlerException is caught and rethrown as
     * XMIParserException still preserving the original exception as 'cause'.
     * </p>
     *
     * <p>
     * The parsing process is described in the class documentation.
     * </p>
     *
     * @param stream - XMI stream to be parsed
     *
     * @throws XMIParserException - if any exception is caught when processing
     *         the input, and the exception is wrapped as the cause of
     *         XMIParserException.
     * @throws IllegalArgumentException - if stream is null.
     */
    public void parse(FileInputStream stream) throws XMIParserException {
        try {
            parseDocument(builder.parse(stream));
        } catch (SAXException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * <p>
     * Parses a given XMI content as string. Any exception that during
     * operations including XMIHandlerException is caught and rethrown as
     * XMIParserException still preserving the original exception as 'cause'.
     * </p>
     *
     * <p>
     * The parsing process is described in the class documentation.
     * </p>
     *
     * @param str - XMI string to be parsed
     *
     * @throws XMIParserException - if any exception is caught when processing
     *         the input, and the exception is wrapped as the cause of
     *         XMIParserException.
     * @throws IllegalArgumentException - if str is null.
     */
    public void parse(String str) throws XMIParserException {
        try {
            parseDocument(builder.parse(new InputSource(new StringReader(str))));
        } catch (SAXException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        } catch (NullPointerException e) {
            // Wrap as IllegalArgumentException to consist with the other
            // overloaded methods
            throw new IllegalArgumentException(STRING_NULL);
        }
    }

    /**
     * <p>
     * Returns the namespace in which the class looks for configuration
     * properties.
     * </p>
     *
     * @return namespace of the configuration
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * <p>
     * Returns a handler for a given diagram/element type. The type should be
     * one of the constants specified in <code>UMLDiagramTypes</code> or
     * <code>UMLElementTypes</code>. For any other invalid types, the method
     * simply returns null because it will never find a handler for an invalid
     * type.
     * </p>
     *
     * @param type - diagram or element type
     *
     * @return the handle for the type if found, null otherwise
     */
    public XMIHandler getHandler(String type) {
        return (XMIHandler) handlerMap.get(handlerRegister.get(type));
    }

    /**
     * <p>
     * This method checks if there is only one handler in the handlerMap. If
     * yes returns it else returns null. So, this method is valid only if a
     * single handler is configured.
     * </p>
     *
     * @return the handler if only one handler exists, null otherwise
     */
    public XMIHandler getHandler() {
        if (handlerMap.size() == 1) {
            return (XMIHandler) handlerMap.values().iterator().next();
        }

        return null;
    }

    /**
     * <p>
     * Registers the handler with the given type. It associates the type with
     * the handler, overwrites if association already existed. The type should
     * ideally be one of the constants defined in <code>UMLDiagramTypes</code>
     * or <code>UMLElementTypes</code> classes. Both parameters must not be
     * null, otherwise, XMIParserException is thrown.
     * </p>
     *
     * @param type - the given diagram/element type
     * @param handler - handler to handle the type
     *
     * @throws XMIParserException - if any of the parameter is null
     */
    public void registerHandler(String type, XMIHandler handler)
        throws XMIParserException {
        if (type == null) {
            throw new XMIParserException(TYPE_NULL);
        }

        if (handler == null) {
            throw new XMIParserException(HANDLER_NULL);
        }

        String handlerClass = handler.getClass().getName();
        handlerRegister.put(type, handlerClass);
    }

    /**
     * <p>
     * Deletes the handler registry with the specified type. The type should
     * ideally be one of the constants specified in
     * <code>UMLDiagramTypes</code> or <code>UMLElementTypes</code> classes.
     * If the type is not in defined Diagram/Element types, false is returned.
     * </p>
     *
     * <p>
     * The method checks if handler for the given type is defined in
     * handlerRegister. If not defined, it returns false. If defined, it then
     * checks whether the instance handler for the type is created, and
     * deletes the record of the instance if created.
     * </p>
     *
     * @param type - Diagram/Element type of the handler.
     *
     * @return whether the handler for this type was deleted.
     */
    public boolean deleteHandler(String type) {
        Object handlerClassName = handlerRegister.remove(type);

        if (handlerClassName != null) {
            handlerMap.remove(handlerClassName);

            return true;
        }

        return false;
    }

    /**
     * <p>
     * Configs the XMIParser with the namespace of <code>namespace</code>. It
     * is a helper of the constructors, it creates the Maps needed, loads the
     * configuration, then registers the handlers and creates the builder for
     * later use.
     * </p>
     *
     * @throws XMIParserException - if any the problem occured when configging
     *         the XMIParser, e.g. cannot find the config file, badly
     *         formatted file, etc.
     */
    private void configXMIParser() throws XMIParserException {
        handlerRegister = new HashMap();
        handlerMap = new HashMap();

        ConfigManager manager = ConfigManager.getInstance();

        try {
            manager.refreshAll();

            Enumeration en = manager.getPropertyNames(namespace);

            while (en.hasMoreElements()) {
                String name = (String) en.nextElement();
                handlerRegister.put(name, manager.getString(namespace, name));
            }
        } catch (UnknownNamespaceException e) {
            throw new XMIParserException("The namespace " + namespace
                + " does not exist!", e);
        } catch (ConfigManagerException e) {
            throw new XMIParserException(CONFIG_EXCEPTION, e);
        }

        // Create the builder for later use
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMIParserException(BUILDER_EXCEPTION, e);
        } catch (FactoryConfigurationError e) {
            throw new XMIParserException(BUILDER_EXCEPTION, e);
        }
    }

    /**
     * <p>
     * Parses the given DOM Document.
     * </p>
     *
     * @param doc - the DOM Document
     *
     * @throws XMIParserException - if any exception is caught when processing
     *         the input, and the exception is wrapped as the cause of
     *         XMIParserException.
     */
    private void parseDocument(Document doc) throws XMIParserException {
        handlerMap.clear();

        // Parse the Diagram Node first
        NodeList nl = doc.getElementsByTagName(DIAGRAM_TAG);

        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);

            // Get the element node "UML:SimpleSemanticModelElement" which
            Element el = (Element) e.getElementsByTagName(DIAGRAM_TYPE_TAG)
                                    .item(0);

            String diagramType = el.getAttribute(TYPE_ATTR);
            parseElement(e, diagramType);
        }

        // Parse the element node
        Element model = (Element) doc.getElementsByTagName(MODEL_TAG).item(0);

        if (model == null) {
            // The input file does not contain "UML:Model" element
            return;
        }

        Element ownedElement = (Element) model.getElementsByTagName(OWNED_ELEMENT_TAG)
                                              .item(0);

        if (ownedElement == null) {
            // The input file does not contain "UML:Namespace.ownedElement"
            // element
            return;
        }

        nl = ownedElement.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i) instanceof Element) {
                Element e = (Element) nl.item(i);

                // Get the full tag name and strip the first 4
                // character("UML:") to get the element type
                String elementType = e.getTagName().substring(4);
                parseElement(e, elementType);
            }
        }
    }

    /**
     * <p>
     * Parses the DOM element of the given type. The type should ideally be one
     * of the constants specified in <code>UMLDiagramTypes</code> or
     * <code>UMLElementTypes</code> classes. If the type is not in defined
     * Diagram/Element types, handler will not be found for the element, the
     * method simply returns.
     * </p>
     *
     * @param element - the DOM element
     * @param type - the given diagram/element type.
     *
     * @throws XMIParserException - if any exception is caught when processing
     *         the input, and the exception is wrapped as the cause of
     *         XMIParserException.
     */
    private void parseElement(Element element, String type)
        throws XMIParserException {
        String handlerClassName = (String) handlerRegister.get(type);

        if (handlerClassName == null) {
            // No handler registered for this diagram type
            return;
        }

        XMIHandler handler = (XMIHandler) handlerMap.get(handlerClassName);

        if (handler == null) {
            // create the handler instance
            try {
                handler = (XMIHandler) Class.forName(handlerClassName)
                                            .newInstance();
            } catch (InstantiationException e) {
                throw new XMIParserException(EXCEPTION_MESSAGE, e);
            } catch (IllegalAccessException e) {
                throw new XMIParserException(EXCEPTION_MESSAGE, e);
            } catch (ClassNotFoundException e) {
                throw new XMIParserException(EXCEPTION_MESSAGE, e);
            }

            handlerMap.put(handlerClassName, handler);
        }

        try {
            handler.handleNode(element, type);
        } catch (XMIHandlerException e) {
            throw new XMIParserException(EXCEPTION_MESSAGE, e);
        }
    }
}
