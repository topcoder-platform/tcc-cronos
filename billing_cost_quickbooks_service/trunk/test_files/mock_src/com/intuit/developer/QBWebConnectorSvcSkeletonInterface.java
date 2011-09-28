
/**
 * QBWebConnectorSvcSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.5  Built on : May 28, 2011 (08:30:56 CEST)
 */
    package com.intuit.developer;
    /**
     *  QBWebConnectorSvcSkeletonInterface java skeleton interface for the axisService
     */
    public interface QBWebConnectorSvcSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param sendRequestXML
         */

        
                public com.intuit.developer.SendRequestXMLResponse sendRequestXML
                (
                  com.intuit.developer.SendRequestXML sendRequestXML
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param connectionError
         */

        
                public com.intuit.developer.ConnectionErrorResponse connectionError
                (
                  com.intuit.developer.ConnectionError connectionError
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param clientVersion
         */

        
                public com.intuit.developer.ClientVersionResponse clientVersion
                (
                  com.intuit.developer.ClientVersion clientVersion
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param serverVersion
         */

        
                public com.intuit.developer.ServerVersionResponse serverVersion
                (
                  com.intuit.developer.ServerVersion serverVersion
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param closeConnection
         */

        
                public com.intuit.developer.CloseConnectionResponse closeConnection
                (
                  com.intuit.developer.CloseConnection closeConnection
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLastError
         */

        
                public com.intuit.developer.GetLastErrorResponse getLastError
                (
                  com.intuit.developer.GetLastError getLastError
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param authenticate
         */

        
                public com.intuit.developer.AuthenticateResponse authenticate
                (
                  com.intuit.developer.Authenticate authenticate
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param receiveResponseXML
         */

        
                public com.intuit.developer.ReceiveResponseXMLResponse receiveResponseXML
                (
                  com.intuit.developer.ReceiveResponseXML receiveResponseXML
                 )
            ;
        
         }
    