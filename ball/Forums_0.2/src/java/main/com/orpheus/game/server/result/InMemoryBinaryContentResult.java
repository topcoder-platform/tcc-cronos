/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Result;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>A custom {@link Result} implementation to be used for outputing the binary content bound to request or session
 * to response outoging to client. The scope, name of attribute providing the content and  name of attribute providing
 * the MIME content type are specified during initialization. The content is expected to be of <code>byte</code> array
 * type.</p>
 *
 * @author isv
 * @version 1.0
 */
public class InMemoryBinaryContentResult implements Result {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request/session attribute to get the binary content from.</p>
     */
    public static final String CONTENT_ATTR_NAME_CONFIG = "content_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request/session attribute to get the MIME type binary content from.</p>
     */
    public static final String CONTENT_TYPE_ATTR_NAME_CONFIG = "content_type_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * scope to get the binary content from (request or session).</p>
     */
    public static final String SCOPE_CONFIG = "scope";

    /**
     * <p>A <code>String</code> providing the name of request/session attribute to get the binary content from.</p>
     */
    private final String contentAttrName;

    /**
     * <p>A <code>String</code> providing the scope to get the binary content from (request or session).</p>
     */
    private final String scope;

    /**
     * <p>A <code>String</code> providing the name of request/session attribute to get the MIME type of binary content
     * from.</p>
     */
    private final String contentTypeAttrName;

    /**
     * <p>Constructs new <code>InMemoryBinaryContentResult</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;content_key&gt;ballImage&lt;/content_key&gt;
     *      &lt;content_type_key&gt;ballImageType&lt;/content_type_key&gt;
     *      &lt;scope&gt;session&lt;/scope&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public InMemoryBinaryContentResult(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        this.contentAttrName = getElement(element, CONTENT_ATTR_NAME_CONFIG);
        this.contentTypeAttrName = getElement(element, CONTENT_TYPE_ATTR_NAME_CONFIG);
        this.scope = getElement(element, SCOPE_CONFIG);
    }

    /**
     * <p>Executes this result. Locates the content in the specified scope under the specified name and outputs it to
     * response outgoing to client setting the specified MIME type. If the scope is session then the respective
     * attributes providing the content and content type are deleted from session.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @throws ResultExecutionException if an unrecoverable error prevents the result from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            // Get the binary content from the request
            HttpServletRequest request = context.getRequest();
            final byte[] content;
            final String contentType;
            if (this.scope.equals("session")) {
                HttpSession session = request.getSession(false);
                content = (byte[]) session.getAttribute(this.contentAttrName);
                contentType = (String) request.getAttribute(this.contentTypeAttrName);
                session.removeAttribute(this.contentAttrName);
                session.removeAttribute(this.contentTypeAttrName);
            } else {
                content = (byte[]) request.getAttribute(this.contentAttrName);
                contentType = (String) request.getAttribute(this.contentTypeAttrName);
            }
            // Set the response content type
            HttpServletResponse response = context.getResponse();
            response.setContentType(contentType);
            // Write content to servlet response
            ServletOutputStream responseOutputStream = response.getOutputStream();
            InputStream imageContentStream = new ByteArrayInputStream(content);
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageContentStream.read(data)) > 0) {
                responseOutputStream.write(data, 0, bytesRead);
            }
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (Exception e) {
            throw new ResultExecutionException("Could not output the binary content to response", e);
        }
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @return a <code>String</code> providing the text value of the given tag.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    private static String getElement(Element element, String key) {
        NodeList nodeList;
        nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
        }
        if (nodeList.getLength() != 1) {
            throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
        }
        Node node = nodeList.item(0).getFirstChild();
        if (node == null) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        }
        String value = node.getNodeValue();
        if ((value == null) || (value.trim().length() == 0)) {
            throw new IllegalArgumentException("The [" + key + "] element is empty");
        }
        return value;
    }
}
