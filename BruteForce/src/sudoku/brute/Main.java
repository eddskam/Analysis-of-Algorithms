/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.brute;

import java.io.File;

import sudoku.util.Solver;
import sudoku.util.Timer;
import sudoku.util.board.RegularSudokuParser;
import sudoku.util.board.SudokuBoard;

/**
 * The driver for the brute force solver
 * 
 * @author Kenneth S. Janes
 */
public class Main {
	/**
	 * The main function for brute force solver
	 * 
	 * @param args - the location of the puzzle to be solved
	 */
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println ("Usage: java Main sudoku-puzzle");
			System.exit(0);
		}

		try
		{
			System.out.println ("Puzzle: " + args[0]);
			Timer timer = new Timer ( );
			SudokuBoard board = new RegularSudokuParser().parse(new File(args[0]));
			System.out.println(board);
			timer.start();
			Solver solver = new BruteForceSolver();
			solver.solve(board);
			timer.stop();
			System.out.println ( "\n\nDuration: " + timer.getDuration() + " millsec");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
