/**
 * FullProjectData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class FullProjectData  extends phases.project.topcoder.com.Project  implements java.io.Serializable {
    private com.liquid.portal.service.ContestSaleData[] contestSales;

    private project.management.topcoder.com.Project projectHeader;

    private com.liquid.portal.service.Resource[] resources;

    private com.liquid.portal.service.TeamHeader[] teams;

    private java.lang.String[] technologies;

    public FullProjectData() {
    }

    public FullProjectData(
           com.liquid.portal.service.AttributableObjectAttributesEntry[] attributes,
           long id,
           phases.project.topcoder.com.Phase[] phases,
           java.util.Calendar startDate,
           com.liquid.portal.service.DefaultWorkdays workdays,
           com.liquid.portal.service.ContestSaleData[] contestSales,
           project.management.topcoder.com.Project projectHeader,
           com.liquid.portal.service.Resource[] resources,
           com.liquid.portal.service.TeamHeader[] teams,
           java.lang.String[] technologies) {
        super(
            attributes,
            id,
            phases,
            startDate,
            workdays);
        this.contestSales = contestSales;
        this.projectHeader = projectHeader;
        this.resources = resources;
        this.teams = teams;
        this.technologies = technologies;
    }


    /**
     * Gets the contestSales value for this FullProjectData.
     * 
     * @return contestSales
     */
    public com.liquid.portal.service.ContestSaleData[] getContestSales() {
        return contestSales;
    }


    /**
     * Sets the contestSales value for this FullProjectData.
     * 
     * @param contestSales
     */
    public void setContestSales(com.liquid.portal.service.ContestSaleData[] contestSales) {
        this.contestSales = contestSales;
    }

    public com.liquid.portal.service.ContestSaleData getContestSales(int i) {
        return this.contestSales[i];
    }

    public void setContestSales(int i, com.liquid.portal.service.ContestSaleData _value) {
        this.contestSales[i] = _value;
    }


    /**
     * Gets the projectHeader value for this FullProjectData.
     * 
     * @return projectHeader
     */
    public project.management.topcoder.com.Project getProjectHeader() {
        return projectHeader;
    }


    /**
     * Sets the projectHeader value for this FullProjectData.
     * 
     * @param projectHeader
     */
    public void setProjectHeader(project.management.topcoder.com.Project projectHeader) {
        this.projectHeader = projectHeader;
    }


    /**
     * Gets the resources value for this FullProjectData.
     * 
     * @return resources
     */
    public com.liquid.portal.service.Resource[] getResources() {
        return resources;
    }


    /**
     * Sets the resources value for this FullProjectData.
     * 
     * @param resources
     */
    public void setResources(com.liquid.portal.service.Resource[] resources) {
        this.resources = resources;
    }

    public com.liquid.portal.service.Resource getResources(int i) {
        return this.resources[i];
    }

    public void setResources(int i, com.liquid.portal.service.Resource _value) {
        this.resources[i] = _value;
    }


    /**
     * Gets the teams value for this FullProjectData.
     * 
     * @return teams
     */
    public com.liquid.portal.service.TeamHeader[] getTeams() {
        return teams;
    }


    /**
     * Sets the teams value for this FullProjectData.
     * 
     * @param teams
     */
    public void setTeams(com.liquid.portal.service.TeamHeader[] teams) {
        this.teams = teams;
    }

    public com.liquid.portal.service.TeamHeader getTeams(int i) {
        return this.teams[i];
    }

    public void setTeams(int i, com.liquid.portal.service.TeamHeader _value) {
        this.teams[i] = _value;
    }


    /**
     * Gets the technologies value for this FullProjectData.
     * 
     * @return technologies
     */
    public java.lang.String[] getTechnologies() {
        return technologies;
    }


    /**
     * Sets the technologies value for this FullProjectData.
     * 
     * @param technologies
     */
    public void setTechnologies(java.lang.String[] technologies) {
        this.technologies = technologies;
    }

    public java.lang.String getTechnologies(int i) {
        return this.technologies[i];
    }

    public void setTechnologies(int i, java.lang.String _value) {
        this.technologies[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FullProjectData)) return false;
        FullProjectData other = (FullProjectData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contestSales==null && other.getContestSales()==null) || 
             (this.contestSales!=null &&
              java.util.Arrays.equals(this.contestSales, other.getContestSales()))) &&
            ((this.projectHeader==null && other.getProjectHeader()==null) || 
             (this.projectHeader!=null &&
              this.projectHeader.equals(other.getProjectHeader()))) &&
            ((this.resources==null && other.getResources()==null) || 
             (this.resources!=null &&
              java.util.Arrays.equals(this.resources, other.getResources()))) &&
            ((this.teams==null && other.getTeams()==null) || 
             (this.teams!=null &&
              java.util.Arrays.equals(this.teams, other.getTeams()))) &&
            ((this.technologies==null && other.getTechnologies()==null) || 
             (this.technologies!=null &&
              java.util.Arrays.equals(this.technologies, other.getTechnologies())));
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
        if (getContestSales() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContestSales());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContestSales(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProjectHeader() != null) {
            _hashCode += getProjectHeader().hashCode();
        }
        if (getResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTeams() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTeams());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTeams(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTechnologies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTechnologies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTechnologies(), i);
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
        new org.apache.axis.description.TypeDesc(FullProjectData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "fullProjectData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestSales");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestSales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSaleData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.management.project", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resources");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resource"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("teams");
        elemField.setXmlName(new javax.xml.namespace.QName("", "teams"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "teamHeader"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("technologies");
        elemField.setXmlName(new javax.xml.namespace.QName("", "technologies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
