package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
	 import mx.rpc.xml.Schema
	 public class BaseProjectServiceFacadeBeanServiceSchema
	{
		 public var schemas:Array = new Array();
		 public var targetNamespaces:Array = new Array();
		 public function BaseProjectServiceFacadeBeanServiceSchema():void
		{
			 var xsdXML0:XML = <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://schemas.xmlsoap.org/wsdl/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://ejb.project.facade.service.topcoder.com/" xmlns:xs="http://www.w3.org/2001/XMLSchema" attributeFormDefault="unqualified" elementFormDefault="unqualified" targetNamespace="http://ejb.project.facade.service.topcoder.com/">
    <xsd:element name="AuthorizationFailedFault" type="tns:AuthorizationFailedFault"/>
    <xsd:element name="IllegalArgumentFault" type="tns:IllegalArgumentFault"/>
    <xsd:element name="PersistenceFault" type="tns:PersistenceFault"/>
    <xsd:element name="ProjectHasCompetitionsFault" type="tns:ProjectHasCompetitionsFault"/>
    <xsd:element name="ProjectNotFoundFault" type="tns:ProjectNotFoundFault"/>
    <xsd:element name="UserNotFoundFault" type="tns:UserNotFoundFault"/>
    <xsd:element name="createProject" type="tns:createProject"/>
    <xsd:element name="createProjectResponse" type="tns:createProjectResponse"/>
    <xsd:element name="deleteProject" type="tns:deleteProject"/>
    <xsd:element name="deleteProjectResponse" type="tns:deleteProjectResponse"/>
    <xsd:element name="getAllProjects" type="tns:getAllProjects"/>
    <xsd:element name="getAllProjectsResponse" type="tns:getAllProjectsResponse"/>
    <xsd:element name="getProject" type="tns:getProject"/>
    <xsd:element name="getProjectResponse" type="tns:getProjectResponse"/>
    <xsd:element name="getProjectsForUser" type="tns:getProjectsForUser"/>
    <xsd:element name="getProjectsForUserResponse" type="tns:getProjectsForUserResponse"/>
    <xsd:element name="updateProject" type="tns:updateProject"/>
    <xsd:element name="updateProjectResponse" type="tns:updateProjectResponse"/>
    <xsd:complexType name="createProject">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="projectData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element minOccurs="0" name="projectId" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="createProjectResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="PersistenceFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="IllegalArgumentFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getProject">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getProjectResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="ProjectNotFoundFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="AuthorizationFailedFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getProjectsForUser">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getProjectsForUserResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="UserNotFoundFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getAllProjects">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllProjectsResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateProject">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:projectData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateProjectResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="deleteProject">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="deleteProjectResponse">
        <xsd:sequence>
            <xsd:element name="return" type="xs:boolean"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="ProjectHasCompetitionsFault">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
</xsd:schema>
;
			 var xsdSchema0:Schema = new Schema(xsdXML0);
			schemas.push(xsdSchema0);
			targetNamespaces.push(new Namespace('','http://ejb.project.facade.service.topcoder.com/'));
		}
	}
}