/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;


/**
 * <p>
 * Interface specifying the contract for translating between <code>Auction</code> and <code>Bid</code> instances and
 * their transport equivalents <code>AuctionDTO</code> and <code>BidDTO</code>. The former are value objects used on
 * the outside world and a DTO is an entity this component uses to ferry info between the clients and the DAOs.
 * Implementations will constrain the data types they support.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionTranslator {
    /**
     * <p>
     * Assembles the <code>Auction</code> from the <code>AuctionDTO</code>. The DTO is used as the custom transfer
     * object inside this component to ensure serializability.
     * </p>
     *
     * @param auctionDTO the <code>AuctionDTO</code> instance to assemble.
     *
     * @return auction the assembled <code>Auction</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     * @throws TranslationException If there is any problem during the translation.
     */
    Auction assembleAuction(AuctionDTO auctionDTO) throws TranslationException;

    /**
     * <p>
     * Assembles the <code>AuctionDTO</code> from the <code>Auction</code>. The DTO is used as the custom transfer
     * object inside this component to ensure serializability.
     * </p>
     *
     * @param auction the <code>Auction</code> instance to assemble.
     *
     * @return auctionDTO the assembled <code>AuctionDTO</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     * @throws TranslationException If there is any problem during the translation.
     */
    AuctionDTO assembleAuctionDTO(Auction auction) throws TranslationException;

    /**
     * <p>
     * Assembles the <code>Bid</code> from the <code>BidDTO</code>. The DTO is used as the custom transfer object
     * inside this component to ensure serializability.
     * </p>
     *
     * @param bidDTO the <code>BidDTO</code> instance to assemble.
     *
     * @return bid the assembled <code>Bid</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     * @throws TranslationException If there is any problem during the translation.
     */
    Bid assembleBid(BidDTO bidDTO) throws TranslationException;

    /**
     * <p>
     * Assembles the <code>BidDTO</code> from the <code>Bid</code>. The DTO is used as the custom transfer object
     * inside this component to ensure serializability.
     * </p>
     *
     * @param bid the <code>Bid</code> instance to assemble.
     *
     * @return bidDTO the assembled <code>BidDTO</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     * @throws TranslationException If there is any problem during the translation.
     */
    BidDTO assembleBidDTO(Bid bid) throws TranslationException;
}
