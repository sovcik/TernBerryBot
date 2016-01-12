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
package tern.language;

import tern.compiler.*;
import topcodes.TopCode;

import java.util.ArrayList;


public class Sensor extends PStatement {
	
	public static final int PRESS   = 419;
	public static final int RELEASE = 425;
	public static final int LIGHT   = 453;
	public static final int DARK    = 465;
	public static final int OBJECT = 31;

	
	public Sensor(TopCode top) {
		super(top);
	}
	
	
	public static void register() {
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(PRESS)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(RELEASE)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(LIGHT)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(DARK)));
		StatementFactory.registerStatementType(
			new Sensor(new TopCode(OBJECT)));
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
	
	
	public Statement newInstance(TopCode top) {
		return new Sensor(top);
	}
	
	
	public ArrayList<String> getTest(String label) {
		ArrayList<String> a = new ArrayList();

		// INPUT_READ(layer,port,sensor_type,sensor_mode,variable)
		switch (top.getCode()) {
		case PRESS:
			a.add("INPUT_READ(LAYER, PORT_TOUCH, SEN_TYPE_TOUCH, SEN_MODE_TOUCH_TOUCH, senTouch)");
			a.add("JR_EQ8(senTouch, 1, "+label+")");
			break;
		case RELEASE:
			a.add("INPUT_READ(LAYER, PORT_TOUCH, SEN_TYPE_TOUCH, SEN_MODE_TOUCH_TOUCH, senTouch)");
			a.add("JR_EQ8(senTouch, 0, "+label+")");
			break;
		case DARK:
			a.add("INPUT_READ(LAYER, PORT_LIGHT, SEN_TYPE_LIGHT, SEN_MODE_LIGHT_REFLECTED, senLight)");
			a.add("JR_GT8(senLight, 42, "+label+")");
			break;
		case LIGHT:
			a.add("INPUT_READ(LAYER, PORT_LIGHT, SEN_TYPE_LIGHT, SEN_MODE_LIGHT_REFLECTED, senLight)");
			a.add("JR_LTEQ8(senLight, 42, "+label+")");
			break;
		case OBJECT:
			a.add("INPUT_READ(LAYER, PORT_ULTRASONIC, SEN_TYPE_ULTRASONIC, SEN_MODE_ULTRASONIC_CM, senUltrasonic)");
			a.add("JR_LTEQ8(senUltrasonic, 15, "+label+")");
			break;
		default:
			a.add("JR("+label+")");
		}

		return a;
	}


	public void compile(Program program) throws CompileException {
		setDebugInfo(program);
		if (this.next != null) next.compile(program);
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
