/**
 * CompetionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class CompetionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CompetionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _STUDIO = "STUDIO";
    public static final java.lang.String _CONCEPTUALIZATION = "CONCEPTUALIZATION";
    public static final java.lang.String _SPECIFICATION = "SPECIFICATION";
    public static final java.lang.String _ARCHITECTURE = "ARCHITECTURE";
    public static final java.lang.String _COMPONENT_DESIGN = "COMPONENT_DESIGN";
    public static final java.lang.String _COMPONENT_DEVELOPMENT = "COMPONENT_DEVELOPMENT";
    public static final java.lang.String _ASSEMBLY = "ASSEMBLY";
    public static final java.lang.String _TESTING = "TESTING";
    public static final java.lang.String _SOFTWARE = "SOFTWARE";
    public static final CompetionType STUDIO = new CompetionType(_STUDIO);
    public static final CompetionType CONCEPTUALIZATION = new CompetionType(_CONCEPTUALIZATION);
    public static final CompetionType SPECIFICATION = new CompetionType(_SPECIFICATION);
    public static final CompetionType ARCHITECTURE = new CompetionType(_ARCHITECTURE);
    public static final CompetionType COMPONENT_DESIGN = new CompetionType(_COMPONENT_DESIGN);
    public static final CompetionType COMPONENT_DEVELOPMENT = new CompetionType(_COMPONENT_DEVELOPMENT);
    public static final CompetionType ASSEMBLY = new CompetionType(_ASSEMBLY);
    public static final CompetionType TESTING = new CompetionType(_TESTING);
    public static final CompetionType SOFTWARE = new CompetionType(_SOFTWARE);
    public java.lang.String getValue() { return _value_;}
    public static CompetionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CompetionType enumeration = (CompetionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CompetionType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompetionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
