
package sudoku.brute;

import java.util.Arrays;
import java.util.Stack;
import sudoku.util.Solver;
import sudoku.util.board.SudokuBoard;

/**
 * A sudoku solver which employs sudoku.brute force to solve puzzles.
 * Puzzles are solved through explicit enumeration of all possible board
 * states. For instance, it starts by plugging a 1 into each empty cell
 * and then, with each subsequent attempt, the guess is incremented as if
 * a number in base <board size>.
 */
@SuppressWarnings("unused")
public class BruteForceSolver implements Solver{
	
	public void solve(SudokuBoard board)
	{
		for(int i=0; i<(board.size()*board.size());i++) {
		if(board.valueAt(i) == 0) {
		number++;}}
		
		
		int [] answer = new int [number];
		System.out.println(number);
		for(int i =0; i < answer.length;i++) {
			answer[i] = 1;}
		
		int power = (int) Math.pow(board.size(),number)-1;
		System.out.println(power);
		int boardcells = board.size()*board.size();
		
		{
		for(int i =0; i<power && (!board.isSolved());i++) {
		int number = 0;
		while(!(board.history().isEmpty()) && (!board.isSolved())){
		board.unmove();}
			
	
		for(int j =0; j < boardcells-1;j++) {
		if(board.valueAt(j) == 0) {
		board.move(answer[number],j);
		number++;
		if(board.isSolved()) {
			break;}}}
		increment(board, answer);}
		
		
		if(board.isSolved()) {
		System.out.println(board);
		} else {
		System.out.println("Counldnt Slove");
		}}}

	
	private void increment( SudokuBoard board,int[] answer) {
		for(int i = 0; i< answer.length; i++) {
	    answer[i]++;
	    if(answer[i] == board.size()+1) {
	    answer[i] = 1;}
			else {
				break;}}}int number =0;
				}

