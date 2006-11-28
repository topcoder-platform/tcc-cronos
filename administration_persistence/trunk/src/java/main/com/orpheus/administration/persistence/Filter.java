/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.io.Serializable;

/**
 * <p>An empty interface that marks an object as being a persistence filter. It is part of the composite
 * pattern. It is a common interface for both individual and composite components so that both the individual
 * components and composite components can be viewed uniformly. It is serializable so it can be used in the
 * Administration EJB framework. It is meant to parallel the Filter in the Search Builder component. The parallel
 * is almost exact, but all implementations will ignore the validation and cloning aspects. It also incorporates
 * only 1.3 version, and all deprecated items have been removed.</p>
 *
 * <p><strong>Thread Safety</strong>: Implementations of this interface are required to be thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface Filter extends Serializable {
}


