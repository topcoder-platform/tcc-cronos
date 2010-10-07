/**
 * ContestMultiRoundInformationData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestMultiRoundInformationData  implements java.io.Serializable {
    private long id;

    private java.lang.String milestoneDate;

    private java.lang.Boolean submittersLockedBetweenRounds;

    private java.lang.String roundOneIntroduction;

    private java.lang.String roundTwoIntroduction;

    public ContestMultiRoundInformationData() {
    }

    public ContestMultiRoundInformationData(
           long id,
           java.lang.String milestoneDate,
           java.lang.Boolean submittersLockedBetweenRounds,
           java.lang.String roundOneIntroduction,
           java.lang.String roundTwoIntroduction) {
           this.id = id;
           this.milestoneDate = milestoneDate;
           this.submittersLockedBetweenRounds = submittersLockedBetweenRounds;
           this.roundOneIntroduction = roundOneIntroduction;
           this.roundTwoIntroduction = roundTwoIntroduction;
    }


    /**
     * Gets the id value for this ContestMultiRoundInformationData.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this ContestMultiRoundInformationData.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the milestoneDate value for this ContestMultiRoundInformationData.
     * 
     * @return milestoneDate
     */
    public java.lang.String getMilestoneDate() {
        return milestoneDate;
    }


    /**
     * Sets the milestoneDate value for this ContestMultiRoundInformationData.
     * 
     * @param milestoneDate
     */
    public void setMilestoneDate(java.lang.String milestoneDate) {
        this.milestoneDate = milestoneDate;
    }


    /**
     * Gets the submittersLockedBetweenRounds value for this ContestMultiRoundInformationData.
     * 
     * @return submittersLockedBetweenRounds
     */
    public java.lang.Boolean getSubmittersLockedBetweenRounds() {
        return submittersLockedBetweenRounds;
    }


    /**
     * Sets the submittersLockedBetweenRounds value for this ContestMultiRoundInformationData.
     * 
     * @param submittersLockedBetweenRounds
     */
    public void setSubmittersLockedBetweenRounds(java.lang.Boolean submittersLockedBetweenRounds) {
        this.submittersLockedBetweenRounds = submittersLockedBetweenRounds;
    }


    /**
     * Gets the roundOneIntroduction value for this ContestMultiRoundInformationData.
     * 
     * @return roundOneIntroduction
     */
    public java.lang.String getRoundOneIntroduction() {
        return roundOneIntroduction;
    }


    /**
     * Sets the roundOneIntroduction value for this ContestMultiRoundInformationData.
     * 
     * @param roundOneIntroduction
     */
    public void setRoundOneIntroduction(java.lang.String roundOneIntroduction) {
        this.roundOneIntroduction = roundOneIntroduction;
    }


    /**
     * Gets the roundTwoIntroduction value for this ContestMultiRoundInformationData.
     * 
     * @return roundTwoIntroduction
     */
    public java.lang.String getRoundTwoIntroduction() {
        return roundTwoIntroduction;
    }


    /**
     * Sets the roundTwoIntroduction value for this ContestMultiRoundInformationData.
     * 
     * @param roundTwoIntroduction
     */
    public void setRoundTwoIntroduction(java.lang.String roundTwoIntroduction) {
        this.roundTwoIntroduction = roundTwoIntroduction;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestMultiRoundInformationData)) return false;
        ContestMultiRoundInformationData other = (ContestMultiRoundInformationData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.milestoneDate==null && other.getMilestoneDate()==null) || 
             (this.milestoneDate!=null &&
              this.milestoneDate.equals(other.getMilestoneDate()))) &&
            ((this.submittersLockedBetweenRounds==null && other.getSubmittersLockedBetweenRounds()==null) || 
             (this.submittersLockedBetweenRounds!=null &&
              this.submittersLockedBetweenRounds.equals(other.getSubmittersLockedBetweenRounds()))) &&
            ((this.roundOneIntroduction==null && other.getRoundOneIntroduction()==null) || 
             (this.roundOneIntroduction!=null &&
              this.roundOneIntroduction.equals(other.getRoundOneIntroduction()))) &&
            ((this.roundTwoIntroduction==null && other.getRoundTwoIntroduction()==null) || 
             (this.roundTwoIntroduction!=null &&
              this.roundTwoIntroduction.equals(other.getRoundTwoIntroduction())));
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
        if (getMilestoneDate() != null) {
            _hashCode += getMilestoneDate().hashCode();
        }
        if (getSubmittersLockedBetweenRounds() != null) {
            _hashCode += getSubmittersLockedBetweenRounds().hashCode();
        }
        if (getRoundOneIntroduction() != null) {
            _hashCode += getRoundOneIntroduction().hashCode();
        }
        if (getRoundTwoIntroduction() != null) {
            _hashCode += getRoundTwoIntroduction().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestMultiRoundInformationData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestMultiRoundInformationData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("milestoneDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "milestoneDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submittersLockedBetweenRounds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submittersLockedBetweenRounds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roundOneIntroduction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roundOneIntroduction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roundTwoIntroduction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roundTwoIntroduction"));
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
