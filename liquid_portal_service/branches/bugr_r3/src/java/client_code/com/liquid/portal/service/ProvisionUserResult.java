/**
 * ProvisionUserResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ProvisionUserResult  implements java.io.Serializable {
    private com.liquid.portal.service.Project[] billingProjects;

    private com.liquid.portal.service.ProjectData[] cockpitProjects;

    private com.liquid.portal.service.Warning[] result;  // attribute

    public ProvisionUserResult() {
    }

    public ProvisionUserResult(
           com.liquid.portal.service.Warning[] param1,
           com.liquid.portal.service.Project[] billingProjects,
           com.liquid.portal.service.ProjectData[] cockpitProjects) {
        this.param1 = param1;
        this.billingProjects = billingProjects;
        this.cockpitProjects = cockpitProjects;
    }


    /**
     * Gets the billingProjects value for this ProvisionUserResult.
     * 
     * @return billingProjects
     */
    public com.liquid.portal.service.Project[] getBillingProjects() {
        return billingProjects;
    }


    /**
     * Sets the billingProjects value for this ProvisionUserResult.
     * 
     * @param billingProjects
     */
    public void setBillingProjects(com.liquid.portal.service.Project[] billingProjects) {
        this.billingProjects = billingProjects;
    }

    public com.liquid.portal.service.Project getBillingProjects(int i) {
        return this.billingProjects[i];
    }

    public void setBillingProjects(int i, com.liquid.portal.service.Project _value) {
        this.billingProjects[i] = _value;
    }


    /**
     * Gets the cockpitProjects value for this ProvisionUserResult.
     * 
     * @return cockpitProjects
     */
    public com.liquid.portal.service.ProjectData[] getCockpitProjects() {
        return cockpitProjects;
    }


    /**
     * Sets the cockpitProjects value for this ProvisionUserResult.
     * 
     * @param cockpitProjects
     */
    public void setCockpitProjects(com.liquid.portal.service.ProjectData[] cockpitProjects) {
        this.cockpitProjects = cockpitProjects;
    }

    public com.liquid.portal.service.ProjectData getCockpitProjects(int i) {
        return this.cockpitProjects[i];
    }

    public void setCockpitProjects(int i, com.liquid.portal.service.ProjectData _value) {
        this.cockpitProjects[i] = _value;
    }


    /**
     * Gets the result value for this ProvisionUserResult.
     * 
     * @return result
     */
    public com.liquid.portal.service.Warning[] getResult() {
        return result;
    }


    /**
     * Sets the result value for this ProvisionUserResult.
     * 
     * @param result
     */
    public void setResult(com.liquid.portal.service.Warning[] result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProvisionUserResult)) return false;
        ProvisionUserResult other = (ProvisionUserResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.billingProjects==null && other.getBillingProjects()==null) || 
             (this.billingProjects!=null &&
              java.util.Arrays.equals(this.billingProjects, other.getBillingProjects()))) &&
            ((this.cockpitProjects==null && other.getCockpitProjects()==null) || 
             (this.cockpitProjects!=null &&
              java.util.Arrays.equals(this.cockpitProjects, other.getCockpitProjects()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              java.util.Arrays.equals(this.result, other.getResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getBillingProjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBillingProjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBillingProjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCockpitProjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCockpitProjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCockpitProjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResult(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProvisionUserResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUserResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingProjects");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingProjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cockpitProjects");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cockpitProjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
