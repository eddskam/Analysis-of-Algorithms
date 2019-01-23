/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util;

import sudoku.util.board.SudokuBoard;

/**
 * All sudoku solvers shall implement this interface.
 * 
 * @author Kenneth S. Janes
 */
public interface Solver {
	/**
	 * Solve a sudoku board
	 * 
	 * @param board - the board to be solved
	 */
	public abstract void solve(SudokuBoard board);
}
