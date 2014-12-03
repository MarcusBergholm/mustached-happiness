import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class for "Lexikalanalys". Basically creates a list of tokens based on 
 * an input stream.
 * @author Bergholm
 *
 */
public class Lexer {
	private ArrayList<Token> tokens;
	private int currentToken;
	
	/**
	 * Reads the input stream and builds a string of it.
	 */
	private static String readInput(InputStream f) throws java.io.IOException {
		Reader stdin = new InputStreamReader(f);
		StringBuilder buf = new StringBuilder();
		char input[] = new char[1024];
		int read = 0;
		while ((read = stdin.read(input)) != -1) {
			buf.append(input, 0, read);
		}
		return buf.toString();
	}

	/**
	 * Create the lexer and do convert the string into tokens.
	 */
	public Lexer(InputStream in) throws java.io.IOException, SyntaxError {
	    String input = Lexer.readInput(in);
		int line = 1; 
		
		// Regex for valid cmds
		String forw = "[Ff][Oo][Rr][Ww]";
		String back = "[Bb][Aa][Cc][Kk]";
		String left = "[Ll][Ee][Ff][Tt]";
		String right = "[Rr][Ii][Gg][Hh][Tt]";
		String color = "[Cc][Oo][Ll][Oo][Rr]";
		String up = "[Uu][Pp]";
		String down = "[Dd][Oo][Ww][Nn]";
		String rep = "[Rr][Ee][Pp]";
		String num = "[0-9]+";
		String colortag = "[#][A-Fa-f0-9]{6}";
		String quote = "\"";
		String dot = "\\.";
		String comment = "[%].*";
		
		Pattern tokenPattern = Pattern.compile("\n|\t| |"+ forw +"|"+ back +"|"+ right +"|"+ left +"|"
				+ up +"|"+ down +"|"+ color +"|"+ rep +"|"+ num +"|"
				+ colortag +"|"+ quote +"|"+ dot +"|"+ comment);
		Matcher m = tokenPattern.matcher(input);
		int inputPos = 0;
		tokens = new ArrayList<Token>();
		currentToken = 0;
		
		while (m.find()) {
		// Token don't match regex, set IVALID token.
		if (m.start() != inputPos)
			tokens.add(new Token(TokenType.INVALID, "", line));
		if (m.group().matches("\n")) {
			tokens.add(new Token(TokenType.NL, "", line));
			line ++;
		}
		else if (m.group().matches(forw))
			tokens.add(new Token(TokenType.Instr1, "Forw", line));
		else if (m.group().matches(back))
			tokens.add(new Token(TokenType.Instr1, "Back", line));
		else if (m.group().matches(left))
			tokens.add(new Token(TokenType.Instr1, "Left", line));
		else if (m.group().matches(right))
			tokens.add(new Token(TokenType.Instr1, "Right", line));
		else if (m.group().matches(up))
			tokens.add(new Token(TokenType.Instr2, "Up", line));	
		else if (m.group().matches(down))
			tokens.add(new Token(TokenType.Instr2, "Down", line));	
		else if (m.group().matches(color))
			tokens.add(new Token(TokenType.Color, "Color", line));	
		else if (m.group().matches(rep))
			tokens.add(new Token(TokenType.Rep, "Rep", line));	
		else if (Character.isDigit(m.group().charAt(0)))
			tokens.add(new Token(TokenType.Number, Integer.parseInt(m.group()), line));
		else if (m.group().matches(colortag))
			tokens.add(new Token(TokenType.C, m.group(), line));
		else if (m.group().matches(quote))
			tokens.add(new Token(TokenType.Quote, "\"", line));
		else if (m.group().matches(dot))
			tokens.add(new Token(TokenType.End, ".", line));
		else if (m.group().matches("\t"))
			tokens.add(new Token(TokenType.WS, "", line));
		else if (m.group().matches(" "))
			tokens.add(new Token(TokenType.WS, "", line));
		else if (m.group().matches(comment)) {}
		inputPos = m.end();
		}
		
		// Check if there was any data left that not was a token.
		// Then set it as INVALID token.
		if (inputPos != input.length())
			tokens.add(new Token(TokenType.INVALID, "", line));
		// Removes unnecessary WS and NL tokens at end of list. 
		if(!(tokens.isEmpty())) {
			Token t = tokens.get(tokens.size()-1);
			while(t.getType() == TokenType.NL || t.getType() == TokenType.WS) {
				tokens.remove(tokens.size()-1);
				if(tokens.isEmpty()) break;
				t = tokens.get(tokens.size()-1);
			}
		}
	}

	/**
	 * Peek at the current token in tokens.
	 */
	public Token peekToken() throws SyntaxError {
		if (!hasMoreTokens()) 
			throw new SyntaxError(tokens.get((currentToken-1)).getLine());
		return tokens.get(currentToken);
	}

	/**
	 * Eats and returns the current token in tokens.
	 * Also jumps to the next token in tokens.
	 */
	public Token nextToken() throws SyntaxError {
		Token res = peekToken();
		++currentToken;
		return res;
	}

	/**
	 * Check if there is any more tokens in tokens.
	 */
	public boolean hasMoreTokens() {
		return currentToken < tokens.size();
	}
	
	/**
	 * Check if the tokens list is empty.
	 * @return true if tokens is empty.
	 */
	public boolean isEmpty() {
		return tokens.isEmpty();
	}
}