/**
 * TeamHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class TeamHeader  implements java.io.Serializable {
    private int captainPaymentPercentage;

    private long captainResourceId;

    private java.lang.String description;

    private boolean finalized;

    private java.lang.String name;

    private long projectId;

    private long teamId;

    public TeamHeader() {
    }

    public TeamHeader(
           int captainPaymentPercentage,
           long captainResourceId,
           java.lang.String description,
           boolean finalized,
           java.lang.String name,
           long projectId,
           long teamId) {
           this.captainPaymentPercentage = captainPaymentPercentage;
           this.captainResourceId = captainResourceId;
           this.description = description;
           this.finalized = finalized;
           this.name = name;
           this.projectId = projectId;
           this.teamId = teamId;
    }


    /**
     * Gets the captainPaymentPercentage value for this TeamHeader.
     * 
     * @return captainPaymentPercentage
     */
    public int getCaptainPaymentPercentage() {
        return captainPaymentPercentage;
    }


    /**
     * Sets the captainPaymentPercentage value for this TeamHeader.
     * 
     * @param captainPaymentPercentage
     */
    public void setCaptainPaymentPercentage(int captainPaymentPercentage) {
        this.captainPaymentPercentage = captainPaymentPercentage;
    }


    /**
     * Gets the captainResourceId value for this TeamHeader.
     * 
     * @return captainResourceId
     */
    public long getCaptainResourceId() {
        return captainResourceId;
    }


    /**
     * Sets the captainResourceId value for this TeamHeader.
     * 
     * @param captainResourceId
     */
    public void setCaptainResourceId(long captainResourceId) {
        this.captainResourceId = captainResourceId;
    }


    /**
     * Gets the description value for this TeamHeader.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this TeamHeader.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the finalized value for this TeamHeader.
     * 
     * @return finalized
     */
    public boolean isFinalized() {
        return finalized;
    }


    /**
     * Sets the finalized value for this TeamHeader.
     * 
     * @param finalized
     */
    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }


    /**
     * Gets the name value for this TeamHeader.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this TeamHeader.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the projectId value for this TeamHeader.
     * 
     * @return projectId
     */
    public long getProjectId() {
        return projectId;
    }


    /**
     * Sets the projectId value for this TeamHeader.
     * 
     * @param projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    /**
     * Gets the teamId value for this TeamHeader.
     * 
     * @return teamId
     */
    public long getTeamId() {
        return teamId;
    }


    /**
     * Sets the teamId value for this TeamHeader.
     * 
     * @param teamId
     */
    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TeamHeader)) return false;
        TeamHeader other = (TeamHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.captainPaymentPercentage == other.getCaptainPaymentPercentage() &&
            this.captainResourceId == other.getCaptainResourceId() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            this.finalized == other.isFinalized() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.projectId == other.getProjectId() &&
            this.teamId == other.getTeamId();
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
        _hashCode += getCaptainPaymentPercentage();
        _hashCode += new Long(getCaptainResourceId()).hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        _hashCode += (isFinalized() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += new Long(getProjectId()).hashCode();
        _hashCode += new Long(getTeamId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TeamHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "teamHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("captainPaymentPercentage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "captainPaymentPercentage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("captainResourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "captainResourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("finalized");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finalized"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("projectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("teamId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "teamId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
