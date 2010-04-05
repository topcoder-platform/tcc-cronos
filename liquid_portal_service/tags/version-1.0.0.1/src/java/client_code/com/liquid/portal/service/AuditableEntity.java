/**
 * AuditableEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public abstract class AuditableEntity  implements java.io.Serializable {
    private long id;

    private java.lang.String createUsername;

    private java.util.Calendar createDate;

    private java.lang.String modifyUsername;

    private java.util.Calendar modifyDate;

    private java.lang.String name;

    private java.lang.Boolean deleted;

    public AuditableEntity() {
    }

    public AuditableEntity(
           long id,
           java.lang.String createUsername,
           java.util.Calendar createDate,
           java.lang.String modifyUsername,
           java.util.Calendar modifyDate,
           java.lang.String name,
           java.lang.Boolean deleted) {
           this.id = id;
           this.createUsername = createUsername;
           this.createDate = createDate;
           this.modifyUsername = modifyUsername;
           this.modifyDate = modifyDate;
           this.name = name;
           this.deleted = deleted;
    }


    /**
     * Gets the id value for this AuditableEntity.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this AuditableEntity.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the createUsername value for this AuditableEntity.
     * 
     * @return createUsername
     */
    public java.lang.String getCreateUsername() {
        return createUsername;
    }


    /**
     * Sets the createUsername value for this AuditableEntity.
     * 
     * @param createUsername
     */
    public void setCreateUsername(java.lang.String createUsername) {
        this.createUsername = createUsername;
    }


    /**
     * Gets the createDate value for this AuditableEntity.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this AuditableEntity.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the modifyUsername value for this AuditableEntity.
     * 
     * @return modifyUsername
     */
    public java.lang.String getModifyUsername() {
        return modifyUsername;
    }


    /**
     * Sets the modifyUsername value for this AuditableEntity.
     * 
     * @param modifyUsername
     */
    public void setModifyUsername(java.lang.String modifyUsername) {
        this.modifyUsername = modifyUsername;
    }


    /**
     * Gets the modifyDate value for this AuditableEntity.
     * 
     * @return modifyDate
     */
    public java.util.Calendar getModifyDate() {
        return modifyDate;
    }


    /**
     * Sets the modifyDate value for this AuditableEntity.
     * 
     * @param modifyDate
     */
    public void setModifyDate(java.util.Calendar modifyDate) {
        this.modifyDate = modifyDate;
    }


    /**
     * Gets the name value for this AuditableEntity.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this AuditableEntity.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the deleted value for this AuditableEntity.
     * 
     * @return deleted
     */
    public java.lang.Boolean getDeleted() {
        return deleted;
    }


    /**
     * Sets the deleted value for this AuditableEntity.
     * 
     * @param deleted
     */
    public void setDeleted(java.lang.Boolean deleted) {
        this.deleted = deleted;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuditableEntity)) return false;
        AuditableEntity other = (AuditableEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.createUsername==null && other.getCreateUsername()==null) || 
             (this.createUsername!=null &&
              this.createUsername.equals(other.getCreateUsername()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.modifyUsername==null && other.getModifyUsername()==null) || 
             (this.modifyUsername!=null &&
              this.modifyUsername.equals(other.getModifyUsername()))) &&
            ((this.modifyDate==null && other.getModifyDate()==null) || 
             (this.modifyDate!=null &&
              this.modifyDate.equals(other.getModifyDate()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.deleted==null && other.getDeleted()==null) || 
             (this.deleted!=null &&
              this.deleted.equals(other.getDeleted())));
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
        _hashCode += new Long(getId()).hashCode();
        if (getCreateUsername() != null) {
            _hashCode += getCreateUsername().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getModifyUsername() != null) {
            _hashCode += getModifyUsername().hashCode();
        }
        if (getModifyDate() != null) {
            _hashCode += getModifyDate().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDeleted() != null) {
            _hashCode += getDeleted().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuditableEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "auditableEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createUsername");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createUsername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyUsername");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifyUsername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifyDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("deleted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
