/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.server.util.dropdown.DropDownDataImpl;
import com.orpheus.game.server.util.dropdown.DropDownItemImpl;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom {@link Handler} implementation to be used for prepearing the data necessary to display the page for
 * creating new game by administrator from scratch. The purpose of this handler is to obtain the details for existing
 * domains and images and put them to request.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ManualGameDataHandler extends AbstractGameServerHandler implements Handler{

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>ManualGameDataHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;auctions_request_attr_key&gt;openAuctions&lt;/auctions_request_attr_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public ManualGameDataHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, DOMAINS_ATTR_NAME_CONFIG, true);
        readAsString(element, IMAGES_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Obtains the auctions from the request and locates
     * the list of games which correspond to provided auctions. The list of such games is bound to request for further
     * use.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();

        try {
            GameDataEJBAdapter gameDataEjbAdapter = getGameDataEJBAdapter(this.jndiContext);

            // Load details for sponsor domains and associated images
            Domain[] domains = gameDataEjbAdapter.getApprovedDomains();
            DropDownDataImpl domainsDropDownData = buildDomainsDropDownData(domains);
            DropDownDataImpl imagesDropDownData = buildImagesDropDownData(domains, domainsDropDownData);

            // Put all data to request
            request.setAttribute(getString(DOMAINS_ATTR_NAME_CONFIG), domainsDropDownData);
            request.setAttribute(getString(IMAGES_ATTR_NAME_CONFIG), imagesDropDownData);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for placing a bid in private auction", e);
        }
    }


    /**
     * <p>Builds the drop-down data list from the specified list of domains.</p>
     *
     * @param domains a <code>Domain</code> array providing the details for domain.
     * @return a <code>DropDownData</code> to be used for presenting the domains as drop-down list.
     */
    private static DropDownDataImpl buildDomainsDropDownData(Domain[] domains) {
        DropDownItemImpl domain;
        DropDownDataImpl data = new DropDownDataImpl();
        for (int i = 0; i < domains.length; i++) {
            domain = new DropDownItemImpl(String.valueOf(domains[i].getId()), domains[i].getDomainName());
            data.addDropDownItem(domain);
        }
        return data;
    }

    /**
     * <p>Builds the drop-down data list for images associated with domains from the specified list of domains.</p>
     *
     * @param domains a <code>Domain</code> array providing the details for domain.
     * @param domainsDropDown a <code>DropDownData</code> providing the details for domains drop-down list.
     * @return a <code>DropDownData</code> to be used for presenting the domains as drop-down list.
     */
    private static DropDownDataImpl buildImagesDropDownData(Domain[] domains, DropDownDataImpl domainsDropDown) {
        DropDownItemImpl image;
        ImageInfo[] images;
        DropDownDataImpl data = new DropDownDataImpl();
        for (int i = 0; i < domains.length; i++) {
            images = domains[i].getImages();
            for (int j = 0; j < images.length; j++) {
                image = new DropDownItemImpl(String.valueOf(images[j].getId()), images[j].getDescription());
                image.addParent(domainsDropDown.getDropDownItem(String.valueOf(domains[i].getId())));
                data.addDropDownItem(image);
            }
        }
        return data;
    }
}
