/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.game.completion;

import com.orpheus.game.server.framework.game.completion.GameCompletion;
import com.orpheus.game.server.framework.game.completion.GameCompletionException;

/**
 * <p></p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FixedDateGameCompletionType extends AbstractGameCompletionType {

    /**
     * <p>Constructs new <code>FixedDateGameCompletionType</code> instance with specified ID and name. The ID must
     * uniquelly identify this type among other types while name is expected to provide an opaque label for this game
     * completion type.</p>
     *
     * @param id an <code>int</code> providing the ID of this game completion type.
     * @param name a <code>String</code> providing the name of this game completion type.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public FixedDateGameCompletionType(int id, String name) {
        super(id, name);
    }

    /**
     * <p>Gets the bounce point calculator of this type. The implementations may choose to create a new instance of
     * calculator on each call to this method or return the same cached calculator instance. In latter case the returned
     * instance must be thread-safe.</p>
     *
     * @return a <code>BouncePointCalculator</code> to be used for calculating the bounce points in accordance with
     *         rules set for this type.
     * @throws GameCompletionException if an unexpected error occurs while creating thew new bounce point calculator
     * instance of this type.
     */
    public GameCompletion getGameCompletion() throws GameCompletionException {
        return new FixedDateGameCompletion();
    }
}
