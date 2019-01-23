/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

import java.util.Stack;


/**
 * A sudoku board whose regions are boxes each with identical width and height
 * 
 * @author Kenneth S. Janes
 */
public class RegularSudokuBoard implements SudokuBoard
{	
	private final int boxWidth, boxHeight;
	private final int boardSize;
	private final int[] boardCells;
	private final Stack<Move> history;
	
	// if there are two sevens in row 0, let's say,
	// the sixth cell in row 0 array contains the number 2
	// we don't keep track of the uses of the empty cell
	private int[][] rowUseCount;
	private int[][] colUseCount;
	private int[][] boxUseCount;
	
	/**
	 * @param width - the width of a region
	 * @param height - the height of a region
	 */
	public RegularSudokuBoard(int width, int height)
	{
		boxWidth = width;
		boxHeight = height;
		boardSize = boxWidth * boxHeight;
		boardCells = new int[boardSize * boardSize];
		history = new Stack<Move>();
		
		// use these to determine correctness quickly
		rowUseCount = new int[boardSize][boardSize];
		colUseCount = new int[boardSize][boardSize];
		boxUseCount = new int[boardSize][boardSize];
	}
	
	@Override
	public void move(int num, int cell)
	{
		Move move = new Move(num, cell);
		move(move);
	}
	
	@Override
	public void move(Move move)
	{
		setCell(move.getNum(), move.getCell());
		history.push(move);
	}
	
	@Override
	public void unmove()
	{
		Move move = history.pop();
		resetCell(move.getCell());
	}
	
	@Override
	public void setCell(int num, int cell)
	{
		boardCells[cell] = num;
		if(num != 0) // we don't care about empty cells
		{
			rowUseCount[getRow(cell)][num - 1]++;
			colUseCount[getCol(cell)][num - 1]++;
			boxUseCount[getBox(cell)][num - 1]++;
		}
	}
	
	@Override
	public void resetCell(int cell)
	{
		// decrement all of the usage counts
		int tmp = boardCells[cell] - 1;
		rowUseCount[getRow(cell)][tmp]--;
		colUseCount[getCol(cell)][tmp]--;
		boxUseCount[getBox(cell)][tmp]--;
		// reset the cell's value to 0
		boardCells[cell] = 0;
	}
	
	@Override
	public boolean isLegal(int num, int cell)
	{
		return boardCells[cell] == 0 && // is cell empty?
				rowUseCount[getRow(cell)][num - 1] == 0 && // is num not in row?
				colUseCount[getCol(cell)][num - 1] == 0 && // is num not in col?
				boxUseCount[getBox(cell)][num - 1] == 0; // is num not in box?
	}
	
	@Override
	public boolean isLegal(Move move)
	{
		int num = move.getNum();
		int cel = move.getCell();
		return isLegal(num, cel);
	}
	
	@Override
	public Stack<Move> history()
	{
		return history; // THIS IS NOT SAFE IMPLEMENTATION
	}
	
	@Override
	public int size()
	{
		return boardSize;
	}
	
	@Override
	public boolean isSolved()
	{
		for(int i = 0; i < boardSize; i++)
		{
			if(!isGoodRow(i) || !isGoodCol(i) || !isGoodBox(i))
				return false;
		}
		return true;
	}
	
	private boolean isGoodRow(int row)
	{
		for(int i = 0; i < boardSize; i++)
		{
			if(rowUseCount[row][i] != 1)
				return false;
		}
		return true;
	}
	
	private boolean isGoodCol(int col)
	{
		for(int i = 0; i < boardSize; i++)
		{
			if(colUseCount[col][i] != 1)
				return false;
		}
		return true;
	}
	
	private boolean isGoodBox(int box)
	{
		for(int i = 0; i < boardSize; i++)
		{
			if(boxUseCount[box][i] != 1)
				return false;
		}
		return true;
	}
	
	@Override
	public int valueAt(int cell)
	{
		return boardCells[cell];
	}
	
	@Override
	public int getRow(int cell)
	{
		return cell / boardSize;
	}
	
	@Override
	public int getCol(int cell)
	{
		return cell % boardSize;
	}
	
	@Override
	public int getBox(int cell)
	{
		int c = getRow(cell) / boxHeight;
		int r = getCol(cell) / boxWidth;
		return r + c * boxHeight;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("");
		builder.append("Board size: "+boardSize+ " X " + boardSize +"\n\n");
		for(int i = 0; i < Math.pow(boardSize, 2); i++)
		{
			// builder.append(boardCells[i] == 0 ? "_" : Integer.toString(boardCells[i], boardSize + 1));
			builder.append(boardCells[i] == 0 ? String.format("%3s", "_") : String.format("%3s", ""+boardCells[i]));
			builder.append(" ");
			if(i % boardSize == boardSize - 1)
				builder.append("\n");
		}
		return builder.toString();
	}
}