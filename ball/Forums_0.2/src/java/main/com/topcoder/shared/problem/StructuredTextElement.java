/*
 * StructuredTextElement
 * 
 * Created 06/12/2006
 */
package com.topcoder.shared.problem;

import java.io.IOException;
import java.io.ObjectStreamException;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

/**
 * Represents a document section of an XML. 
 * It's contains as <code>text</code> the XML section writen by the user.
 * It should be used to wrap the text entered by the user in text fields that
 * allow inner tags.  
 * 
 * @author Diego Belfer (mural)
 * @version $Id: StructuredTextElement.java,v 1.2 2006/07/07 15:26:29 thefaxman Exp $
 */
public class StructuredTextElement extends BaseElement {
    /**
     * This is the tag name used for XML representation
     */
    private String name = "";
    
    /**
     * Inner XML text
     */
    private String innerXMLText = "";

    public StructuredTextElement() {
    }

    /**
     * Builds a new StructuredTextElement
     * 
     * @param name name of the tag to use for XML representation
     * @param text Inner XML text
     */
    public StructuredTextElement(String name, String text) {
        this.name = name;
        this.innerXMLText = text;
    }
    
    /**
     * Returns the XML representation of this Element
     * All ocurrences of character &amp; in the text are replaced by its escaped form
     */
    public String toXML() {
        StringBuffer sb = new StringBuffer(20);
        sb.append("<").append(name).append(" escaped=\"1\">");
        int pos = innerXMLText.indexOf('&');
        if (pos > -1) {
            int lstPos = 0;
            while (pos > -1) {
                sb.append(innerXMLText.substring(lstPos, pos));
                sb.append("&amp;");
                pos++;
                lstPos = pos; 
                pos = innerXMLText.indexOf('&', lstPos);
            }
            sb.append(innerXMLText.substring(lstPos));
        } else {
            sb.append(innerXMLText);
        }
        sb.append("</").append(name).append(">");
        return sb.toString();
    }
    
    public String toString() {
        return innerXMLText;
    }
    
    public void customWriteObject(CSWriter writer) throws IOException {
        writer.writeString(name);
        writer.writeString(innerXMLText);
    }

    public void customReadObject(CSReader reader) throws IOException, ObjectStreamException {
        this.name = reader.readString();
        innerXMLText = reader.readString();
    }
    
    /**
     * Returns the text that will be used as the inner XML of this 
     * node.
     */
    public String getInnerXmlText() {
        return innerXMLText;
    }
}