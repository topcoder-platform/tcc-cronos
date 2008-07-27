/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * <p>
 * The mock Query class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockQuery implements Query {

    public int executeUpdate() {
        // TODO Auto-generated method stub
        return 0;
    }

    public List getResultList() {
        // TODO Auto-generated method stub
        return new ArrayList();
    }

    public Object getSingleResult() {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setFirstResult(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setFlushMode(FlushModeType arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setHint(String arg0, Object arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setMaxResults(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(String arg0, Object arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(String arg0, Date arg1, TemporalType arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(String arg0, Calendar arg1, TemporalType arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(int arg0, Date arg1, TemporalType arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    public Query setParameter(int arg0, Calendar arg1, TemporalType arg2) {
        // TODO Auto-generated method stub
        return null;
    }

}
