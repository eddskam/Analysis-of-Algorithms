/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

import java.util.Stack;


/**
 * A sudoku board whose regions are boxes each with identical width and height
 */
public class RegularSudokuBoard implements SudokuBoard
{
	private final int boardSize;
	private final int[] boardCells;
	public int boxWidth = 0;
	public int boxHeight = 0;
	
	Stack<Move> history;
	public int [][] rowUseCount;
	public int [][] colUseCount;
	public int [][] boxUseCount;
	public int initValues = 0;
	

	/**
	 * @param width - the width of a region
	 * @param height - the height of a region
	 */
	public RegularSudokuBoard(int width, int height)
	{
		this.boxWidth = width;
		this.boxHeight = height;
		boardSize = width * height;
		boardCells = new int[boardSize * boardSize];
		rowUseCount = new int [boardSize][boardSize];
		colUseCount = new int [boardSize][boardSize];
		boxUseCount = new int [boardSize][boardSize];
		history = new Stack<Move>();
		
	}
	
	
	public void move(Move move)
	{
	
	}
	
	public void move(int num, int cell)
	{
		
		if(initValues == 0) {
			for(int i = 0; i< boardCells.length; i++) {
				if (boardCells[i] != 0) {
					boxUseCount[this.getBox(i)][boardCells[i]-1]++;
					rowUseCount[this.getRow(i)][boardCells[i]-1]++;
					colUseCount[this.getCol(i)][boardCells[i]-1]++;								
				}
			}
			initValues++;
		}
		if(this.isLegal(num, cell)) {
			this.setCell(num, cell);
			rowUseCount[this.getRow(cell)][num-1]++;
			colUseCount[this.getCol(cell)][num-1]++;
			boxUseCount[this.getBox(cell)][num-1]++;
			Move move = new Move(num, cell);
			history.add(move);
		}
		
	}
	
	public void unmove()
	{
		Move move = history.pop();
		int lastCell = move.getCell();
		this.resetCell(lastCell);
		
	}
	
	public void setCell(int num, int cell)
	{
		boardCells[cell] = num;
		
	}
	
	public void resetCell(int cell)
	{
		int num = boardCells[cell];
		boardCells[cell] = 0;
		rowUseCount[this.getRow(cell)][num-1]--;
		colUseCount[this.getCol(cell)][num-1]--;
		boxUseCount[this.getBox(cell)][num-1]--;
		
	}
	
	public boolean isLegal(int num, int cell)
	{
		if((rowUseCount[this.getRow(cell)][num-1] == 0) && (colUseCount[this.getCol(cell)][num-1] == 0) 
				&&(boxUseCount[this.getBox(cell)][num-1] == 0)) {
				return true;
				}
			
				return false;
		
	}
	
	public boolean isLegal(Move move)
	{
		return false;
	}
	
	public Stack<Move> history()
	{
		//return null;
		return history;
	}
	
	public int size()
	{
		//return 0;
		return boardSize;
	}
	
	public boolean isSolved()
	{
		for (int i = 0; i < rowUseCount.length; i++) {
			for (int j = 0; j < rowUseCount.length; j++) {
				if (rowUseCount[i][j] != 1) {
					return false;
				}
			}
		}
		for (int i = 0; i < colUseCount.length; i++) {
			for (int j = 0; j < colUseCount.length; j++) {
				if (colUseCount[i][j] != 1) {
					return false;
				}
			}
		}
		for (int i = 0; i < boxUseCount.length; i++) {
			for (int j = 0; j < boxUseCount.length; j++) {
				if (boxUseCount[i][j] != 1) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public int valueAt(int cell)
	{
		//return 0;
		return boardCells[cell];
	}
	
	public int getRow(int cell)
	{
		return cell/boardSize;
	}
	
	public int getCol(int cell)
	{
		//return 0;
		return cell % boardSize;
	}
	
	public int getBox(int cell)
	{
		
		int boxRow = this.getRow(cell) / boxHeight;
		
		int boxColumn = this.getCol(cell) / boxWidth;
		
		int box = boxRow * boxHeight + boxColumn;
		
		
		return box;
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("");
		builder.append("Board size: "+boardSize+ " X " + boardSize +"\n\n");
		for(int i = 0; i < Math.pow(boardSize, 2); i++)
		{
			//builder.append(boardCells[i] == 0 ? "_" : Integer.toString(boardCells[i], boardSize + 1));
			builder.append(boardCells[i] == 0 ? String.format("%3s", "_") : String.format("%3s", ""+boardCells[i]));
			builder.append(" ");
			if(i%boardSize == boardSize - 1)
				builder.append("\n");
		}
		return builder.toString();
	}
}