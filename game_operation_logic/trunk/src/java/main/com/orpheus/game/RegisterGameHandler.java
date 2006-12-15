/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.InvalidConfigException;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Loop;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.NodeList;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * Registers the current (logged-in) user for a specified game. The game to register for will be identified by its
 * unique ID, parsed from a request parameter of configurable name. This class is immutable and thread safe.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class RegisterGameHandler implements Handler {
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>
     * The key to retrieve template source ID from a Map.
     * </p>
     *
     * <p>
     * This class uses Document Generator component to generate the email body
     * which will be the response to user. Document Generator uses different
     * generator classes to generate different documents. Each generator used by
     * Document Generator component is configured in a configuration file and
     * have an ID, or equivalently, a unique generator name.
     * </p>
     *
     * <p>
     * This class needs to know which generator to use. The generator to use is
     * specified in the given Map, as the value mapped to the key
     * "templateSource", which is the constant value of this variable.
     * </p>
     *
     * <p>
     * The constant is public so the user can use it to set the template source
     * ID in the map.
     * </p>
     */
    public static final String TEMPLATE_SOURCE_PROP = "templateSource";

    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>
     * The key to retrieve template file location from a Map.
     * </p>
     *
     * <p>
     * This class uses Document Generator component to generate the email body
     * which will be the response to user. Document Generator needs a template
     * to generate the email body. The template will be stored as a file in
     * local file system, and the file location is stored as a String in the
     * given Map. This variable is the key used to retrieve the template file
     * location value from the given Map.
     * </p>
     */
    public static final String TEMPLATE_NAME_PROP = "templateName";
    
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>Default template source use for one param constructor.</p>
     */
    private static final String DEFAULT_TEMPLATE_SOURCE = "testTemplate";
    
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>Default template name use for one param constructor.</p>
     */
    private static final String DEFAULT_TEMPLATE_NAME = "test_files/emailTemplate.txt";
    
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>
     * The template of the generator used to generate the email body.
     * </p>
     *
     * <p>
     * This is set in the constructors and cannot be changed afterwards.
     * </p>
     */
    private final Template mailBodyTemplate;

    /** A configurable param name to retrieve the game id from the request parameters. */
    private final String gameIdParamKey;

    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>Create the instance with given parameters.</p>
     *
     * @param gameIdParamKey the game id param key
     * @param templateName The template file used to generate the email body
     * @param templateSource The template source ID of the generator used to generate the email body
     *
     * @throws IllegalArgumentException if string argument is null or empty
     * @throws IllegalStateException
     *         If error getting instance of DocumentGenerator, configuration of DocumentGenerator
     *         is invalid or template got is of invalid format.
     * @throws IllegalArgumentException
     *         If cannot get template with given parameters
     */
    public RegisterGameHandler(String gameIdParamKey, String templateName, String templateSource) {
        // Parameter Validation
            ParameterCheck.checkNullEmpty("gameIdParamKey", gameIdParamKey);
        ParameterCheck.checkNullEmpty(TEMPLATE_NAME_PROP, templateName);
        ParameterCheck.checkNullEmpty(TEMPLATE_SOURCE_PROP, templateSource);
        
        // Set values
        this.gameIdParamKey = gameIdParamKey;
        try {
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            this.mailBodyTemplate = docGenerator
                    .getTemplate(templateSource, templateName);
        } catch (ConfigManagerException cme) {
                throw new IllegalStateException(
                                "Error occur while getting instance of DocumentGenerator");
        } catch (InvalidConfigException ice) {
                throw new IllegalStateException(
                                "Configuration of DocumentGenerator is invalid");
        } catch (TemplateSourceException tse) {
                throw new IllegalArgumentException(
                                "Cannot get template with given templateSource and templateName");
        } catch (TemplateFormatException tfe) {
                throw new IllegalStateException(
                                "Template got is of invalid format");
        }
    }

    /**
     * <p>Modified by Zulander to fix BALL-4675.</p>
     * 
     * <p>Create the instance with given gameIdParamKey.</p>
     *
     * @param gameIdParamKey the game id param key
     *
     * @throws IllegalArgumentException
     *         If string argument is null or empty
     * @throws IllegalStateException
     *         If error getting instance of DocumentGenerator, configuration of DocumentGenerator
     *         is invalid or template got is of invalid format.
     * @throws IllegalArgumentException
     *         If cannot get template with given parameters
     */
    public RegisterGameHandler(String gameIdParamKey) {
        this(gameIdParamKey, DEFAULT_TEMPLATE_NAME, DEFAULT_TEMPLATE_SOURCE);
    }

    /**
     * <p>Modified by Zulander to fix BALL-4675.</p>
     * <p>Create the instance from element.</p>
     * <pre>Follow is a sample xml:
     *  &lt;handler type=&quot;x&quot;&gt;
     *  &lt;game_id_param_key&gt; game_id&lt;/game_id_param_key&gt;
     *  &lt;/handler&gt; </pre>
     * <p>
     * Following is simple explanation of the above XML structure.<br>
     * The handler¡¯s type attribute is required by Front Controller component, it won¡¯t be used in this design. <br>
     * The game_id_param_key node¡¯s value represents the http request parameter name to get the game id.<br>
     * </p>
     *
     * @param element the xml element to create the handler instance.
     *
     * @throws IllegalArgumentException if element is null or failed to extract what we want
     * @throws IllegalStateException
     *         If error getting instance of DocumentGenerator, configuration of DocumentGenerator
     *         is invalid or template got is of invalid format.
     * @throws IllegalArgumentException
     *         If cannot get template with given parameters
     */
    public RegisterGameHandler(Element element) {
        // Parameter Validation
            ParameterCheck.checkNull("element", element);
        
        this.gameIdParamKey = XMLHelper.getNodeValue(element, "game_id_param_key", true);
        // Get template
        String templateName = XMLHelper.getNodeValue(element, TEMPLATE_NAME_PROP, true);
        String templateSource = XMLHelper.getNodeValue(element, TEMPLATE_SOURCE_PROP, true);
        try {
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            this.mailBodyTemplate = docGenerator
                    .getTemplate(templateSource, templateName);
        } catch (ConfigManagerException cme) {
                throw new IllegalStateException(
                                "Error occur while getting instance of DocumentGenerator");
        } catch (InvalidConfigException ice) {
                throw new IllegalStateException(
                                "Configuration of DocumentGenerator is invalid");
        } catch (TemplateSourceException tse) {
                throw new IllegalArgumentException(
                                "Cannot get template with given templateSource and templateName");
        } catch (TemplateFormatException tfe) {
                throw new IllegalStateException(
                                "Template got is of invalid format");
        }
    }

    /**
     * Registers the current (logged-in) user for a specified game.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws IllegalArgumentException if context is null
     * @throws HandlerExecutionException if any error occurred while executing this handler
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();

        UserProfile user = LoginHandler.getAuthenticatedUser(request.getSession());

        if (user == null) {
            throw new HandlerExecutionException("user does not login yet");
        }

        long userId = ((Long) user.getIdentifier()).longValue();

        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        long gameId = RequestHelper.getLongParameter(request, gameIdParamKey); //gameId from request parameter

        try {
            Game game;

            if (golu.isUseLocalInterface()) {
                GameDataLocal gameData = golu.getGameDataLocalHome().create();

                gameData.recordRegistration(userId, gameId);
                game = gameData.getGame(gameId);
            } else {
                GameData gameData = golu.getGameDataRemoteHome().create();

                gameData.recordRegistration(userId, gameId);
                game = gameData.getGame(gameId);
            }
            
            ///////////////////////////////////////////////////////////
            // Added by Zulander to fix BALL-4675
            context.setAttribute("message-to", 
                            user.getProperty(BaseProfileType.EMAIL_ADDRESS));
            context.setAttribute("message-subject",
                            "You are registered for " + game.getName());
            // Fill parameters for document generator
            Map parameters = new TreeMap();
            parameters.put("GAME_NAME", game.getName());
            parameters.put("START_DATE", game.getStartDate().toString());
            parameters.put("FIRST_DOMAIN",
                            game.getBlocks()[0].getSlots()[0].getDomain().getDomainName());
            context.setAttribute("message-body", generateEmailBody(parameters));
            ///////////////////////////////////////////////////////////
        } catch (Exception e) {
            throw new HandlerExecutionException("error occurs while recording user registation", e);
        }

        return null;
    }
    
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>
     * Generates the email body using Document Generator. The required field
     * values should be in the given Map. The template source ID and template
     * file have already been set in the constructors.
     * </p>
     *
     * <p>
     * If any exception occurs, this method catches it and throws a
     * ProcessActionException, with the cause exception wrapped inside.
     * </p>
     *
     * @param values
     *            the Map containing field values.
     * @return email body generated by applying the template set in the
     *         constructors to the values given in the values Map.
     * @throws HandlerExecutionException
     *             if any exception occurs during the document generation
     *             process. The cause exception is wrapped inside the thrown
     *             HandlerExecutionException.
     */
    private String generateEmailBody(Map values) throws HandlerExecutionException {
        try {
            /*
             * Obtain the Template from the DocumentGenerator using the
             * templateSource and templateName strings. The template is obtained
             * every time and it is not stored in a field so it will not be
             * affected by a previous usage.
             */
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            TemplateFields root = docGenerator.getFields(this.mailBodyTemplate);

            // Set values of the fields according to the values in the given
            // Map.
            setTemplateFieldValues(root, values);

            // Generate the document to a String and return.
            return docGenerator.applyTemplate(root);
        } catch (Exception e) {

            // Catch any exception into a ProcessActionException
            throw new HandlerExecutionException(
                    "Error when generating email body. ", e);
        }
    }
    
    /**
     * <p>Added by Zulander to fix BALL-4675.</p>
     * 
     * <p>
     * A helper function to fill parameter's in template of Document Generator.
     * </p>
     *
     * <p>
     * Initially this method should be applied to the root element (as nodeList
     * argument). If the specified template has Loop elements, we need to
     * process the elements of Loop recursively. The argument nodeList should be
     * now the element of Loop that's been processed.
     * </p>
     *
     * @param nodeList
     *            the list of nodes to be populated.
     * @param values
     *            the values to be used to populate the nodes. In the Map, the
     *            value for a "Field" should be a String, and the value for a
     *            "Loop" should be a List of Map instances.
     * @throws HandlerExecutionException
     *             if the given values Map is invalid. For example, if a
     *             required field value is not given in the Map and the field
     *             has no default value in the template; if some values are of
     *             wrong type; if some values are illegal such as null value;
     *             etc.
     */
    private void setTemplateFieldValues(NodeList nodeList, Map values)
            throws HandlerExecutionException {
        Node[] nodes = nodeList.getNodes();

        // Iterate through all nodes.
        for (int i = 0; i < nodes.length; i++) {
            Node thisNode = nodes[i];

            // Node can be either "Field" or Loop.
            if (thisNode instanceof Field) {
                Field field = (Field) thisNode;

                // Try to get the value from the given Map.
                String value = (String) values.get(field.getName());
                if (value != null) {
                    field.setValue(value);
                } else if (field.getValue() == null) { // Any default value for
                                                        // this field?

                    // Not default value, and no value is given, throw
                    // ProcessActionException
                    throw new HandlerExecutionException(
                            "Field: "
                                    + field.getName()
                                    + " in the template "
                                    + " is not specified in the given Map and it doesn't have "
                                    + "a default value.");
                }
            } else { // It's a Loop
                Loop loop = (Loop) thisNode;

                try {

                    // ClassCastException is possible here.
                    List items = (List) values.get(loop.getLoopElement());

                    if (items == null) {
                        throw new HandlerExecutionException(
                                "The list given to Loop: "
                                        + loop.getLoopElement()
                                        + " in the specified map is null.");
                    }

                    // Iterate through all instances of Map in the List
                    for (Iterator mapIter = items.iterator(); mapIter.hasNext();) {
                        NodeList newNodeList = loop.addLoopItem();

                        // ClassCastException is possible here.
                        this.setTemplateFieldValues(newNodeList, (Map) mapIter
                                .next());
                    }
                } catch (ClassCastException e) {
                    throw new HandlerExecutionException(
                            "The values given in the specified Map are not of "
                                    + "correct type.", e);
                }
            }
        }
    }

}
