/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.server;

import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.persistence.LocalOrpheusMessageDataStore;
import com.orpheus.administration.persistence.RemoteOrpheusMessageDataStore;
import com.orpheus.auction.KeyConstants;
import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.GameDataManagerImpl;
import com.orpheus.game.server.auction.OrpheusAuctionListener;
import com.orpheus.game.server.util.LogEventWriter;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.impl.AuctionManagerImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.config.Property;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.util.puzzle.baseimpl.XmlBasedPuzzleTypeSource;
import com.topcoder.util.rssgenerator.DataStore;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Enumeration;
import java.util.Map;

/**
 * <p>A ServletCopntextListener to be utilized by the <code>Game Server</code> module to bind the
 * necessary resources to servlet context at application startup to be used by handlers later, and to
 * clean them up at application shutdown.  Such resources include {@link PuzzleTypeSource},
 * {@link AdministrationManager}, {@link GameDataManager}, {@link AuctionManager}.</p>
 *
 * @version 1.0
 */
public class OrpheusServletContextListener implements ServletContextListener {

    /**
     * Receives notification of application startup events, and binds appropriate global resources
     * to the servlet context in response
     *
     * @param sce a ServletContextEvent describing the context of the application instance that
     *        is starting
     */
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        try {
            ConfigManager cm = ConfigManager.getInstance();
            String puzzleConfigFile = cm.getString(OrpheusFunctions.NAMESPACE, "PuzzleTypeSourceConfigFile");
            String rssDataStoreAttributeName = cm.getString(OrpheusFunctions.NAMESPACE, "RSSDataStore.Name");

            // Put PuzzleTypeSource to servlet context
            XmlBasedPuzzleTypeSource puzzleTypeSource = new XmlBasedPuzzleTypeSource(puzzleConfigFile);
            String puzzleTypeSourceCtxAttr = cm.getString(
                    OrpheusFunctions.NAMESPACE, "PuzzleTypeSourceContextAttr");
            servletContext.setAttribute(puzzleTypeSourceCtxAttr, puzzleTypeSource);

            // Initialize Auction Manager and put it to servlet context
            AuctionManager auctionManager = new AuctionManagerImpl("com.orpheus.game.server.AuctionManager");

            auctionManager.addAuctionListener(new OrpheusAuctionListener(servletContext));
            servletContext.setAttribute(KeyConstants.AUCTION_MANAGER_KEY, auctionManager);

            // Initialize Game Data Manager and put it to servlet context
            GameDataManagerImpl gameDataManager
                    = new GameDataManagerImpl(puzzleTypeSource, "com.orpheus.game.server.GameManager");
            gameDataManager.setAuctionManager(auctionManager);
            servletContext.setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, gameDataManager);

            // Initialize Administration Manager and put it to servlet context
            AdministrationManager adminManager
                    = new AdministrationManager("com.orpheus.game.server.AdministrationManager");
            adminManager.setGameDataManager(gameDataManager);
            servletContext.setAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY, adminManager);

            // Put RSS DataStore to context
            servletContext.setAttribute(rssDataStoreAttributeName, createRSSDataStore());

            // Put a map keeping track of games completed by players
            servletContext.setAttribute("CompletedGames", new HashMap());

            // Put a queue collecting the log events
            servletContext.setAttribute("LogEventQueue", Collections.synchronizedList(new LinkedList()));

            // Start the thread monitoring the log event queue
            Map paramMapping = new HashMap();
            Property p = cm.getPropertyObject(OrpheusFunctions.NAMESPACE, "LogEventParams");
            Enumeration propNames = p.propertyNames();
            while (propNames.hasMoreElements()) {
                String propName = (String) propNames.nextElement();
                String propValue = p.getValue(propName);
                paramMapping.put(propName, new Integer(propValue));
            }
            String specNamespace = cm.getString(OrpheusFunctions.NAMESPACE, "DBConnectionFactory.specNamespace");
            String dbKey = cm.getString(OrpheusFunctions.NAMESPACE, "DBConnectionFactory.factoryKey");
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace));
            DBConnectionFactory connectionFactory = (DBConnectionFactory) objectFactory.createObject(dbKey);
            LogEventWriter logEventWriter
                = new LogEventWriter(2 * 60 * 1000L, servletContext, connectionFactory, paramMapping);
            logEventWriter.start();
            servletContext.setAttribute("LogEventWriter", logEventWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives notification of application shutdown events, and cleans up resources as appropriate
     * in response
     *
     * @param sce a ServletContextEvent describing the context of the application instance that
     *        is stopping
     */
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        // Stop game data manager
        GameDataManagerImpl gameDataManager
                = (GameDataManagerImpl) servletContext.getAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
        if (gameDataManager != null) {
            try {
                gameDataManager.stopManager();
            } catch (GameDataException e) {
                e.printStackTrace();
            }
        }
        // Stop log event writer
        LogEventWriter logEventWriter = (LogEventWriter) servletContext.getAttribute("LogEventWriter");
        logEventWriter.stopWriter();
    }

    /**
     * <p>Creates a {@link DataStore} instance to be used in context of <code>Orpheus Game Server</code> application for
     * locating and obtaining the <code>RSS</code> feeds to to be sent to interested recipients.</p>
     *
     * @return a <code>DataStore</code> to be used to locate the desired items for RSS feeds. 
     * @throws com.orpheus.administration.persistence.InstantiationException if a <code>DataStore</code> instance can
     *         not be created.
     * @throws UnknownNamespaceException if a requested configuration namespace does not exist.
     */
    private DataStore createRSSDataStore() throws com.orpheus.administration.persistence.InstantiationException,
            UnknownNamespaceException {
        ConfigManager cm = ConfigManager.getInstance();
        boolean useRemoteDataStore = Boolean.valueOf(
                cm.getString(OrpheusFunctions.NAMESPACE, "RSSDataStore.Remote")).booleanValue();
        String namespace = cm.getString(OrpheusFunctions.NAMESPACE, "RSSDataStore.NameSpace");

        return ((useRemoteDataStore) ? (DataStore) new RemoteOrpheusMessageDataStore(namespace)
                : (DataStore) new LocalOrpheusMessageDataStore(namespace));
    }
}

