/**
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 */

package sudoku.util.board;

/**
 * This is a move on a sudoku board.
 * 
 * @author Kenneth S. Janes
 */
public class Move
{
	private final int num, cell;
	
	/**
	 * @param num - the number to be filled into a cell
	 * @param cell - the cell on the board to fill
	 */
	public Move(int num, int cell)
	{
		this.num = num;
		this.cell = cell;
	}
	
	/**
	 * Get the value of this move
	 * 
	 * @return - the value to be filled into the cell
	 */
	public int getNum()
	{
		return num;
	}
	
	/**
	 * Get the cell of this move
	 * 
	 * @return - the cell this move is acting on
	 */
	public int getCell()
	{
		return cell;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Move))
			return false;
		Move m = (Move)o;
		return m.getCell() == getCell() && m.getNum() == getNum();
	}
}
