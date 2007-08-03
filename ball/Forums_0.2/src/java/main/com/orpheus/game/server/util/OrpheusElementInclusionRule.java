/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.web.sitestatistics.impl.InclusionRule;
import com.topcoder.util.web.sitestatistics.impl.rules.AndRule;
import com.topcoder.util.web.sitestatistics.impl.rules.IncludeElementRule;
import com.topcoder.util.web.sitestatistics.impl.rules.OrRule;
import com.topcoder.util.web.sitestatistics.impl.rules.LengthRule;

/**
 * <p>A custom implementation of elements inclusion rule to be used by <code>Web Site Statistics</code> component for
 * filtering the <code>HTML</code> elements which could be used for gathering the statistics.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusElementInclusionRule extends AndRule {

    /**
     * <p>Constructs new <code>OrpheusElementInclusionRule</code> instance with appropriate names of <code>HTML</code>
     * elements to be supported.</p>
     */
    public OrpheusElementInclusionRule() {
        super(new InclusionRule[] { new LengthRule(1, 20), new IncludeElementRule("BODY"),
                new OrRule(new InclusionRule[] {new IncludeElementRule("A", false),
		new IncludeElementRule("ABBR", false), new IncludeElementRule("ACRONYM", false),
		new IncludeElementRule("ADDRESS", false), new IncludeElementRule("B", false),
		new IncludeElementRule("BASEFONT", false), new IncludeElementRule("BDO", false),
		new IncludeElementRule("BIG", false), new IncludeElementRule("BLOCKQUOTE", false),
		new IncludeElementRule("BODY", false), new IncludeElementRule("CENTER", false),
		new IncludeElementRule("CITE", false), new IncludeElementRule("CODE", false),
		new IncludeElementRule("DD", false), new IncludeElementRule("DEL", false),
		new IncludeElementRule("DFN", false), new IncludeElementRule("DIV", false),
		new IncludeElementRule("DL", false), new IncludeElementRule("DT", false),
		new IncludeElementRule("EM", false), new IncludeElementRule("FONT", false),
		new IncludeElementRule("FORM", false), new IncludeElementRule("H1", false),
		new IncludeElementRule("H2", false), new IncludeElementRule("H3", false),
		new IncludeElementRule("H4", false), new IncludeElementRule("H5", false),
		new IncludeElementRule("H6", false), new IncludeElementRule("INS", false),
		new IncludeElementRule("KBD", false), new IncludeElementRule("LI", false),
		new IncludeElementRule("OL", false), new IncludeElementRule("P", false),
		new IncludeElementRule("PRE", false), new IncludeElementRule("Q", false),
		new IncludeElementRule("S", false), new IncludeElementRule("SAMP", false),
		new IncludeElementRule("SMALL", false), new IncludeElementRule("SPAN", false),
		new IncludeElementRule("STRIKE", false), new IncludeElementRule("STRONG", false),
		new IncludeElementRule("SUB", false), new IncludeElementRule("TABLE", false),
		new IncludeElementRule("TD", false), new IncludeElementRule("TH", false),
		new IncludeElementRule("TR", false), new IncludeElementRule("TT", false),
		new IncludeElementRule("U", false), new IncludeElementRule("UL", false),
		new IncludeElementRule("VAR", false)})});
    }
}
