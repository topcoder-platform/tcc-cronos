package com.topcoder.service;

/**
 * This class represents the identify of a service category. It is uniquely defined by id and name
 * combination. 'equals' method is overriden to ensure categories are equal if their name and id are same
 * <p>
 * This class is immutable, and thread-safe.
 * </p>
 * 
 */
public class Category {

    /**
     * Represents the id of category. It can't be negative. It is set in constructor, and never changed later.
     * 
     */
    private long id = -1;

    /**
     * Represents the name of category. It shouldn't be null or empty. It is set in constructor, and never
     * changed later.
     * 
     */
    private String name;

    /**
     * Constructor. Assign given args to corresponding fields.
     * 
     * 
     * @param id
     *            the id of category
     * @param name
     *            the name of category
     * @throws IllegalArgumentException
     *             if id is negative or name is null or empty.
     */
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter of id field.
     * 
     * 
     * @return id of this category
     */
    public long getId() {
        return this.id;
    }

    /**
     * Getter of name field.
     * 
     * 
     * @return name of this category
     */
    public String getName() {
        return this.name;
    }

    /**
     * Override of equals method. return (obj instanceof Category) && this.id = obj.id &&
     * this.name.equals(obj.name);
     * 
     * 
     * @return wether this category equals to given obj.
     * @param obj
     *            another object to compare. null is allowed.
     */
    public boolean equals(Object obj) {
        return (obj instanceof Category) && this.id == ((Category) obj).id
                && this.name.equals(((Category) obj).name);
    }

    /**
     * Override of hashcode method. Returns (this.id XOR this.name.hashCode());
     * 
     * 
     * @return the hash code of this object.
     */
    public int hashCode() {
        return (int) this.id ^ this.name.hashCode();
    }
}
