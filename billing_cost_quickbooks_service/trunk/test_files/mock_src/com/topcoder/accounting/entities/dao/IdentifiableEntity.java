package com.topcoder.accounting.entities.dao;

import java.io.Serializable;

/**
 * #### Purpose This is the base class for all entities that have an identification number. #### Thread Safety This
 * class is mutable and not thread safe
 */
public abstract class IdentifiableEntity implements Serializable {
    /**
     * #### Purpose Represents the primary identifier of the entity #### Usage it is managed with a getter and setter
     * #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private long id;

    /**
     * #### Purpose Empty constructor
     */
    protected IdentifiableEntity() {
    }

    /**
     * <p>
     * Getter method for the id.
     * </p>
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Setter method for the id.
     * </p>
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdentifiableEntity other = (IdentifiableEntity) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
