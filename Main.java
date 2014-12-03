import java.io.IOException;
import java.util.ArrayList;


public class Main {
	public static void main(String args[]) throws IOException {
		try {
			Lexer lex = new Lexer(System.in);
			if(!(lex.isEmpty())){
				Parser parser = new Parser(lex);
				ArrayList<Instruction> result = parser.startParse();
				new Leonardo(result);
			}
		} catch (SyntaxError e) {
			System.out.println("Syntaxfel på rad " + e.getLine());
			return;
		}
		
	}
}