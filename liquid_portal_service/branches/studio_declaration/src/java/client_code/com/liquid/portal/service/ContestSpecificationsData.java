/**
 * ContestSpecificationsData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestSpecificationsData  implements java.io.Serializable {
    private java.lang.String colors;

    private java.lang.String fonts;

    private java.lang.String layoutAndSize;

    private java.lang.String additionalRequirementsAndRestrictions;

    public ContestSpecificationsData() {
    }

    public ContestSpecificationsData(
           java.lang.String colors,
           java.lang.String fonts,
           java.lang.String layoutAndSize,
           java.lang.String additionalRequirementsAndRestrictions) {
           this.colors = colors;
           this.fonts = fonts;
           this.layoutAndSize = layoutAndSize;
           this.additionalRequirementsAndRestrictions = additionalRequirementsAndRestrictions;
    }


    /**
     * Gets the colors value for this ContestSpecificationsData.
     * 
     * @return colors
     */
    public java.lang.String getColors() {
        return colors;
    }


    /**
     * Sets the colors value for this ContestSpecificationsData.
     * 
     * @param colors
     */
    public void setColors(java.lang.String colors) {
        this.colors = colors;
    }


    /**
     * Gets the fonts value for this ContestSpecificationsData.
     * 
     * @return fonts
     */
    public java.lang.String getFonts() {
        return fonts;
    }


    /**
     * Sets the fonts value for this ContestSpecificationsData.
     * 
     * @param fonts
     */
    public void setFonts(java.lang.String fonts) {
        this.fonts = fonts;
    }


    /**
     * Gets the layoutAndSize value for this ContestSpecificationsData.
     * 
     * @return layoutAndSize
     */
    public java.lang.String getLayoutAndSize() {
        return layoutAndSize;
    }


    /**
     * Sets the layoutAndSize value for this ContestSpecificationsData.
     * 
     * @param layoutAndSize
     */
    public void setLayoutAndSize(java.lang.String layoutAndSize) {
        this.layoutAndSize = layoutAndSize;
    }


    /**
     * Gets the additionalRequirementsAndRestrictions value for this ContestSpecificationsData.
     * 
     * @return additionalRequirementsAndRestrictions
     */
    public java.lang.String getAdditionalRequirementsAndRestrictions() {
        return additionalRequirementsAndRestrictions;
    }


    /**
     * Sets the additionalRequirementsAndRestrictions value for this ContestSpecificationsData.
     * 
     * @param additionalRequirementsAndRestrictions
     */
    public void setAdditionalRequirementsAndRestrictions(java.lang.String additionalRequirementsAndRestrictions) {
        this.additionalRequirementsAndRestrictions = additionalRequirementsAndRestrictions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestSpecificationsData)) return false;
        ContestSpecificationsData other = (ContestSpecificationsData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.colors==null && other.getColors()==null) || 
             (this.colors!=null &&
              this.colors.equals(other.getColors()))) &&
            ((this.fonts==null && other.getFonts()==null) || 
             (this.fonts!=null &&
              this.fonts.equals(other.getFonts()))) &&
            ((this.layoutAndSize==null && other.getLayoutAndSize()==null) || 
             (this.layoutAndSize!=null &&
              this.layoutAndSize.equals(other.getLayoutAndSize()))) &&
            ((this.additionalRequirementsAndRestrictions==null && other.getAdditionalRequirementsAndRestrictions()==null) || 
             (this.additionalRequirementsAndRestrictions!=null &&
              this.additionalRequirementsAndRestrictions.equals(other.getAdditionalRequirementsAndRestrictions())));
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
        if (getColors() != null) {
            _hashCode += getColors().hashCode();
        }
        if (getFonts() != null) {
            _hashCode += getFonts().hashCode();
        }
        if (getLayoutAndSize() != null) {
            _hashCode += getLayoutAndSize().hashCode();
        }
        if (getAdditionalRequirementsAndRestrictions() != null) {
            _hashCode += getAdditionalRequirementsAndRestrictions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestSpecificationsData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSpecificationsData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fonts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fonts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layoutAndSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "layoutAndSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalRequirementsAndRestrictions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "additionalRequirementsAndRestrictions"));
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
