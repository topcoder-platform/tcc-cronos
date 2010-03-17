/**
 * LiquidPortalServiceBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class LiquidPortalServiceBindingStub extends org.apache.axis.client.Stub implements com.liquid.portal.service.LiquidPortalService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[7];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createCompetition");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competitionData"), com.liquid.portal.service.CompetitionData.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetitonResult"));
        oper.setReturnClass(com.liquid.portal.service.CreateCompetitonResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"),
                      "com.liquid.portal.service.ActionNotPermittedException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "InvalidHandleException"),
                      "com.liquid.portal.service.InvalidHandleException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "InvalidHandleException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("decommissionUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"),
                      "com.liquid.portal.service.ActionNotPermittedException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteCompetition");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"),
                      "com.liquid.portal.service.ActionNotPermittedException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ContestNotFoundException"),
                      "com.liquid.portal.service.ContestNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ContestNotFoundException"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("provisionProject");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "result"));
        oper.setReturnClass(com.liquid.portal.service.Warning[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "warnings"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"),
                      "com.liquid.portal.service.ActionNotPermittedException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("provisionUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg3"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg4"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUserResult"));
        oper.setReturnClass(com.liquid.portal.service.ProvisionUserResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "InvalidHandleException"),
                      "com.liquid.portal.service.InvalidHandleException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "InvalidHandleException"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.portal.liquid.com/", "user"), com.liquid.portal.service.User.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "registerUserResult"));
        oper.setReturnClass(com.liquid.portal.service.RegisterUserResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleCreationException"),
                      "com.liquid.portal.service.HandleCreationException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleCreationException"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validateUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.portal.liquid.com/", "userInfo"), com.liquid.portal.service.UserInfo.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "result"));
        oper.setReturnClass(com.liquid.portal.service.Warning[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "warnings"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"),
                      "com.liquid.portal.service.HandleNotFoundException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"),
                      "com.liquid.portal.service.LiquidPortalServicingException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"),
                      "com.liquid.portal.service.LiquidPortalIllegalArgumentException",
                      new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException"), 
                      true
                     ));
        _operations[6] = oper;

    }

    public LiquidPortalServiceBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public LiquidPortalServiceBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public LiquidPortalServiceBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("clientsmodel.clients.topcoder.com", "projectStatus");
            cachedSerQNames.add(qName);
            cls = com.topcoder.clients.clientsmodel.ProjectStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.management.project", ">>project>properties>entry");
            cachedSerQNames.add(qName);
            cls = project.management.topcoder.com.ProjectPropertiesEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.management.project", ">project>properties");
            cachedSerQNames.add(qName);
            cls = project.management.topcoder.com.ProjectPropertiesEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("com.topcoder.management.project", ">>project>properties>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("com.topcoder.management.project", "project");
            cachedSerQNames.add(qName);
            cls = project.management.topcoder.com.Project.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.project.phases", "dependency");
            cachedSerQNames.add(qName);
            cls = phases.project.topcoder.com.Dependency.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.project.phases", "phase");
            cachedSerQNames.add(qName);
            cls = phases.project.topcoder.com.Phase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.project.phases", "project");
            cachedSerQNames.add(qName);
            cls = phases.project.topcoder.com.Project.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("com.topcoder.service.project", "project");
            cachedSerQNames.add(qName);
            cls = project.service.topcoder.com.Project.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">>attributableObject>attributes>entry");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AttributableObjectAttributesEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">>resource>properties>entry");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ResourcePropertiesEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">attributableObject>attributes");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AttributableObjectAttributesEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">>attributableObject>attributes>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">resource>properties");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ResourcePropertiesEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", ">>resource>properties>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ActionNotPermittedException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ActionNotPermittedException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "address");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "assetDTO");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AssetDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "attributableObject");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AttributableObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "auditableEntity");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AuditableEntity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "auditableObject");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AuditableObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "auditableResourceStructure");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.AuditableResourceStructure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Category.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "client");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Client.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "clientStatus");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ClientStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "company");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Company.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "compDocumentation");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CompDocumentation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competionType");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CompetionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competition");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Competition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competitionData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CompetitionData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competitionPrize");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CompetitionPrize.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "compUploadedFile");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CompUploadedFile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestGeneralInfoData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestGeneralInfoData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestMultiRoundInformationData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestMultiRoundInformationData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "ContestNotFoundException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestNotFoundException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestPayload");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestPaymentData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestPaymentData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSaleData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestSaleData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSpecificationsData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestSpecificationsData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestStatusData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ContestStatusData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetition");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CreateCompetition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetitionResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CreateCompetitionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetitonResult");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.CreateCompetitonResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "decommissionUser");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.DecommissionUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "decommissionUserResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.DecommissionUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "defaultWorkdays");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.DefaultWorkdays.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "deleteCompetition");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.DeleteCompetition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "deleteCompetitionResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.DeleteCompetitionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "fullProjectData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.FullProjectData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleCreationException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.HandleCreationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "HandleNotFoundException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.HandleNotFoundException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "InvalidHandleException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.InvalidHandleException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalIllegalArgumentException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.LiquidPortalIllegalArgumentException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "LiquidPortalServicingException");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.LiquidPortalServicingException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "mediumData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.MediumData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "milestonePrizeData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.MilestonePrizeData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "phaseStatus");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.PhaseStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "phaseType");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.PhaseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "prizeData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.PrizeData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "project");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Project.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectCategory");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProjectCategory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectData");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProjectData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectSpec");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProjectSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectStatus");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProjectStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectType");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProjectType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionProject");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProvisionProject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionProjectResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProvisionProjectResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUser");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProvisionUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUserResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProvisionUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUserResult");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ProvisionUserResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "registerUser");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.RegisterUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "registerUserResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.RegisterUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "registerUserResult");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.RegisterUserResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resource");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Resource.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resourceRole");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ResourceRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "result");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Warning[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "warning");
            qName2 = new javax.xml.namespace.QName("", "warnings");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "softwareCompetition");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.SoftwareCompetition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "status");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Status.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "studioCompetition");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.StudioCompetition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "teamHeader");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.TeamHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "technology");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Technology.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "uploadedDocument");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.UploadedDocument.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "user");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.User.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "userInfo");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.UserInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "validateUser");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ValidateUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "validateUserResponse");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.ValidateUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.portal.liquid.com/", "warning");
            cachedSerQNames.add(qName);
            cls = com.liquid.portal.service.Warning.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.liquid.portal.service.CreateCompetitonResult createCompetition(java.lang.String arg0, com.liquid.portal.service.CompetitionData arg1, java.lang.String[] arg2) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.InvalidHandleException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetition"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1, arg2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.liquid.portal.service.CreateCompetitonResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.liquid.portal.service.CreateCompetitonResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.liquid.portal.service.CreateCompetitonResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.ActionNotPermittedException) {
              throw (com.liquid.portal.service.ActionNotPermittedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.InvalidHandleException) {
              throw (com.liquid.portal.service.InvalidHandleException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public void decommissionUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "decommissionUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.ActionNotPermittedException) {
              throw (com.liquid.portal.service.ActionNotPermittedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public void deleteCompetition(java.lang.String arg0, long arg1, boolean arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.ContestNotFoundException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "deleteCompetition"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, new java.lang.Long(arg1), new java.lang.Boolean(arg2), arg3});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.ActionNotPermittedException) {
              throw (com.liquid.portal.service.ActionNotPermittedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.ContestNotFoundException) {
              throw (com.liquid.portal.service.ContestNotFoundException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.liquid.portal.service.Warning[] provisionProject(java.lang.String arg0, java.lang.String arg1, java.lang.String[] arg2) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionProject"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1, arg2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.liquid.portal.service.Warning[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.liquid.portal.service.Warning[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.liquid.portal.service.Warning[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.ActionNotPermittedException) {
              throw (com.liquid.portal.service.ActionNotPermittedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.liquid.portal.service.ProvisionUserResult provisionUser(java.lang.String arg0, java.lang.String arg1, boolean arg2, java.lang.String[] arg3, long[] arg4) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.InvalidHandleException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "provisionUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1, new java.lang.Boolean(arg2), arg3, arg4});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.liquid.portal.service.ProvisionUserResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.liquid.portal.service.ProvisionUserResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.liquid.portal.service.ProvisionUserResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.InvalidHandleException) {
              throw (com.liquid.portal.service.InvalidHandleException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.liquid.portal.service.RegisterUserResult registerUser(com.liquid.portal.service.User arg0, java.util.Calendar arg1) throws java.rmi.RemoteException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.HandleCreationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "registerUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.liquid.portal.service.RegisterUserResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.liquid.portal.service.RegisterUserResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.liquid.portal.service.RegisterUserResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleCreationException) {
              throw (com.liquid.portal.service.HandleCreationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.liquid.portal.service.Warning[] validateUser(com.liquid.portal.service.UserInfo arg0, boolean arg1) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "validateUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, new java.lang.Boolean(arg1)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.liquid.portal.service.Warning[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.liquid.portal.service.Warning[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.liquid.portal.service.Warning[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.HandleNotFoundException) {
              throw (com.liquid.portal.service.HandleNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalServicingException) {
              throw (com.liquid.portal.service.LiquidPortalServicingException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.liquid.portal.service.LiquidPortalIllegalArgumentException) {
              throw (com.liquid.portal.service.LiquidPortalIllegalArgumentException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
