/**
 * Phase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package phases.project.topcoder.com;

public class Phase  extends com.liquid.portal.service.AttributableObject  implements java.io.Serializable {
    private java.util.Calendar actualEndDate;

    private java.util.Calendar actualStartDate;

    private phases.project.topcoder.com.Dependency[] dependencies;

    private java.util.Calendar fixedStartDate;

    private long id;

    private long length;

    private com.liquid.portal.service.PhaseStatus phaseStatus;

    private com.liquid.portal.service.PhaseType phaseType;

    private phases.project.topcoder.com.Project project;

    private java.util.Calendar scheduledEndDate;

    private java.util.Calendar scheduledStartDate;

    public Phase() {
    }

    public Phase(
           com.liquid.portal.service.AttributableObjectAttributesEntry[] attributes,
           java.util.Calendar actualEndDate,
           java.util.Calendar actualStartDate,
           phases.project.topcoder.com.Dependency[] dependencies,
           java.util.Calendar fixedStartDate,
           long id,
           long length,
           com.liquid.portal.service.PhaseStatus phaseStatus,
           com.liquid.portal.service.PhaseType phaseType,
           phases.project.topcoder.com.Project project,
           java.util.Calendar scheduledEndDate,
           java.util.Calendar scheduledStartDate) {
        super(
            attributes);
        this.actualEndDate = actualEndDate;
        this.actualStartDate = actualStartDate;
        this.dependencies = dependencies;
        this.fixedStartDate = fixedStartDate;
        this.id = id;
        this.length = length;
        this.phaseStatus = phaseStatus;
        this.phaseType = phaseType;
        this.project = project;
        this.scheduledEndDate = scheduledEndDate;
        this.scheduledStartDate = scheduledStartDate;
    }


    /**
     * Gets the actualEndDate value for this Phase.
     * 
     * @return actualEndDate
     */
    public java.util.Calendar getActualEndDate() {
        return actualEndDate;
    }


    /**
     * Sets the actualEndDate value for this Phase.
     * 
     * @param actualEndDate
     */
    public void setActualEndDate(java.util.Calendar actualEndDate) {
        this.actualEndDate = actualEndDate;
    }


    /**
     * Gets the actualStartDate value for this Phase.
     * 
     * @return actualStartDate
     */
    public java.util.Calendar getActualStartDate() {
        return actualStartDate;
    }


    /**
     * Sets the actualStartDate value for this Phase.
     * 
     * @param actualStartDate
     */
    public void setActualStartDate(java.util.Calendar actualStartDate) {
        this.actualStartDate = actualStartDate;
    }


    /**
     * Gets the dependencies value for this Phase.
     * 
     * @return dependencies
     */
    public phases.project.topcoder.com.Dependency[] getDependencies() {
        return dependencies;
    }


    /**
     * Sets the dependencies value for this Phase.
     * 
     * @param dependencies
     */
    public void setDependencies(phases.project.topcoder.com.Dependency[] dependencies) {
        this.dependencies = dependencies;
    }

    public phases.project.topcoder.com.Dependency getDependencies(int i) {
        return this.dependencies[i];
    }

    public void setDependencies(int i, phases.project.topcoder.com.Dependency _value) {
        this.dependencies[i] = _value;
    }


    /**
     * Gets the fixedStartDate value for this Phase.
     * 
     * @return fixedStartDate
     */
    public java.util.Calendar getFixedStartDate() {
        return fixedStartDate;
    }


    /**
     * Sets the fixedStartDate value for this Phase.
     * 
     * @param fixedStartDate
     */
    public void setFixedStartDate(java.util.Calendar fixedStartDate) {
        this.fixedStartDate = fixedStartDate;
    }


    /**
     * Gets the id value for this Phase.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Phase.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the length value for this Phase.
     * 
     * @return length
     */
    public long getLength() {
        return length;
    }


    /**
     * Sets the length value for this Phase.
     * 
     * @param length
     */
    public void setLength(long length) {
        this.length = length;
    }


    /**
     * Gets the phaseStatus value for this Phase.
     * 
     * @return phaseStatus
     */
    public com.liquid.portal.service.PhaseStatus getPhaseStatus() {
        return phaseStatus;
    }


    /**
     * Sets the phaseStatus value for this Phase.
     * 
     * @param phaseStatus
     */
    public void setPhaseStatus(com.liquid.portal.service.PhaseStatus phaseStatus) {
        this.phaseStatus = phaseStatus;
    }


    /**
     * Gets the phaseType value for this Phase.
     * 
     * @return phaseType
     */
    public com.liquid.portal.service.PhaseType getPhaseType() {
        return phaseType;
    }


    /**
     * Sets the phaseType value for this Phase.
     * 
     * @param phaseType
     */
    public void setPhaseType(com.liquid.portal.service.PhaseType phaseType) {
        this.phaseType = phaseType;
    }


    /**
     * Gets the project value for this Phase.
     * 
     * @return project
     */
    public phases.project.topcoder.com.Project getProject() {
        return project;
    }


    /**
     * Sets the project value for this Phase.
     * 
     * @param project
     */
    public void setProject(phases.project.topcoder.com.Project project) {
        this.project = project;
    }


    /**
     * Gets the scheduledEndDate value for this Phase.
     * 
     * @return scheduledEndDate
     */
    public java.util.Calendar getScheduledEndDate() {
        return scheduledEndDate;
    }


    /**
     * Sets the scheduledEndDate value for this Phase.
     * 
     * @param scheduledEndDate
     */
    public void setScheduledEndDate(java.util.Calendar scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }


    /**
     * Gets the scheduledStartDate value for this Phase.
     * 
     * @return scheduledStartDate
     */
    public java.util.Calendar getScheduledStartDate() {
        return scheduledStartDate;
    }


    /**
     * Sets the scheduledStartDate value for this Phase.
     * 
     * @param scheduledStartDate
     */
    public void setScheduledStartDate(java.util.Calendar scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Phase)) return false;
        Phase other = (Phase) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.actualEndDate==null && other.getActualEndDate()==null) || 
             (this.actualEndDate!=null &&
              this.actualEndDate.equals(other.getActualEndDate()))) &&
            ((this.actualStartDate==null && other.getActualStartDate()==null) || 
             (this.actualStartDate!=null &&
              this.actualStartDate.equals(other.getActualStartDate()))) &&
            ((this.dependencies==null && other.getDependencies()==null) || 
             (this.dependencies!=null &&
              java.util.Arrays.equals(this.dependencies, other.getDependencies()))) &&
            ((this.fixedStartDate==null && other.getFixedStartDate()==null) || 
             (this.fixedStartDate!=null &&
              this.fixedStartDate.equals(other.getFixedStartDate()))) &&
            this.id == other.getId() &&
            this.length == other.getLength() &&
            ((this.phaseStatus==null && other.getPhaseStatus()==null) || 
             (this.phaseStatus!=null &&
              this.phaseStatus.equals(other.getPhaseStatus()))) &&
            ((this.phaseType==null && other.getPhaseType()==null) || 
             (this.phaseType!=null &&
              this.phaseType.equals(other.getPhaseType()))) &&
            ((this.project==null && other.getProject()==null) || 
             (this.project!=null &&
              this.project.equals(other.getProject()))) &&
            ((this.scheduledEndDate==null && other.getScheduledEndDate()==null) || 
             (this.scheduledEndDate!=null &&
              this.scheduledEndDate.equals(other.getScheduledEndDate()))) &&
            ((this.scheduledStartDate==null && other.getScheduledStartDate()==null) || 
             (this.scheduledStartDate!=null &&
              this.scheduledStartDate.equals(other.getScheduledStartDate())));
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
        if (getActualEndDate() != null) {
            _hashCode += getActualEndDate().hashCode();
        }
        if (getActualStartDate() != null) {
            _hashCode += getActualStartDate().hashCode();
        }
        if (getDependencies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDependencies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDependencies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFixedStartDate() != null) {
            _hashCode += getFixedStartDate().hashCode();
        }
        _hashCode += new Long(getId()).hashCode();
        _hashCode += new Long(getLength()).hashCode();
        if (getPhaseStatus() != null) {
            _hashCode += getPhaseStatus().hashCode();
        }
        if (getPhaseType() != null) {
            _hashCode += getPhaseType().hashCode();
        }
        if (getProject() != null) {
            _hashCode += getProject().hashCode();
        }
        if (getScheduledEndDate() != null) {
            _hashCode += getScheduledEndDate().hashCode();
        }
        if (getScheduledStartDate() != null) {
            _hashCode += getScheduledStartDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Phase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "phase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actualEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actualStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependencies");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependencies"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "dependency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixedStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fixedStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("", "length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phaseStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phaseStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "phaseStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phaseType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phaseType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "phaseType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("project");
        elemField.setXmlName(new javax.xml.namespace.QName("", "project"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduledEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scheduledEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduledStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scheduledStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
