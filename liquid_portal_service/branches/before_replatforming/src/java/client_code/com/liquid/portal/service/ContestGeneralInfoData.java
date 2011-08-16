/**
 * ContestGeneralInfoData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestGeneralInfoData  implements java.io.Serializable {
    private java.lang.String goals;

    private java.lang.String targetAudience;

    private java.lang.String brandingGuidelines;

    private java.lang.String dislikedDesignsWebsites;

    private java.lang.String otherInstructions;

    private java.lang.String winningCriteria;

    public ContestGeneralInfoData() {
    }

    public ContestGeneralInfoData(
           java.lang.String goals,
           java.lang.String targetAudience,
           java.lang.String brandingGuidelines,
           java.lang.String dislikedDesignsWebsites,
           java.lang.String otherInstructions,
           java.lang.String winningCriteria) {
           this.goals = goals;
           this.targetAudience = targetAudience;
           this.brandingGuidelines = brandingGuidelines;
           this.dislikedDesignsWebsites = dislikedDesignsWebsites;
           this.otherInstructions = otherInstructions;
           this.winningCriteria = winningCriteria;
    }


    /**
     * Gets the goals value for this ContestGeneralInfoData.
     * 
     * @return goals
     */
    public java.lang.String getGoals() {
        return goals;
    }


    /**
     * Sets the goals value for this ContestGeneralInfoData.
     * 
     * @param goals
     */
    public void setGoals(java.lang.String goals) {
        this.goals = goals;
    }


    /**
     * Gets the targetAudience value for this ContestGeneralInfoData.
     * 
     * @return targetAudience
     */
    public java.lang.String getTargetAudience() {
        return targetAudience;
    }


    /**
     * Sets the targetAudience value for this ContestGeneralInfoData.
     * 
     * @param targetAudience
     */
    public void setTargetAudience(java.lang.String targetAudience) {
        this.targetAudience = targetAudience;
    }


    /**
     * Gets the brandingGuidelines value for this ContestGeneralInfoData.
     * 
     * @return brandingGuidelines
     */
    public java.lang.String getBrandingGuidelines() {
        return brandingGuidelines;
    }


    /**
     * Sets the brandingGuidelines value for this ContestGeneralInfoData.
     * 
     * @param brandingGuidelines
     */
    public void setBrandingGuidelines(java.lang.String brandingGuidelines) {
        this.brandingGuidelines = brandingGuidelines;
    }


    /**
     * Gets the dislikedDesignsWebsites value for this ContestGeneralInfoData.
     * 
     * @return dislikedDesignsWebsites
     */
    public java.lang.String getDislikedDesignsWebsites() {
        return dislikedDesignsWebsites;
    }


    /**
     * Sets the dislikedDesignsWebsites value for this ContestGeneralInfoData.
     * 
     * @param dislikedDesignsWebsites
     */
    public void setDislikedDesignsWebsites(java.lang.String dislikedDesignsWebsites) {
        this.dislikedDesignsWebsites = dislikedDesignsWebsites;
    }


    /**
     * Gets the otherInstructions value for this ContestGeneralInfoData.
     * 
     * @return otherInstructions
     */
    public java.lang.String getOtherInstructions() {
        return otherInstructions;
    }


    /**
     * Sets the otherInstructions value for this ContestGeneralInfoData.
     * 
     * @param otherInstructions
     */
    public void setOtherInstructions(java.lang.String otherInstructions) {
        this.otherInstructions = otherInstructions;
    }


    /**
     * Gets the winningCriteria value for this ContestGeneralInfoData.
     * 
     * @return winningCriteria
     */
    public java.lang.String getWinningCriteria() {
        return winningCriteria;
    }


    /**
     * Sets the winningCriteria value for this ContestGeneralInfoData.
     * 
     * @param winningCriteria
     */
    public void setWinningCriteria(java.lang.String winningCriteria) {
        this.winningCriteria = winningCriteria;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestGeneralInfoData)) return false;
        ContestGeneralInfoData other = (ContestGeneralInfoData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.goals==null && other.getGoals()==null) || 
             (this.goals!=null &&
              this.goals.equals(other.getGoals()))) &&
            ((this.targetAudience==null && other.getTargetAudience()==null) || 
             (this.targetAudience!=null &&
              this.targetAudience.equals(other.getTargetAudience()))) &&
            ((this.brandingGuidelines==null && other.getBrandingGuidelines()==null) || 
             (this.brandingGuidelines!=null &&
              this.brandingGuidelines.equals(other.getBrandingGuidelines()))) &&
            ((this.dislikedDesignsWebsites==null && other.getDislikedDesignsWebsites()==null) || 
             (this.dislikedDesignsWebsites!=null &&
              this.dislikedDesignsWebsites.equals(other.getDislikedDesignsWebsites()))) &&
            ((this.otherInstructions==null && other.getOtherInstructions()==null) || 
             (this.otherInstructions!=null &&
              this.otherInstructions.equals(other.getOtherInstructions()))) &&
            ((this.winningCriteria==null && other.getWinningCriteria()==null) || 
             (this.winningCriteria!=null &&
              this.winningCriteria.equals(other.getWinningCriteria())));
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
        if (getGoals() != null) {
            _hashCode += getGoals().hashCode();
        }
        if (getTargetAudience() != null) {
            _hashCode += getTargetAudience().hashCode();
        }
        if (getBrandingGuidelines() != null) {
            _hashCode += getBrandingGuidelines().hashCode();
        }
        if (getDislikedDesignsWebsites() != null) {
            _hashCode += getDislikedDesignsWebsites().hashCode();
        }
        if (getOtherInstructions() != null) {
            _hashCode += getOtherInstructions().hashCode();
        }
        if (getWinningCriteria() != null) {
            _hashCode += getWinningCriteria().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestGeneralInfoData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestGeneralInfoData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goals");
        elemField.setXmlName(new javax.xml.namespace.QName("", "goals"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetAudience");
        elemField.setXmlName(new javax.xml.namespace.QName("", "targetAudience"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandingGuidelines");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brandingGuidelines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dislikedDesignsWebsites");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dislikedDesignsWebsites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "otherInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("winningCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "winningCriteria"));
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
