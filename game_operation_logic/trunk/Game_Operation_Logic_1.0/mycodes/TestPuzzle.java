import java.io.ByteArrayOutputStream;

import com.orpheus.game.persistence.MockGameData;
import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.util.puzzle.SolutionTester;

public class TestPuzzle {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String mediaType = "mt";
		PuzzleData puzzleData = new MockGameData().getPuzzle(1);
		 String typeName = puzzleData.getAttribute(PuzzleData.PUZZLE_TYPE_ATTRIBUTE);
         PuzzleTypeSource puzzleTypeSource = new MockPuzzleTypeSource();
         
         PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(typeName);
         PuzzleRenderer puzzleRenderer = puzzleType.createRenderer(mediaType);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         SolutionTester solutionTester = puzzleRenderer.renderPuzzle(puzzleData, baos);
	}

}
