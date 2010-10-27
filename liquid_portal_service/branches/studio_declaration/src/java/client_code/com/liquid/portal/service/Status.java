/**
 * Status.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class Status implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected Status(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DELETED = "DELETED";
    public static final java.lang.String _ACTIVE = "ACTIVE";
    public static final java.lang.String _REQUESTED = "REQUESTED";
    public static final java.lang.String _APPROVED = "APPROVED";
    public static final java.lang.String _DUPLICATE = "DUPLICATE";
    public static final java.lang.String _DECLINED = "DECLINED";
    public static final java.lang.String _PENDING_ACTIVATION = "PENDING_ACTIVATION";
    public static final java.lang.String _NEW_POST = "NEW_POST";
    public static final java.lang.String _REPOST = "REPOST";
    public static final java.lang.String _TOURNAMENT = "TOURNAMENT";
    public static final Status DELETED = new Status(_DELETED);
    public static final Status ACTIVE = new Status(_ACTIVE);
    public static final Status REQUESTED = new Status(_REQUESTED);
    public static final Status APPROVED = new Status(_APPROVED);
    public static final Status DUPLICATE = new Status(_DUPLICATE);
    public static final Status DECLINED = new Status(_DECLINED);
    public static final Status PENDING_ACTIVATION = new Status(_PENDING_ACTIVATION);
    public static final Status NEW_POST = new Status(_NEW_POST);
    public static final Status REPOST = new Status(_REPOST);
    public static final Status TOURNAMENT = new Status(_TOURNAMENT);
    public java.lang.String getValue() { return _value_;}
    public static Status fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Status enumeration = (Status)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Status fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Status.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "status"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
