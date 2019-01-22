/**
 *
 * This material is based upon work supported by 
 * the National Science Foundation under Grant No. 1140753.
 * 
 */

package sudoku.util;

/**
 * Simple class that can be used to time a computation
 *
 * @author Dr. Andrea F. Lobo
 * @author Dr. Ganesh R. Baliga
 * @author Kenneth Janes
 * 
 */

public class Timer {

	long start, end;

	/**
	 * Called just before the computation starts
	 */
	public void start ( ) {
		start = System.currentTimeMillis();

	}

	/**
	 * Called just after the computation ends
	 */
	public void stop ( ) {
		end = System.currentTimeMillis ( );

	}

	/**
	 * @return the total duration of the computation in milliseconds
	 */
	public long getDuration ( ) {
		return end - start;
	}
}
