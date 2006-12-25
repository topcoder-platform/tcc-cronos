import java.util.HashMap;

import com.orpheus.game.GameDataManager;
import com.orpheus.game.MockGameDataManager;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.MockGameData;
import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.util.puzzle.SolutionTester;

public class TestPuzzleSolution {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		GameData gameData = new MockGameData();
		
		Long puzzledId = gameData.getSlot(1).getPuzzleId();
        SolutionTester tester = new MockSolutionTester();

        tester.testSolution(new HashMap());
        
            gameData.recordGameCompletion(1, 2);
            GameDataManager gdMgr = new MockGameDataManager();
            gdMgr.advanceHostingSlot(3);


	}

}
