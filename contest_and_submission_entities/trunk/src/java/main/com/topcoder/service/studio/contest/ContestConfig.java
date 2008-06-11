/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>config</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestConfig implements Serializable {
    /**
     * Generated serial version id.
     */
	private static final long serialVersionUID = -859939008179387964L;

	/**
	 * Represents the composite identifier for this entity.
	 */
	private Identifier id;

    /**
     * Represents the configuration value.
     */
    private String value;


    /**
     * Default constructor.
     */
    public ContestConfig() {
        // empty
    }

    /**
     * Return the identifier.
     * 
     * @return the identifier.
     */
	public Identifier getId() {
		return id;
	}


	/**
	 * Set the identifier
	 * 
	 * @param id the identifier
	 */
	public void setId(Identifier id) {
		this.id = id;
	}


    /**
     * Returns the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Updates the value with the specified value.
     *
     * @param value
     *            the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	
    	if ((obj == null) || (obj.getClass() != this.getClass())) {
    		return false;
    	}

    	ContestConfig other = (ContestConfig) obj;
    	
    	return id.equals(other.getId());
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Config}
     */
    @Override
    public int hashCode() {
    	return id.hashCode();
    }



    /**
     * Composite identifier for a ContestConfig
     * 
     * @since 1.1
     */
    public static class Identifier implements Serializable {
    	/**
    	 * Generated serial version id.
		 */
		private static final long serialVersionUID = -1512528048724458809L;

		/**
    	 * Contest to which this configuration belongs
    	 */
        private Contest contest;
        
        /**
         * Property being configured
         */
        private ContestProperty property;

        /**
         * Returns the contest.
         *
         * @return the contest.
         */
        public Contest getContest() {
            return contest;
        }

        /**
         * Updates the contest with the specified value.
         *
         * @param contest
         *            the contest to set.
         */
        public void setContest(Contest contest) {
            this.contest = contest;
        }

        /**
         * Returns the property.
         *
         * @return the property.
         */
        public ContestProperty getProperty() {
            return property;
        }

        /**
         * Updates the property with the specified value.
         *
         * @param property
         *            the property to set.
         */
        public void setProperty(ContestProperty property) {
            this.property = property;
        }
        
        /**
         * Override the equals method to be consistent with this class definition
         * 
         * @param obj object to compare to this
         * @return true if obj is equal to this object
         */
        @Override
        public boolean equals(Object obj) {
        	if (this == obj) {
        		return true;
        	}
        	
        	if ((obj == null) || (obj.getClass() != this.getClass())) {
        		return false;
        	}
        	
        	Identifier id = (Identifier) obj;
        	
        	return id.getContest().getContestId().equals(contest.getContestId()) &&
        		id.getProperty().getPropertyId() == property.getPropertyId();
        }
        
        /**
         * Override the hashCode method to provide a hash code based on the identifier.
         * 
         * @return a hashcode for this identifier
         */
        @Override
        public int hashCode() {
        	return (contest.getContestId() + "-" + property.getPropertyId()).hashCode();
        }
        
    }

}
