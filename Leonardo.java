import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class run leonardo a program from an ArrayList
 * of Instructions.
 * @author Bergholm
 * 
 * (x+dcos(πv/180),y+dsin(πv/180))
 */
public class Leonardo {
	private double xpos;		// X-position
	private double ypos;		// Y-position
	private int v;			// The angle for leonardo
	private boolean penDown;	// True is leonardos pen is down
	private String color;	// The color leonardo paints
	
	public Leonardo() {
		//i = input;
		xpos = 0.0000;
		ypos = 0.0000;
		v = 0;
		color = "#0000FF";
		penDown = false;
	}
	
	/**
	 * Runs the program until the array is empty 
	 */
	public void run(ArrayList<Instruction> input) {
		for(Instruction i : input) {
			//System.out.println("Hej");
			//System.out.println(i.toString());
			switch(i.toString()) {
				case "Instr1": instr1(i); break;
				case "Instr2": instr2(i); break;
				case "Color": color(i); break;
				case "Rep1": rep1(i); break;
				case "Rep2": rep2(i); break;
				default: break;
			}
		}
	}

	private void rep2(Instruction input) {
		int rep = ((Rep2) input).getNum();
		ArrayList <Instruction> instrs = ((Rep2) input).getInstrs();
		for(int i = 0; i < rep; i++) {
			run(instrs);
		}
	}
	
	/**
	 * Runs a rep instruktion with only one instruction. 
	 * Therefore a rep with no "\""
	 */
	private void rep1(Instruction input) {
		int rep = ((Rep1) input).getNum();
		Instruction instr = ((Rep1)input).getInstr();
		for(int i = 0 ; i < rep; i++) {
			switch(instr.toString()) {
			case "Instr1": instr1(instr); break;
			case "Instr2": instr2(instr); break;
			case "Color": color(instr); break;
			case "Rep1": rep1(instr); break;
			case "Rep2": rep2(instr); break;
			default: break;
		}
		}
	}
	
	/**
	 * Changes the color.
	 */
	private void color(Instruction input) {
		color = ((Color) input).getColor();
		//System.out.println(color);
	}
	
	/**
	 * Takes the pen up or down.
	 */
	private void instr2(Instruction input) {
		if(input.getTokenData().equals("Down")) {
			//System.out.println("Down");
			penDown = true;
		} 
		else if(input.getTokenData().equals("Up")){
			//System.out.println("Up");
			penDown = false;
		}
	}
	
	/**new DecimalFormat("#0.0000").format()
	 * Changes Leonardos position.
	 */
	private void instr1(Instruction input) {
		if(input.getTokenData().equals("Left")) {
			v = v + ((Instr1) input).getNum();
		}
		else if(input.getTokenData().equals("Right")) {
			v = v - ((Instr1) input).getNum();
		}
		else if(input.getTokenData().equals("Forw")) {
			double xtemp = xpos;
			double ytemp = ypos;
			xpos = xpos + (((Instr1) input).getNum()) * Math.cos(Math.toRadians(v));		
	        ypos = ypos + (((Instr1) input).getNum()) * Math.sin(Math.toRadians(v));
	        if (penDown) {
		        System.out.println(color + " " + new DecimalFormat("#0.000000").format(xtemp) + " " +
		        				   new DecimalFormat("#0.000000").format(ytemp) + " " + 
		        				   new DecimalFormat("#0.000000").format(xpos) + " " + 
		        				   new DecimalFormat("#0.000000").format(ypos));
	        }
		}	
		else if(input.getTokenData().equals("Back")) {
			double xtemp = xpos;
			double ytemp = ypos;
			xpos = xpos - (((Instr1) input).getNum()) * Math.cos(Math.toRadians(v));		
	        ypos = ypos - (((Instr1) input).getNum()) * Math.sin(Math.toRadians(v));
	        if(penDown) {
		        System.out.println(color + " " + new DecimalFormat("#0.000000").format(xtemp) + " " +
	 				   new DecimalFormat("#0.000000").format(ytemp) + " " + 
	 				   new DecimalFormat("#0.000000").format(xpos) + " " + 
	 				   new DecimalFormat("#0.000000").format(ypos));
	        	}
	        }
	}
}