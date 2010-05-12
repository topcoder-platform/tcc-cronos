/**
 * CompetitionData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class CompetitionData  implements java.io.Serializable {
    private boolean autoReschedule;

    private long billingProjectId;

    private boolean cca;

    private java.lang.String cockpitProjectName;

    private java.lang.String contestName;

    private java.lang.String contestTypeName;

    private java.util.Calendar requestedStartDate;

    private java.lang.String subContestTypeName;

    public CompetitionData() {
    }

    public CompetitionData(
           boolean autoReschedule,
           long billingProjectId,
           boolean cca,
           java.lang.String cockpitProjectName,
           java.lang.String contestName,
           java.lang.String contestTypeName,
           java.util.Calendar requestedStartDate,
           java.lang.String subContestTypeName) {
           this.autoReschedule = autoReschedule;
           this.billingProjectId = billingProjectId;
           this.cca = cca;
           this.cockpitProjectName = cockpitProjectName;
           this.contestName = contestName;
           this.contestTypeName = contestTypeName;
           this.requestedStartDate = requestedStartDate;
           this.subContestTypeName = subContestTypeName;
    }


    /**
     * Gets the autoReschedule value for this CompetitionData.
     * 
     * @return autoReschedule
     */
    public boolean isAutoReschedule() {
        return autoReschedule;
    }


    /**
     * Sets the autoReschedule value for this CompetitionData.
     * 
     * @param autoReschedule
     */
    public void setAutoReschedule(boolean autoReschedule) {
        this.autoReschedule = autoReschedule;
    }


    /**
     * Gets the billingProjectId value for this CompetitionData.
     * 
     * @return billingProjectId
     */
    public long getBillingProjectId() {
        return billingProjectId;
    }


    /**
     * Sets the billingProjectId value for this CompetitionData.
     * 
     * @param billingProjectId
     */
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }


    /**
     * Gets the cca value for this CompetitionData.
     * 
     * @return cca
     */
    public boolean isCca() {
        return cca;
    }


    /**
     * Sets the cca value for this CompetitionData.
     * 
     * @param cca
     */
    public void setCca(boolean cca) {
        this.cca = cca;
    }


    /**
     * Gets the cockpitProjectName value for this CompetitionData.
     * 
     * @return cockpitProjectName
     */
    public java.lang.String getCockpitProjectName() {
        return cockpitProjectName;
    }


    /**
     * Sets the cockpitProjectName value for this CompetitionData.
     * 
     * @param cockpitProjectName
     */
    public void setCockpitProjectName(java.lang.String cockpitProjectName) {
        this.cockpitProjectName = cockpitProjectName;
    }


    /**
     * Gets the contestName value for this CompetitionData.
     * 
     * @return contestName
     */
    public java.lang.String getContestName() {
        return contestName;
    }


    /**
     * Sets the contestName value for this CompetitionData.
     * 
     * @param contestName
     */
    public void setContestName(java.lang.String contestName) {
        this.contestName = contestName;
    }


    /**
     * Gets the contestTypeName value for this CompetitionData.
     * 
     * @return contestTypeName
     */
    public java.lang.String getContestTypeName() {
        return contestTypeName;
    }


    /**
     * Sets the contestTypeName value for this CompetitionData.
     * 
     * @param contestTypeName
     */
    public void setContestTypeName(java.lang.String contestTypeName) {
        this.contestTypeName = contestTypeName;
    }


    /**
     * Gets the requestedStartDate value for this CompetitionData.
     * 
     * @return requestedStartDate
     */
    public java.util.Calendar getRequestedStartDate() {
        return requestedStartDate;
    }


    /**
     * Sets the requestedStartDate value for this CompetitionData.
     * 
     * @param requestedStartDate
     */
    public void setRequestedStartDate(java.util.Calendar requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }


    /**
     * Gets the subContestTypeName value for this CompetitionData.
     * 
     * @return subContestTypeName
     */
    public java.lang.String getSubContestTypeName() {
        return subContestTypeName;
    }


    /**
     * Sets the subContestTypeName value for this CompetitionData.
     * 
     * @param subContestTypeName
     */
    public void setSubContestTypeName(java.lang.String subContestTypeName) {
        this.subContestTypeName = subContestTypeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompetitionData)) return false;
        CompetitionData other = (CompetitionData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.autoReschedule == other.isAutoReschedule() &&
            this.billingProjectId == other.getBillingProjectId() &&
            this.cca == other.isCca() &&
            ((this.cockpitProjectName==null && other.getCockpitProjectName()==null) || 
             (this.cockpitProjectName!=null &&
              this.cockpitProjectName.equals(other.getCockpitProjectName()))) &&
            ((this.contestName==null && other.getContestName()==null) || 
             (this.contestName!=null &&
              this.contestName.equals(other.getContestName()))) &&
            ((this.contestTypeName==null && other.getContestTypeName()==null) || 
             (this.contestTypeName!=null &&
              this.contestTypeName.equals(other.getContestTypeName()))) &&
            ((this.requestedStartDate==null && other.getRequestedStartDate()==null) || 
             (this.requestedStartDate!=null &&
              this.requestedStartDate.equals(other.getRequestedStartDate()))) &&
            ((this.subContestTypeName==null && other.getSubContestTypeName()==null) || 
             (this.subContestTypeName!=null &&
              this.subContestTypeName.equals(other.getSubContestTypeName())));
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
        _hashCode += (isAutoReschedule() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getBillingProjectId()).hashCode();
        _hashCode += (isCca() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCockpitProjectName() != null) {
            _hashCode += getCockpitProjectName().hashCode();
        }
        if (getContestName() != null) {
            _hashCode += getContestName().hashCode();
        }
        if (getContestTypeName() != null) {
            _hashCode += getContestTypeName().hashCode();
        }
        if (getRequestedStartDate() != null) {
            _hashCode += getRequestedStartDate().hashCode();
        }
        if (getSubContestTypeName() != null) {
            _hashCode += getSubContestTypeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompetitionData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competitionData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoReschedule");
        elemField.setXmlName(new javax.xml.namespace.QName("", "autoReschedule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingProjectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingProjectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cockpitProjectName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cockpitProjectName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestedStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subContestTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subContestTypeName"));
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
