package com.topcoder.project.phases;

import com.topcoder.util.errorhandling.BaseRuntimeException;

public class CyclicDependencyException extends BaseRuntimeException {

    public CyclicDependencyException(String msg) {
        super(msg);
    }
}
