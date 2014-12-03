/**
 * Used to throw syntax error on the row 
 * where the syntax error accord.
 * @author Bergholm
 *
 */
@SuppressWarnings("serial")
public class SyntaxError extends Exception {
	int line;
	public SyntaxError(int line) {
		this.line = line;
	}
	
	// Return the row of the error.
	public int getLine() {
		return line;
	}
}