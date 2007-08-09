/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.bloom.BitSetSerializer;
import com.topcoder.bloom.serializers.DefaultBitSetSerializer;

import java.util.BitSet;

/**
 * <p>A custom {@link BitSetSerializer} implementation which is intended to resolve the interoperability issue between
 * the <code>Java</code> and <code>.NET</code> implementations of <code>Bloom Filter</code>.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusBitSetSerializer extends DefaultBitSetSerializer implements BitSetSerializer {

    /**
     * <p>Constructs new <code>OrpheusBitSetSerializer</code> instance. This implementation does nothing.</p>
     */
    public OrpheusBitSetSerializer() {
    }

    /**
     * <p>Returns string representation of this Bloom filter's bit array.</p>
     *
     * @param bitSet the BitSet data.
     * @param bitSetSize the size of bitSet.
     * @return serialized representation of the input BitSet.
     * @throws IllegalArgumentException if the input bitset is null or if bitSetSize is < 2.
     */
    public String getSerialized(BitSet bitSet, int bitSetSize) {
        String serialized = super.getSerialized(bitSet, bitSetSize);
        return serialized + "|default_bitArraySerializer";
    }
}
