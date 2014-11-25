@SuppressWarnings("serial")
public class SyntaxError extends Exception {
	int line;
	public SyntaxError(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}
}