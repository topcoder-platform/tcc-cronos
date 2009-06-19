/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.vo {
    import com.topcoder.flex.widgets.widgetcontent.projectadminwidget.ProjectAdminWidget;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.AbstractOperation;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.soap.SOAPHeader;


    /**
     * <p>
     * This class is for storing project data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     * @since Cockpit Share Submission Integration
     */
    [Bindable]
    public class Project {

        /**
         * A default empty constructor.
         */
        public function Project() {
        }

        /**
         * Id of the project.
         */
        public var id:int

        /**
         * Name of the project.
         */
        public var name:String;

        /**
         * Access mode permissions of user for this project.
         */
        public var access:String="";

        /**
         * Access mode permissions of user for this project, which is currently persisted in db.
         */
        public var persistedAccess:String="";

        /**
         * Id of the persisted permission.
         */
        //public var persistedPermissionId:Number=0;

        /**
         * Collection of contests & their permissions.
         */
        public var contests:ArrayCollection=new ArrayCollection();

        /**
         * User for this project.
         */
        public var user:User;

        /**
         * Updates the current permission to backend.
         *
         * @param reference to project admin widget.
         */
        public function updatePermission(projectWidget:ProjectAdminWidget):void {
            projectWidget.showLoadingProgress();
            var header:SOAPHeader=projectWidget.getHeader(projectWidget.username, projectWidget.password);
            projectWidget.contestServiceFacadeWS.clearHeaders();
            projectWidget.contestServiceFacadeWS.addHeader(header);
            var getPermission:AbstractOperation=projectWidget.contestServiceFacadeWS.getOperation("getPermissions");
            if (getPermission) {
                getPermission.send(user.id,id);
            }
        }
        

        /**
         * Deletes the current permission to backend.
         *
         * @param reference to project admin widget.
         */
        public function deletePermission(projectWidget:ProjectAdminWidget,permissionid:int):void {
            projectWidget.showLoadingProgress();
            var header:SOAPHeader=projectWidget.getHeader(projectWidget.username, projectWidget.password);
            projectWidget.contestServiceFacadeWS.clearHeaders();
            projectWidget.contestServiceFacadeWS.addHeader(header);
            var deletePermission:AbstractOperation=projectWidget.contestServiceFacadeWS.getOperation("deletePermission");
            if (deletePermission) {
                deletePermission.addEventListener("result", function(e:ResultEvent):void {
                        projectWidget.hideLoadingProgress();
                    });

                deletePermission.send(permissionid);
            }
        }

        /**
         * Adds the current permission to backend.
         *
         * @param reference to project admin widget.
         */
        public function addPermission(projectWidget:ProjectAdminWidget):void {
            projectWidget.showLoadingProgress();
            var header:SOAPHeader=projectWidget.getHeader(projectWidget.username, projectWidget.password);
            projectWidget.contestServiceFacadeWS.clearHeaders();
            projectWidget.contestServiceFacadeWS.addHeader(header);
            var addPermission:AbstractOperation=projectWidget.contestServiceFacadeWS.getOperation("addPermission");
            if (addPermission) {
                addPermission.addEventListener("result", function(e:ResultEvent):void {
                        projectWidget.hideLoadingProgress();
                        
                    });

                addPermission.send(getPermissionDTO());
            }
        }

        /**
         * Gets the permission dto object.
         *
         * @return permission dto object for persistance.
         */
        private function getPermissionDTO():Object {
            var perm:Object=new Object();
            /*
            if (persistedPermissionId && persistedPermissionId > 0) {
                perm.permissionId=persistedPermissionId;
            }*/
            perm.userId=user.id;
            perm.projectId=id;
            perm.permissionType=new Object();
            if (access == "Full") {
                perm.permissionType.name="project_full";
                perm.permissionType.permissionTypeId=3;
            } else if (access == "Write") {
                perm.permissionType.name="project_write";
                perm.permissionType.permissionTypeId=2;
            } else if (access == "Read") {
                perm.permissionType.name="project_read";
                perm.permissionType.permissionTypeId=1;
            }

            return perm;
        }
    }
}
