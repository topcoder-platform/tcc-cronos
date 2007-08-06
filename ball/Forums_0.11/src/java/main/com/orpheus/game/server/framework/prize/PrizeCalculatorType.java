/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.prize;

/**
 * <p>This interface defines the behavior of objects that represent the semantics of particular types of game winner
 * prize amount calculators. Such a type acts like a mini-manager or factory for prize amount calculators represented by
 * this type.</p>
 *
 * <p><b>Thread Safety</b>: Implementations are not required to be thread safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface PrizeCalculatorType {

    /**
     * <p>Gets the ID of this prize amount calculator type uniquelly identifying this type among other prize amount
     * calculator types within the same configuration scope.</p>
     *
     * @return an <code>int</code> providing the ID of this prize amount calculator type.
     */
    public int getId();

    /**
     * <p>Gets the name of this prize amount calculator type which should be considered an opaque label significant only
     * within the scope spanned by this component's configuration. Names are not required to be unique amongst prize
     * amount calculators.</p>
     *
     * @return a <code>String</code> providing the name of this prize amount calculator type.
     */
    public String getName();

    /**
     * <p>Gets the prize amount calculator of this type. The implementations may choose to create a new instance of
     * calculator on each call to this method or return the same cached calculator instance. In latter case the returned
     * instance must be thread-safe.</p>
     *
     * @return a <code>PrizeCalculator</code> to be used for calculating the prize amounts in accordance with rules set
     *         for this type.
     * @throws PrizeException if an unexpected error occurs while creating thew new prize amount calculator
     *         instance of this type.
     */
    public PrizeCalculator getCalculator() throws PrizeException;
}
