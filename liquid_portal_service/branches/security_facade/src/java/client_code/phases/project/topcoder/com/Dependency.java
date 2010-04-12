/**
 * Dependency.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package phases.project.topcoder.com;

public class Dependency  implements java.io.Serializable {
    private phases.project.topcoder.com.Phase dependency;

    private boolean dependencyStart;

    private phases.project.topcoder.com.Phase dependent;

    private boolean dependentStart;

    private long lagTime;

    public Dependency() {
    }

    public Dependency(
           phases.project.topcoder.com.Phase dependency,
           boolean dependencyStart,
           phases.project.topcoder.com.Phase dependent,
           boolean dependentStart,
           long lagTime) {
           this.dependency = dependency;
           this.dependencyStart = dependencyStart;
           this.dependent = dependent;
           this.dependentStart = dependentStart;
           this.lagTime = lagTime;
    }


    /**
     * Gets the dependency value for this Dependency.
     * 
     * @return dependency
     */
    public phases.project.topcoder.com.Phase getDependency() {
        return dependency;
    }


    /**
     * Sets the dependency value for this Dependency.
     * 
     * @param dependency
     */
    public void setDependency(phases.project.topcoder.com.Phase dependency) {
        this.dependency = dependency;
    }


    /**
     * Gets the dependencyStart value for this Dependency.
     * 
     * @return dependencyStart
     */
    public boolean isDependencyStart() {
        return dependencyStart;
    }


    /**
     * Sets the dependencyStart value for this Dependency.
     * 
     * @param dependencyStart
     */
    public void setDependencyStart(boolean dependencyStart) {
        this.dependencyStart = dependencyStart;
    }


    /**
     * Gets the dependent value for this Dependency.
     * 
     * @return dependent
     */
    public phases.project.topcoder.com.Phase getDependent() {
        return dependent;
    }


    /**
     * Sets the dependent value for this Dependency.
     * 
     * @param dependent
     */
    public void setDependent(phases.project.topcoder.com.Phase dependent) {
        this.dependent = dependent;
    }


    /**
     * Gets the dependentStart value for this Dependency.
     * 
     * @return dependentStart
     */
    public boolean isDependentStart() {
        return dependentStart;
    }


    /**
     * Sets the dependentStart value for this Dependency.
     * 
     * @param dependentStart
     */
    public void setDependentStart(boolean dependentStart) {
        this.dependentStart = dependentStart;
    }


    /**
     * Gets the lagTime value for this Dependency.
     * 
     * @return lagTime
     */
    public long getLagTime() {
        return lagTime;
    }


    /**
     * Sets the lagTime value for this Dependency.
     * 
     * @param lagTime
     */
    public void setLagTime(long lagTime) {
        this.lagTime = lagTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Dependency)) return false;
        Dependency other = (Dependency) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dependency==null && other.getDependency()==null) || 
             (this.dependency!=null &&
              this.dependency.equals(other.getDependency()))) &&
            this.dependencyStart == other.isDependencyStart() &&
            ((this.dependent==null && other.getDependent()==null) || 
             (this.dependent!=null &&
              this.dependent.equals(other.getDependent()))) &&
            this.dependentStart == other.isDependentStart() &&
            this.lagTime == other.getLagTime();
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
        if (getDependency() != null) {
            _hashCode += getDependency().hashCode();
        }
        _hashCode += (isDependencyStart() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDependent() != null) {
            _hashCode += getDependent().hashCode();
        }
        _hashCode += (isDependentStart() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getLagTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Dependency.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "dependency"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependency"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "phase"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependencyStart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependencyStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependent"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "phase"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependentStart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependentStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lagTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lagTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
