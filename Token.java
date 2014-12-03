/**
 * Class to represent tokens
 * @author Bergholm
 *
 */
public class Token {
	private TokenType type;
	private Object data;
	private int line;

	public Token(TokenType type, Object data, int line) {
		this.type = type;
		this.data = data;
		this.line = line;
	}

	public TokenType getType() { return type; }
	public Object getData() { return data; }
	public int getLine() { return line; }
}

enum TokenType {
	Number, C, Quote, End, Instr1, Instr2, Rep, Color, INVALID, WS, Comment, NL
}
