/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Build Script Generator XSLT 1.0 (Accuracy Test)
 */
package com.topcoder.buildutility.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p> Helper methods for tests. </p>
 * 
 * @author matmis
 * @version 1.0
 */
public class TestHelper {

    /** Private constructor to prevent instantiation. */
    private TestHelper() {
        // private
    }

    /**
     * <p>Reads file contents into a string (with default charset).</p>
     * 
     * @param file a string with file name
     * @return file contents
     * @throws BaseRuntimeException if an exception occurs
     */
    public static String readFile(String file) {
        try {
            StringWriter writer = new StringWriter();
            Reader reader = new BufferedReader(new FileReader(file));
            try {
                int c;
                while ((c = reader.read()) != -1) {
                    writer.write(c);
                }
                return writer.toString();
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            // wrap as unchecked exception
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * <p>Writes given content to a file with specified name.</p>
     * 
     * @param content String to be written to a file
     * @param file String filename of the file to be written to.
     * @throws BaseRuntimeException if an exception occurs
     */
    public static void writeFile(String content, String file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            // wrap as unchecked exception
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * <p>Loads transform from specified xslt transform file.</p>
     * 
     * @param filename the name of the file with xslt transform
     * @return Transfomer instance loaded from specified file
     * @throws BaseRuntimeException if an exception occurs
     */
    public static Transformer loadTransformer(String filename) {
        try {
            return TransformerFactory.newInstance().newTransformer(new StreamSource(new File(filename)));
        } catch (TransformerConfigurationException e) {
            System.err.println(e);
            // wrap as unchecked exception
            throw new BaseRuntimeException(e);
        } catch (TransformerFactoryConfigurationError e) {
            System.err.println(e);
            // wrap as unchecked exception
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * <p>Returns a string representation of the given xml DOM node.</p>
     * 
     * @param node Node xml DOM node to convert to string
     * @return String with representation of the given xml DOM node.
     * @throws BaseRuntimeException if an exception occurs
     */
    public static String xmlToString(Node node) {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * Parse and rewrite xml in a normalized form (attributes and elements are sorted)
     * 
     * @param xml String xml document to normalize
     * @return a normalized form of an xml
     */
    public static String normalizeXml(String xml) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            normalizeNode(doc);
            return xmlToString(doc);
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * <p>Normalizes an xml node. Sorts atttributes and children</p>
     * 
     * @param node first xml node to check
     */
    private static void normalizeNode(Node node) {
        node.normalize();
        // attributes
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Map attrMap = new TreeMap();
            for (int i = 0; i < attributes.getLength(); i++) {
                Attr attr = (Attr) attributes.item(i);
                attrMap.put(attr.getName(), attributes.getNamedItem(attr.getName()));
            }
            for (Iterator iter = attrMap.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                attributes.removeNamedItem((String)entry.getKey());
            }
            for (Iterator iter = attrMap.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                Node attrNode = (Node) entry.getValue();
                Node attrNode2 = node.getOwnerDocument().importNode(attrNode, true);
                node.getAttributes().setNamedItem(attrNode2);
            }
        }

        // subelements
        NodeList list = node.getChildNodes();
        Node[] nodes = new Node[list.getLength()];
        for (int i = 0; i < list.getLength(); i++) {
            nodes[i] = list.item(i);
        }
        for (int i = 0; i < nodes.length; i++) {
            normalizeNode(nodes[i]);
            node.removeChild(nodes[i]);
        }
        Arrays.sort(nodes, new Comparator() {

            public int compare(Object o1, Object o2) {
                String s1 = xmlToString((Node) o1), s2 = xmlToString((Node) o2);
                return s1.compareTo(s2);
            }

        });
        for (int i = 0; i < nodes.length; i++) {
            node.appendChild(nodes[i]);
        }
    }

}
