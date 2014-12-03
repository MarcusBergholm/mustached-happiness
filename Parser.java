import java.util.ArrayList;

public class Parser {
	private Lexer lexer;
	private ArrayList <Instruction> i;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
		i = new ArrayList <Instruction>();
	}
	
	/**
	 * Starts the parsing and returns an arraylist
	 * of instructions that can be executed by the leonardo program 
	 */
	public ArrayList<Instruction> startParse() throws SyntaxError {
		parse();
		return i;
	}
	
	/**
	 * A recursive metod that take the next token in the lexer
	 * @throws SyntaxError
	 */
	private void parse() throws SyntaxError {
		Token t = lexer.nextToken();
		i.add(instr(t));
		// If we have any more tokens call parse again.
		if(lexer.hasMoreTokens()) parse();
	}
	
	/**
	 * Take a token and check if its a instr or not.
	 * If it is a instr it runs the approbate metod for the instr.
	 */
	private Instruction instr(Token t) throws SyntaxError {
		t = removeWSNL(t); 
		Instruction i;
		switch(t.getType()) {
			case Instr1:  i = instr1(t); break;
			case Instr2: i = instr2(t); break;
			case Color: i = color(t); break;
			case Rep: i = rep(t); break;
			default: throw new SyntaxError(t.getLine());
		}
		t = removeWSNL(t);
		return i;
	}
	
	/**
	 * Creates a Instr1 instruction
	 */
	private Instruction instr1(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			temp = removeWSNL(temp);
			// Should be a number.
			if(temp.getType() != TokenType.Number) throw new SyntaxError(temp.getLine());
			int num = (int) temp.getData(); // Used to save down the num for later use
			if(num == 0) throw new SyntaxError(temp.getLine());
			temp = lexer.nextToken();
			temp = removeWSNL(temp);
			// Should be the end of the instruction therefore a "."
			if(temp.getType() != TokenType.End) throw new SyntaxError(temp.getLine());
			return new Instr1(t, num);
		} else
			throw new SyntaxError(temp.getLine());
	}
	
	/**
	 * Creates a Instr2 instruction
	 */
	private Instruction instr2(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		temp = removeWSNL(temp);
		// Should be the end of the instruction therefore a "."
		if(temp.getType() != TokenType.End) throw new SyntaxError(temp.getLine());
		return new Instr2(t);
	}
	
	/**
	 * Creates a color instruction
	 */
	private Instruction color(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			temp = removeWSNL(temp);
			// Should be a color.
			if(temp.getType() != TokenType.C) throw new SyntaxError(temp.getLine());
			String c = (String) temp.getData(); // Used to save down the color for later use
			temp = lexer.nextToken();
			temp = removeWSNL(temp);
			// Should be the end of the instruction therefore a "."
			if(temp.getType() != TokenType.End) throw new SyntaxError(temp.getLine());
			return new Color(t, c);
		} else
			throw new SyntaxError(temp.getLine());
	}
	
	/**
	 * Creates a rep instruction.
	 */
	private Instruction rep(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			temp = removeWSNL(temp);
			// Should be a number.
			if(temp.getType() != TokenType.Number) throw new SyntaxError(temp.getLine());
			int num = (int) temp.getData(); // Used to save down the num for later use
			if(num == 0) throw new SyntaxError(temp.getLine());
			temp = lexer.nextToken();
			// If we have a whitespace or newline between the num and "\""
			// remove it.
			if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
				temp = lexer.nextToken();
				temp = removeWSNL(temp);
				// Here rep can take two way either with a quote statement and followed by
				// X instructions or we can just have one instruction with not quotes.
				// So we start by looking for quotes.
				if(temp.getType() == TokenType.Quote) {
					temp = lexer.nextToken();
					ArrayList <Instruction> instr = new ArrayList <Instruction>();
					while(temp.getType() != TokenType.Quote) {		
						instr.add(instr(temp));
						temp = lexer.nextToken();
						temp = removeWSNL(temp);
					}
					return new Rep2(t, num, instr);
				} else {
					return new Rep1(t, num, instr(temp));
			    }
			} else {
			throw new SyntaxError(temp.getLine());
			}
		} else
			throw new SyntaxError(temp.getLine());
	}
	
	/**
	 * Removes all whitespaces, tabs and new lines
	 * between the input token and the next relevant token.
	 * @param t
	 * @return
	 * @throws SyntaxError
	 */
	private Token removeWSNL(Token t) throws SyntaxError {
		while(t.getType() == TokenType.NL || t.getType() == TokenType.WS)
			t = lexer.nextToken();
		return t;
	}
}
