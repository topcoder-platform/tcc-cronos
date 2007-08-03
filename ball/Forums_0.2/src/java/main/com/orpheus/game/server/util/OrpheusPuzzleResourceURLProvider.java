/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.puzzle.slidingtile.renderer.PuzzleResourceURLProvider;
import com.topcoder.util.puzzle.PuzzleResource;

/**
 * <p>A custom implementation of {@link PuzzleResourceURLProvider} to be used in context of <code>Orpheus Game Server
 * </code> for providing the URLs for the resources(images) for <code>Sliding Tile</code> puzzles.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusPuzzleResourceURLProvider implements PuzzleResourceURLProvider {

    /**
     * <p>Constructs new <code>OrpheusPuzzleResourceURLProvider</code> instance. This implementation does nothing.</p>
     */
    public OrpheusPuzzleResourceURLProvider() {
    }

    /**
     * <p>Returns the URL for the specified <code>PuzzleResource</code>. Such an URL can be used for retrieving the
     * content of the image corresponding to specified puzzle resource.</p>
     *
     * @param resource a <code>PuzzleResource</code> representing the resource for which to retrieve the URL.
     * @return a <code>String</code> providing the URL for the specified <code>PuzzleResource</code>.
     * @throws IllegalArgumentException if <code>resource</code> is <code>null</code>.
     */
    public String getURLForResource(PuzzleResource resource) {
        if (resource == null) {
            return "/server/puzzleResourceIsNull.do";
        }
        return "/server/getPuzzleImage.do?puzzleResource=" + resource.getName();
    }
}
