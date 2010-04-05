/**
 * AuditableObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public abstract class AuditableObject  implements java.io.Serializable {
    private java.util.Calendar creationTimestamp;

    private java.lang.String creationUser;

    private java.util.Calendar modificationTimestamp;

    private java.lang.String modificationUser;

    public AuditableObject() {
    }

    public AuditableObject(
           java.util.Calendar creationTimestamp,
           java.lang.String creationUser,
           java.util.Calendar modificationTimestamp,
           java.lang.String modificationUser) {
           this.creationTimestamp = creationTimestamp;
           this.creationUser = creationUser;
           this.modificationTimestamp = modificationTimestamp;
           this.modificationUser = modificationUser;
    }


    /**
     * Gets the creationTimestamp value for this AuditableObject.
     * 
     * @return creationTimestamp
     */
    public java.util.Calendar getCreationTimestamp() {
        return creationTimestamp;
    }


    /**
     * Sets the creationTimestamp value for this AuditableObject.
     * 
     * @param creationTimestamp
     */
    public void setCreationTimestamp(java.util.Calendar creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }


    /**
     * Gets the creationUser value for this AuditableObject.
     * 
     * @return creationUser
     */
    public java.lang.String getCreationUser() {
        return creationUser;
    }


    /**
     * Sets the creationUser value for this AuditableObject.
     * 
     * @param creationUser
     */
    public void setCreationUser(java.lang.String creationUser) {
        this.creationUser = creationUser;
    }


    /**
     * Gets the modificationTimestamp value for this AuditableObject.
     * 
     * @return modificationTimestamp
     */
    public java.util.Calendar getModificationTimestamp() {
        return modificationTimestamp;
    }


    /**
     * Sets the modificationTimestamp value for this AuditableObject.
     * 
     * @param modificationTimestamp
     */
    public void setModificationTimestamp(java.util.Calendar modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }


    /**
     * Gets the modificationUser value for this AuditableObject.
     * 
     * @return modificationUser
     */
    public java.lang.String getModificationUser() {
        return modificationUser;
    }


    /**
     * Sets the modificationUser value for this AuditableObject.
     * 
     * @param modificationUser
     */
    public void setModificationUser(java.lang.String modificationUser) {
        this.modificationUser = modificationUser;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuditableObject)) return false;
        AuditableObject other = (AuditableObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.creationTimestamp==null && other.getCreationTimestamp()==null) || 
             (this.creationTimestamp!=null &&
              this.creationTimestamp.equals(other.getCreationTimestamp()))) &&
            ((this.creationUser==null && other.getCreationUser()==null) || 
             (this.creationUser!=null &&
              this.creationUser.equals(other.getCreationUser()))) &&
            ((this.modificationTimestamp==null && other.getModificationTimestamp()==null) || 
             (this.modificationTimestamp!=null &&
              this.modificationTimestamp.equals(other.getModificationTimestamp()))) &&
            ((this.modificationUser==null && other.getModificationUser()==null) || 
             (this.modificationUser!=null &&
              this.modificationUser.equals(other.getModificationUser())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCreationTimestamp() != null) {
            _hashCode += getCreationTimestamp().hashCode();
        }
        if (getCreationUser() != null) {
            _hashCode += getCreationUser().hashCode();
        }
        if (getModificationTimestamp() != null) {
            _hashCode += getModificationTimestamp().hashCode();
        }
        if (getModificationUser() != null) {
            _hashCode += getModificationUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuditableObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "auditableObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creationTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationUser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creationUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modificationTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modificationTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modificationUser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modificationUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
