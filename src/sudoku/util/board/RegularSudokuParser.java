/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

import java.io.File;
import java.util.Scanner;

/**
 * A simple parser.
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
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(puzzleFile);
		int cell = 0;
		int width = sc.nextInt();
		System.out.println(width + " boardWidth ");
		int height = sc.nextInt();
		System.out.println(height + " boardHeight");
		
		RegularSudokuBoard board = new RegularSudokuBoard(width, height);
		System.out.println(board.toString());
		while (sc.hasNextInt()) {
			board.setCell(sc.nextInt(), cell);
			cell++;
		}
			System.out.println(board.toString());
			return board;			
			}

}
