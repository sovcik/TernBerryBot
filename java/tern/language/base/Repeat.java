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
package tern.language.base;

import topcodes.TopCode;


public abstract class Repeat extends tern.language.PStatement {

	public static final int BEGIN = 171;
	public static final int END   = 179;

	protected static int NEST = 0;
	protected static int VAR = 0;

	public Repeat(TopCode top) {
		super(top);
	}


	public void resetNesting(){
		NEST = 0;
		VAR = 0;
	}

	public String getName() {
		return ("REPEAT");
	}
	
	
	public void toXML(java.io.PrintWriter out) {
		if (getCode() == BEGIN) {
			out.println("   <repeat />");
		} else if (getCode() == END) {
			out.println("   <end-repeat />");
		}
	}
}
