/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.GameData;

import com.topcoder.util.config.ConfigManagerException;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import org.w3c.dom.Element;

import java.rmi.RemoteException;

import javax.naming.NamingException;


/**
 * A Handler that records an instance of a plug-in download. It will use a plug-in name taken from a request parameter
 * of configurable name to identify the plug-in downloaded to the game data persistence component. This class is
 * thread safe since it does not contain any mutable state.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class PluginDownloadHandler implements Handler {
    /** A configurable param name to retrieve the plugin name from the request parameters. */
    private final String pluginNameParamKey;

    /**
     * Create the instance with given arguments.
     *
     * @param pluginNameParamKey the plugin name param key
     *
     * @throws IllegalArgumentException if string argument is null or empty
     */
    public PluginDownloadHandler(String pluginNameParamKey) {
        ParameterCheck.checkNullEmpty("pluginNameParamKey", pluginNameParamKey);
        this.pluginNameParamKey = pluginNameParamKey;
    }

    /**
     * Create the instance from element. The structure of the element can be found in CS.
     *
     * @param element the xml element
     *
     * @throws IllegalArgumentException if element is null or invalid
     */
    public PluginDownloadHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        this.pluginNameParamKey = XMLHelper.getNodeValue(element, "plugin_name_param_key", true);
    }

    /**
     * records an instance of a plug-in download.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws HandlerExecutionException if any error occurred while executing this handler
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        String pluginName = context.getRequest().getParameter(this.pluginNameParamKey);

        if (pluginName == null) {
            throw new HandlerExecutionException("parameter:" + pluginNameParamKey + " does not exist");
        }

        GameData gameData = null;
        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        try {
            if (golu.isUseLocalInterface()) {
                gameData = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            gameData.recordPluginDownload(pluginName);
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (NamingException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (GameDataException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        } catch (RemoteException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        }

        return null;
    }
}
