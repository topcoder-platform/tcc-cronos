package com.topcoder.registration.team.service.failuretests;

import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferManager;
import com.topcoder.management.team.offer.OfferManagerException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

public class MockOfferManager implements OfferManager {

    public MockOfferManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void sendOffer(Offer arg0) {
        // TODO Auto-generated method stub
        
    }

    public void acceptOffer(long arg0) {
        // TODO Auto-generated method stub
        
    }

    public void rejectOffer(long arg0, String arg1) {
        if (arg0 == 1001) {
            throw new OfferManagerException("");
        }
        
    }

    public void expireOffer(long arg0) {
        // TODO Auto-generated method stub
        
    }

    public Offer[] findOffers(Filter arg0) {
        if (arg0 instanceof NullFilter) {
            throw new OfferManagerException("");
        }
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(1002);
        
        return new Offer[]{offer};
    }

}
