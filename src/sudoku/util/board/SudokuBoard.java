/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

import java.util.Stack;

/**
 * All sudoku boards shall implement this interface.
 * 
 * @author Kenneth S. Janes
 */
public interface SudokuBoard {
	/**
	 * Perform the move specified.
	 * 
	 * @param move
	 */
	public void move(Move move);
	
	/**
	 * Perform the move specified. This method is for convenience's sake.
	 * 
	 * @param num - the number to be placed in the cell
	 * @param cell - the cell upon which this move acts
	 */
	public void move(int num, int cell);
	
	/**
	 * Undoes the last move done unto this board
	 */
	public void unmove();
	
	/**
	 * Sets the value of some cell
	 * 
	 * @param num - the number to be written to the cell
	 * @param cell - the cell to write to
	 */
	public void setCell(int num, int cell);
	
	/**
	 * Resets the cell to the empty cell
	 * @param cell - the cell to reset
	 */
	public void resetCell(int cell);
	
	/**
	 * Verifies that a move is a move which would not subsequently
	 * put the board into an illegal configuration
	 * 
	 * @param cell - the move to test
	 * @return - true only if the move does not produce duplicates in any row,
	 * column, or box
	 */
	public boolean isLegal(int cell);
	
	/**
	 * Verifies that a move is a move which would not subsequently
	 * put the board into an illegal configuration
	 * 
	 * @param num - the number to place into the cell
	 * @param cell - the cell to write to
	 * @return - true only if the move does not produce duplicates in any row,
	 * column, or box
	 */
	public boolean isLegal(int num, int cell);
	
	/**
	 * Tells if the board is solved
	 * 
	 * @return - true only if the board is solved according to the rules of sudoku
	 */
	public boolean isSolved();
	
	/**
	 * Get the row of some cell
	 * 
	 * @param cell - the cell whose row we must find
	 * @return - the row of the cell, starting from 0 on the far left
	 */
	public int getRow(int cell);
	
	/**
	 * Get the column of some cell
	 * 
	 * @param cell - the cell whose column we must find
	 * @return - the column of the cell, starting from 0 on the top
	 */
	public int getCol(int cell);
	
	/**
	 * Get the box of some cell
	 * 
	 * @param cell - the cell whose box to find
	 * @return - the box of the cell, starting from 0 in the upper left
	 * and increasing in order going to the right and then down at the end
	 * of a row
	 */
	public int getBox(int cell);
	
	/**
	 * Get the size of the board
	 * 
	 * @return - box width * box height
	 */
	public int size();
	
	/**
	 * Retrieves the value stored in a cell
	 * 
	 * @param cell - the cell whose value is to be seen
	 * @return - the value contained in that cell
	 */
	public int valueAt(int cell);
	
	/**
	 * Retrieves the move history for this board
	 * 
	 * @return - the move history for this board
	 */
	public Stack<Move> history();
}
