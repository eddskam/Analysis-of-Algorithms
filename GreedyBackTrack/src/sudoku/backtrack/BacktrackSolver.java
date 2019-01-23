/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.backtrack;


import sudoku.util.Solver;
import sudoku.util.board.SudokuBoard;

/**
 * A sudoku solver which employs plain backtracking to finish a puzzle.
 */
public class BacktrackSolver implements Solver {
	
	
	public BacktrackSolver(SudokuBoard board)
	{
		
	}
	
	public boolean solve(SudokuBoard board, int cell) {
		int boardCells = board.size() * board.size();
		if(cell == boardCells) {
			return true;
		}
		int nextCell = boardCells;
		for (int i = 0; i < boardCells; i++) {
			if (board.valueAt(i) == 0) {
				nextCell = i;
				cell = nextCell;
				break;
			}
		}
		for (int num = 1; num <= board.size(); num++) {
			if (board.isLegal(num, cell)) {
				board.move(num, cell);
				if (board.isSolved()) {
					System.out.println(board);
					return true;
				}
				if (solve(board, nextCell)) {
					return true;
				}
				if (!(board.history().isEmpty() && !(board.isSolved()))) {
					board.unmove();
				}
			}
		}
		return false;
	}
	
	@Override
	public void solve(SudokuBoard board)
	{
		int boardCells = board.size() * board.size();
		int start = boardCells + 1;
		for (int i = 0; i < boardCells; i++) {
			if (board.valueAt(i) == 0) {
				start = i;
				break;
			}
		}
		if (start == (boardCells + 1)) {
			System.out.println("All cells filled");
			return;
		}
		if (solve(board, start)) {
			System.out.println(board);
		}
		else {
			System.out.println("No possible solution");
		}

	}
	//Run times and comparisons:
	//BackTracking                               BruteForce
	//puz1: 32 ms                                //puz1: 23 ms 
	//puz2: 33 ms                                //puz2: 474 ms
	//puz3: 42 ms                                //puz3: 2986 ms
	//puz4: 36 ms								//puz4: 2279 ms
	//puz5: 44 ms								//puz5: 5507 ms
	//unsolvable: 36 ms (no possible solution)   //unsolvable: 1825 ms (Couldn't solve)
	
}