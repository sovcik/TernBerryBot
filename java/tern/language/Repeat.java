/*
 * @(#) Repeat.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Portions Copyright (C) 2015 Jozef Sovcik
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Modified by Mariam Hussien - Oct,2011
 */
package tern.language;

import java.util.*;
import tern.compiler.*;
import topcodes.TopCode;


public class Repeat extends PStatement {

	public static final int BEGIN = 171;
	public static final int END   = 179;

	public static int NEST = 0;
	public static int VAR = 0;

	public static Stack<ArrayList> st = new Stack();

	public Repeat(TopCode top) {
		super(top);
	}
	
	
	public static void register() {
		StatementFactory.registerStatementType(
			new Repeat(new TopCode(BEGIN)));
		StatementFactory.registerStatementType(
			new Repeat(new TopCode(END)));
	}


	public int getCode() {
		return top.getCode();
	}

	
	public String getName() {
		return ("REPEAT");
	}
	
	
	public Statement newInstance(TopCode top) {
		return new Repeat(top);
	}

	
	public void compile(Program program) throws CompileException {
		setDebugInfo(program);
		
		if (getCode() == BEGIN) {
			compileBeginRepeat(program);
		} else if (getCode() == END) {
			compileEndRepeat(program);
		}
	}
	
	
	protected void compileEndRepeat(Program program) throws CompileException {
		ArrayList<String> a;
		int i = 0;

		if (Repeat.NEST > 0) {
			a = st.pop();
			while (i < a.size()) {
				program.addInstruction(a.get(i));
				i++;
			}
		}

		Repeat.NEST--;

		program.codeOutdent();
		if (this.next != null) next.compile(program);

	}
	
	
	protected void compileBeginRepeat(Program program) throws CompileException {

		int i;

		// count depth of nested loops
		int nest = NEST;
		NEST++;

		ArrayList<String> a = new ArrayList();

		// each new loop is going to have unique label & counter
		String var = "count" + VAR;
		String loopLabel = "loop" + VAR;
		VAR++;

		if (next != null && next instanceof Num) {
			int param = ((Num)next).getValue();

			// initialize loop counter
			program.addInstruction("DATA32 " + var );
			program.addInstruction("MOVE32_32(0, " + var + ")");

			// add loop label
			program.addInstruction(loopLabel + ":");

			// and indent code following the label
			program.codeIndent();

			// push instructions which should be added at the end of loop (after processing instructions inside loop)
			a.add("ADD32(1, " + var + "," + var + ")");
			a.add("JR_LTE32(" + var + "," + param + "," + loopLabel + ")");
			st.push(a);

		}
		else if (next != null && next instanceof Sensor) {
			// add loop label
			program.addInstruction(loopLabel + ":");

			// and indent code following the label
			program.codeIndent();

			Sensor sen = (Sensor)next;
			a = sen.getTest(loopLabel);

			st.push(a);
		}
		else {
			// add loop label
			program.addInstruction(loopLabel + ":");

			// and indent code following the label
			program.codeIndent();

			// evaluate loop condition
			a.add("JR(" + loopLabel + ")");  // loop forever
			st.push(a);
		}

		// compile remaining symbols
		if (this.next != null) next.compile(program);

		// if symbol EndRepeat is missing, then do nothing
		if (NEST > nest) {
			program.addInstruction("// EndRepeat missing");
			a = st.pop();
			i = 0;
			while (i < a.size()) {
				program.addInstruction(a.get(i));
				i++;
			}
            program.codeOutdent();
			NEST--;
		}


	}
	
	
	public void toXML(java.io.PrintWriter out) {
		if (getCode() == BEGIN) {
			out.println("   <repeat />");
		} else if (getCode() == END) {
			out.println("   <end-repeat />");
		}
	}
}
