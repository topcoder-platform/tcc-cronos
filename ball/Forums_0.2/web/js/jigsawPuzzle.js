/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

var utilityClass = new UtilityClass();
/*--------------------------------------------------Class Separator--------------------------------------------------*/

/**
 * This javascript object represents the Jigsaw Puzzle.  It encompasses the puzzle area over which
 * the pieces may be dragged.  It also offers supporting methods to support the draggable
 * functionality of the puzzle pieces.
 * Finally, it has a method to submit the solution to a preconfigured URL through the use of
 * a hidden form.
 *
 * @constructor
 * @param {DivElement} divPuzzleArea the area encompassing this puzzle.
 * @param {SolutionArea} pSolutionArea the area where the pieces must be placed to form a solution
 * @param {InputElement} elPuzzleStateField the input element into which the puzzle state must be written during submission.
 * @param {FormElement} elForm the form used to submit the puzzle solution.
 * @param {int} iHeight the height of the puzzle, in pieces.
 * @param {int} iWidth the width of the puzzle, in pieces.
 * @param {String} sId the unique identifier for this puzzle rendition.
 * @returns a new JigsawPuzzle object
 */
function JigsawPuzzle(divPuzzleArea, pSolutionArea , elPuzzleStateField , elForm, iHeight, iWidth, SId) {


    /**
     * This is the div element representing the puzzle area.  It is used to determine whether a
     * dragged puzzle piece has overstepped the acceptable bounds of the puzzle.  It also
     * serves as the HTML container element for all the other Jigsaw puzzle elements.
     *
     * @type Div Element
     * @private
     */
    var puzzleArea = divPuzzleArea;

    /**
     * This is the PuzzlePiece that is considered 'active' and is being dragged by
     * the user.
     *
     * @type PuzzlePiece
     * @private
     */
    var dragTarget = null;

    /**
     * The area where the pieces must be placed to form a solution.
     *
     * @type SolutionArea
     * @private
     */
    var solutionArea = pSolutionArea;


    /**
     * This is the text field to which the puzzle state should be written prior to submitting it
     * to the preconfigured URL.
     * @type TextField Element
     * @private
     */
    var puzzleStateField = elPuzzleStateField;

    /**
     * This is the form element which is used to actually submit the user data to a preconfigured
     * URL.
     *
     * @type Form Element
     * @private
     */
    var form = elForm;

    /**
     * A unique string that identifies this particular rendition of a Jigsaw Puzzle.
     *
     * @type String
     * @private
     */
    var id = SId;

    /**
     * This method verifies that the given image is still within the bounds of
     * the puzzleArea. It will be used to ensure that no piece is dragged outside
     * of the puzzle area.
     *
     * @param{ImageElement} image to check bounds on.
     * @return{boolean} Whether the image lies within the bounds of the puzzle area.
     */
    this.checkImageBounds = function(image) {
        var pieceLeft = utilityClass.getObjectLeft(image);
        var pieceTop = utilityClass.getObjectTop(image);
        var pieceWidth = utilityClass.getObjectWidth(image);
        var pieceHeight = utilityClass.getObjectHeight(image);

        return this.checkBounds(pieceLeft, pieceTop, pieceWidth, pieceHeight);
    }

    /**
     * Tests whether the rectangular area described by the parameters is within
     * the bounds of this PuzzleArea
     *
     * @param{integer} left the x coordinate of the left edge of the area to check, in pixels
     * @param{integer} top the y coordinate of the top edge of the area to check, in pixels
     * @param{integer} width the width of the area to check, in pixels
     * @param{integer} height the height of the area to check, in pixels
     * @return{boolean} Whether the image lies within the bounds of the puzzle area.
     */
    this.checkBounds = function(left, top, width, height) {
        var puzzleLeft = utilityClass.getObjectLeft(puzzleArea);
        var puzzleTop = utilityClass.getObjectTop(puzzleArea);
        var puzzleWidth = utilityClass.getObjectWidth(puzzleArea);
        var puzzleHeight = utilityClass.getObjectHeight(puzzleArea);

        if (left >= puzzleLeft && top >= puzzleTop
                && left + width < puzzleLeft + puzzleWidth
                && top + height < puzzleTop + puzzleHeight) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mouse handling event that is assigned to the puzzleArea when the mouse is lifted up.
     * This will snap the target to Grid, and release it from being dragged.
     *
     * @param event The mouse event that was triggered.
     * @return false to indicate that the event was captured.
     */
    this.onMouseUp = function(event) {
        if (dragTarget) {
            // get the image
            var img = dragTarget.getImage();

            // clear the drag target right away, to be sure that isn't forestalled by any error
            dragTarget = null;

            // set the outline and zIndex
            img.style.outline = "";

            // make sure the original puzzlePositions for current image is clear.
            solutionArea.forgetPiece(img.id);

            // snap to the puzzle grid if close enough
            solutionArea.snapToGrid(img.puzzlePiece);

            return false;
        }
    }

    this.onMouseMove = function(event) {
        if (dragTarget) {
            dragTarget.getImage().puzzlePiece.onMouseMove(event);
        }
    }
    
    /**
     * Submits a solution through the hidden form that was generated by the puzzle renderer
     * and passed to this constructor. This function will set the state field content.
     *
     */
    this.submitSolution = function() {
        puzzleStateField.value = solutionArea.encodePuzzleState();
        form.submit();
    }

    /**
     * Sets the drag target which is the PuzzlePiece that is considered 'active'
     * and is being dragged by the user.
     *
     * @param{PuzzlePiece} pDragTarget the dragtarget to set
     *
     */
    this.setDragTarget = function(pDragTarget) {
        dragTarget = pDragTarget;
    }

    /**
     * Retrieves the drag target which is the PuzzlePiece that is considered 'active'
     * and is being dragged by the user.
     *
     * @return the dragTarget
     */
    this.getDragTarget = function() {
        return dragTarget;
    }

    /**
     * Retrieves the SolutionArea where the pieces must be placed in order to form a solution.
     *
     * @return the solution area.
     */
    this.getSolutionArea = function() {
        return solutionArea;
    }

    /**
     * Retrieves the HTML Div element which acts as a container for the entire jigsaw puzzle.
     *
     * @return the puzzleArea that solve the puzzle;
     */
    this.getPuzzleArea = function() {
        return puzzleArea;
    }

    /**
     * Returns the unique identifier for this puzzle rendition.
     *
     * @returns the id of the puzzle
     */
    this.getId = function() {
        return id;
    }
}


/*--------------------------------------------------Class Separator--------------------------------------------------*/

/**
 * This represents the area of the JigsawPuzzle where the puzzle pieces are placed in order
 * to solve the puzzle. It contains a method to support placing the pieces on its area, and
 * a method that will encode the puzzle state as a String. This may be an alternative to directly
 * calling submitSolution, so that the web page writer may utilize another technique for sending
 * the encoded solution for checking.
 *
 * @param{DivElement} divElement The HTML Element representing the solution area.
 * @param{int} iheight The height of the puzzle, in pieces.
 * @param{int} iwidth The width of the puzzle, in pieces.
 * @param{int} curveBufferTopBottom The tail size in the TOP/BOTTOM position
 * @param{int} curveBufferLeftRight The tail size in the LEFT/RIGHT position
 */
function SolutionArea(divElement, iHeight, iWidth, curveBufferTopBottom, curveBufferLeftRight) {
    /**
     * This is the div element that represents the actual solution area.  It should be reasonably close
     * to the size of the original base image used to create the pieces.
     *
     * @type DivElement
     * @private
     */
    var div = divElement;

    /**
     * This is the height of the puzzle, in pieces.
     */
    var height = iHeight;

    /**
     * This is the width of the puzzle, in pieces.
     */
    var width = iWidth;

    /**
     * This is the overlap of height direction.
     */
    var overlapTopBottom = curveBufferTopBottom;

    /**
     * This is the overlap of width direction.
     */
    var overlapLeftRight = curveBufferLeftRight;

    /**
     * This represents the logical location of the puzzle pieces.  The solution area can be divided
     * into a h x w grid (where w is the width in pieces and h is the height in pieces).  The String
     * array desribes the positions of the puzzle pieces are they are layed out in the SolutionArea.
     * The indices of the array are X-Y coordinates of a grid slot (0,0 being the bottom-leftmost piece).
     * moving rightward until (h,w) which is the top-rightmost piece.
     */
    var puzzlePositions = new Array(height);

    //
    // Initialize the puzzle position array.
    //
    for (var row = 0; row < height; row++) {
        puzzlePositions[row] = new Array(width);
        for (var col = 0; col < width; col++) {
            puzzlePositions[row][col] = null;
        }
    }

    /**
     * Snap buffer value define how close when image is to the point, do a snap.
     */
    var snapBuffer = 0.35;

    /**
     * Encodes the user's solution into into String form.  This is done by iterating over the
     * puzzlePositions array and building a comma-delimited String of integers from it.
     * The first integer represents the puzzle piece that belongs in the top-leftmost area,
     * moving progressively to the right, then going to the next row and starting
     * again at the leftmost position.  The value of the integer would be the image id
     * of the Puzzle Pieces' ImageElement.
     *
     * @return The user's solution encoded in String form.
     */
    this.encodePuzzleState = function() {
        var encodeResult = "";

        for (var row = 0; row < height; row++) {

            // it's not the first row, should preceed with a comma
            if (row != 0) {
                encodeResult += ',';
            }

            for (var col = 0; col < width; col++) {

                if (col != 0) {
                    // it's not the first item in the row, should preceed with a comma
                    encodeResult += ",";
                }

                if (puzzlePositions[row][col]) {
                    encodeResult += puzzlePositions[row][col];
                }
            }
        }

        return encodeResult;
    }

    /**
     * Causes the specified piece ID to be removed from the recorded puzzle positions
     * wherever it is present (the ID assigned to any such positions is set to null)
     *
     * @param pieceId the ID to remove from among the puzzle positions
     */
    this.forgetPiece = function(pieceId) {
        for (var row = 0; row < height; row++) {
            for (var col = 0; col < width; col++) {
                if (puzzlePositions[row][col] == pieceId) {
                    puzzlePositions[row][col] = null;
                }
            }
        }
    }

    /*
     * This will snap a puzzlePiece into place to 'regularize' the puzzle piece placement into
     * the puzzlePositions array.
     *
     * @param piece This is the puzzlePiece which to check for snapping to grid.
     */
    this.snapToGrid = function(piece) {
        // get the left, top, width, height of solutionArea
        var areaLeft = utilityClass.getObjectLeft(div);
        var areaTop = utilityClass.getObjectTop(div);
        var areaWidth = utilityClass.getObjectWidth(div);
        var areaHeight = utilityClass.getObjectHeight(div);

        // get the left and top of the PuzzlePiece, relative to the solution area
        var pieceLeft = utilityClass.getObjectLeft(piece.getImage()) - areaLeft;
        var pieceTop = utilityClass.getObjectTop(piece.getImage()) - areaTop;
        var pieceWidth = utilityClass.getObjectWidth(piece.getImage());
        var pieceHeight = utilityClass.getObjectHeight(piece.getImage());
        var pieceCenterX = pieceLeft + (pieceWidth / 2);
        var pieceCenterY = pieceTop + (pieceHeight / 2);

        // check whether the piece's center coordinates are in the solution area
        // it is not necessary for the whole piece to be in the area.
        if (pieceCenterX >= 0 && pieceCenterX < areaWidth &&
                    pieceCenterY >= 0 && pieceCenterY < areaHeight) {

            // after calculation, we can know that every snap point is of the
            // form (i * (pieceWidth - overlapLeftRight), j * (pieceHeight - overlapTopBottom)
            // calcuate the width and height of one piece
            var gridCellWidth = pieceWidth - overlapLeftRight;
            var gridCellHeight = pieceHeight - overlapTopBottom;

            var gridOffsetX = overlapLeftRight / 2;
            var gridOffsetY = overlapTopBottom / 2;

            // get the grid current piece belong to
            var x = parseInt(Math.floor((pieceCenterX - gridOffsetX) / gridCellWidth), 10);
            var y = parseInt(Math.floor((pieceCenterY - gridOffsetY) / gridCellHeight), 10);

            x = Math.min(Math.max(x, 0), width - 1);
            y = Math.min(Math.max(y, 0), height - 1);

            if (Math.abs(pieceLeft - x * gridCellWidth)  < snapBuffer * gridCellWidth
                    && Math.abs(pieceTop - y * gridCellHeight) < snapBuffer * gridCellHeight) {
                // should snap to the grid
                puzzlePositions[y][x] = piece.getImage().id;
                utilityClass.shiftToAbsolute(piece.getImage(), x * gridCellWidth + areaLeft, y * gridCellHeight + areaTop);
            }
        }
    }

    /**
     * get the div element.
     *
     * @return the HTML element representing the SolutionArea.
     */
    this.getDiv = function() {
        return div;
    }

    /**
     * get the puzzle position array
     *
     * @return a two dimension array of String type of ids
     */
    this.getPuzzlePositions = function() {
        return puzzlePositions;
    }
}

/*--------------------------------------------------Class Separator--------------------------------------------------*/
/**
 * This represents a piece of a JigsawPuzzle.  It contains the image which is the visual representation
 * of the piece, as well as supporting methods that allow the user to drag the pieces in order to
 * solve the puzzle.
 *
 * @constructor
 * @param {ImageElement} img The visual representation of the puzzle piece.
 * @param {JigsawPuzzle} JPuzzle The JigsawPuzzle to which this piece belongs.
 * @return a new PuzzlePiece object
 */
function PuzzlePiece(img, JPuzzle) {
    /**
     * This is the visual representation of the puzzle piece.
     *
     * @type ImageElement
     * @private
     */
    var image = img;

    /**
      * Attribute for the handle of 'this', because we will assign onMouseDown to a html element, so 'this' key word
      * in the onMouseDown function will refer to the html element, but not the PuzzlePiece object, with this attribute
      * we can get current PuzzlePiece object in function onMouseDown, this is the implementation recommend by "Ajax in
      * action".
      *
      */
    image.puzzlePiece = this;

    /**
     * This is the JigsawPuzzle to which this PuzzlePiece belongs.
     *
     * @type JigsawPuzzle
     * @private
     */
    var puzzle = JPuzzle;

    /**
     * Retrieves the visual representation of the puzzle piece.
     *
     * @return the visual representation of the image
     * @type ImageElement
     */
    this.getImage = function() {
        return image;
    }

    /**
     * Remember the position.
     */
    var x;

    /**
     * Remember the position.
     */
    var y;

    /**
     * Remember the document root.
     */
    var root;

    /**
     * Remember the position incresement.
     */
    var mx;

    /**
     * Remember the position incresement.
     */
    var my;

    /**
     * Remember the original position, when image go out of bound, just restore to the original.
     */
    var lastValidX;

    /**
     * Remember the original position, when image go out of bound, just restore to the original.
     */
    var lastValidY;

    /**
     * The width of the image; this will change infrequently at best, so it is stored in the object
     * and updated only when this piece is selected or clicked.
     */
    var pieceWidth;

    /**
     * The height of the image; this will change infrequently at best, so it is stored in the object
     * and updated only when this piece is selected or clicked.
     */
    var pieceHeight;

    /**
     * <p>
     * This function is used to support the 'dragging' functionality of the puzzle piece.  If the
     * puzzlePiece is a drag target, then this will update the location of the image to
     * follow the mouse cursor, making sure that it stays within the bounds of the JigsawPuzzle.
     * </p>
     */
    this.onMouseMove = function(evt) {
        evt = evt || window.event;
        if (puzzle.getDragTarget() == image.puzzlePiece) {
            // get new mx and my calcuate the different and add it to x and y.
            var newmx = evt.pageX || evt.clientX + root.scrollLeft;
            var newmy = evt.pageY || evt.clientY + root.scrollTop;
            var deltaX = newmx - mx;
            var deltaY = newmy - my;
            var newLeft = utilityClass.getObjectLeft(image) + deltaX;
            var newTop = utilityClass.getObjectTop(image) + deltaY;

            // do not allow dragging out of the puzzle area
            if (puzzle.checkBounds(newLeft, newTop, pieceWidth, pieceHeight)) {
                // track the position and cumulative delta
                x += deltaX;
                y += deltaY;
                mx = newmx;
                my = newmy;

                // set the image position
                img.xPos = x;
                img.yPos = y;
                img.style.left = x+"px";
                img.style.top = y+"px";
                img.style.outline="2px solid red";
            }

            return false;
        }
    }

    /**
     * This function is used to support the 'dragging' functionality of the puzzle piece.
     *
     * @param{MouseEvent} event  The object that describes the circumstances of the event.
     * @return False to indicate that the event was captured.
     * @type boolean
     */
    this.onMouseDown = function(evt) {
        // fix the event to catch IE event.
        evt = evt || window.event;

        // set the position stype to relative.
        img.style.position="relative";

        // move the image to the front
        img.style.zIndex = "" + utilityClass.getNextZIndex();

        // get the root of the document.
        root = document.documentElement || document.body;

        // get initial of x and y, if img.xPos is not set, will set to 0, 0
        x = img.xPos || 0;
        y = img.yPos || 0;

        // set the mx and my, handle different browser
        mx = evt.pageX || evt.clientX + root.scrollLeft;
        my = evt.pageY || evt.clientY + root.scrollTop;

        // set the drag target
        puzzle.setDragTarget(image.puzzlePiece);

        // remember current position for restore
        lastValidX = x;
        lastValidY = y;
        pieceWidth = utilityClass.getObjectWidth(image);
        pieceHeight = utilityClass.getObjectHeight(image);

        return false;
    }

    pieceWidth = utilityClass.getObjectWidth(img);
    pieceHeight = utilityClass.getObjectHeight(img);
}

/**
 * <p>
 * Utility class handle the init of script, and moving of object.
 * </p>
 */
function UtilityClass() {
    /**
     * <p>
     * Global variables for different browser
     * </p>
     */
    var isCSS, isW3C, isIE4, isNN4, isIE6CSS;

    /**
     * A counter that tracks the largest Z index yet assigned, used to put an object
     * on top of all other objects
     */
    var nextZIndex;

    /**
     * Initialize upon load to let all browsers establish content objects
     */
    this.initUtility = function( ) {
        nextZIndex = 0;
        if (document.images) {
            isCSS = (document.body && document.body.style) ? true : false;
            isW3C = (isCSS && document.getElementById) ? true : false;
            isIE4 = (isCSS && document.all) ? true : false;
            isNN4 = (document.layers) ? true : false;
            isIE6CSS = (document.compatMode && document.compatMode.indexOf("CSS1") >= 0) ?
                true : false;
        }
    }

    /**
     * increments and returns the Z-index counter
     */
    this.getNextZIndex = function( ) {
        return ++nextZIndex;
    }

    /**
     * Convert object name string into a valid element object reference
     *
     * @param{String} an object id or object itselft.
     */
    this.getRawObject = function(obj) {
        var theObj;
        if (typeof obj == "string") {
            if (isW3C) {
                theObj = document.getElementById(obj);
            } else if (isIE4) {
                theObj = document.all(obj);
            } else if (isNN4) {
                theObj = seekLayer(document, obj);
            }
        }
        return theObj;
    }

    /**
     * Get absolute top position of a object.
     * Recursive get the parent object and get the offset.
     *
     * @param{HTMLElement} the object to get the position.
     *
     * @return int value represent the absolute top position.
     */
    this.getObjectTop = function(obj) {
        var curtop = 0;
        if (obj.offsetParent) {
            curtop = obj.offsetTop
            while (obj = obj.offsetParent) {
                curtop += obj.offsetTop
            }
        }
        return curtop;
    }

    /**
     * Get absolute left position of a object.
     * Recursive get the parent object and get the offset.
     *
     * @param{HTMLELement} obj the object to get the position.
     *
     * @return int value represent the absolute left position.
     */
    this.getObjectLeft = function(obj) {
        var curleft = 0;
        if (obj.offsetParent) {
            curleft = obj.offsetLeft
            while (obj = obj.offsetParent) {
                curleft += obj.offsetLeft
            }
        }
        return curleft;
    }

    /**
     * Get absolute top position of a object.
     *
     * @param the object to get the position.
     *
     * @return int value represent the height of a object.
     */
    this.getObjectWidth = function(obj) {
        var ret = parseInt(obj.style.width);

        return parseInt(obj.style.width);
    }

    /**
     * Get height of a object.
     *
     * @param{HTMLElement} obj the object to get the position.
     *
     * @return int value represent the height of a object.
     */
    this.getObjectHeight = function(obj) {
        return parseInt(obj.style.height);
    }

    /**
     * Shift the object to a absolute position.
     *
     * @param{HTMLElement} obj the object to shift.
     * @param{int} targetX the target position
     * @param{int} targetY the target position
     */
    this.shiftToAbsolute = function(obj, targetX, targetY) {
        // get absolute of obj;
        var currentX = this.getObjectLeft(obj);
        var currentY = this.getObjectTop(obj);

        // calculate the offset
        var offsetX = targetX - currentX;
        var offsetY = targetY - currentY;

        this.shiftToRelative(obj, this.getObjectLeftRelative(obj) + offsetX, this.getObjectTopRelative(obj) + offsetY);
    }

    /**
     * Shift the object to a relative position.
     *
     * @param{HTMLElement} obj the object to shift.
     * @param{int} targetX the target position
     * @param{int} targetY the target position
     */
    this.shiftToRelative = function(obj, targetX, targetY) {
        obj.xPos = targetX;
        obj.yPos = targetY;
        obj.style.top = targetY + 'px';
        obj.style.left = targetX + 'px';
    }

    /**
     * Get relative left position of a object.
     *
     * @param{HTMLElement} obj the object to get the position.
     * @param int value represent the relative left position
     */
    this.getObjectLeftRelative = function(obj) {
        return parseInt(obj.style.left);
    }

    /**
     * Get relative top position of a object.
     *
     * @param{HTMLElement} obj the object to get the position.
     * @return int value represent the relative top position
     */
    this.getObjectTopRelative = function(obj) {
        return parseInt(obj.style.top);
    }
}

