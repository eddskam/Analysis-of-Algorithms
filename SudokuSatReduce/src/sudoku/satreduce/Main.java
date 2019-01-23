/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.satreduce;

import java.io.File;

import sudoku.util.Timer;
import sudoku.util.board.RegularSudokuParser;
import sudoku.util.board.SudokuBoard;

/**
 * Driver for the SAT reducer solver
 * 
 * @author Kenneth S. Janes
 */
public class Main {
			
	/**
	 * Solves a board by converting it to a SAT problem, using a well-established
	 * SAT solver to solve the instance, and then mapping that solution back to the
	 * sudoku solution.
	 * 
	 * @param args - the location of the instance to be solved
	 * @throws Exception - not enough arguments or board is not found
	 */
	public static void main(String[] args) throws Exception
	{
		
		if (args.length < 1) {
			System.err.println ("Usage: java Main sudoku-puzzle");
			System.exit(0);
		}

		Timer timer = new Timer ( );
		SudokuBoard board = new RegularSudokuParser().parse(new File(args[0]));
		System.out.println(board);
		SudokuToSATReducerSolver reduceSolver = new SudokuToSATReducerSolver();
		timer.start();
		reduceSolver.solve(board);
		timer.stop();
		System.out.println ( "\n\nDuration: " + timer.getDuration() + " millsec");
	}
}
