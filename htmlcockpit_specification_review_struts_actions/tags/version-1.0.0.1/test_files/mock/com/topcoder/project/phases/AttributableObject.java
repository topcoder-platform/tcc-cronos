package com.topcoder.project.phases;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AttributableObject implements Serializable {

    private Map attributes;

    public AttributableObject() {
        this.attributes = new HashMap();
    }

    public Serializable getAttribute(Serializable key) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");

        return ((Serializable) this.attributes.get(key));
    }

    public Map getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map attributes) {
        if (attributes == null)
            return;
        this.attributes = attributes;
    }

    public void setAttribute(Serializable key, Serializable value) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");
        ProjectPhaseHelper.checkObjectNotNull(value, "value");

        this.attributes.put(key, value);
    }

    public Serializable removeAttribute(Serializable key) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");

        return ((Serializable) this.attributes.remove(key));
    }

    public void clearAttributes() {
        this.attributes.clear();
    }
}
