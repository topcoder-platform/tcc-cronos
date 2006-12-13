/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.io.IOException;
import java.io.InputStream;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.topcoder.util.config.ConfigParserException;

public final class DocumentHelper {
    private DocumentHelper(){
        
    }
    
    /**
     * Loads Document from the given xml file.
     *@param fileName
     *@return
     *@throws Exception
     */
    private static Document loadDocument(String fileName) throws Exception{
        InputStream input = DocumentHelper.class.getResourceAsStream(fileName);
        DOMParser parser = new DOMParser();
        InputSource is = new InputSource(input);
        try {
            parser.parse(is);
        } catch (SAXException saxe) {
            throw new ConfigParserException(saxe.getMessage());
        } catch (IOException ioe) {
            throw new ConfigParserException(ioe.getMessage());
        }
        
        Document doc = parser.getDocument();
        return doc;
    }
    
    /**
     * Obtains a nested Document from the given xml file with specified tagName and node name.
     * 
     *@param fileName
     *@param tagName
     *@param nodeName
     *@return
     *@throws Exception
     */
    public static Element getDocument(String fileName, String tagName, String nodeName) throws Exception{
        Document doc = loadDocument(fileName);
        NodeList nodes = getNodes(doc.getDocumentElement(),tagName);
        for (int i=0;i<nodes.getLength();i++){
            Node node = nodes.item(i);
            NamedNodeMap map = node.getAttributes();
            if (map.getLength() == 0){
                continue;
            }
            Node attribute = map.getNamedItem("name");
            if (attribute == null){
                continue;
            }
            
            if (nodeName.equals(attribute.getNodeValue())){
                return  getFirstChildElement(node);
            }
        }
        return null;
    }
    
    private static Element getFirstChildElement(Node root){
        NodeList list = root.getChildNodes();
        if (list.getLength()==0){
            return null;
        }
        for (int i=0;i<list.getLength();i++){
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE){
                return (Element) list.item(i);
            }
        }
        return null;
    }
    
    /**
     * Helper method to obtain a list of nodes from root element according to the given hierachical name.
     *@param root
     *@param name
     *@return
     */
    public static final NodeList getNodes(Element root, String name){
        int dotIndex = name.indexOf('.');
        String currentName = name;
        String childName = null;
        
        //if the name represents a hierachical name, then splits it to a parent name and a subtitle name
        if (dotIndex>0){
            currentName = name.substring(0,dotIndex);
            childName = name.substring(dotIndex+1,name.length());
        }
        
        NodeList nodeList = root.getElementsByTagName(currentName);
        
        if (childName==null){
            return nodeList;
        }
        
        if (nodeList.getLength()==0){
            return null;
        }
        
        //search nodes from children
        return getNodes((Element) nodeList.item(0),childName);
    }
}
