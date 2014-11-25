import java.io.IOException;
import java.util.ArrayList;


public class Main {
	public static void main(String args[]) throws IOException {
		try {
			Lexer lex = null;
			lex = new Lexer(System.in);
			
			if(!(lex.isEmpty())){
				Parser parser = new Parser(lex);
				ArrayList<Instruction> result = null;
				result = parser.parse();
				Leonardo l = new Leonardo();
				l.run(result);
			} else
				System.out.println();
		} catch (SyntaxError e) {
			System.out.println("Syntaxfel på rad " + e.getLine());
			e.printStackTrace();
			return;
		}
		
	}
}