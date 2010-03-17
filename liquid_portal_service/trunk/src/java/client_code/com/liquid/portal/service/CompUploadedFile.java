/**
 * CompUploadedFile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class CompUploadedFile  implements java.io.Serializable {
    private byte[] fileData;

    private java.lang.String uploadedFileName;

    private java.lang.String uploadedFileDesc;

    private java.lang.Long uploadedFileType;

    public CompUploadedFile() {
    }

    public CompUploadedFile(
           byte[] fileData,
           java.lang.String uploadedFileName,
           java.lang.String uploadedFileDesc,
           java.lang.Long uploadedFileType) {
           this.fileData = fileData;
           this.uploadedFileName = uploadedFileName;
           this.uploadedFileDesc = uploadedFileDesc;
           this.uploadedFileType = uploadedFileType;
    }


    /**
     * Gets the fileData value for this CompUploadedFile.
     * 
     * @return fileData
     */
    public byte[] getFileData() {
        return fileData;
    }


    /**
     * Sets the fileData value for this CompUploadedFile.
     * 
     * @param fileData
     */
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }


    /**
     * Gets the uploadedFileName value for this CompUploadedFile.
     * 
     * @return uploadedFileName
     */
    public java.lang.String getUploadedFileName() {
        return uploadedFileName;
    }


    /**
     * Sets the uploadedFileName value for this CompUploadedFile.
     * 
     * @param uploadedFileName
     */
    public void setUploadedFileName(java.lang.String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }


    /**
     * Gets the uploadedFileDesc value for this CompUploadedFile.
     * 
     * @return uploadedFileDesc
     */
    public java.lang.String getUploadedFileDesc() {
        return uploadedFileDesc;
    }


    /**
     * Sets the uploadedFileDesc value for this CompUploadedFile.
     * 
     * @param uploadedFileDesc
     */
    public void setUploadedFileDesc(java.lang.String uploadedFileDesc) {
        this.uploadedFileDesc = uploadedFileDesc;
    }


    /**
     * Gets the uploadedFileType value for this CompUploadedFile.
     * 
     * @return uploadedFileType
     */
    public java.lang.Long getUploadedFileType() {
        return uploadedFileType;
    }


    /**
     * Sets the uploadedFileType value for this CompUploadedFile.
     * 
     * @param uploadedFileType
     */
    public void setUploadedFileType(java.lang.Long uploadedFileType) {
        this.uploadedFileType = uploadedFileType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompUploadedFile)) return false;
        CompUploadedFile other = (CompUploadedFile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileData==null && other.getFileData()==null) || 
             (this.fileData!=null &&
              java.util.Arrays.equals(this.fileData, other.getFileData()))) &&
            ((this.uploadedFileName==null && other.getUploadedFileName()==null) || 
             (this.uploadedFileName!=null &&
              this.uploadedFileName.equals(other.getUploadedFileName()))) &&
            ((this.uploadedFileDesc==null && other.getUploadedFileDesc()==null) || 
             (this.uploadedFileDesc!=null &&
              this.uploadedFileDesc.equals(other.getUploadedFileDesc()))) &&
            ((this.uploadedFileType==null && other.getUploadedFileType()==null) || 
             (this.uploadedFileType!=null &&
              this.uploadedFileType.equals(other.getUploadedFileType())));
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
        if (getFileData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUploadedFileName() != null) {
            _hashCode += getUploadedFileName().hashCode();
        }
        if (getUploadedFileDesc() != null) {
            _hashCode += getUploadedFileDesc().hashCode();
        }
        if (getUploadedFileType() != null) {
            _hashCode += getUploadedFileType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompUploadedFile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "compUploadedFile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uploadedFileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uploadedFileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uploadedFileDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uploadedFileDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uploadedFileType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uploadedFileType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
