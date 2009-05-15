/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget {
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.utils.ObjectProxy;

    /**
     * <p>
     * This is the data model class for Thumbnail Gallery Viewer.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author shailendra_80
     * @version 1.0
     * @since Flex Submission Viewer Overhaul
     */
    [Bindable]
    public class ThumbnailGalleryDataModel {
        /**
         * Singleton instance of this model class.
         */
        //private static var _instance:ThumbnailGalleryDataModel;

        /**
         * Reference to the main submission viewer widget.
         */
        public var subViewer:SubmissionViewerWidget;

        /**
         * True if submission rank is allowed, otherwise false.
         *
         * If it is false, then submissions can not be ranked.
         */
        public var isRankMode:Boolean=true;

        /**
         * Array collection of ranked submissions.
         *
         * In current case, list size can not be more than 5.
         */
        public var rankedSubmissionList:ArrayCollection;

        /**
         * Array collection of currently active / visible submissions in the gallery view.
         */
        public var activeSubmission:ArrayCollection;

        /**
         * Page index of currently active / visible submissions.
         */
        public var activeSubmissionIndex:int=-1;
        
        /**
         * Id of the active submission
         */
        public var activeSubmissionId:int=-1;

        /**
         * Current index of the submission which is same as the first in the active submission list.
         */
        public var currentIndex:int=0;

        /**
         * Current index of the submission which is same as the last in the active submission list.
         */
        public var currentLast:int=0;

        /**
         * Determines which view is currently selected. By default gallery view is selected.
         */
        public var selectedIndex:int=0;

        /**
         * True if footer should be shown, else false.
         */
        public var showFooter:Boolean=false;

        /**
         * Currently selected image.
         */
        public var selectedImage:String;

        /**
         * Array collection of images.
         */
        public var imageList:ArrayCollection;

        /**
         * Currently selected / previewed submission.
         */
        public var selectedSubmission:Object=null;

        /**
         * String array of rank labels.
         */
        public var rankLabel:Array=["1st Place", "2nd Place", "3rd Place", "4th Place", "5th Place"];


	private static const CONTEST_READ_PERMISSION:String = "contest_read";

	private static const PROJECT_READ_PERMISSION:String = "project_read";

        /**
         * Gets the singleton instance of this model class.
         *
         * @return singleton instance of this model class.
         */
        /*public static function get instance():ThumbnailGalleryDataModel {
            if (!_instance) {
                _instance=new ThumbnailGalleryDataModel();
            }
            return _instance;
        }*/

        /**
         * Initializes this model.
         */
        public function ThumbnailGalleryDataModel() {
            currentIndex=0;
            selectedIndex=0;
            selectedSubmission=null;
            selectedImage=null;

            rankedSubmissionList=new ArrayCollection();

            for each (var str:String in rankLabel) {
                var ranked:Object=new Object();
                ranked.label=str;
                ranked.thumbnail="";
                ranked.showClose=false;
                ranked.parentModel=this;

                var rankedProxy:ObjectProxy=new ObjectProxy(ranked);
                rankedSubmissionList.addItem(rankedProxy);
            }
        }

        /**
         * Gets the array collection of all submissions.
         *
         * @return the array collection of all submissions.
         */
        public function get submissionList():ArrayCollection {
            return this.subViewer.submissionList;
        }

        /**
         * Sets the activeSubmissionIndex to the index of specified submission id.
         */
        public function setCurrentSubmission(id:int):void {
            var i:int=0;
            for each (var item:Object in this.subViewer.submissionList) {
                if (item.id == id) {
                    activeSubmissionIndex=i;
                    activeSubmissionId=id;
                }

                i++;
            }
            
            trace("@@@@@@@ activeSubmissionIndex: " + activeSubmissionIndex);
        }

        /**
         * Determines if navigation to next page can happen.
         *
         * @return true if navigation to next page can happen, else false.
         */
        public function canGoNext(nouse:Object=null):Boolean {
            if (activeSubmission.length <= 0) {
                return false;
            }
            return !(activeSubmission.getItemAt(activeSubmission.length - 1) == this.subViewer.submissionList.getItemAt(this.subViewer.submissionList.length - 1) || activeSubmission.length + currentIndex >= this.subViewer.submissionList.length);
        }

        /**
         * Determines if navigation to previous page can happen.
         *
         * @return if navigation to previous page can happen, else false.
         */
        public function canGoPrev(nouse:Object=null):Boolean {
            if (activeSubmission.length <= 0) {
                return false;
            }
            return !(activeSubmission.getItemAt(0) == this.subViewer.submissionList.getItemAt(0) || currentIndex == 0);
        }

        /**
         * Handler for going to the next page in the view.
         */
        public function goNext():void {
            if (currentIndex < 0 || this.subViewer.submissionList.length == 0) {
                currentIndex=0;
            } else {
                if (!canGoNext()) {

                    // Do nothing here.
                } else {
                    activeSubmission=new ArrayCollection();
                    currentIndex=currentIndex + 10;
                    if (currentIndex >= this.subViewer.submissionList.length) {
                        currentIndex=this.subViewer.submissionList.length - 1;
                    }
                    var i:int=currentIndex;
                    for (; i < currentIndex + 10 && i < this.subViewer.submissionList.length; i++) {
                        activeSubmission.addItem(this.subViewer.submissionList.getItemAt(i));
                    }
                    currentLast=i - 1;
                }
            }

        }

        /**
         * Handler for going to the previous page in the view.
         */
        public function goPrev():void {
            if (currentIndex < 0 || this.subViewer.submissionList.length == 0) {
                currentIndex=0;
            } else {
                if (!canGoPrev()) {
                    currentIndex=0;
                        // Do nothing here.
                } else {
                    activeSubmission=new ArrayCollection();
                    currentIndex=currentIndex - 10;
                    if (currentIndex < 0) {
                        currentIndex=0;
                    }
                    var i:int=currentIndex;
                    for (; i < currentIndex + 10 && i < this.subViewer.submissionList.length; i++) {
                        activeSubmission.addItem(this.subViewer.submissionList.getItemAt(i));
                    }
                    currentLast=i - 1;
                }
            }
        }

        /**
         * Updates rank of the specified submission data to i
         */
        public function updateRank(i:int, id:Number=0):void {
            //trace("----------------------------- ################# id: " + id);
            if (id < 0) {
                if (activeSubmissionIndex >= 0) {
                    id=this.subViewer.submissionList.getItemAt(activeSubmissionIndex).id as Number;
                } else {
                    id=0;
                }
            }
            var index:int=0;
            for each (var obj:Object in rankedSubmissionList) {
                if (id > 0 && obj.id == id) {
                    var ranked:Object=new Object();
                    ranked.label=rankLabel[index] as String;
                    ranked.thumbnail="";
                    ranked.showClose=false;
                    ranked.parentModel=this;

                    var rankedProxy:ObjectProxy=new ObjectProxy(ranked);

                    rankedSubmissionList.setItemAt(rankedProxy, index);
                }
                
                index++;
            }

            var data:Object=null;

            for each (var o:Object in this.subViewer.submissionList) {
                if (id > 0 && o.id == id) {
                    data=o;
                    break;
                }
            }
            
            var rankObj:Object=new Object();
            var rankObjProxy:ObjectProxy=new ObjectProxy(rankObj);
            
            if (data) {
                trace("Setting rank: " + i + " for id: " + data.id);
                // set the rank of ranked object.
                if (data.rank && data.rank > 0) {
                    // first remove the item if it was at older place.
                    //data.rank=0;
                    this.subViewer.removeFromRankList(data);
                }
                
                data.rank=i + 1;
                this.subViewer.updateRankList(data);
                updateSubmission(data);
                
                rankObj.thumbnail=data.thumbnail;
                rankObj.showClose=data.id && data.thumbnail && data.thumbnail != "" ? true : false;
                rankObj.id=data.id;
                rankObj.label=rankLabel[i] as String;
                rankObj.purchased=data.id ? data.purchased : false;
                rankObj.parentModel=this;

                rankedSubmissionList.setItemAt(rankObjProxy, i);
            } else {
                trace("Setting empty placeholder at i: " + i);
                rankObj.thumbnail="";
                rankObj.showClose=false;
                rankObj.id=0;
                rankObj.label=rankLabel[i] as String;
                rankObj.purchased=false;
                rankObj.parentModel=this;

                rankedSubmissionList.setItemAt(rankObjProxy, i);        
            }
        }

        /**
         * Removes specified submission data from the rank list.
         */
        public function removeRank(id:Number=0):void {

            //trace("Removing rank for id: " + id);

            if (!id || id <= 0) {
                id=this.subViewer.submissionList.getItemAt(activeSubmissionIndex).id as Number;
            }
            var index:int=0;
            for each (var obj:Object in rankedSubmissionList) {
                if (obj.id == id) {
                    var ranked:Object=new Object();
                    ranked.label=rankLabel[index] as String;
                    ranked.thumbnail="";
                    ranked.showClose=false;
                    ranked.parentModel=this;

                    var rankedProxy:ObjectProxy=new ObjectProxy(ranked);
                    rankedSubmissionList.setItemAt(rankedProxy, index);
                }
                index++;
            }

            var data:Object=null;

            for each (var o:Object in this.subViewer.submissionList) {
                if (o.id == id) {
                    data=o;
                    break;
                }
            }

            if (!data) {
                return;
            }

            //trace("To be removed data: " + data);

            data.rank=0;
            this.subViewer.updateRankList(data);
            updateSubmission(data);
        }

        /**
         * Reorders the submission by id.
         */
        public function reorderById():void {
            var sort:Sort=new Sort();
            sort.compareFunction=compareNumber;
            this.subViewer.submissionList.sort=sort;
            this.subViewer.submissionList.refresh();
            setSubmission();
        }

        /**
         * Reorders the submission by thumb up and then thumb down.
         */
        public function reorderByUpDown():void {
            var sort:Sort=new Sort();
            sort.compareFunction=compareUpDown;
            this.subViewer.submissionList.sort=sort;
            this.subViewer.submissionList.refresh();
            setSubmission();
        }

        /**
         * Comparator function for comparing thumn up and down.
         */
        private function compareUpDown(a:Object, b:Object, fields:Array=null):int {
            var upDown1:String=a.upDown;
            var upDown2:String=b.upDown;
            if (upDown1 == "up" && upDown2 != "up") {
                return -1;
            } else if (upDown2 == "up" && upDown1 != "up") {
                return 1;
            } else if (upDown1 == "down" && upDown2 != "down") {
                return 1;
            } else if (upDown2 == "down" && upDown1 != "down") {
                return -1;
            }
            return 0;
        }

        /**
         * Comparator function for comparing two numbers.
         */
        private function compareNumber(a:Object, b:Object, fields:Array=null):int {
            var id1:int=a.id;
            var id2:int=b.id;
            if (id1 > id2) {
                return 1;
            } else if (id1 < id2) {
                return -1;
            }
            return 0;
        }

        /**
         * It resets the model state.
         */
        public function reset(fullReset:Boolean):void {
            currentIndex=0;
            //selectedIndex=0;
            selectedSubmission=null;
            selectedImage=null;
            
            if (fullReset) {
                activeSubmission=null;
                activeSubmissionIndex=-1;
                activeSubmissionId=-1;
                currentLast=0;
            }

            rankedSubmissionList=new ArrayCollection();

            for each (var str:String in rankLabel) {
                var ranked:Object=new Object();
                ranked.label=str;
                ranked.thumbnail="";
                ranked.showClose=false;
                ranked.parentModel=this;

                var rankedProxy:ObjectProxy=new ObjectProxy(ranked);
                rankedSubmissionList.addItem(rankedProxy);
            }
        }

        /**
         * It sets the active submission.
         *
         * It gets called on submission data reload.
         */
        public function setSubmission(fullReset:Boolean=true):void {
            activeSubmission=new ArrayCollection();
            currentIndex=0;
            var i:int=0;
            for (; i < this.subViewer.submissionList.length && i < 10; i++) {
                activeSubmission.addItem(this.subViewer.submissionList.getItemAt(i));
            }
            currentLast=i - 1;
            
            if (fullReset) {
                if (selectedIndex==1 || selectedIndex==2) {
                    setCurrentSubmission(activeSubmission.getItemAt(0).id);
                }
            }
        }

        /**
         * It updates the rank list.
         */
        public function updateRankList():void {
            if (this.subViewer.rankList) {
                var i:int=0;
                for (; i < this.subViewer.rankList.length; i++) {
                    var obj:Object=this.subViewer.rankList.getItemAt(i);
                    if (obj && obj.id && obj.rank && obj.rank > 0) {
                        //trace("------------------------- UPDATING RANK FOR: " + obj.id + "," + obj.rank);
                        updateRank(i, obj.id as Number);
                    }
                }
            }
        }

        /**
         * Determines the rank of currently selected submission.
         */
        public function getRank():int {
            var item:Object=this.subViewer.submissionList.getItemAt(activeSubmissionIndex);
            for (var i:int=0; i < rankedSubmissionList.length; i++) {
                var rank:Object=rankedSubmissionList.getItemAt(i);
                if (rank.id == item.id) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * Determines the movement direction.
         * It is used for page navigation animation in single image viewer or multi image viewer.
         */
        public var direction:String;

        /**
         * Handler for next in simple image viewer or multi image viewer.
         */
        public function next():void {
            activeSubmissionIndex=Math.min(activeSubmissionIndex + 1, this.subViewer.submissionList.length - 1);
            var obj:Object=this.subViewer.submissionList.getItemAt(activeSubmissionIndex);
            activeSubmissionId=obj.id;
            if (obj.multi) {
                selectedIndex=2;
            } else {
                selectedIndex=1;
            }
            direction="next";
        }

        /**
         * Handler for previous in simple image viewer or multi image viewer.
         */
        public function prev():void {
            activeSubmissionIndex=Math.max(activeSubmissionIndex - 1, 0);
            var obj:Object=this.subViewer.submissionList.getItemAt(activeSubmissionIndex);
            activeSubmissionId=obj.id;
            if (obj.multi) {
                selectedIndex=2;
            } else {
                selectedIndex=1;
            }

            direction="prev";
        }

        /**
         * Gets the id of the previous submission.
         *
         * @return id of the previous submission.
         */
        public function getPrevId():String {
            if (activeSubmissionIndex >= 1 && activeSubmissionIndex < this.subViewer.submissionList.length) {
                var item:Object=this.subViewer.submissionList.getItemAt(activeSubmissionIndex - 1)
                return item.id.toString();
            }
            return "";
        }

        /**
         * Gets the id of the next submission.
         *
         * @return id of the next submission.
         */
        public function getNextId():String {
            if (activeSubmissionIndex >= 0 && activeSubmissionIndex < this.subViewer.submissionList.length - 1) {
                var item:Object=this.subViewer.submissionList.getItemAt(activeSubmissionIndex + 1);
                return item.id.toString();
            }
            return "";
        }

        /**
         * Update submission attributes. After submission has been marked/removed for purchase or ranked.
         */
        public function updateSubmission(item:Object):void {
            // BUGR-1169
            var contestInfo:Object=this.subViewer.contestInfoDictionary[this.subViewer.selectedContestId];
            var prizes:ArrayCollection=contestInfo.prizes as ArrayCollection;

            trace("Update submission before: {" + item.id + "," + item.rank + "," + item.markedForPurchase + "," + item.price + "," + item.purchased + "}");
            
            item.price=0;

            // if item is getting purchased separately, use the value from db.
            if (item.savedPrice && item.savedPrice > 0 && (item.markedForPurchase || item.paidFor)) {
                item.price=item.savedPrice;
            } else {
                if (prizes) {
                    // give more priority to marked for purchase.
                    // as there can be cases where item is ranked
                    // but doesn't have the corresponding prize.
                    if (item.markedForPurchase) {
                        //trace("Item is marked for purchase: " + item.id);
                        // if markedForPurchase is there,
                        // then price must be equal to 2nd place prize.
                        if (prizes.length > 1 && prizes[1] > 0) {
                            trace("Prize: " + prizes[1]);
                            item.price=prizes[1];
                        }

                    } else if (item.rank && item.rank > 0) {
                        // retrieve the price from contest info's prizeList.
                        if (prizes.length < item.rank) {
                            // do nothing.
                        } else if (prizes[item.rank - 1] > 0) {
                            //trace("Item price picked from: " + (item.rank - 1) + " = " + prizes[item.rank - 1]);
                            item.price=prizes[item.rank - 1];
                        } 
                    } 
                } 
            }

            // set the purchased attribute correctly.
            if (prizes) {
                item.mustPurchase=item.rank && item.rank > 0 && item.rank <= prizes.length && item.price && item.price > 0;
            }

            item.purchased=item.markedForPurchase || item.mustPurchase;

            //trace("Update submission after: {" + item.id + "," + item.rank + "," + item.markedForPurchase + "," + item.price + "," + item.purchased + "}");

            this.subViewer.updatePurchase();
        }
        
        /**
         * Gets the submission for given id.
         */
        public function getSubmission(id:int):Object {
            for each (var item:Object in this.subViewer.submissionList) {
                if (item.id == id) {
                    return item;
                }
            }
            
            return null;
        }

	/**
	 * check if contest is read only
	 */
	public function isPermissionReadOnly():Boolean {
		
		 var contestInfo:Object=this.subViewer.contestInfoDictionary[this.subViewer.selectedContestId];
		 return (contestInfo.permission == CONTEST_READ_PERMISSION || contestInfo.permission == PROJECT_READ_PERMISSION);

	    }

    }
}
