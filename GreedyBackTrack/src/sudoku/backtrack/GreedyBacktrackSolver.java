package sudoku.backtrack;

import java.util.PriorityQueue;

//import javafx.scene.control.Cell;
import sudoku.util.Solver;
import sudoku.util.board.SudokuBoard;;

public class GreedyBacktrackSolver implements Solver{

	public PriorityQueue<Cell> nextQ;

	public GreedyBacktrackSolver(SudokuBoard board)
	{	
		nextQ = new PriorityQueue<Cell>();
	}
	@SuppressWarnings("unused")
	public boolean solve(SudokuBoard board, Cell cell)
	{
		System.out.println(cell.cellNumber);
		Cell nextCell;

		if(cell==null) {
			return true;
		}

		if(nextQ.isEmpty()) {
			nextCell = null;
		}
		else {
			nextCell = nextQ.remove();
			;		}
		for(int num=1; num<=board.size(); num++) {
			if(board.isLegal(num, cell.cellNumber)) {
				board.move(num, cell.cellNumber);
				if(board.isSolved()) {
					System.out.println(board);
					return true;
				}
				if(solve(board, nextCell)) {
					return true;

				}
				if(!(board.history().isEmpty() && !(board.isSolved()))) {
					board.unmove();
				}
			}
		}

		nextQ.add(nextCell);
		return false;

	}
	public void solve(SudokuBoard board) {
		// TODO Auto-generated method stub
		Cell start;
		for(int i = 0; i < board.size()*board.size(); i++) {
			if(board.valueAt(i) == 0) {
				Cell cell = new Cell(board, i);
				nextQ.add(cell);	

			}
		}
		if(nextQ.isEmpty()) {
			start = null;
		}
		else {
			start = nextQ.remove();
		}
		if(start==null) {
			System.out.println("No more empty cells");
			return;
		}
		if(solve(board, start)) {
			System.out.println("The game has completed");
		}
		else {
			System.out.println("There is no solution");
		}
	}

	protected class Cell implements Comparable<Cell> {
		private final SudokuBoard board;
		private final int cellNumber;

		public Cell(SudokuBoard board, int cellNumber) {
			this.board = board;
			this.cellNumber = cellNumber;
		}

		private int priority(int cell ) {
			int guessValues = 0;
			for(int i = 1; i<=board.size();i++) {
				if(board.isLegal(i, cell)) {
					guessValues++;
				}
			}
			return guessValues;
		}
		@Override
		public int compareTo(Cell o) {
			//TODO Auto-generated method stub
			return priority(this.cellNumber) - priority(o.cellNumber);
		}
	}
	//Run times and comparisons:
	//BackTracking      GreedyBackTrack           BruteForce
	//puz1: 32 ms       puz1: 35 ms               puz1: 23 ms 
	//puz2: 33 ms       puz2: 37 ms               puz2: 474 ms
	//puz3: 42 ms       puz3: 39 ms               puz3: 2986 ms
	//puz4: 36 ms	    puz4: 42 ms				 puz4: 2279 ms
	//puz5: 44 ms	    puz5: 46 ms				 puz5: 5507 ms
//unsolvable: 36 ms   unsolvable: 35 ms        unsolvable: 1825 ms
}

