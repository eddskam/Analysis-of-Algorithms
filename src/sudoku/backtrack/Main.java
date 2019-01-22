/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.backtrack;

import java.io.File;

import sudoku.util.Solver;
import sudoku.util.Timer;
import sudoku.util.board.RegularSudokuParser;
import sudoku.util.board.SudokuBoard;

/**
 * The driver for backtrack solving.
 * 
 * @author Kenneth S. Janes
 */
public class Main {
	/**
	 * The brute force sudoku solver driver. 
	 * 
	 * @param args - the location of the sudoku instance to be solved
	 */
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println ("Usage: java Main sudoku-puzzle");
			System.exit(0);
		}

		try
		{
			Timer timer = new Timer ( );
			timer.start();
			SudokuBoard board = new RegularSudokuParser().parse(new File(args[0]));
			System.out.println(board);
			//Solver solver = new BacktrackSolver(board);
			Solver solver = new GreedyBacktrackSolver(board);
			solver.solve(board);
			//solver2.solve(board);
			timer.stop();
			System.out.println ( "\nDuration: " + timer.getDuration() + " millisec");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
