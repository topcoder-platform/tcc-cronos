/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.prize;

import java.util.Collection;

/**
 * <p>This interface defines the behavior of objects that serve as registries or factories for
 * {@link PrizeCalculatorType} objects.</p>
 *
 * <p><b>Thread Safety</b>: Implementations are required to operate in thread safe fashion.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface PrizeCalculatorTypeSource {

    /**
     * <p>Gets the list of all existing prize amount calculator types provided by this source.</p>
     *
     * @return a <code>Collection</code> of <code>PrizeCalculatorType</code> objects representing the existing prize
     *         amount calculator types.
     */
    public Collection getAllTypes();

    /**
     * <p>Gets the prize amount calculator type referenced by the specified ID.</p>
     *
     * @param id a <code>int</code> providing the ID of desired prize amount calculator type.
     * @return a <code>PrizeCalculatorType</code> mapped to specified ID.
     * @throws PrizeException if there is no prize amount calculator type mapped to specified ID or an unexpected
     *         error is encountered.
     */
    public PrizeCalculatorType getPrizeCalculatorType(int id) throws PrizeException;
}
