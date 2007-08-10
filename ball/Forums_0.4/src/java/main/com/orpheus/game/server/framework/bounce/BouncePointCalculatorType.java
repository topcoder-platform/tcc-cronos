/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.bounce;

/**
 * <p>This interface defines the behavior of objects that represent the semantics of particular types of bounce point
 * calculators. Such a type acts like a mini-manager or factory for bounce point calculators represented by this type.
 * </p>
 *
 * <p><b>Thread Safety</b>: Implementations are required to be thread safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface BouncePointCalculatorType {

    /**
     * <p>Gets the ID of this bounce point calculator type uniquelly identifying this type among other bounce point
     * calculator types within the same configuration scope.</p>
     *
     * @return an <code>int</code> providing the ID of this bounce point calculator type.
     */
    public int getId();

    /**
     * <p>Gets the name of this bounce point calculator type which should be considered an opaque label significant only
     * within the scope spanned by this component's configuration. Names are not required to be unique amongst bounce
     * point calculators.</p>
     *
     * @return a <code>String</code> providing the name of this bounce point calculator type.
     */
    public String getName();

    /**
     * <p>Gets the bounce point calculator of this type. The implementations may choose to create a new instance of
     * calculator on each call to this method or return the same cached calculator instance. In latter case the returned
     * instance must be thread-safe.</p>
     *
     * @return a <code>BouncePointCalculator</code> to be used for calculating the bounce points in accordance with
     *         rules set for this type.
     * @throws BouncePointException if an unexpected error occurs while creating thew new bounce point calculator
     *         instance of this type.
     */
    public BouncePointCalculator getCalculator() throws BouncePointException;
}
