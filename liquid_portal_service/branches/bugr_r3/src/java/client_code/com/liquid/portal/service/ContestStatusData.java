/**
 * ContestStatusData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestStatusData  implements java.io.Serializable {
    private long statusId;

    private java.lang.String name;

    private java.lang.String description;

    private java.lang.Long[] allowableNextStatus;

    private java.lang.String displayIcon;

    public ContestStatusData() {
    }

    public ContestStatusData(
           long statusId,
           java.lang.String name,
           java.lang.String description,
           java.lang.Long[] allowableNextStatus,
           java.lang.String displayIcon) {
           this.statusId = statusId;
           this.name = name;
           this.description = description;
           this.allowableNextStatus = allowableNextStatus;
           this.displayIcon = displayIcon;
    }


    /**
     * Gets the statusId value for this ContestStatusData.
     * 
     * @return statusId
     */
    public long getStatusId() {
        return statusId;
    }


    /**
     * Sets the statusId value for this ContestStatusData.
     * 
     * @param statusId
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }


    /**
     * Gets the name value for this ContestStatusData.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ContestStatusData.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the description value for this ContestStatusData.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this ContestStatusData.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the allowableNextStatus value for this ContestStatusData.
     * 
     * @return allowableNextStatus
     */
    public java.lang.Long[] getAllowableNextStatus() {
        return allowableNextStatus;
    }


    /**
     * Sets the allowableNextStatus value for this ContestStatusData.
     * 
     * @param allowableNextStatus
     */
    public void setAllowableNextStatus(java.lang.Long[] allowableNextStatus) {
        this.allowableNextStatus = allowableNextStatus;
    }

    public java.lang.Long getAllowableNextStatus(int i) {
        return this.allowableNextStatus[i];
    }

    public void setAllowableNextStatus(int i, java.lang.Long _value) {
        this.allowableNextStatus[i] = _value;
    }


    /**
     * Gets the displayIcon value for this ContestStatusData.
     * 
     * @return displayIcon
     */
    public java.lang.String getDisplayIcon() {
        return displayIcon;
    }


    /**
     * Sets the displayIcon value for this ContestStatusData.
     * 
     * @param displayIcon
     */
    public void setDisplayIcon(java.lang.String displayIcon) {
        this.displayIcon = displayIcon;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestStatusData)) return false;
        ContestStatusData other = (ContestStatusData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.statusId == other.getStatusId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.allowableNextStatus==null && other.getAllowableNextStatus()==null) || 
             (this.allowableNextStatus!=null &&
              java.util.Arrays.equals(this.allowableNextStatus, other.getAllowableNextStatus()))) &&
            ((this.displayIcon==null && other.getDisplayIcon()==null) || 
             (this.displayIcon!=null &&
              this.displayIcon.equals(other.getDisplayIcon())));
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
        _hashCode += new Long(getStatusId()).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getAllowableNextStatus() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllowableNextStatus());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllowableNextStatus(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDisplayIcon() != null) {
            _hashCode += getDisplayIcon().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestStatusData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestStatusData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusId"));
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowableNextStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allowableNextStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("displayIcon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "displayIcon"));
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
