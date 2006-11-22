/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import org.w3c.dom.Element;

import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.message.messenger.MessengerPlugin;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * <code>UserMessagingHandler</code> implements <code>Handler</code>
 * interface from the FrontController component. This handler uses the Messenger
 * Framework to dispatch a message to a specified user. The handler will accept
 * a messenger plugin name and a Map from message parameter name Strings to
 * associated value Objects, both in the form of action context attributes of
 * configurable name. It will load the named messenger plugin, use it to create
 * a MessageAPI instance, set the specified parameters on that instance, and use
 * the plug-in to dispatch the message.
 * </p>
 * <p>
 * <strong>Thread-safety: </strong> This class is thread-safe since it is
 * immutable. Though Messenger Framework component is not thread-safe, it is
 * used in a thread-safe way in this class. The Messenger class is expected to
 * be initialized before this handler is called, and this class only reads
 * plugins from it. Concrete MessengerPlugin subclass registered into the
 * Messenger must be thread-safe to ensure the thread-safety. And each time
 * execute method is called, a new MessageAPI object is created, so it won't
 * affect the thread-safety of this class.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UserMessagingHandler implements Handler {

    /**
     * <p>
     * Represents the messenger plugin key to get the messenger plugin name from
     * the ActionContext's attributes in the execute method.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It is
     * non-null, non-empty string.
     * </p>
     *
     */
    private final String messengerPluginKey;

    /**
     * <p>
     * Represents the message parameter keys to get the message parameter values
     * from the ActionContext's attributes in the execute method.
     * </p>
     * <p>
     * It is initialized in constructor, and never changed afterwards. It is
     * non-null, and must contain at least 1 elements. Every element in this
     * array must be non-null, non-empty string.
     * </p>
     *
     */
    private final String[] messageParamKeys;

    /**
     * <p>
     * Constructor with the messenger plugin key and message parameter keys.
     * </p>
     * <p>
     * NOTE: the array is shallowly copied.
     * </p>
     *
     *
     * @param messengerPluginKey
     *            the messenger plugin key.
     * @param messageParamKeys
     *            an array of message parameter keys.
     * @throws IllegalArgumentException
     *             if any argument is null, or messengerPluginKey is null or
     *             empty string, or messageParamKeys contains null object or
     *             empty string or zero elements.
     */
    public UserMessagingHandler(String messengerPluginKey,
            String[] messageParamKeys) {
        Helper.checkArrayNullEmptyContainsNullEmpty(messageParamKeys,
                "messageParamKeys");

        this.messengerPluginKey = Helper.checkString(messengerPluginKey,
                "messengerPluginKey");
        this.messageParamKeys = Helper.copy(messageParamKeys);
    }

    /**
     * <p>
     * Constructor taking an xml Element object, it will extract values from the
     * xml Element, and then assign them to the corresponding instance
     * variables.
     *
     * Here is an example of the xml element:
     *
     * <pre>
     *    &lt;handler type=&quot;x&quot;&gt;
     *        &lt;messenger_plugin_key&gt;plug_in_key&lt;/messenger_plugin_key&gt;
     *        &lt;message_param_keys&gt;
     *            &lt;value&gt;key1&lt;/value&gt;
     *            &lt;value&gt;key2&lt;/value&gt;
     *        &lt;/message_param_keys&gt;
     *    &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     *
     *
     * @param handlerElement
     *            the xml Element to extract configured values to initialize
     *            this object.
     * @throws IllegalArgumentException
     *             if the arg is null, or the configured value in the xml
     *             element is invalid. (all values in the xml should follow the
     *             same constraints defined in
     *             {@link UserMessagingHandler#UserMessagingHandler(String, String[])}).
     */
    public UserMessagingHandler(Element handlerElement) {
        Helper.checkNull(handlerElement, "handlerElement");
        this.messengerPluginKey = Helper.getValue(handlerElement,
                "/handler/messenger_plugin_key");
        this.messageParamKeys = Helper.getValues(handlerElement,
                "/handler/message_param_keys");
    }

    /**
     * <p>
     * Process the user messaging request. This method loads necessary values
     * from the conext's attributes map using the key variables, and then use
     * APIs from Messenger Framework component to get the messenger plugin and
     * create message to send. It returns null if the operation succeeds,
     * otherwise, exception is thrown.
     * </p>
     *
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return null if the operation succeeds
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             if the messenger plugin name is missing in context, or there
     *             is corresponding plugin registered in Messenger, or the
     *             operations of Messenger Framework component fail.
     */
    public String execute(ActionContext context)
        throws HandlerExecutionException {
        Helper.checkNull(context, "context");
        // get messenger plugin key
        Object obj = context.getAttribute(messengerPluginKey);
        if (!(obj instanceof String)) {
            throw new HandlerExecutionException(
                    "The messengerPluginKey in ActionContext is not type of String.");
        }
        String messengerPluginName = (String) obj;
        if (messengerPluginName.trim().length() == 0) {
            throw new HandlerExecutionException(
                    "The messenger plugin name in ActionContext is empty.");
        }

        try { // send message using messenger framework
            MessengerPlugin plugin = Messenger.createInstance().getPlugin(
                    messengerPluginName);
            MessageAPI msg = plugin.createMessage();
            for (int i = 0; i < messageParamKeys.length; i++) {
                msg.setParameterValue(messageParamKeys[i], context
                        .getAttribute(messageParamKeys[i]));
            }
            plugin.sendMessage(msg);
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException(
                    "Failed to get Messenger plug in via key:"
                            + messengerPluginName + ".", e);
        } catch (Exception e) {
            throw new HandlerExecutionException(
                    "Failed to send message, caused by " + e.getMessage(), e);
        }

        return null;
    }
}
