package com.topcoder.registration.validation.failuretests;

import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * A mock implementation of AbstractConfigurableValidator for testing.
 * @author slion
 *
 */
class MockAbstractConfigurableValidator extends AbstractConfigurableValidator {

    /**
     * Flag indicates if throw the exception.
     */
    private boolean throwException = false;

    /**
     * Sets the flag.
     * @param flag
     */
    public void setThrowException(boolean flag) {
        this.throwException = flag;
    }

    /**
     * Default constructor with bundle info.
     * @param bundleInfo
     */
    public MockAbstractConfigurableValidator(BundleInfo bundleInfo) {
        super(bundleInfo);
    }
    
    /**
     * Default constructor with message.
     * @param message
     */
    public MockAbstractConfigurableValidator(String message) {
        super(message);
    }

    public String getMessage(Object arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException("");
        }
        if (!(arg0 instanceof ValidationInfo)) {
            throw new IllegalArgumentException("");
        }
        if (throwException) {
            throw new ValidationProcessingException("");
        }

        return "test";
    }
}