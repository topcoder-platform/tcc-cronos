/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.server.framework.prize.PrizeCalculatorType;

/**
 * <p>A base class for classes implementing {@link PrizeCalculatorType} interface. Maintains the ID and the name
 * of the prize amount calculator type and implements the respective methods from the interface.</p>
 *
 * @author isv
 * @version 1.0
 */
public abstract class AbstractPrizeCalculatorType implements PrizeCalculatorType {

    /**
     * <p>An <code>int</code> providing the ID of this prize amount calculator type.</p>
     */
    private final int id;

    /**
     * <p>A <code>String</code> providing the name of this prize amount calculator type.</p>
     */
    private final String name;

    /**
     * <p>Constructs new <code>AbstractPrizeCalculatorType</code> instance with specified ID and name. The ID must
     * uniquelly identify this type among other types while name is expected to provide an opaque label for this prize
     * amount calculator type.</p>
     *
     * @param id an <code>int</code> providing the ID of this prize amount calculator type.
     * @param name a <code>String</code> providing the name of this prize amount calculator type.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    protected AbstractPrizeCalculatorType(int id, String name) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter [name] is NULL");
        }
        this.id = id;
        this.name = name;
    }

    /**
     * <p>Gets the ID of this prize amount calculator type uniquelly identifying this type among other prize amount
     * calculator types within the same configuration scope.</p>
     *
     * @return an <code>int</code> providing the ID of this prize amount calculator type.
     */
    public int getId() {
        return this.id;
    }

    /**
     * <p>Gets the name of this prize amount calculator type which should be considered an opaque label significant only
     * within the scope spanned by this component's configuration. Names are not required to be unique amongst prize
     * amount calculators.</p>
     *
     * @return a <code>String</code> providing the name of this prize amount calculator type.
     */
    public String getName() {
        return this.name;
    }
}
