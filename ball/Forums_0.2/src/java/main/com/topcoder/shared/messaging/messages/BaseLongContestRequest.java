package com.topcoder.shared.messaging.messages;

import java.io.Serializable;

/**
 * @author dok
 * @version $Revision: 1.1 $ Date: 2005/01/01 00:00:00
 *          Create Date: Feb 7, 2006
 */
public abstract class BaseLongContestRequest implements Serializable {
    protected boolean sync = true;

    public boolean isSynchronous() {
        return sync;
    }

}
