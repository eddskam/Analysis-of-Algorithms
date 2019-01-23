/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.satreduce;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

import sudoku.util.Solver;
import sudoku.util.board.SudokuBoard;

/**
 * This class provides functionality for reducing sudoku problems
 * to SAT problems, solving sudoku problems as SAT problems, and
 * mapping SAT instance solutions to analogous sudoku instance
 * solutions.
 * 
 * For a better explanation of how this works, please see Teaching Problem Reduction:
 * NP-Completeness via Sudoku, by Dr. Andrea F. Lobo.
 *  
 * @author Dr. Andrea F. Lobo
 * @author Dr. Ganesh R. Baliga
 * @author Kenneth Janes
 */
public class SudokuToSATReducerSolver implements Solver
{
	static final String tempFileName = "temp.cnf";

	private final Writer writer;
	private int[][] boxIndex;
	private long variables, clauses;
	// private long clauseCount = 0;

	/* side effects of invoking function decode */
	int decodedValue;
	int decodedRow;
	int decodedCol;
	int counter = 0;

	/**
	 * Solves a given sudoku problem by reducing it to a SAT problem,
	 * solving that SAT problem, and then mapping said solution back
	 * to the solution for that sudoku problem.
	 */
	public void solve (SudokuBoard board) {
		reduce(board);
		unmap (board);
	}

	private void unmap (SudokuBoard board ) {
		ISolver solver = SolverFactory.newDefault(); 
		try {
			Reader reader = new DimacsReader(solver);
			IProblem problem = reader.parseInstance(tempFileName); 
			int [] vars = problem.findModel();
			if (vars == null) {
				System.out.println ("Soduku does not have a solution");
		}
			else {
				
				// TODO
				// Complete code here which takes the SAT solution and produces
				// the Sudoku solution.
				int counter1 = 0;
				for (int i = 0; i < vars.length; i++) {
					if (vars[i]> 0) {
						decode(board, vars[i]);
					//}
						counter1++;
						if (counter1 != board.size()) {
							System.out.print(String.format("%4s", "" + decodedValue));
						}
						else {
							System.out.println(String.format("%4s", "" + decodedValue));
							counter1 = 0;
						
						}
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a SudokuToSATReducerSolver which will perform its operations on
	 * the provided board.
	 * 
	 * @param board - the board to be solved or reducde
	 * @throws Exception
	 */
	public SudokuToSATReducerSolver() throws Exception
	{
		this(new PrintWriter(tempFileName));
	}

	private SudokuToSATReducerSolver(Writer writer) throws Exception
	{
		this.writer = writer;
	}

	/**
	 * Reduces the sudoku problem provided in the constructor to a SAT instance.
	 */
	private void reduce(SudokuBoard board)
	{
		int base = board.size() + 1;
		this.variables = base * base * base;
		
		this.clauses = calculateClauses(board);

		// boxIndex maps (box #, cell # in a box) to (board cell #)
		// Use with enumerate in atLeastOneInBox and in atMostOneInBox.
		
		boxIndex = new int[board.size()][board.size()];
		int[] tracker = new int[board.size()];
		for(int i = 0; i < Math.pow(board.size(), 2); i++)
		{
			boxIndex[board.getBox(i)][tracker[board.getBox(i)]] = i;
			tracker[board.getBox(i)]++;
		}
		
		write("p cnf " + variables + " " + clauses + "\n");
		
		for(int i = 0; i < board.size(); i++)
		{
			for(int value = 1; value <= board.size(); value++)
			{
				atLeastOneInRow(board, i, value);
				atMostOneInRow(board, i, value);
				atLeastOneInCol(board, i, value);
				atMostOneInCol(board, i, value);
				atLeastOneInBox(board, i, value);
				atMostOneInBox(board, i, value);
			}
		}
		
		System.out.println(counter + " in reduce \n");
		counter = 0;
		for(int i = 0; i < Math.pow(board.size(), 2); i++)
		{
			cellHasAtLeastOneValue(board, i);
			cellHasAtMostOneValue(board, i);
		}
		System.out.println(counter + " in reduce \n");
		counter = 0;
		cellSetClauses(board);
		System.out.println(counter + " in reduce \n");
	}
	
	// generate a clause which says a row has at least one of some value
	private void atLeastOneInRow(SudokuBoard board, int row, int value)
	{
		
		// TODO
		for (int col = 0; col < board.size(); col++) {
			write(encode(board, row, col, value) + " ");
			counter++;
		}
		endClause();
		counter++;
		
	}

	// generate a clause which says a row has at most one of some value
	private void atMostOneInRow(SudokuBoard board, int row, int value)
	{

		// TODO
		for (int i = 0; i < board.size(); i++) {
			for (int k = board.size() - 1; k > i; k--) {
				write(-(encode(board, row, i, value)) + " " + -(encode(board, row, k, value)) + " ");
				counter++;
				endClause();
			}
		}
		
	}

	// generate the clauses for cells that are already set to a value
	private void atLeastOneInCol(SudokuBoard board, int col, int value)
	{

		// TODO
		for (int row = 0; row < board.size(); row++) {
			write(encode(board, row, col, value) + " ");
			counter++;
		}
		endClause();
		counter++;
		
	}

	// generate a clause which says a column has at most one of some value
	private void atMostOneInCol(SudokuBoard board, int col, int value)
	{

		// TODO
		for (int i = 0; i < board.size(); i++) {
			for (int k = board.size() - 1; k > i; k--) {
				write(-(encode(board, i, col, value)) + " " + -(encode(board, k, col, value)) + " ");
				counter++;
				endClause();
			}
		}
		
	}

	// generate a clause which says a given box has at least one of some value
	private void atLeastOneInBox(SudokuBoard board, int box, int value)
	{

		// TODO
		for (int i = 0; i < board.size(); i++) {
			write(enumerate(board, boxIndex[box][i], value) + " ");
			counter++;
		}
		endClause();
		counter++;
	}

	// generate a clause which says a given box has at most one of some value
	private void atMostOneInBox(SudokuBoard board, int box, int value)
	{

		// TODO
		for (int i = 0; i < board.size(); i++) {
			for (int k = board.size() - 1; k > i; k--) {
				write(-(enumerate(board, boxIndex[box][i], value)) + " " + -(enumerate(board, boxIndex[box][k], value)) + " ");
				counter++;
				endClause();
			}
		}
		
	}

	// generate a clause which says a cell has at least one value
	private void cellHasAtLeastOneValue(SudokuBoard board, int cell)
	{

		// TODO
		for (int val = 1; val <= board.size(); val++) {
			write(enumerate(board, cell, val) + " ");
			counter++;
		}
		endClause();
		counter++;
		
	}

	// generate a clause which says that a cell has at most one value
	private void cellHasAtMostOneValue(SudokuBoard board, int cell)
	{

		// TODO
		for (int i = 1; i <= board.size(); i++) {
			for (int k = board.size(); k > i; k--) {
				write(-(enumerate(board, cell, i)) + " " + -(enumerate(board, cell, k)) + " ");
				counter++;
				endClause();
			}
		}
		
	}

	// generate clauses for cells that are already set
	private void cellSetClauses(SudokuBoard board)
	{

		// TODO
		for (int i=0; i < board.size() * board.size(); i++) {
			if(!((board.valueAt(i))==0)) {
				cellIsSetTo(board, i, board.valueAt(i));
			}
		}
		
	}

	// generate a clause which says a cell is set to some value
	private void cellIsSetTo(SudokuBoard board, int cell, int value)
	{
		write(enumerate(board, cell, value) + " ");
		endClause();
	}

	// enumerate a cell and value for the SAT solver format
	private int enumerate(SudokuBoard board, int cell, int value)
	{
		return encode(board, board.getRow(cell), board.getCol(cell), value);
	}

	// see enumerate function for use
	private int encode(SudokuBoard board, int row, int col, int value)
	{
		int base = board.size() + 1;
		return base * base * row + base * col + value;
	}

	// unmap some encoded value from a SAT solver to part of a solution
	private void decode (SudokuBoard board, int codedValue ) {
		int base = board.size() + 1;
		decodedValue = codedValue % base;
		codedValue /= base;
		decodedCol = codedValue % base;
		decodedRow = codedValue / base;
		if (decodedValue == 0)
			decodedValue = base - 1;

	}

	private void write(String s)
	{
		try
		{
			writer.write(s);
			writer.flush();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	// SAT problem format specifies clauses end in 0
	private void endClause()
	{
		write("0\n");
	}

	// calculate the number of clauses which will be in the solution
	private long calculateClauses(SudokuBoard board)
	{
		
		// TODO
		long count = 0;
		long count1 = 0;
		long clause1 = 0;
		count++;
		count1 = count + ((board.size() * ((board.size() - 1))) / 2);
		
		count = board.size() * count1 * board.size() * 3;
		System.out.println(count + " in caluclate clause first" + "\n");
		clause1 = (count1 * board.size() * board.size());
		System.out.println(clause1 + " in caluclate clause second" + "\n");
		count = count + clause1;
		for (int i = 0; i < board.size() * board.size(); i++) {
			if (board.valueAt(i) != 0) {

				count++;
			}
		}
		System.out.println(count);
		
		return count;   // dummy return value; replace
	}
	
	//Run times and comparisons:
		//BackTracking      GreedyBackTrack           BruteForce				SudokuToSATReduce
		//puz1: 32 ms       puz1: 35 ms               puz1: 23 ms 			puz1: 266 ms
		//puz2: 33 ms       puz2: 37 ms               puz2: 474 ms			puz2: 2077 ms
		//puz3: 42 ms       puz3: 39 ms               puz3: 2986 ms			puz3: 1900 ms
		//puz4: 36 ms	    puz4: 42 ms				 puz4: 2279 ms			puz4: 7569 ms
		//puz5: 44 ms	    puz5: 46 ms				 puz5: 5507 ms			puz0: 49 ms
	//unsolvable: 36 ms   unsolvable: 35 ms        unsolvable: 1825 ms		unsolvable: 248 ms
}
