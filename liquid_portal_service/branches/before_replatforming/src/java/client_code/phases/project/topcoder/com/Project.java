/**
 * Project.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package phases.project.topcoder.com;

public class Project  extends com.liquid.portal.service.AttributableObject  implements java.io.Serializable {
    private long id;

    private phases.project.topcoder.com.Phase[] phases;

    private java.util.Calendar startDate;

    private com.liquid.portal.service.DefaultWorkdays workdays;

    public Project() {
    }

    public Project(
           com.liquid.portal.service.AttributableObjectAttributesEntry[] attributes,
           long id,
           phases.project.topcoder.com.Phase[] phases,
           java.util.Calendar startDate,
           com.liquid.portal.service.DefaultWorkdays workdays) {
        super(
            attributes);
        this.id = id;
        this.phases = phases;
        this.startDate = startDate;
        this.workdays = workdays;
    }


    /**
     * Gets the id value for this Project.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Project.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the phases value for this Project.
     * 
     * @return phases
     */
    public phases.project.topcoder.com.Phase[] getPhases() {
        return phases;
    }


    /**
     * Sets the phases value for this Project.
     * 
     * @param phases
     */
    public void setPhases(phases.project.topcoder.com.Phase[] phases) {
        this.phases = phases;
    }

    public phases.project.topcoder.com.Phase getPhases(int i) {
        return this.phases[i];
    }

    public void setPhases(int i, phases.project.topcoder.com.Phase _value) {
        this.phases[i] = _value;
    }


    /**
     * Gets the startDate value for this Project.
     * 
     * @return startDate
     */
    public java.util.Calendar getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Project.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the workdays value for this Project.
     * 
     * @return workdays
     */
    public com.liquid.portal.service.DefaultWorkdays getWorkdays() {
        return workdays;
    }


    /**
     * Sets the workdays value for this Project.
     * 
     * @param workdays
     */
    public void setWorkdays(com.liquid.portal.service.DefaultWorkdays workdays) {
        this.workdays = workdays;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Project)) return false;
        Project other = (Project) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.id == other.getId() &&
            ((this.phases==null && other.getPhases()==null) || 
             (this.phases!=null &&
              java.util.Arrays.equals(this.phases, other.getPhases()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.workdays==null && other.getWorkdays()==null) || 
             (this.workdays!=null &&
              this.workdays.equals(other.getWorkdays())));
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
        _hashCode += new Long(getId()).hashCode();
        if (getPhases() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPhases());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPhases(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getWorkdays() != null) {
            _hashCode += getWorkdays().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Project.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "project"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phases");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phases"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "phase"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workdays");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workdays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "defaultWorkdays"));
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
