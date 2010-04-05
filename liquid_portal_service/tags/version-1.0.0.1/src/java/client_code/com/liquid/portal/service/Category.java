/**
 * Category.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class Category  implements java.io.Serializable {
    private java.lang.String catalogName;

    private java.lang.String description;

    private java.lang.Long id;

    private java.lang.String name;

    private com.liquid.portal.service.Category parentCategory;

    private com.liquid.portal.service.Status status;

    private boolean viewable;

    public Category() {
    }

    public Category(
           java.lang.String catalogName,
           java.lang.String description,
           java.lang.Long id,
           java.lang.String name,
           com.liquid.portal.service.Category parentCategory,
           com.liquid.portal.service.Status status,
           boolean viewable) {
           this.catalogName = catalogName;
           this.description = description;
           this.id = id;
           this.name = name;
           this.parentCategory = parentCategory;
           this.status = status;
           this.viewable = viewable;
    }


    /**
     * Gets the catalogName value for this Category.
     * 
     * @return catalogName
     */
    public java.lang.String getCatalogName() {
        return catalogName;
    }


    /**
     * Sets the catalogName value for this Category.
     * 
     * @param catalogName
     */
    public void setCatalogName(java.lang.String catalogName) {
        this.catalogName = catalogName;
    }


    /**
     * Gets the description value for this Category.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Category.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the id value for this Category.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this Category.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the name value for this Category.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Category.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the parentCategory value for this Category.
     * 
     * @return parentCategory
     */
    public com.liquid.portal.service.Category getParentCategory() {
        return parentCategory;
    }


    /**
     * Sets the parentCategory value for this Category.
     * 
     * @param parentCategory
     */
    public void setParentCategory(com.liquid.portal.service.Category parentCategory) {
        this.parentCategory = parentCategory;
    }


    /**
     * Gets the status value for this Category.
     * 
     * @return status
     */
    public com.liquid.portal.service.Status getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Category.
     * 
     * @param status
     */
    public void setStatus(com.liquid.portal.service.Status status) {
        this.status = status;
    }


    /**
     * Gets the viewable value for this Category.
     * 
     * @return viewable
     */
    public boolean isViewable() {
        return viewable;
    }


    /**
     * Sets the viewable value for this Category.
     * 
     * @param viewable
     */
    public void setViewable(boolean viewable) {
        this.viewable = viewable;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Category)) return false;
        Category other = (Category) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.catalogName==null && other.getCatalogName()==null) || 
             (this.catalogName!=null &&
              this.catalogName.equals(other.getCatalogName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.parentCategory==null && other.getParentCategory()==null) || 
             (this.parentCategory!=null &&
              this.parentCategory.equals(other.getParentCategory()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            this.viewable == other.isViewable();
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
        if (getCatalogName() != null) {
            _hashCode += getCatalogName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getParentCategory() != null) {
            _hashCode += getParentCategory().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        _hashCode += (isViewable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Category.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalogName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "catalogName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("parentCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "status"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viewable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "viewable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
