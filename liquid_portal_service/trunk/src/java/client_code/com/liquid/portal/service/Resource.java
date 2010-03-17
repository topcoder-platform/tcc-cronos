/**
 * Resource.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class Resource  extends com.liquid.portal.service.AuditableResourceStructure  implements java.io.Serializable {
    private long id;

    private java.lang.String name;

    private java.lang.Long phase;

    private java.lang.Long project;

    private com.liquid.portal.service.ResourcePropertiesEntry[] properties;

    private com.liquid.portal.service.ResourceRole resourceRole;

    private java.lang.Long[] submissions;

    public Resource() {
    }

    public Resource(
           java.util.Calendar creationTimestamp,
           java.lang.String creationUser,
           java.util.Calendar modificationTimestamp,
           java.lang.String modificationUser,
           long id,
           java.lang.String name,
           java.lang.Long phase,
           java.lang.Long project,
           com.liquid.portal.service.ResourcePropertiesEntry[] properties,
           com.liquid.portal.service.ResourceRole resourceRole,
           java.lang.Long[] submissions) {
        super(
            creationTimestamp,
            creationUser,
            modificationTimestamp,
            modificationUser);
        this.id = id;
        this.name = name;
        this.phase = phase;
        this.project = project;
        this.properties = properties;
        this.resourceRole = resourceRole;
        this.submissions = submissions;
    }


    /**
     * Gets the id value for this Resource.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Resource.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the name value for this Resource.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Resource.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the phase value for this Resource.
     * 
     * @return phase
     */
    public java.lang.Long getPhase() {
        return phase;
    }


    /**
     * Sets the phase value for this Resource.
     * 
     * @param phase
     */
    public void setPhase(java.lang.Long phase) {
        this.phase = phase;
    }


    /**
     * Gets the project value for this Resource.
     * 
     * @return project
     */
    public java.lang.Long getProject() {
        return project;
    }


    /**
     * Sets the project value for this Resource.
     * 
     * @param project
     */
    public void setProject(java.lang.Long project) {
        this.project = project;
    }


    /**
     * Gets the properties value for this Resource.
     * 
     * @return properties
     */
    public com.liquid.portal.service.ResourcePropertiesEntry[] getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this Resource.
     * 
     * @param properties
     */
    public void setProperties(com.liquid.portal.service.ResourcePropertiesEntry[] properties) {
        this.properties = properties;
    }


    /**
     * Gets the resourceRole value for this Resource.
     * 
     * @return resourceRole
     */
    public com.liquid.portal.service.ResourceRole getResourceRole() {
        return resourceRole;
    }


    /**
     * Sets the resourceRole value for this Resource.
     * 
     * @param resourceRole
     */
    public void setResourceRole(com.liquid.portal.service.ResourceRole resourceRole) {
        this.resourceRole = resourceRole;
    }


    /**
     * Gets the submissions value for this Resource.
     * 
     * @return submissions
     */
    public java.lang.Long[] getSubmissions() {
        return submissions;
    }


    /**
     * Sets the submissions value for this Resource.
     * 
     * @param submissions
     */
    public void setSubmissions(java.lang.Long[] submissions) {
        this.submissions = submissions;
    }

    public java.lang.Long getSubmissions(int i) {
        return this.submissions[i];
    }

    public void setSubmissions(int i, java.lang.Long _value) {
        this.submissions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Resource)) return false;
        Resource other = (Resource) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.id == other.getId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.phase==null && other.getPhase()==null) || 
             (this.phase!=null &&
              this.phase.equals(other.getPhase()))) &&
            ((this.project==null && other.getProject()==null) || 
             (this.project!=null &&
              this.project.equals(other.getProject()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              java.util.Arrays.equals(this.properties, other.getProperties()))) &&
            ((this.resourceRole==null && other.getResourceRole()==null) || 
             (this.resourceRole!=null &&
              this.resourceRole.equals(other.getResourceRole()))) &&
            ((this.submissions==null && other.getSubmissions()==null) || 
             (this.submissions!=null &&
              java.util.Arrays.equals(this.submissions, other.getSubmissions())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += new Long(getId()).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPhase() != null) {
            _hashCode += getPhase().hashCode();
        }
        if (getProject() != null) {
            _hashCode += getProject().hashCode();
        }
        if (getProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResourceRole() != null) {
            _hashCode += getResourceRole().hashCode();
        }
        if (getSubmissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubmissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubmissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Resource.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resource"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("project");
        elemField.setXmlName(new javax.xml.namespace.QName("", "project"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">>resource>properties>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceRole");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceRole"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resourceRole"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
