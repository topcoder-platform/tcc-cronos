/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.objectfactory.ObjectFactory;


/**
 * <p>
 * Static factory for supplying the auction DAO instance to the EJBs. It uses synchronized lazy instantiation to get
 * the initial instance of each DAO. Supports the creation of the <code>AuctionDAO</code> instance.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionDAOFactory {
    /**
     * <p>
     * Represents the namespace used to retrieve the dao instance in the getter method.
     * </p>
     */
    private static final String NAMESPACE = "com.orpheus.auction.persistence.AuctionDAOFactory";

    /**
     * <p>
     * Represents the property name to retrieve the specNamespace value.
     * </p>
     */
    private static final String SPEC_NAMESPACE_PROPERTY = "specNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the auctionDAO value.
     * </p>
     */
    private static final String AUCTION_DAO_PROPERTY = "auctionDAO";

    /**
     * <p>
     * Represents the auction DAO instance.
     * </p>
     *
     * <p>
     * Created when the instance is first requested, and will not change or be null after that.
     * </p>
     */
    private static AuctionDAO auctionDAO;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    private AuctionDAOFactory() {
    }

    /**
     * <p>
     * Obtains the <code>AuctionDAO</code> instance. If it does not exist yet, it will be created using ConfigManager
     * and ObjectFactory. Synchronized to avoid threading issues with lazy instantiation.
     * </p>
     *
     * @return the created <code>AuctionDAO</code> instance.
     *
     * @throws ObjectInstantiationException If there is an error with construction.
     */
    public static synchronized AuctionDAO getAuctionDAO() throws ObjectInstantiationException {
        if (auctionDAO == null) {
            // create the ObjectFactory with the namespace obtained from ConfigManager
            String specNamespace = AuctionPersistenceHelper.getStringPropertyValue(NAMESPACE,
                    SPEC_NAMESPACE_PROPERTY, true);

            // obtain the key for auction DAO form ConfgManager
            String auctionDAOKey = AuctionPersistenceHelper.getStringPropertyValue(NAMESPACE,
                    AUCTION_DAO_PROPERTY, true);

            // create the AuctionDAO object
            ObjectFactory objectFactory = AuctionPersistenceHelper.createObjectFactory(specNamespace);
            auctionDAO = (AuctionDAO) AuctionPersistenceHelper.createObject(objectFactory, auctionDAOKey,
                    AuctionDAO.class);
        }

        return auctionDAO;
    }
}
