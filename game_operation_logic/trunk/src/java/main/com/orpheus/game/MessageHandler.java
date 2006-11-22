/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.MessageException;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.message.messenger.MessengerPlugin;

import com.topcoder.util.config.ConfigManagerException;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * A handler that supports general-purpose messaging between the application and users via the Messenger Framework.  It
 * is configured with the name of a MessengerPlugin and with one or more mappings between message property names and
 * request parameter names or request / session / application / action context attribute names.  The handler obtains a
 * MessengerPlugin instance, creates a MessageAPI instance, assigns values to the message parameters as directed by
 * its configuration, then dispatches the message via the MessengerPlugin.  This class is thread safe since it does
 * not contain any mutable state.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class MessageHandler implements Handler {
    /** Message property name map, AttributeScope as key, and String representation of message property name as value.*/
    private final Map msgPropertyNameMap;

    /** The name used to get MessengerPlugin from Messenger. */
    private final String msgPluginName;

    /**
     * Create the instance with given arguments.
     *
     * @param pluginName the plugin name
     * @param propertyNameMap the property name map, AttributeScope as key, and String representation of message
     *        property name as value.
     *
     * @throws IllegalArgumentException if string argument is null or empty, propertyNameMap is null or empty, or key
     *         set or value set contain null
     */
    public MessageHandler(String pluginName, Map propertyNameMap) {
        ParameterCheck.checkNullEmpty("pluginName", pluginName);
        ParameterCheck.checkEmptyMap("propertyNameMap", propertyNameMap);

        //checks whether the map contains empty value
        Iterator iter = propertyNameMap.entrySet().iterator();

        while (iter.hasNext()) {
            String value = (String) ((Entry) iter.next()).getValue();

            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("value should not be empty string");
            }
        }

        this.msgPluginName = pluginName;
        this.msgPropertyNameMap = new HashMap(propertyNameMap);
    }

    /**
     * Create the instance from element. The structure of the element will be like this:
     * <pre>&lt;handler type=&quot;x&quot; &gt;
     *  &lt;plugin_name&gt;some name&lt;/plugin_name&gt;
     *  &lt;attribute_names&gt;
     *  &lt;value scope=¡±request_parameter¡±&gt;paramName1&lt;/value&gt;
     *  &lt;value scope=¡±application¡±&gt;attributeName2&lt;/value&gt;
     *  .....
     *  &lt;/attribute_names&gt;
     *  &lt;message_property_names&gt;
     *  &lt;value&gt;some value&lt;/value&gt;
     *  &lt;value&gt;some value&lt;/value&gt;
     *  .........
     *  &lt;/message_property_names&gt;
     *  &lt;/handler&gt;</pre>
     * <p>
     * Following is simple explanation of the above XML structure.<br>
     * The handler¡¯s type attribute is required by Front Controller component, it won¡¯t be used in this design. <br>
     * The plugin_name node¡¯s value represents the message plugin name used to get the MessagePlugin<br>
     * The attribute_names node¡¯s values are used to get the corresponding message property value from the request
     * parameter or attribute, session attribute, application attribute, or ActionContext attribute, dependent on the
     * scope attribute. The scope value must be one of the following values:<br>
     * request_parameter, request, session, application, action_context.<br>
     * The message_property_names¡¯ values service as the message property name to keep the property value.<br>
     * Please note that the attribute_names must have the same number of children value nodes as the
     * message_property_names.<br>
     * </p>
     *
     * @param element the xml element
     *
     * @throws IllegalArgumentException if element is null or invalid
     */
    public MessageHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        this.msgPluginName = XMLHelper.getNodeValue(element, "plugin_name", true);

        String[] propertyNames = XMLHelper.getNodeValues(element, "message_property_names.value");

        NodeList nodeList = XMLHelper.getNodes(element, "attribute_names.value");

        if ((nodeList == null) || (nodeList.getLength() == 0)) {
            throw new IllegalArgumentException("node is not found:" + "attribute_names");
        }

        if (nodeList.getLength() != propertyNames.length) {
            throw new IllegalArgumentException("nodes:" + "attribute_names" + " and " + "message_property_names"
                    + " must have equal length");
        }

        Map propertyMap = new HashMap(propertyNames.length);

        for (int i = 0; i < propertyNames.length; i++) {
            Node node = nodeList.item(i);
            String scope = node.getAttributes().getNamedItem("scope").getNodeValue();
            String propertyName = node.getFirstChild().getNodeValue();

            if ((propertyName == null) || (propertyName.trim().length() == 0)) {
                throw new IllegalArgumentException("scope can not be empty string");
            }

            propertyMap.put(new AttributeScope(propertyName, scope), propertyNames[i]);
        }

        this.msgPropertyNameMap = propertyMap;
    }

    /**
     * Obtains message plugin according to the plugin name, and sends message. Message's parameters are retrieved from
     * context according to the names and scopes specified in the property name map.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws HandlerExecutionException if any error occurred while executing this handler
     * @throws IllegalArgumentException if the context is null
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        try {
            Messenger messenger = Messenger.createInstance();
            MessengerPlugin plugin = messenger.getPlugin(this.msgPluginName);
            MessageAPI api = plugin.createMessage();
            Iterator iter = this.msgPropertyNameMap.entrySet().iterator();
            Entry entry;

            while (iter.hasNext()) {
                entry = (Entry) iter.next();
                api.setParameterValue((String) entry.getValue(), getProperty(context, (AttributeScope) entry.getKey()));
            }

            plugin.sendMessage(api);
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("failed to load Messenger", e);
        } catch (MessageException e) {
            throw new HandlerExecutionException("failed to send message", e);
        } catch (Exception e) {
            throw new HandlerExecutionException("failed to send message", e);
        }

        return null;
    }

    /**
     * Get property from context according to the given AttributeScope.
     *
     * @param context context from where the property value be obtained
     * @param attributeScope attributeScope
     *
     * @return property value from given context and attribute scope
     */
    private Object getProperty(ActionContext context, AttributeScope attributeScope) {
        String attribute = attributeScope.getAttribute();
        String scope = attributeScope.getScope();

        if ("request".equals(scope)) {
            return context.getRequest().getAttribute(attribute);
        } else if ("action_context".equals(scope)) {
            return context.getAttribute(attribute);
        } else if ("application".equals(scope)) {
            return context.getRequest().getSession().getAttribute(attribute);
        } else if ("action_context".equals(scope)) {
            return context.getAttribute(attribute);
        } else if ("request_parameter".equals(scope)) {
            return context.getRequest().getParameter(attribute);
        }

        return null;
    }
}
