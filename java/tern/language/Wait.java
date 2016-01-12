/*
 * @(#) Wait.java
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
 */
package tern.language;

import tern.compiler.*;
import topcodes.TopCode;

import java.util.ArrayList;


public class Wait extends PStatement {

	public static final int CODE = 611;
	public static int waitLoop = 0;
   
	public Wait(TopCode top) {
		super(top);
	}


	public static void register() {
      StatementFactory.registerStatementType(new Wait(new TopCode(CODE)));
	}


	public int getCode() {
		return Wait.CODE;
	}


	public String getName() {
		return ("WAIT");
	}

   
	public Statement newInstance(TopCode top) {
		return new Wait(top);
	}


	public void compile(Program program) throws CompileException {
		ArrayList<String> a;
		int i = 0;

		waitLoop++;
	   	setDebugInfo(program);

		// Wait for sensor if the next symbol is a sensor
	   	if (next != null && next instanceof Sensor) {
			program.addInstruction("wait_for_sensor_"+waitLoop+":");
			program.codeIndent(); // indent opcodes inside loop
			a = ((Sensor) next).getTest("wait_for_sensor_"+waitLoop);
			while (i < a.size()) {
				program.addInstruction(a.get(i));
				i++;
			}
			program.codeOutdent(); // end of loop
	   	}
		// Wait specific time if the next symbol is a number
	   	else if (next != null && next instanceof Num) {
		   	int d = ((Num)next).getValue() * 100;
		   	program.addInstruction("CALL(Wait," + d + ")");
	   	}



		if (this.next != null) next.compile(program);
	}


	public void toXML(java.io.PrintWriter out) {
		out.println("   <wait />");
	}
}
