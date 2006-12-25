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

import com.orpheus.game.XMLHelper;
import com.topcoder.util.config.ConfigParserException;

/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

public class TestDom {

    /**
     *@param args
     */
    public static void main(String[] args) throws Exception{
       
            new TestDom().test();
    }
    
    private void test() throws Exception{
        String file = "TestDom.xml";
        Document doc = getDocument(file,"Config.Property","theObj");
    }
    
    private Document loadDocument(String fileName) throws Exception{
        InputStream input = TestDom.class.getResourceAsStream(fileName);
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
    
    private Document getDocument(String fileName, String tagName, String nodeName) throws Exception{
        Document doc = loadDocument(fileName);
        NodeList nodes = XMLHelper.getNodes(doc.getDocumentElement(),tagName);
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
                return (Document) node;
            }
        }
        return null;
    }

}
