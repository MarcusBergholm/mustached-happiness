import java.util.ArrayList;

public class Parser {
	private Lexer lexer;
	private ArrayList <Instruction> i;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
		i = new ArrayList <Instruction>();
	}
	
	public ArrayList <Instruction> parse() throws SyntaxError {
		instr();
		return i;
	}
	
	private void instr() throws SyntaxError {
		Token t = lexer.nextToken();
		while(t.getType() == TokenType.NL || t.getType() == TokenType.WS)
			t = lexer.nextToken();
		
		switch(t.getType()) {
			case Instr1: i.add(instr1(t)); break;
			case Instr2: i.add(instr2(t)); break;
			case Color: i.add(color(t)); break;
			case Rep: i.add(rep(t)); break;
			default: throw new SyntaxError(t.getLine());
		}
		
		while(t.getType() == TokenType.NL || t.getType() == TokenType.WS)
			t = lexer.nextToken();
		
		// If we have any more tokens call parse again.
		if(lexer.hasMoreTokens())
			instr();
		
		return;
	}
	
	private Instruction instr1(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
				temp = lexer.nextToken();
			// Should be a number.
			if(temp.getType() != TokenType.Number)
				throw new SyntaxError(temp.getLine());
			int num = (int) temp.getData(); // Used to save down the num for later use
			if(num == 0)
				throw new SyntaxError(temp.getLine());
			temp = lexer.nextToken();
			// If we have a whitespace or newline between the num and "."
			// remove it.
			while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
				temp = lexer.nextToken();
			// Should be the end of the instruction therefore a "."
			if(temp.getType() != TokenType.End)
				throw new SyntaxError(temp.getLine());
			return new Instr1(t, num);
		
		} else
			throw new SyntaxError(temp.getLine());
	}
	
	private Instruction instr2(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		// If we have a whitespace or newline between the num and "."
		// remove it.
		while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
			temp = lexer.nextToken();
		// Should be the end of the instruction therefore a "."
		if(temp.getType() != TokenType.End)
			throw new SyntaxError(temp.getLine());
		return new Instr2(t);
	}
	
	private Instruction color(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
				temp = lexer.nextToken();
			// Should be a color.
			if(temp.getType() != TokenType.C)
				throw new SyntaxError(temp.getLine());
			String c = (String) temp.getData(); // Used to save down the color for later use
			temp = lexer.nextToken();
			// If we have a whitespace or newline between the color and "."
			// remove it.
			while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
				temp = lexer.nextToken();
			// Should be the end of the instruction therefore a "."
			if(temp.getType() != TokenType.End)
				throw new SyntaxError(temp.getLine());
			return new Color(t, c);
		} else
			throw new SyntaxError(temp.getLine());
	}
	private Instruction rep(Token t) throws SyntaxError {
		Token temp = lexer.nextToken();
		if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
			while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
				temp = lexer.nextToken();
			// Should be a number.
			if(temp.getType() != TokenType.Number)
				throw new SyntaxError(temp.getLine());
			int num = (int) temp.getData(); // Used to save down the num for later use
			if(num == 0)
				throw new SyntaxError(temp.getLine());
			temp = lexer.nextToken();
			// If we have a whitespace or newline between the num and "\""
			// remove it.
			if(temp.getType() == TokenType.WS || temp.getType() == TokenType.NL) {
				temp = lexer.nextToken();
				while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
					temp = lexer.nextToken();
				// Here rep can take two way either with a quote statement and followed by
				// X instructions or we can just have one instruction with not quotes.
				// So we start by looking for quotes.
				if(temp.getType() == TokenType.Quote) {
					temp = lexer.nextToken();

					ArrayList <Instruction> instr = new ArrayList <Instruction>();
					while(temp.getType() != TokenType.Quote) {		
						while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
							temp = lexer.nextToken();
						switch(temp.getType()) {
						case Instr1: instr.add(instr1(temp)); break;
						case Instr2: instr.add(instr2(temp)); break;
						case Color: instr.add(color(temp)); break;
						case Rep: instr.add(rep(temp)); break;
						default: throw new SyntaxError(temp.getLine());
						}
						temp = lexer.peekToken();
						if(temp.getType() == TokenType.Quote) {
							lexer.nextToken();
							break;
						}
						while(temp.getType() == TokenType.NL || temp.getType() == TokenType.WS)
							temp = lexer.nextToken();
					}
					
					return new Rep2(t, num, instr);
				} else {
					Instruction instr;
					switch(temp.getType()) {
					case Instr1: instr = instr1(temp); break;
					case Instr2: instr = instr2(temp); break;
					case Color: instr = color(temp); break;
					case Rep: instr = rep(temp); break;
					default: throw new SyntaxError(temp.getLine());
					}
					return new Rep1(t, num, instr);
			    }
			} else {
			throw new SyntaxError(temp.getLine());
			}
		} else
			throw new SyntaxError(temp.getLine());
	}
}
