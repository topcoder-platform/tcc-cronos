/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.randomstringimg.ObfuscationAlgorithm;
import com.topcoder.randomstringimg.ObfuscationException;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * <p>A custom image obfuscation algorithm which does nothing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusImageObfuscator implements ObfuscationAlgorithm {

    /**
     * <p>Constructs new <code>OrpheusImageObfuscator</code> instance. This implementation does nothing.</p>
     */
    public OrpheusImageObfuscator() {
    }

    /**
     * <p>Gets the processing type of this obfuscator (one of the constants defined in this class).</p>
     *
     * @return an <code>int</code> providing the processing type.
     */
    public int getType() {
        return ObfuscationAlgorithm.AFTER_TEXT;
    }

    /**
     * <p>Obfuscates a given image (through a BufferedImage instance). This implementation does noting.</p>
     *
     * @param image the buffered image that should be obfuscated.
     * @param background the background color used in the image.
     * @param text the text color used in the image.
     * @throws ObfuscationException if an exception occured during the obfuscation with the cause being dependant on the
     *         implementation of each algorithm (this exception can be used to wrap other exceptions or to simply embed
     *         a message explaining the problem).
     */
    public void obfuscate(BufferedImage image, Color background, Color text) throws ObfuscationException {
    }
}
