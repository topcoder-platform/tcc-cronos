/**
 * LiquidPortalServiceBeanServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service.bean;

public class LiquidPortalServiceBeanServiceLocator extends org.apache.axis.client.Service implements com.liquid.portal.service.bean.LiquidPortalServiceBeanService {

    public LiquidPortalServiceBeanServiceLocator() {
    }


    public LiquidPortalServiceBeanServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LiquidPortalServiceBeanServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LiquidPortalServiceBeanPort
    private java.lang.String LiquidPortalServiceBeanPort_address = "http://cockpit.cloud.topcoder.com:80/cockpit2.1-liquid_portal_service/LiquidPortalServiceBean";

    public java.lang.String getLiquidPortalServiceBeanPortAddress() {
        return LiquidPortalServiceBeanPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LiquidPortalServiceBeanPortWSDDServiceName = "LiquidPortalServiceBeanPort";

    public java.lang.String getLiquidPortalServiceBeanPortWSDDServiceName() {
        return LiquidPortalServiceBeanPortWSDDServiceName;
    }

    public void setLiquidPortalServiceBeanPortWSDDServiceName(java.lang.String name) {
        LiquidPortalServiceBeanPortWSDDServiceName = name;
    }

    public com.liquid.portal.service.LiquidPortalService getLiquidPortalServiceBeanPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LiquidPortalServiceBeanPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLiquidPortalServiceBeanPort(endpoint);
    }

    public com.liquid.portal.service.LiquidPortalService getLiquidPortalServiceBeanPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.liquid.portal.service.LiquidPortalServiceBindingStub _stub = new com.liquid.portal.service.LiquidPortalServiceBindingStub(portAddress, this);
            _stub.setPortName(getLiquidPortalServiceBeanPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLiquidPortalServiceBeanPortEndpointAddress(java.lang.String address) {
        LiquidPortalServiceBeanPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.liquid.portal.service.LiquidPortalService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.liquid.portal.service.LiquidPortalServiceBindingStub _stub = new com.liquid.portal.service.LiquidPortalServiceBindingStub(new java.net.URL(LiquidPortalServiceBeanPort_address), this);
                _stub.setPortName(getLiquidPortalServiceBeanPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LiquidPortalServiceBeanPort".equals(inputPortName)) {
            return getLiquidPortalServiceBeanPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bean.service.portal.liquid.com/", "LiquidPortalServiceBeanService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bean.service.portal.liquid.com/", "LiquidPortalServiceBeanPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LiquidPortalServiceBeanPort".equals(portName)) {
            setLiquidPortalServiceBeanPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
