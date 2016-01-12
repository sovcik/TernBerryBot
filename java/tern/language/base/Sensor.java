/*
 * @(#) Sensor.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Portions Copyright (C) Jozef Sovcik
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


public abstract class Sensor extends tern.language.PStatement {
	
	public static final int PRESS   = 419;
	public static final int RELEASE = 425;
	public static final int LIGHT   = 453;
	public static final int DARK    = 465;
	public static final int OBJECT = 31;

	
	public Sensor(TopCode top) {
		super(top);
	}
	
	
	public int getCode() {
		return top.getCode();
	}
	
	
	public String getName() {
		switch (top.getCode()) {
		case PRESS:
			return "UNTIL-PRESS";
		case RELEASE:
			return "UNTIL-RELEASE";
		case LIGHT:
			return "UNTIL-LIGHT";
		case DARK:
			return "UNTIL-DARK";
		case OBJECT:
			return "UNTIL-OBJECT";
		default:
			return "SENSOR";
                    
		}
	}

	public void toXML(java.io.PrintWriter out) {
		switch (top.getCode()) {
		case PRESS:   out.println("   <until-push />"); break;
		case RELEASE: out.println("   <until-release />"); break;
		case LIGHT:   out.println("   <until-light />"); break;
		case DARK:    out.println("   <until-dark />"); break;
		case OBJECT:  out.println("   <until-object />"); break;
		default:
		}
	}
}
