/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

import java.io.File;
import java.util.Scanner;

/**
 * A simple parser.
 * 
 * @author Kenneth S. Janes
 */
public class RegularSudokuParser
{
	/**
	 * Parses a sudoku file. See the sudoku puzzle directory for specifications
	 * 
	 * @param puzzleFile - the puzzle to be parsed
	 * @return - the sudoku puzzle the file represented
	 * @throws Exception - the file was not found
	 */
	public RegularSudokuBoard parse(File puzzleFile) throws Exception
	{
		RegularSudokuBoard board;
		int width, height;
		
		Scanner sc = new Scanner(puzzleFile);
		
		// Ignore comment lines
		while (sc.findInLine("c:") != null )
			sc.nextLine();


		// Read width and height
		width = sc.nextInt();
		height = sc.nextInt();
		
		// Read cell values and fill board
		board = new RegularSudokuBoard(width, height);
		for(int cell = 0; sc.hasNextInt(); cell++)
		{
			board.setCell(sc.nextInt(), cell);
		}
		return board;
	}
}
