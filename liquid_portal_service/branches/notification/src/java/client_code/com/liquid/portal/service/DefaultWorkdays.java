/**
 * DefaultWorkdays.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class DefaultWorkdays  implements java.io.Serializable {
    private java.lang.String fileFormat;

    private java.lang.String fileName;

    private boolean saturdayWorkday;

    private boolean sundayWorkday;

    private int workdayEndTimeHours;

    private int workdayEndTimeMinutes;

    private int workdayStartTimeHours;

    private int workdayStartTimeMinutes;

    public DefaultWorkdays() {
    }

    public DefaultWorkdays(
           java.lang.String fileFormat,
           java.lang.String fileName,
           boolean saturdayWorkday,
           boolean sundayWorkday,
           int workdayEndTimeHours,
           int workdayEndTimeMinutes,
           int workdayStartTimeHours,
           int workdayStartTimeMinutes) {
           this.fileFormat = fileFormat;
           this.fileName = fileName;
           this.saturdayWorkday = saturdayWorkday;
           this.sundayWorkday = sundayWorkday;
           this.workdayEndTimeHours = workdayEndTimeHours;
           this.workdayEndTimeMinutes = workdayEndTimeMinutes;
           this.workdayStartTimeHours = workdayStartTimeHours;
           this.workdayStartTimeMinutes = workdayStartTimeMinutes;
    }


    /**
     * Gets the fileFormat value for this DefaultWorkdays.
     * 
     * @return fileFormat
     */
    public java.lang.String getFileFormat() {
        return fileFormat;
    }


    /**
     * Sets the fileFormat value for this DefaultWorkdays.
     * 
     * @param fileFormat
     */
    public void setFileFormat(java.lang.String fileFormat) {
        this.fileFormat = fileFormat;
    }


    /**
     * Gets the fileName value for this DefaultWorkdays.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this DefaultWorkdays.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the saturdayWorkday value for this DefaultWorkdays.
     * 
     * @return saturdayWorkday
     */
    public boolean isSaturdayWorkday() {
        return saturdayWorkday;
    }


    /**
     * Sets the saturdayWorkday value for this DefaultWorkdays.
     * 
     * @param saturdayWorkday
     */
    public void setSaturdayWorkday(boolean saturdayWorkday) {
        this.saturdayWorkday = saturdayWorkday;
    }


    /**
     * Gets the sundayWorkday value for this DefaultWorkdays.
     * 
     * @return sundayWorkday
     */
    public boolean isSundayWorkday() {
        return sundayWorkday;
    }


    /**
     * Sets the sundayWorkday value for this DefaultWorkdays.
     * 
     * @param sundayWorkday
     */
    public void setSundayWorkday(boolean sundayWorkday) {
        this.sundayWorkday = sundayWorkday;
    }


    /**
     * Gets the workdayEndTimeHours value for this DefaultWorkdays.
     * 
     * @return workdayEndTimeHours
     */
    public int getWorkdayEndTimeHours() {
        return workdayEndTimeHours;
    }


    /**
     * Sets the workdayEndTimeHours value for this DefaultWorkdays.
     * 
     * @param workdayEndTimeHours
     */
    public void setWorkdayEndTimeHours(int workdayEndTimeHours) {
        this.workdayEndTimeHours = workdayEndTimeHours;
    }


    /**
     * Gets the workdayEndTimeMinutes value for this DefaultWorkdays.
     * 
     * @return workdayEndTimeMinutes
     */
    public int getWorkdayEndTimeMinutes() {
        return workdayEndTimeMinutes;
    }


    /**
     * Sets the workdayEndTimeMinutes value for this DefaultWorkdays.
     * 
     * @param workdayEndTimeMinutes
     */
    public void setWorkdayEndTimeMinutes(int workdayEndTimeMinutes) {
        this.workdayEndTimeMinutes = workdayEndTimeMinutes;
    }


    /**
     * Gets the workdayStartTimeHours value for this DefaultWorkdays.
     * 
     * @return workdayStartTimeHours
     */
    public int getWorkdayStartTimeHours() {
        return workdayStartTimeHours;
    }


    /**
     * Sets the workdayStartTimeHours value for this DefaultWorkdays.
     * 
     * @param workdayStartTimeHours
     */
    public void setWorkdayStartTimeHours(int workdayStartTimeHours) {
        this.workdayStartTimeHours = workdayStartTimeHours;
    }


    /**
     * Gets the workdayStartTimeMinutes value for this DefaultWorkdays.
     * 
     * @return workdayStartTimeMinutes
     */
    public int getWorkdayStartTimeMinutes() {
        return workdayStartTimeMinutes;
    }


    /**
     * Sets the workdayStartTimeMinutes value for this DefaultWorkdays.
     * 
     * @param workdayStartTimeMinutes
     */
    public void setWorkdayStartTimeMinutes(int workdayStartTimeMinutes) {
        this.workdayStartTimeMinutes = workdayStartTimeMinutes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DefaultWorkdays)) return false;
        DefaultWorkdays other = (DefaultWorkdays) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileFormat==null && other.getFileFormat()==null) || 
             (this.fileFormat!=null &&
              this.fileFormat.equals(other.getFileFormat()))) &&
            ((this.fileName==null && other.getFileName()==null) || 
             (this.fileName!=null &&
              this.fileName.equals(other.getFileName()))) &&
            this.saturdayWorkday == other.isSaturdayWorkday() &&
            this.sundayWorkday == other.isSundayWorkday() &&
            this.workdayEndTimeHours == other.getWorkdayEndTimeHours() &&
            this.workdayEndTimeMinutes == other.getWorkdayEndTimeMinutes() &&
            this.workdayStartTimeHours == other.getWorkdayStartTimeHours() &&
            this.workdayStartTimeMinutes == other.getWorkdayStartTimeMinutes();
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
        if (getFileFormat() != null) {
            _hashCode += getFileFormat().hashCode();
        }
        if (getFileName() != null) {
            _hashCode += getFileName().hashCode();
        }
        _hashCode += (isSaturdayWorkday() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSundayWorkday() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getWorkdayEndTimeHours();
        _hashCode += getWorkdayEndTimeMinutes();
        _hashCode += getWorkdayStartTimeHours();
        _hashCode += getWorkdayStartTimeMinutes();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DefaultWorkdays.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "defaultWorkdays"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saturdayWorkday");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saturdayWorkday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sundayWorkday");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sundayWorkday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workdayEndTimeHours");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workdayEndTimeHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workdayEndTimeMinutes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workdayEndTimeMinutes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workdayStartTimeHours");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workdayStartTimeHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workdayStartTimeMinutes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workdayStartTimeMinutes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
