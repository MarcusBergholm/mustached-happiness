import java.util.ArrayList;

/**
 * This class run leonardo a program from an ArrayList
 * of Instructions.
 * 
 * @author Bergholm
 */
public class Leonardo {
	private double xpos;		// X-position
	private double ypos;		// Y-position
	private int v;				// The angle for leonardo
	private boolean penDown;	// True is leonardos pen is down
	private String color;		// The color leonardo paints
	
	/**
	 * Creates a new leonadro and runs the program.
	 * @param input An ArrayList with instructions
	 */
	public Leonardo(ArrayList<Instruction> input) {
		xpos = 0.0000;
		ypos = 0.0000;
		v = 0;
		color = "#0000FF";
		penDown = false;
		
		for(Instruction i : input) {
			run(i);
		}
	}
	
	/**
	 * Runs the program until the array is empty 
	 */
	public void run(Instruction i) {
		switch(i.toString()) {
			case "Instr1": instr1(i); break;
			case "Instr2": instr2(i); break;
			case "Color": color(i); break;
			case "Rep1": rep1(i); break;
			case "Rep2": rep2(i); break;
			default: break;
		}
	}
	
	/**
	 * Runs the rep command with the quotes.
	 */
	private void rep2(Instruction input) {
		int rep = ((Rep2) input).getNum();
		ArrayList <Instruction> instrs = ((Rep2) input).getInstrs();
		for(int i = 0; i < rep; i++) {
			for(Instruction in : instrs)
				run(in);
		}
	}
	
	/**
	 * Runs a rep instruction with only one instruction. 
	 * Therefore a rep with no "\""
	 */
	private void rep1(Instruction input) {
		int rep = ((Rep1) input).getNum();
		Instruction instr = ((Rep1)input).getInstr();
		for(int i = 0 ; i < rep; i++)
			run(instr);
	}
	
	/**
	 * Changes the color.
	 */
	private void color(Instruction input) {
		color = ((Color) input).getColor();
	}
	
	/**
	 * Takes the pen up or down.
	 */
	private void instr2(Instruction input) {
		if(input.getTokenData().equals("Down"))
			penDown = true;
		else if(input.getTokenData().equals("Up"))
			penDown = false;
	}
	
	/**
	 * Changes Leonardos position or the way he is faceing.
	 */
	private void instr1(Instruction input) {
		if(input.getTokenData().equals("Left")) v = v + ((Instr1) input).getNum();
		else if(input.getTokenData().equals("Right")) v = v - ((Instr1) input).getNum();
		else if(input.getTokenData().equals("Forw")) {
			double xtemp = xpos;
			double ytemp = ypos;
			xpos = xpos + (((Instr1) input).getNum()) * Math.cos(Math.toRadians(v));		
	        ypos = ypos + (((Instr1) input).getNum()) * Math.sin(Math.toRadians(v));
	        
	        if(Math.abs(xpos) < 0.0001 ) xpos = 0.0000;
	        if(Math.abs(ypos) < 0.0001 ) ypos = 0.0000;
	        if (penDown) {
		    	System.out.format("%s %.4f %.4f %.4f %.4f%n", color, xtemp, ytemp, xpos, ypos);
	        }
		}	
		else if(input.getTokenData().equals("Back")) {
			double xtemp = xpos;
			double ytemp = ypos;
			xpos = xpos - (((Instr1) input).getNum()) * Math.cos(Math.toRadians(v));		
	        ypos = ypos - (((Instr1) input).getNum()) * Math.sin(Math.toRadians(v));
	        
	        if(Math.abs(xpos) < 0.0001 ) xpos = 0.0000;
	        if(Math.abs(ypos) < 0.0001 ) ypos = 0.0000;
	        if(penDown) {
	        	System.out.format("%s %.4f %.4f %.4f %.4f%n", color, xtemp, ytemp, xpos, ypos);
	        }
		}
	}
}