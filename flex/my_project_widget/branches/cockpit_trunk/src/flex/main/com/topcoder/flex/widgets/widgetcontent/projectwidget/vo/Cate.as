/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.vo {
    import mx.collections.ArrayCollection;

    /**
     * <p>
     * This class represents a contest group and contests in it.
     * Contest group gets changed as per the user selection.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since My Project Overhaul Assembly.
     */
    [Bindable]
    public class Cate {
        /**
         * Label of the contest group.
         */
        public var label:String;

        /**
         * Contests in the group.
         */
        public var contests:ArrayCollection;

        /**
         * A default empty constrcutor.
         */
        public function Cate() {
        }
    }
}