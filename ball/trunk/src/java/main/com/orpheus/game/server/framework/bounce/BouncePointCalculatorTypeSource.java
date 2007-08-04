/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.bounce;

import java.util.Collection;

/**
 * <p>This interface defines the behavior of objects that serve as registries or factories for
 * {@link BouncePointCalculatorType} objects.</p>
 *
 * <p><b>Thread Safety</b>: Implementations are required to operate in thread safe fashion.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface BouncePointCalculatorTypeSource {

    /**
     * <p>Gets the list of all existing bounce point calculator types provided by this source.</p>
     *
     * @return a <code>Collection</code> of <code>BouncePointCalculatorType</code> objects representing the existing
     *         bounce point calculator types. 
     */
    public Collection getAllTypes();

    /**
     * <p>Gets the bounce point calculator type referenced by the specified ID.</p>
     *
     * @param id a <code>int</code> providing the ID of desired bounce point calculator type.
     * @return a <code>BouncePointCalculatorType</code> mapped to specified ID. 
     * @throws BouncePointException if there is no bounce point calculator type mapped to specified ID or an unexpected
     *         error is encountered.
     */
    public BouncePointCalculatorType getBouncePointCalculatorType(int id) throws BouncePointException;
}
