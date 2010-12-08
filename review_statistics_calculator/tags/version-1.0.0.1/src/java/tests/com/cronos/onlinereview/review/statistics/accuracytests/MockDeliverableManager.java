package com.cronos.onlinereview.review.statistics.accuracytests;

import java.util.Date;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableManager;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

public class MockDeliverableManager implements DeliverableManager {

	public Deliverable[] searchDeliverables(Filter arg0, Boolean arg1)
			throws DeliverablePersistenceException, SearchBuilderException,
			DeliverableCheckingException {
		Deliverable del = new Deliverable(2, 2, 2, 2L, false);
		del.setCompletionDate(new Date());
		Deliverable[] result = new Deliverable[1];
		result[0] = del;
		return result;
	}

	public Deliverable[] searchDeliverablesWithSubmissionFilter(Filter arg0,
			Boolean arg1) throws DeliverablePersistenceException,
			SearchBuilderException, DeliverableCheckingException {
		
		return null;
	}

}
