import java.util.ArrayList;

/**
 * Abstract class for instructions, used to 
 * describe instructions for the leonardo program.
 * @author Bergholm
 */
abstract class Instruction {
	private Token token;
	public Instruction(Token t) {
		token = t;
	}
	
	public TokenType getInstrType() {
		return token.getType();
	}
	
	public Object getTokenData() {
		return token.getData();
	}
	
	@Override
	public abstract String toString();
}


// Used for Instr1, instruction with one parameter
class Instr1 extends Instruction {
	int number;
	public Instr1(Token t, int n) {
		super(t);
		number = n;
	}
	
	public int getNum() {
		return number;
	}
	
	@Override
	public String toString() {
		return "Instr1";
	}
}

// Used for Instr2, instruction used for Up and Down.
class Instr2 extends Instruction {
	public Instr2(Token t) {
		super(t);
	}
	
	@Override
	public String toString() {
		return "Instr2";
	}
}

// Used for Color.
class Color extends Instruction {
	String color;
	public Color(Token t, String c) {
		super(t);
		color = c;
	}
	
	public String getColor() {
		return color.toUpperCase();
	}
	
	@Override
	public String toString() {
		return "Color";
	}
}

// Used for reps with only one instruction, 
// therefor a rep with no quotes.
class Rep1 extends Instruction {
	int number;
	Instruction instruction;
	
	public Rep1(Token t, int n, Instruction i) {
		super(t);
		number = n;
		instruction = i;
	}
	public Instruction getInstr() {
		return instruction;
	}
	
	public int getNum() {
		return number;
	}
	
	@Override
	public String toString() {
		return "Rep1";
	}
}

// Used for rep with quotes.
class Rep2 extends Instruction {
	int number;
	ArrayList <Instruction> i;
	
	public Rep2(Token t, int n, ArrayList <Instruction> i) {
		super(t);
		number = n;
		this.i = i;
	}
	
	public ArrayList<Instruction> getInstrs() {
		return i;
	}
	
	public int getNum() {
		return number;
	}
	
	@Override
	public String toString() {
		return "Rep2";
	}
}

