package com.topcoder.formvalidator;

import org.w3c.dom.Document;

public interface ValidationResult {

	boolean isValid();

	Document getResultInXmlFormat();

}
